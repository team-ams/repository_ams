package com.ams.system.service;

import com.ams.common.enums.AssetsAllocateStatus;
import com.ams.common.enums.AssetsCheckStatus;
import com.ams.common.enums.AssetsStatus;
import com.ams.system.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
    @Autowired
    private IAssetsRepairService repairService;
    @Autowired
    private IAssetsAccidentService accidentService;
    @Autowired
    private IAssetsTransferService transferService;
    @Autowired
    private IAssetsCheckTaskService checkTaskService;
    @Autowired
    private IAssetsCheckItemService checkItemService;

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

    /**
     * 维修事务（Transactional注解在class类上）
     *
     * @param maintainUserId
     * @param repairData
     * @return
     * @throws Exception
     */
    public int repairAssets(int maintainUserId, String createBy, AssetsRepair repairData) throws Exception {
        if (repairData == null) {
            return 0;
        }
        String[] assetsNumbers = repairData.getAssetsNumber().split(",");
        List<Assets> updateList = accountingService.getAssetsByNumbers(assetsNumbers);
        if (updateList == null || updateList.size() == 0) {
            return 0;
        }
        List<AssetsRepair> insertLists = new ArrayList<>();
        //当有多个资产编号的时候，每次更新资产编号
        for (Assets assets : updateList) {
            repairData.setRepairUserId(maintainUserId);
            repairData.setCreateBy(createBy);
            repairData.setAssetsNumber(assets.getAssetsNumber());
            insertLists.add(repairData);
        }

        int insertRows = repairService.insertRepairList(insertLists);
        //成功才返回大于0的数
        if (insertRows == updateList.size()) {
            return insertRows;
        }
        throw new Exception("维修登记失败");
    }

    /**
     * 事故登记事务（Transactional注解在class类上）
     *
     * @param reportUserId
     * @param accidentData
     * @return
     * @throws Exception
     */
    public int accidentAssets(int reportUserId, String createBy, AssetsAccident accidentData) throws Exception {
        if (accidentData == null) {
            return 0;
        }
        String[] assetsNumbers = accidentData.getAssetsNumber().split(",");
        List<Assets> updateList = accountingService.getAssetsByNumbers(assetsNumbers);
        if (updateList == null || updateList.size() == 0) {
            return 0;
        }
        List<AssetsAccident> insertLists = new ArrayList<>();
        //当有多个资产编号的时候，每次更新资产编号
        for (Assets assets : updateList) {
            accidentData.setReportUserId(reportUserId);
            accidentData.setCreateBy(createBy);
            accidentData.setAssetsNumber(assets.getAssetsNumber());
            insertLists.add(accidentData);
        }
        int insertRows = accidentService.insertAccidentList(insertLists);
        //成功才返回大于0的数
        if (insertRows == updateList.size()) {
            return insertRows;
        }
        throw new Exception("事故登记失败");
    }

    /**
     * 转移事务（Transactional注解在class类上）
     *
     * @param transferUserId
     * @param transferData
     * @return
     * @throws Exception
     */
    public int transferAssets(int transferUserId, String createBy, AssetsTransfer transferData) throws Exception {
        if (transferData == null) {
            return 0;
        }
        String[] assetsNumbers = transferData.getAssetsNumber().split(",");
        List<Assets> updateList = accountingService.getAssetsByNumbers(assetsNumbers);
        if (updateList == null || updateList.size() == 0) {
            return 0;
        }

        for (Assets update : updateList) {
            //更新 资产 存放地点
            update.setStorageAddr(transferData.getPresentAddr());
        }
        List<AssetsTransfer> insertLists = new ArrayList<>();
        //当有多个资产编号的时候，每次更新资产编号
        for (Assets assets : updateList) {
            transferData.setTransferUserId(transferUserId);
            transferData.setCreateBy(createBy);
            transferData.setAssetsNumber(assets.getAssetsNumber());
            insertLists.add(transferData);
        }

        int updateRows = accountingService.updateAssetsLists(updateList);
        int insertRows = transferService.insertTransferList(insertLists);
        //成功才返回大于0的数
        if (updateRows == updateList.size() && insertRows == updateList.size()) {
            return updateRows + insertRows;
        }
        throw new Exception("转移失败");
    }

    /**
     * 新增盘点任务事务（Transactional注解在class类上）
     *
     * @param createBy
     * @param checkUserId
     * @param checkAddr
     * @return
     * @throws Exception
     */
    public int checkTaskAssets(String createBy, String checkUserId, String checkAddr) throws Exception {
        if (createBy == null || checkUserId == null || checkAddr == null) {
            return 0;
        }
        //盘点单号
        String checkNumber = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

        //根据存放地址查询得到的资产编号集合
        List<String> assetsNumberList = accountingService.getAssetsNumberListByStorageAddr(checkAddr);

        int insertCheckItemRows = 0;
        for (String assetsNumber : assetsNumberList) {
            AssetsCheckItem assetsCheckItem = new AssetsCheckItem();
            //盘点单号
            assetsCheckItem.setCheckNumber(checkNumber);
            //资产编号
            assetsCheckItem.setAssetsNumber(assetsNumber);
            assetsCheckItem.setCreateBy(createBy);
            int i = checkItemService.insertCheckItem(assetsCheckItem);
            if (i > 0) {
                insertCheckItemRows += 1;
            }
        }

        //盘点应盘数量
        int targetNum = assetsNumberList.size();
        AssetsCheckTask task = new AssetsCheckTask();
        //盘点单号
        task.setCheckNumber(checkNumber);
        //盘点地址
        task.setCheckAddr(checkAddr);
        //负责人
        task.setCheckUserId(Integer.parseInt(checkUserId));
        //应盘数量
        task.setTargetNum(targetNum);
        task.setCreateBy(createBy);

        int insertCheckTaskRows = checkTaskService.insertCheckTask(task);

        //成功才返回大于0的数
        if (insertCheckItemRows == assetsNumberList.size() && insertCheckTaskRows > 0) {
            return insertCheckItemRows + insertCheckTaskRows;
        }
        throw new Exception("添加盘点任务失败");
    }

    /**
     * 盘点完成
     *
     * @param taskId
     * @param checkResultList
     * @param updateBy
     * @return
     * @throws Exception
     */
    public int checkFinish(int taskId, List<String> checkResultList, String updateBy) throws Exception {
        if (checkResultList == null || checkResultList.size() == 0) {
            return 0;
        }
        //根据盘点任务id获取此次盘点任务
        AssetsCheckTask checkTask = checkTaskService.getCheckTaskByTaskId(taskId);
        if (checkTask == null) {
            return 0;
        }
        //获取该盘点任务应盘的 资产编号
        List<AssetsCheckItem> checkItems = checkTask.getCheckItems();
        String checkNumber = checkTask.getCheckNumber();
        if (checkItems.size() == 0 || checkNumber.equals("")) {
            return 0;
        }
        //获得 资产编号
        List<String> targetList = accountingService.getAssetsNumberListByStorageAddr(checkTask.getCheckAddr());
        //计算盘盈 通过差集（checkResultList - targetList）
        List<String> checkProfit = checkResultList.stream().filter(checkResult -> !targetList.contains(checkResult)).collect(Collectors.toList());
        int checkProfitCount = checkProfit.size();
        //盘盈项 入库做好记录
        for (String assetsNumber : checkProfit) {
            AssetsCheckItem checkItem = new AssetsCheckItem();
            checkItem.setCheckNumber(checkNumber);
            checkItem.setAssetsNumber(assetsNumber);
            //3  为盘盈标志
            checkItem.setCheckItemStatus("3");
            checkItemService.insertCheckItem(checkItem);
        }

        //计算盘亏 通过差集（targetList - checkResultList）
        List<String> checkLoss = targetList.stream().filter(target -> !checkResultList.contains(target)).collect(Collectors.toList());
        int checkLossCount = checkLoss.size();

        //计算盘到
        List<String> checked = targetList.stream().filter(target -> checkResultList.contains(target)).collect(Collectors.toList());
        //盘亏项 更新应盘状态为盘亏
        for (AssetsCheckItem checkItem : checkItems) {
            for (String assetsNumber : checkLoss) {
                //盘亏匹配
                if (checkItem.getAssetsNumber().equals(assetsNumber)) {
                    //2 为盘亏状态
                    checkItem.setCheckItemStatus("2");
                    checkItemService.updateCheckItemStatus(checkItem);
                    break;
                }
            }
            for (String assetsNumber : checked) {
                //盘到匹配
                if (checkItem.getAssetsNumber().equals(assetsNumber)) {
                    //1 为盘到
                    checkItem.setCheckItemStatus("1");
                    checkItemService.updateCheckItemStatus(checkItem);
                    break;
                }
            }
        }

        //更新盘点任务  根据盘点任务id更新  更新状态为 已盘点(1)、盘盈、盘亏、盘点后状态
        checkTask.setIsCheck("1");
        checkTask.setCheckProfit(checkProfitCount);
        checkTask.setCheckLoss(checkLossCount);
        checkTask.setCheckStatus(AssetsCheckStatus.APPROVALING.getCode());
        checkTask.setUpdateBy(updateBy);
        int updateCheckTaskRows = checkTaskService.updateCheckTask(checkTask);
        if (updateCheckTaskRows > 0) {
            return updateCheckTaskRows;
        }
        throw new Exception("盘点失败！");
    }

    /**
     * 盘点通过
     *
     * @param taskId
     * @return
     * @throws Exception
     */
    public int checkExamineOK(String taskId) throws Exception {
        AssetsCheckTask checkTask = checkTaskService.getCheckTaskByTaskId(Integer.parseInt(taskId));
        if (checkTask == null) {
            return 0;
        }

        //更新任务状态为通过
        checkTask.setCheckStatus(AssetsCheckStatus.AGREE.getCode());
        int updateCheckTaskRows = checkTaskService.updateCheckTask(checkTask);
        if (updateCheckTaskRows > 0) {
            return updateCheckTaskRows;
        }
        throw new Exception("审核失败！");
    }


    /**
     * 盘点驳回
     *
     * @param taskId
     * @return
     * @throws Exception
     */
    public int checkExamineReject(String taskId) throws Exception {
        AssetsCheckTask checkTask = checkTaskService.getCheckTaskByTaskId(Integer.parseInt(taskId));
        if (checkTask == null) {
            return 0;
        }

        //更新任务状态为驳回
        checkTask.setCheckStatus(AssetsCheckStatus.REJECT.getCode());
        int updateCheckTaskRows = checkTaskService.updateCheckTask(checkTask);
        if (updateCheckTaskRows > 0) {
            return updateCheckTaskRows;
        }
        throw new Exception("审核失败！");
    }
}
