package com.ams.system.service;

import com.ams.common.enums.AssetsExamineStatus;
import com.ams.common.enums.AssetsCheckStatus;
import com.ams.common.enums.AssetsStatus;
import com.ams.common.utils.StringUtils;
import com.ams.system.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.Size;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AssetsService {

    @Autowired
    private ISysUserService userService;
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
    @Autowired
    private IAssetsReturnService returnService;

    /**
     * 领用事务（Transactional注解在class类上）
     *
     * @param allocateUserId
     * @param assetsNumbers
     * @return 0：程序异常报错 -1：业务异常，设备状态异常 >0正常
     * @throws Exception
     */
    public int allocateAssets(int allocateUserId, String[] assetsNumbers) throws Exception {
        //生成领用单号LY****
        String allocateOrderNumber = "LY" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        SysUser sysUser = userService.selectUserById(Integer.toUnsignedLong(allocateUserId));
        List<Assets> updateList = accountingService.getAssetsByNumbers(assetsNumbers);
        if (updateList == null) {
            return 0;
        }
        for (Assets update : updateList) {
            //资产状态不是“闲置”则退出
            if (!update.getUseStatus().equals(AssetsStatus.NORMAL.getCode())) {
                return -1;
            }
            //更新 资产 状态为“审核中”
            update.setUseStatus(AssetsStatus.RESERVE.getCode());
        }

        List<AssetsAllocate> insertLists = new ArrayList<>();

        for (String number : assetsNumbers) {
            AssetsAllocate insert = new AssetsAllocate();
            insert.setAllocateOrderNum(allocateOrderNumber);
            insert.setAssetsNumber(number);
            insert.setUserId(allocateUserId);
            insert.setCreateBy(sysUser.getLoginName());
            insertLists.add(insert);
        }

        int updateRows = accountingService.updateAssetsLists(updateList);
        int insertRows = allocateService.insertAllocateByList(insertLists);
        //成功才返回大于0的数
        if (updateRows > 0 && insertRows > 0) {
            return updateRows + insertRows;
        }
        throw new RuntimeException("申请领用失败");
    }

    /**
     * 领用审批事务 （Transactional注解在class类上）
     *
     * @param auditorId
     * @param orderNum
     * @param allocateUserId
     * @param allocateExamineResult
     * @return
     * @throws Exception
     */
    public int allocateExamine(int auditorId, String orderNum, int allocateUserId, String allocateExamineResult) throws Exception {
        SysUser sysUser = userService.selectUserById(Integer.toUnsignedLong(allocateUserId));
        if (sysUser == null) {
            return 0;
        }
        String userName = sysUser.getUserName();
        if (AssetsExamineStatus.isExamineResult(allocateExamineResult)) {
            List<AssetsAllocate> allocateUpdateList = allocateService.getAssetsAllocateBy(orderNum);

            //更新资产表
            List<Assets> updateList = new ArrayList<>();
            //资产领用表更新数
            int updateAssetsAllocateRows = 0;

            //审核用过
            if (AssetsExamineStatus.AGREE.getCode().equals(allocateExamineResult)) {
                for (AssetsAllocate update : allocateUpdateList) {
                    //更新资产领用状态为“审核通过”
                    update.setStatus(AssetsExamineStatus.AGREE.getCode());
                    //根据资产编号查找到资产并更新状态
                    String assetsNumber = update.getAssetsNumber();
                    Assets assetsUpdate = accountingService.getAssetsByNumber(assetsNumber);
                    //更新资产状态为“在用”
                    assetsUpdate.setUseStatus(AssetsStatus.USING.getCode());
                    //更新资产的使用人为领用人
                    assetsUpdate.setUser(userName);
                    //加入待更新列表
                    updateList.add(assetsUpdate);
                    //增加处理人信息
                    update.setAuditorId(auditorId);
                    //更新资产领用表
                    updateAssetsAllocateRows = allocateService.updateAssetsAllocate(update);
                }
            }
            if (AssetsExamineStatus.REJECT.getCode().equals(allocateExamineResult)) {
                for (AssetsAllocate update : allocateUpdateList) {
                    //更新资产领用状态为“审核被驳回“
                    update.setStatus(AssetsExamineStatus.REJECT.getCode());
                    //根据资产编号查找到资产并更新状态
                    String assetsNumber = update.getAssetsNumber();
                    Assets assetsUpdate = accountingService.getAssetsByNumber(assetsNumber);
                    //更新资产状态为“闲置”
                    assetsUpdate.setUseStatus(AssetsStatus.NORMAL.getCode());
                    //加入待更新列表
                    updateList.add(assetsUpdate);
                    //增加处理人信息
                    update.setAuditorId(auditorId);
                    //更新资产领用表
                    updateAssetsAllocateRows = allocateService.updateAssetsAllocate(update);
                }
            }
            int updateAssetsRows = accountingService.updateAssetsLists(updateList);
            //成功才返回大于0的数
            if (updateAssetsRows > 0 && updateAssetsAllocateRows > 0) {
                return updateAssetsRows + updateAssetsAllocateRows;
            }
            return 0;
        }
        throw new RuntimeException("审批失败！");
    }

    /**
     * 借用事务（Transactional注解在class类上）
     *
     * @param borrowUserId
     * @param borrowData
     * @return 0：程序异常报错 -1：业务异常，设备状态异常 >0正常
     * @throws Exception
     */
    public int borrowAssets(int borrowUserId, String createBy, AssetsBorrow borrowData) throws Exception {
        //生成借用单号JY****
        String borrowOrderNumber = "JY" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        if (borrowData == null) {
            return 0;
        }
        String[] assetsNumbers = borrowData.getAssetsNumber().split(",");
        List<Assets> updateList = accountingService.getAssetsByNumbers(assetsNumbers);
        if (updateList == null || updateList.size() == 0) {
            return 0;
        }

        for (Assets update : updateList) {
            //资产状态不是“闲置”则退出
            if (!update.getUseStatus().equals(AssetsStatus.NORMAL.getCode())) {
                return -1;
            }
            //更新 资产 状态为“审核中”
            update.setUseStatus(AssetsStatus.RESERVE.getCode());
        }
        List<AssetsBorrow> insertLists = new ArrayList<>();
        //当有多个资产编号的时候，每次更新资产编号
        for (Assets assets : updateList) {
            AssetsBorrow borrow = new AssetsBorrow();
            borrow.setBorrowOrderNum(borrowOrderNumber);
            borrow.setBorrowUserId(borrowUserId);
            borrow.setBorrowTime(borrowData.getBorrowTime());
            borrow.setReturnTime(borrowData.getReturnTime());
            borrow.setRemark(borrowData.getRemark());
            borrow.setCreateBy(createBy);
            borrow.setAssetsNumber(assets.getAssetsNumber());
            insertLists.add(borrow);
        }

        int updateRows = accountingService.updateAssetsLists(updateList);
        int insertRows = borrowService.insertBorrowList(insertLists);
        //成功才返回大于0的数
        if (updateRows > 0 && insertRows > 0) {
            return updateRows + insertRows;
        }
        throw new RuntimeException("申请借用失败");
    }

    /**
     * 借用审批事务 （Transactional注解在class类上）
     *
     * @param auditorId
     * @param orderNum
     * @param userId
     * @param examineResult
     * @return
     * @throws Exception
     */
    public int borrowExamine(int auditorId, String orderNum, int userId, String examineResult) throws Exception {
        SysUser sysUser = userService.selectUserById(Integer.toUnsignedLong(userId));
        if (sysUser == null) {
            return 0;
        }
        String userName = sysUser.getUserName();
        if (AssetsExamineStatus.isExamineResult(examineResult)) {
            //根据订单号查找
            List<AssetsBorrow> borrowUpdateList = borrowService.getBorrowListByOrderNum(orderNum);

            //更新资产表
            List<Assets> updateList = new ArrayList<>();
            //资产领用表更新数
            int updateAssetsBorrowRows = 0;

            //审核用过
            if (AssetsExamineStatus.AGREE.getCode().equals(examineResult)) {
                for (AssetsBorrow update : borrowUpdateList) {
                    //更新资产领用状态为“审核通过”
                    update.setStatus(AssetsExamineStatus.AGREE.getCode());
                    //根据资产编号查找到资产并更新状态
                    String assetsNumber = update.getAssetsNumber();
                    Assets assetsUpdate = accountingService.getAssetsByNumber(assetsNumber);
                    //更新资产状态为“在用”
                    assetsUpdate.setUseStatus(AssetsStatus.USING.getCode());
                    //更新资产的使用人为领用人
                    assetsUpdate.setUser(userName);
                    //加入待更新列表
                    updateList.add(assetsUpdate);
                    //增加处理人信息
                    update.setAuditorId(auditorId);
                    //更新资产领用表
                    updateAssetsBorrowRows = borrowService.updateBorrowInfo(update);
                }
            }
            if (AssetsExamineStatus.REJECT.getCode().equals(examineResult)) {
                for (AssetsBorrow update : borrowUpdateList) {
                    //更新资产领用状态为“审核被驳回“
                    update.setStatus(AssetsExamineStatus.REJECT.getCode());
                    //根据资产编号查找到资产并更新状态
                    String assetsNumber = update.getAssetsNumber();
                    Assets assetsUpdate = accountingService.getAssetsByNumber(assetsNumber);
                    //更新资产状态为“闲置”
                    assetsUpdate.setUseStatus(AssetsStatus.NORMAL.getCode());
                    //加入待更新列表
                    updateList.add(assetsUpdate);
                    //增加处理人信息
                    update.setAuditorId(auditorId);
                    //更新资产领用表
                    updateAssetsBorrowRows = borrowService.updateBorrowInfo(update);
                }
            }
            int updateAssetsRows = accountingService.updateAssetsLists(updateList);
            //成功才返回大于0的数
            if (updateAssetsRows > 0 && updateAssetsBorrowRows > 0) {
                return updateAssetsRows + updateAssetsBorrowRows;
            }
            return 0;
        }
        throw new RuntimeException("审批失败！");
    }

    /**
     * 删除借用单事务（同时更新资产状态)
     *
     * @param orderNum
     * @return
     * @throws Exception
     */
    public int borrowDelete(String orderNum) throws Exception {

        if (StringUtils.isEmpty(orderNum)) {
            return 0;
        }
        List<AssetsBorrow> borrowListByOrderNum = borrowService.getBorrowListByOrderNum(orderNum);
        if (borrowListByOrderNum.size() == 0) {
            return 0;
        }
        List<Assets> updateList = new ArrayList<>();
        for (AssetsBorrow borrow : borrowListByOrderNum) {
            Assets assetsByNumber = accountingService.getAssetsByNumber(borrow.getAssetsNumber());
            //将资产状态更新为“闲置”
            assetsByNumber.setUseStatus(AssetsStatus.NORMAL.getCode());
            updateList.add(assetsByNumber);
        }
        if (updateList.size() == 0) {
            return 0;
        }
        //批量更新资产状态
        int updateAssetsRows = accountingService.updateAssetsLists(updateList);
        //删除对应借用单号下的借用信息（可批量）
        int deleteBorrowRows = borrowService.deleteBorrowByOrderNum(orderNum);
        //成功才返回大于0的数
        if (updateAssetsRows > 0 && deleteBorrowRows > 0) {
            return updateAssetsRows + deleteBorrowRows;
        }
        throw new RuntimeException("删除失败！");
    }

    /**
     * 归还事务（Transactional注解在class类上）
     *
     * @param returnUserId
     * @param returnData
     * @return
     * @throws Exception
     */
    public int returnAssets(int returnUserId, String createBy, AssetsReturn returnData) throws Exception {
        //生成归还单号GH****
        String returnOrderNumber = "GH" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        if (returnData == null) {
            return 0;
        }
        String[] assetsNumbers = returnData.getAssetsNumber().split(",");
        List<Assets> updateList = accountingService.getAssetsByNumbers(assetsNumbers);
        if (updateList == null || updateList.size() == 0) {
            return 0;
        }
        for (Assets update : updateList) {
            //更新 资产 状态为“审核中”
            update.setUseStatus(AssetsStatus.RESERVE.getCode());
        }
        List<AssetsReturn> insertLists = new ArrayList<>();
        int updateBorrowRows = 0;
        //当有多个资产编号的时候，每次更新资产编号
        for (Assets assets : updateList) {
            AssetsReturn assetsReturn = new AssetsReturn();
            AssetsBorrow borrowUpdate = borrowService.getBorrowByAssetsNumberAndIsNotReturn(assets.getAssetsNumber());
            //更新借用表的归还信息---->1为归还审批中
            borrowUpdate.setIsReturn("1");
            //更新借用表的是否归还信息 ---->  归还审批中(目的是点击归还按钮后，该借用信息暂时不显示，归还审核驳回则变回未归还状态)
            updateBorrowRows = borrowService.updateBorrowInfo(borrowUpdate);
            assetsReturn.setReturnOrderNum(returnOrderNumber);
            assetsReturn.setReturnUserId(returnUserId);
            assetsReturn.setCreateBy(createBy);
            assetsReturn.setAssetsNumber(assets.getAssetsNumber());
            insertLists.add(assetsReturn);
        }

        int updateRows = accountingService.updateAssetsLists(updateList);
        int insertRows = returnService.insertReturnList(insertLists);
        //成功才返回大于0的数
        if (updateBorrowRows > 0 && updateRows > 0) {
            return updateBorrowRows + insertRows + updateRows;
        }
        throw new RuntimeException("申请归还失败");
    }

    /**
     * 归还审批事务 （Transactional注解在class类上）
     *
     * @param auditorId
     * @param orderNum
     * @param userId
     * @param examineResult
     * @return
     * @throws Exception
     */
    public int returnExamine(int auditorId, String orderNum, int userId, String examineResult) throws Exception {
        SysUser sysUser = userService.selectUserById(Integer.toUnsignedLong(userId));
        if (sysUser == null) {
            return 0;
        }
        String userName = sysUser.getUserName();
        if (AssetsExamineStatus.isExamineResult(examineResult)) {
            //根据订单号查找
            List<AssetsReturn> returnUpdateList = returnService.getReturnListByOrderNum(orderNum);

            //更新资产表
            List<Assets> updateList = new ArrayList<>();
            //资产领用表更新数
            int updateAssetsReturnRows = 0;
            int updateBorrowRows = 0;

            //审核用过
            if (AssetsExamineStatus.AGREE.getCode().equals(examineResult)) {
                for (AssetsReturn update : returnUpdateList) {
                    //更新资产领用状态为“审核通过”
                    update.setStatus(AssetsExamineStatus.AGREE.getCode());
                    //根据资产编号查找到资产并更新状态
                    String assetsNumber = update.getAssetsNumber();
                    Assets assetsUpdate = accountingService.getAssetsByNumber(assetsNumber);
                    AssetsBorrow borrowUpdate = borrowService.getBorrowByAssetsNumberAndIsNotReturn(assetsNumber);
                    //更新借用表的归还信息---->2为已归还
                    borrowUpdate.setIsReturn("2");
                    //更新资产状态为“闲置”
                    assetsUpdate.setUseStatus(AssetsStatus.NORMAL.getCode());
                    //更新资产的使用人为领用人
                    assetsUpdate.setUser("");
                    //加入待更新列表
                    updateList.add(assetsUpdate);
                    //增加处理人信息
                    update.setAuditorId(String.valueOf(auditorId));
                    //更新资产归还表
                    updateAssetsReturnRows = returnService.updateReturnInfo(update);
                    //更新借用表的是否归还信息 ---->  已归还
                    updateBorrowRows = borrowService.updateBorrowInfo(borrowUpdate);
                }
                //归还审批通过同步资产信息
                int updateAssetsRows = accountingService.updateAssetsLists(updateList);
                //成功才返回大于0的数
                if (updateAssetsRows > 0 && updateAssetsReturnRows > 0 && updateBorrowRows > 0) {
                    return updateAssetsRows + updateAssetsReturnRows + updateBorrowRows;
                }
            }
            if (AssetsExamineStatus.REJECT.getCode().equals(examineResult)) {
                for (AssetsReturn update : returnUpdateList) {
                    //更新资产领用状态为“审核被驳回“
                    update.setStatus(AssetsExamineStatus.REJECT.getCode());
                    //增加处理人信息
                    update.setAuditorId(String.valueOf(auditorId));
                    //更新资产归还表
                    updateAssetsReturnRows = returnService.updateReturnInfo(update);
                    Assets assetsUpdate = accountingService.getAssetsByNumber(update.getAssetsNumber());
                    AssetsBorrow borrowUpdate = borrowService.getBorrowByAssetsNumberAndIsNotReturn(update.getAssetsNumber());
                    //更新借用表的归还信息---->3为 归还被驳回
                    borrowUpdate.setIsReturn("3");
                    //更新借用表的是否归还信息 ---->  未归还 驳回则是归还失败
                    updateBorrowRows = borrowService.updateBorrowInfo(borrowUpdate);
                    //更新资产状态为“在用”
                    assetsUpdate.setUseStatus(AssetsStatus.USING.getCode());
                    //加入待更新列表
                    updateList.add(assetsUpdate);
                }
                int updateLows = accountingService.updateAssetsLists(updateList);

                //成功才返回大于0的数  归还审批驳回不更新资产信息
                if (updateBorrowRows > 0 && updateAssetsReturnRows > 0 && updateLows > 0) {
                    return updateBorrowRows + updateAssetsReturnRows + updateLows;
                }
            }
            return 0;
        }
        throw new RuntimeException("审批失败！");
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
        //生成保养单号BY****
        String maintainOrderNumber = "BY" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

        if (maintainData == null) {
            return 0;
        }
        String[] assetsNumbers = maintainData.getAssetsNumber().split(",");
        List<Assets> updateList = accountingService.getAssetsByNumbers(assetsNumbers);
        if (updateList == null || updateList.size() == 0) {
            return 0;
        }
        for (Assets update : updateList) {
            //更新 资产 状态为“审核中”
            update.setUseStatus(AssetsStatus.RESERVE.getCode());
        }
        List<AssetsMaintain> insertLists = new ArrayList<>();
        //当有多个资产编号的时候，每次更新资产编号
        for (Assets assets : updateList) {
            AssetsMaintain assetsMaintain = new AssetsMaintain();
            assetsMaintain.setMaintainOrderNum(maintainOrderNumber);
            assetsMaintain.setMaintainUserId(maintainUserId);
            assetsMaintain.setCreateBy(createBy);
            assetsMaintain.setAssetsNumber(assets.getAssetsNumber());
            insertLists.add(assetsMaintain);
        }

        int updateRows = accountingService.updateAssetsLists(updateList);
        int insertRows = maintainService.insertMaintainList(insertLists);
        //成功才返回大于0的数
        if (updateRows > 0 && insertRows > 0) {
            return updateRows + insertRows;
        }
        throw new RuntimeException("保养失败");
    }

    /**
     * 保养审批事务 （Transactional注解在class类上）
     *
     * @param auditorId
     * @param orderNum
     * @param userId
     * @param examineResult
     * @return
     * @throws Exception
     */
    public int maintainExamine(int auditorId, String orderNum, int userId, String examineResult) throws Exception {
        SysUser sysUser = userService.selectUserById(Integer.toUnsignedLong(userId));
        if (sysUser == null) {
            return 0;
        }
        String userName = sysUser.getUserName();
        if (AssetsExamineStatus.isExamineResult(examineResult)) {
            //根据订单号查找
            List<AssetsMaintain> maintainUpdateList = maintainService.getMaintainListByOrderNum(orderNum);

            //资产保养表更新数
            int updateAssetsMaintainRows = 0;
            List<Assets> updateAssetsLists = new ArrayList<>();
            //审核用过
            if (AssetsExamineStatus.AGREE.getCode().equals(examineResult)) {
                for (AssetsMaintain update : maintainUpdateList) {
                    String assetsNumber = update.getAssetsNumber();
                    Assets asset = accountingService.getAssetsByNumber(assetsNumber);
                    //获取保养时间
                    String maintainTime = update.getMaintainTime();
                    //更新资产的上次保养时间
                    asset.setMaintainDate(maintainTime);
                    updateAssetsLists.add(asset);

                    //更新资产保养状态为“审核通过”
                    update.setStatus(AssetsExamineStatus.AGREE.getCode());
                    //增加处理人信息
                    update.setAuditorId(String.valueOf(auditorId));
                    //更新资产保养表
                    updateAssetsMaintainRows = maintainService.updateMaintainInfo(update);
                }
            }
            if (AssetsExamineStatus.REJECT.getCode().equals(examineResult)) {
                for (AssetsMaintain update : maintainUpdateList) {
                    //更新资产领用状态为“审核被驳回“
                    update.setStatus(AssetsExamineStatus.REJECT.getCode());
                    //增加处理人信息
                    update.setAuditorId(String.valueOf(auditorId));
                    //更新资产领用表
                    updateAssetsMaintainRows = maintainService.updateMaintainInfo(update);
                }
            }
            int assetsUpdateRows = accountingService.updateAssetsLists(updateAssetsLists);
            //成功才返回大于0的数
            if (updateAssetsMaintainRows > 0 && assetsUpdateRows > 0 && assetsUpdateRows == maintainUpdateList.size()) {
                return updateAssetsMaintainRows + assetsUpdateRows;
            }
            return 0;
        }
        throw new RuntimeException("审批失败！");
    }

    /**
     * 维修事务（Transactional注解在class类上）
     *
     * @param repairUserId
     * @param repairData
     * @return
     * @throws Exception
     */
    public int repairAssets(int repairUserId, String createBy, AssetsRepair repairData) throws Exception {
        //生成事故单号WX****
        String repairOrderNumber = "WX" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        if (repairData == null) {
            return 0;
        }
        String[] assetsNumbers = repairData.getAssetsNumber().split(",");
        List<Assets> updateList = accountingService.getAssetsByNumbers(assetsNumbers);
        if (updateList == null || updateList.size() == 0) {
            return 0;
        }
        for (Assets update : updateList) {
            //更新 资产 状态为“审核中”
            update.setUseStatus(AssetsStatus.RESERVE.getCode());
        }
        List<AssetsRepair> insertLists = new ArrayList<>();
        //当有多个资产编号的时候，每次更新资产编号
        for (Assets assets : updateList) {
            AssetsRepair repair = new AssetsRepair();
            repair = repairData;
            repair.setRepairOrderNum(repairOrderNumber);
            repair.setRepairUserId(repairUserId);
            repair.setAssetsNumber(assets.getAssetsNumber());

            insertLists.add(repair);

            //更新资产状态为"维修中"
            assets.setUseStatus(AssetsStatus.MAINTENANCING.getCode());


        }
        int updateAssetsRows = accountingService.updateAssetsLists(updateList);
        int insertRows = repairService.insertRepairList(insertLists);
        //成功才返回大于0的数
        if (insertRows > 0 && updateAssetsRows > 0) {
            return insertRows + updateAssetsRows;
        }
        throw new RuntimeException("维修登记失败");
    }

    /**
     * 删除维修单事务（同时更新资产状态)
     *
     * @param orderNum
     * @return
     * @throws Exception
     */
    public int repairDelete(String orderNum) throws Exception {

        if (StringUtils.isEmpty(orderNum)) {
            return 0;
        }
        List<AssetsRepair> repairListByOrderNum = repairService.getRepairListByOrderNum(orderNum);
        if (repairListByOrderNum.size() == 0) {
            return 0;
        }
        List<Assets> updateList = new ArrayList<>();
        for (AssetsRepair repair : repairListByOrderNum) {
            Assets assetsByNumber = accountingService.getAssetsByNumber(repair.getAssetsNumber());
            //将资产状态更新为“闲置”
            assetsByNumber.setUseStatus(AssetsStatus.NORMAL.getCode());
            updateList.add(assetsByNumber);
        }
        if (updateList.size() == 0) {
            return 0;
        }
        //批量更新资产状态
        int updateAssetsRows = accountingService.updateAssetsLists(updateList);
        //删除对应借用单号下的借用信息（可批量）
        int deleteRepairRows = repairService.deleteRepairByOrderNum(orderNum);
        //成功才返回大于0的数
        if (updateAssetsRows > 0 && deleteRepairRows > 0) {
            return updateAssetsRows + deleteRepairRows;
        }
        throw new RuntimeException("删除失败！");
    }

    /**
     * 维修审批事务 （Transactional注解在class类上）
     *
     * @param auditorId
     * @param orderNum
     * @param userId
     * @param examineResult
     * @return
     * @throws Exception
     */
    public int repairExamine(int auditorId, String orderNum, int userId, String examineResult) throws Exception {
        SysUser sysUser = userService.selectUserById(Integer.toUnsignedLong(userId));
        if (sysUser == null) {
            return 0;
        }
        String userName = sysUser.getUserName();
        if (AssetsExamineStatus.isExamineResult(examineResult)) {
            //根据订单号查找
            List<AssetsRepair> repairUpdateList = repairService.getRepairListByOrderNum(orderNum);

            //更新资产表
            List<Assets> updateList = new ArrayList<>();
            //资产领用表更新数
            int updateAssetsRepairRows = 0;

            //审核通过
            if (AssetsExamineStatus.AGREE.getCode().equals(examineResult)) {
                for (AssetsRepair update : repairUpdateList) {
                    //更新资产维修状态为“审核通过”
                    update.setStatus(AssetsExamineStatus.AGREE.getCode());
                    //根据资产编号查找到资产并更新状态
                    String assetsNumber = update.getAssetsNumber();
                    Assets assetsUpdate = accountingService.getAssetsByNumber(assetsNumber);
                    //根据修复状态更改资产状态（1：正常 2：待报废 3：故障）
                    if (update.getRepairStatus().equals("3")) {
                        //故障
                        assetsUpdate.setUseStatus(AssetsStatus.DISABLE.getCode());
                    } else if (update.getRepairStatus().equals("2")) {
                        //待报废
                        assetsUpdate.setUseStatus(AssetsStatus.TOSCRAPPED.getCode());
                    } else if (update.getRepairStatus().equals("1")) {
                        //正常---闲置
                        assetsUpdate.setUseStatus(AssetsStatus.NORMAL.getCode());
                    }

                    //加入待更新列表
                    updateList.add(assetsUpdate);
                    //增加处理人信息
                    update.setAuditorId(String.valueOf(auditorId));
                    //更新资产维修表
                    updateAssetsRepairRows = repairService.updateRepairInfo(update);
                }
            }
            if (AssetsExamineStatus.REJECT.getCode().equals(examineResult)) {
                for (AssetsRepair update : repairUpdateList) {
                    //更新资产维修状态为“审核被驳回“
                    update.setStatus(AssetsExamineStatus.REJECT.getCode());
                    //增加处理人信息
                    update.setAuditorId(String.valueOf(auditorId));
                    //更新资产维修表
                    updateAssetsRepairRows = repairService.updateRepairInfo(update);
                    //根据资产编号查找到资产并更新状态
                    String assetsNumber = update.getAssetsNumber();
                    Assets assetsUpdate = accountingService.getAssetsByNumber(assetsNumber);
                    assetsUpdate.setUseStatus(AssetsStatus.NORMAL.getCode());
                    //加入待更新列表
                    updateList.add(assetsUpdate);
                }
            }
            int updateAssetsRows = accountingService.updateAssetsLists(updateList);
            //成功才返回大于0的数
            if (updateAssetsRows > 0 && updateAssetsRepairRows > 0) {
                return updateAssetsRows + updateAssetsRepairRows;
            }
            return 0;
        }
        throw new RuntimeException("审批失败！");
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
        //生成事故单号SG****
        String accidentOrderNumber = "SG" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        if (accidentData == null) {
            return 0;
        }
        String[] assetsNumbers = accidentData.getAssetsNumber().split(",");
        List<Assets> updateList = accountingService.getAssetsByNumbers(assetsNumbers);
        if (updateList == null || updateList.size() == 0) {
            return 0;
        }
        for (Assets update : updateList) {
            //更新 资产 状态为“审核中”
            update.setUseStatus(AssetsStatus.RESERVE.getCode());
        }
        List<AssetsAccident> insertLists = new ArrayList<>();
        //当有多个资产编号的时候，每次更新资产编号
        for (Assets assets : updateList) {
            AssetsAccident assetsAccident = new AssetsAccident();
            assetsAccident.setAccidentOrderNum(accidentOrderNumber);
            assetsAccident.setReportUserId(reportUserId);
            assetsAccident.setCreateBy(createBy);
            assetsAccident.setAssetsNumber(assets.getAssetsNumber());

            insertLists.add(assetsAccident);
        }
        int updateAssetsRows = accountingService.updateAssetsLists(updateList);
        int insertRows = accidentService.insertAccidentList(insertLists);
        //成功才返回大于0的数
        if (updateAssetsRows > 0 && insertRows > 0) {
            return insertRows + updateAssetsRows;
        }
        throw new RuntimeException("事故登记失败");
    }


    /**
     * 事故审批事务 （Transactional注解在class类上）
     *
     * @param auditorId
     * @param orderNum
     * @param userId
     * @param examineResult
     * @return
     * @throws Exception
     */
    public int accidentExamine(int auditorId, String orderNum, int userId, String examineResult) throws Exception {
        SysUser sysUser = userService.selectUserById(Integer.toUnsignedLong(auditorId));
        if (sysUser == null) {
            return 0;
        }
        String userName = sysUser.getUserName();
        if (AssetsExamineStatus.isExamineResult(examineResult)) {
            //根据订单号查找
            List<AssetsAccident> accidentUpdateList = accidentService.getAccidentListByOrderNum(orderNum);

            //更新资产表
            List<Assets> updateList = new ArrayList<>();
            //资产领用表更新数
            int updateAssetsAccidentRows = 0;

            //审核通过
            if (AssetsExamineStatus.AGREE.getCode().equals(examineResult)) {
                for (AssetsAccident update : accidentUpdateList) {
                    //更新资产领用状态为“审核通过”
                    update.setStatus(AssetsExamineStatus.AGREE.getCode());
                    //根据资产编号查找到资产并更新状态
                    String assetsNumber = update.getAssetsNumber();
                    Assets assetsUpdate = accountingService.getAssetsByNumber(assetsNumber);
                    //更新资产状态为“待报废”
                    assetsUpdate.setUseStatus(AssetsStatus.TOSCRAPPED.getCode());
                    //清空资产的使用人
                    assetsUpdate.setUser("");
                    assetsUpdate.setUpdateBy(userName);
                    //加入待更新列表
                    updateList.add(assetsUpdate);
                    //增加处理人信息
                    update.setAuditorId(String.valueOf(auditorId));
                    //更新资产事故表
                    updateAssetsAccidentRows = accidentService.updateAccidentInfo(update);
                }
            }
            if (AssetsExamineStatus.REJECT.getCode().equals(examineResult)) {
                for (AssetsAccident update : accidentUpdateList) {
                    //更新资产领用状态为“审核被驳回“
                    update.setStatus(AssetsExamineStatus.REJECT.getCode());
                    //增加处理人信息
                    update.setAuditorId(String.valueOf(auditorId));
                    //更新资产领用表
                    updateAssetsAccidentRows = accidentService.updateAccidentInfo(update);
                    //根据资产编号查找到资产并更新状态
                    String assetsNumber = update.getAssetsNumber();
                    Assets assetsUpdate = accountingService.getAssetsByNumber(assetsNumber);
                    //闲置状态
                    assetsUpdate.setUseStatus(AssetsStatus.NORMAL.getCode());
                    //加入待更新列表
                    updateList.add(assetsUpdate);
                }
            }
            int updateAssetsRows = accountingService.updateAssetsLists(updateList);
            //成功才返回大于0的数
            if (updateAssetsRows > 0 && updateAssetsAccidentRows > 0) {
                return updateAssetsRows + updateAssetsAccidentRows;
            }
            return 0;
        }
        throw new RuntimeException("审批失败！");
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
        //生成转移单号ZY****
        String transferOrderNumber = "ZY" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

        if (transferData == null) {
            return 0;
        }
        String[] assetsNumbers = transferData.getAssetsNumber().split(",");
        List<Assets> updateList = accountingService.getAssetsByNumbers(assetsNumbers);
        if (updateList == null || updateList.size() == 0) {
            return 0;
        }
        for (Assets update : updateList) {
            //更新 资产 状态为“审核中”
            update.setUseStatus(AssetsStatus.RESERVE.getCode());
        }

        List<AssetsTransfer> insertLists = new ArrayList<>();
        //当有多个资产编号的时候，每次更新资产编号
        for (Assets assets : updateList) {
            AssetsTransfer transfer = new AssetsTransfer();
            transfer.setTransferOrderNum(transferOrderNumber);
            transfer.setTransferUserId(transferUserId);
            transfer.setCreateBy(createBy);
            transfer.setAssetsNumber(assets.getAssetsNumber());

            insertLists.add(transferData);
        }

        int updateAssetsRows = accountingService.updateAssetsLists(updateList);
        int insertRows = transferService.insertTransferList(insertLists);
        //成功才返回大于0的数
        if (updateAssetsRows > 0 && insertRows > 0) {
            return insertRows + updateAssetsRows;
        }
        throw new RuntimeException("转移失败");
    }

    /**
     * 转移审批事务 （Transactional注解在class类上）
     *
     * @param auditorId
     * @param orderNum
     * @param userId
     * @param examineResult
     * @return
     * @throws Exception
     */
    public int transferExamine(int auditorId, String orderNum, int userId, String examineResult) throws Exception {
        SysUser sysUser = userService.selectUserById(Integer.toUnsignedLong(userId));
        if (sysUser == null) {
            return 0;
        }
        String userName = sysUser.getUserName();
        if (AssetsExamineStatus.isExamineResult(examineResult)) {
            //根据订单号查找
            List<AssetsTransfer> transferUpdateList = transferService.getTransferListByOrderNum(orderNum);

            //更新资产表
            List<Assets> updateList = new ArrayList<>();
            //资产领用表更新数
            int updateAssetsTransferRows = 0;

            //审核用过
            if (AssetsExamineStatus.AGREE.getCode().equals(examineResult)) {
                for (AssetsTransfer update : transferUpdateList) {
                    //更新资产领用状态为“审核通过”
                    update.setStatus(AssetsExamineStatus.AGREE.getCode());

                    //根据资产编号查找到资产并更新存放地址
                    String assetsNumber = update.getAssetsNumber();
                    Assets assetsUpdate = accountingService.getAssetsByNumber(assetsNumber);
                    //更新转移后的地址为存放地址
                    String presentAddr = update.getPresentAddr();
                    assetsUpdate.setStorageAddr(presentAddr);
                    //更新状态为“闲置”
                    assetsUpdate.setUseStatus(AssetsStatus.NORMAL.getCode());
                    //加入待更新列表
                    updateList.add(assetsUpdate);
                    //增加处理人信息
                    update.setAuditorId(String.valueOf(auditorId));
                    //更新资产转移表
                    updateAssetsTransferRows = transferService.updateTransferInfo(update);
                }
                //转移审批通过，同步更新资产信息
                int updateAssetsRows = accountingService.updateAssetsLists(updateList);
                //成功才返回大于0的数
                if (updateAssetsRows > 0 && updateAssetsTransferRows > 0) {
                    return updateAssetsRows + updateAssetsTransferRows;
                }
            }
            if (AssetsExamineStatus.REJECT.getCode().equals(examineResult)) {
                for (AssetsTransfer update : transferUpdateList) {
                    //更新资产领用状态为“审核被驳回“
                    update.setStatus(AssetsExamineStatus.REJECT.getCode());
                    //增加处理人信息
                    update.setAuditorId(String.valueOf(auditorId));
                    //更新资产转移表
                    updateAssetsTransferRows = transferService.updateTransferInfo(update);
                    //根据资产编号查找到资产并更新状态
                    String assetsNumber = update.getAssetsNumber();
                    Assets assetsUpdate = accountingService.getAssetsByNumber(assetsNumber);
                    //闲置状态
                    assetsUpdate.setUseStatus(AssetsStatus.NORMAL.getCode());
                    //加入待更新列表
                    updateList.add(assetsUpdate);
                }
                int updateAssetsRows = accountingService.updateAssetsLists(updateList);
                //成功才返回大于0的数  转移审批驳回
                if (updateAssetsRows > 0 && updateAssetsTransferRows > 0) {
                    return updateAssetsRows + updateAssetsTransferRows;
                }
            }

            return 0;
        }
        throw new RuntimeException("审批失败！");
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
        String checkNumber = "PD" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

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
        throw new RuntimeException("添加盘点任务失败");
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
        throw new RuntimeException("盘点失败！");
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
        throw new RuntimeException("审核失败！");
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
        throw new RuntimeException("审核失败！");
    }

    public List<Assets> needMaintainList(List<Assets> assetsList) throws ParseException {
        List<Assets> needMaintainList = new ArrayList<>();
        for (Assets asset : assetsList) {
            //获取上次保养时间
            String maintainDate = asset.getMaintainDate();
            //实例化日期对象
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            //上次保养日期为空，根据购入时间，计算保养时间，加入待保养列表
            if (StringUtils.isEmpty(asset.getMaintainDate())) {
                String purchaseDate = asset.getPurchaseDate();
                calendar.setTime(simpleDateFormat.parse(purchaseDate));
                //购入时间的毫秒数
                long purchaseMillis = calendar.getTimeInMillis();
                //获取保养周期的毫秒数
                int maintainCycle = Integer.parseInt(asset.getMaintainCycle());
                int maintainCycleMillis = maintainCycle * 24 * 60 * 60 * 1000;
                //计算下次应该保养的时间毫秒数
                long next = purchaseMillis + maintainCycleMillis;
                //获取当前的时间毫秒数
                long currentMillis = new Date().getTime();
                //判断是否需要保养
                if (currentMillis >= next) {
                    needMaintainList.add(asset);
                }
                continue;
            }
            calendar.setTime(simpleDateFormat.parse(maintainDate));
            //上次保养时间的毫秒数
            long last = calendar.getTimeInMillis();
            //获取保养周期的毫秒数
            int maintainCycle = Integer.parseInt(asset.getMaintainCycle());
            int maintainCycleMillis = maintainCycle * 24 * 60 * 60 * 1000;
            //下次保养时间毫秒数
            long next = last + maintainCycleMillis;
            //当前时间毫秒数
            long currentMillis = new Date().getTime();
            //判断是否需要保养
            if (currentMillis >= next) {
                needMaintainList.add(asset);
            }
        }
        return needMaintainList;
    }
}
