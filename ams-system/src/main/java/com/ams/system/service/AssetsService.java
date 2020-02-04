package com.ams.system.service;

import com.ams.common.enums.AssetsAllocateStatus;
import com.ams.common.enums.AssetsStatus;
import com.ams.system.domain.Assets;
import com.ams.system.domain.AssetsAllocate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class AssetsService {

    @Autowired
    private IAssetsAccountingService accountingService;
    @Autowired
    private IAssetsAllocateService allocateService;

    @Transactional
    public int allocateAssets(int allocateUserId, String[] assetsNumbers) throws Exception {
        List<Assets> updateList = accountingService.getAssetsByNumbers(assetsNumbers);
        if (updateList == null) {
            return 0;
        }
        for (Assets update: updateList) {

            //更新 资产 状态为“审核中”
            update.setUseStatus(AssetsStatus.TOAPPROVAL.getCode());
        }

        List<AssetsAllocate> insertLists = new ArrayList<>();

        for (String number :assetsNumbers) {
            AssetsAllocate insert = new AssetsAllocate();
            insert.setAssetsNumber(number);
            insert.setUserId(allocateUserId);
            insertLists.add(insert);
        }

        int updateRows = accountingService.updateAssetsLists(updateList);
        int insertRows = allocateService.insertAllocateByList(insertLists);
        //成功才返回大于0的数
        if (updateRows > 0 && insertRows > 0) {
            return updateRows + insertRows;
        }
        throw new Exception("申请领用失败");
    }

    @Transactional
    public int allocateExamine(int auditorId, String assetsNumber, int allocateUserId, String allocateExamineResult) throws Exception {
        if (AssetsAllocateStatus.isExamineResult(allocateExamineResult)) {
            Assets assetsUpdate = accountingService.getAssetsByNumber(assetsNumber);
            AssetsAllocate allocateUpdate = allocateService.getAssetsAllocateBy(assetsNumber, allocateUserId);

            //审核用过
            if (AssetsAllocateStatus.AGREE.getCode().equals(allocateExamineResult)) {
                //更新资产领用状态为“审核通过”
                allocateUpdate.setStatus(AssetsAllocateStatus.AGREE.getCode());

                //更新资产状态为“已领用”
                assetsUpdate.setUseStatus(AssetsStatus.ALLOCATE.getCode());

            }
            if (AssetsAllocateStatus.REJECT.getCode().equals(allocateExamineResult)) {
                //更新资产领用状态为“审核被驳回“
                allocateUpdate.setStatus(AssetsAllocateStatus.REJECT.getCode());

                //更新资产状态为“正常”
                assetsUpdate.setUseStatus(AssetsStatus.NORMAL.getCode());
            }
            //增加处理人信息
            allocateUpdate.setAuditorId(auditorId);
            //更新资产领用表
            int updateAssetsAllocateRows = allocateService.updateAssetsAllocate(allocateUpdate);
            //更新资产表
            List<Assets> updateList = new ArrayList<>();
            updateList.add(assetsUpdate);
            int updateAssetsRows = accountingService.updateAssetsLists(updateList);
            //成功才返回大于0的数
            if (updateAssetsRows > 0 && updateAssetsAllocateRows > 0) {
                return updateAssetsRows + updateAssetsAllocateRows;
            }
            return 0;
        }
        throw new Exception("审批失败！");
    }
}
