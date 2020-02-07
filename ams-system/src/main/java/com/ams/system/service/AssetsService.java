package com.ams.system.service;

import com.ams.common.enums.AssetsAllocateStatus;
import com.ams.common.enums.AssetsStatus;
import com.ams.system.domain.Assets;
import com.ams.system.domain.AssetsAllocate;
import com.ams.system.domain.AssetsBorrow;
import com.ams.system.domain.AssetsMaintain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class AssetsService {

    @Autowired
    private IAssetsAccountingService accountingService;
    @Autowired
    private IAssetsAllocateService allocateService;
    @Autowired
    private IAssetsBorrowService borrowService;
    @Autowired
    private IAssetsMaintainService maintainService;

    /**
     * 领用事务（Transactional注解在class类上）
     *
     * @param allocateUserId
     * @param assetsNumbers
     * @return
     * @throws Exception
     */
    public int allocateAssets(int allocateUserId, String[] assetsNumbers) throws Exception {
        List<Assets> updateList = accountingService.getAssetsByNumbers(assetsNumbers);
        if (updateList == null) {
            return 0;
        }
        for (Assets update : updateList) {

            //更新 资产 状态为“审核中”
            update.setUseStatus(AssetsStatus.ALLOCATE.getCode());
        }

        List<AssetsAllocate> insertLists = new ArrayList<>();

        for (String number : assetsNumbers) {
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

    /**
     * 审批事务 （Transactional注解在class类上）
     *
     * @param auditorId
     * @param assetsNumber
     * @param allocateUserId
     * @param allocateExamineResult
     * @return
     * @throws Exception
     */
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

    /**
     * 借用事务（Transactional注解在class类上）
     *
     * @param borrowUserId
     * @param borrowData
     * @return
     * @throws Exception
     */
    public int borrowAssets(int borrowUserId, String createBy, AssetsBorrow borrowData) throws Exception {
        if (borrowData == null) {
            return 0;
        }
        String[] assetsNumbers = borrowData.getAssetsNumber().split(",");
        List<Assets> updateList = accountingService.getAssetsByNumbers(assetsNumbers);
        if (updateList == null || updateList.size() == 0) {
            return 0;
        }
        for (Assets update : updateList) {
            //更新 资产 状态为“已外借”
            update.setUseStatus(AssetsStatus.BORROWED.getCode());

        }
        List<AssetsBorrow> insertLists = new ArrayList<>();
        //当有多个资产编号的时候，每次更新资产编号
        for (Assets assets : updateList) {
            borrowData.setBorrowUserId(borrowUserId);
            borrowData.setCreateBy(createBy);
            borrowData.setAssetsNumber(assets.getAssetsNumber());
            insertLists.add(borrowData);
        }

        int updateRows = accountingService.updateAssetsLists(updateList);
        int insertRows = borrowService.insertBorrowList(insertLists);
        //成功才返回大于0的数
        if (updateRows == updateList.size() && insertRows == updateList.size()) {
            return updateRows + insertRows;
        }
        throw new Exception("申请借用失败");
    }

    /**
     * 保养事务（Transactional注解在class类上）
     *
     * @param maintainUserId
     * @param maintainData
     * @return
     * @throws Exception
     */
    public int maintainAssets(int maintainUserId, String createBy, AssetsMaintain maintainData) throws Exception {
        if (maintainData == null) {
            return 0;
        }
        String[] assetsNumbers = maintainData.getAssetsNumber().split(",");
        List<Assets> updateList = accountingService.getAssetsByNumbers(assetsNumbers);
        if (updateList == null || updateList.size() == 0) {
            return 0;
        }
        List<AssetsMaintain> insertLists = new ArrayList<>();
        //当有多个资产编号的时候，每次更新资产编号
        for (Assets assets : updateList) {
            maintainData.setMaintainUserId(maintainUserId);
            maintainData.setCreateBy(createBy);
            maintainData.setAssetsNumber(assets.getAssetsNumber());
            insertLists.add(maintainData);
        }

        int updateRows = accountingService.updateAssetsLists(updateList);
        int insertRows = maintainService.insertMaintainList(insertLists);
        //成功才返回大于0的数
        if (updateRows == updateList.size() && insertRows == updateList.size()) {
            return updateRows + insertRows;
        }
        throw new Exception("保养失败");
    }
}
