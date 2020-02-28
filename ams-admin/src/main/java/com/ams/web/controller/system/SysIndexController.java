package com.ams.web.controller.system;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.ams.common.core.domain.AjaxResult;
import com.ams.common.core.page.TableDataInfo;
import com.ams.common.enums.AssetsExamineStatus;
import com.ams.system.domain.*;
import com.ams.system.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import com.ams.common.config.Global;
import com.ams.common.core.controller.BaseController;
import com.ams.framework.util.ShiroUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 首页 业务处理
 *
 * @author zengchao
 */
@Controller
public class SysIndexController extends BaseController {

    @Autowired
    private IAssetsAccountingService accountingService;
    @Autowired
    private ISysMenuService menuService;
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
    private IAssetsReturnService returnService;
    @Autowired
    private AssetsService assetsService;

    // 系统首页
    @GetMapping("/index")
    public String index(ModelMap mmap) {
        // 取身份信息
        SysUser user = ShiroUtils.getSysUser();
        // 根据用户id取出菜单
        List<SysMenu> menus = menuService.selectMenusByUser(user);
        mmap.put("menus", menus);
        mmap.put("user", user);
        mmap.put("copyrightYear", Global.getCopyrightYear());
        mmap.put("demoEnabled", Global.isDemoEnabled());
        return "index";
    }

    // 切换主题
    @GetMapping("/system/switchSkin")
    public String switchSkin(ModelMap mmap) {
        return "skin";
    }

    // 系统介绍
    @GetMapping("/system/main")
    public String main(ModelMap mmap) {
        AssetsInfo assetsInfo = new AssetsInfo();
        AssetsInfo zongZiChanInfo = accountingService.getZongZiChanInfo();
        AssetsInfo xianZhiInfo = accountingService.getXianZhiInfo();
        AssetsInfo shiYongInfo = accountingService.getShiYongInfo();
        AssetsInfo daiBaoFeiInfo = accountingService.getDaiBaoFeiInfo();
        AssetsInfo chuZhiInfo = accountingService.getChuZhiInfo();
        assetsInfo.setZongzichanCount(zongZiChanInfo.getZongzichanCount());
        assetsInfo.setZongzichanSum(zongZiChanInfo.getZongzichanSum());
        assetsInfo.setXianzhiCount(xianZhiInfo.getXianzhiCount());
        assetsInfo.setXianzhiSum(xianZhiInfo.getXianzhiSum());
        assetsInfo.setShiyongCount(shiYongInfo.getShiyongCount());
        assetsInfo.setShiyongSum(shiYongInfo.getShiyongSum());
        assetsInfo.setDaibaofeiCount(daiBaoFeiInfo.getDaibaofeiCount());
        assetsInfo.setDaibaofeiSum(daiBaoFeiInfo.getDaibaofeiSum());
        assetsInfo.setChuzhiCount(chuZhiInfo.getChuzhiCount());
        assetsInfo.setChuzhiSum(chuZhiInfo.getChuzhiSum());
        mmap.put("assetsInfo", assetsInfo);
        mmap.put("version", Global.getVersion());
        return "main_v2";
    }

    @PostMapping("/system/mainTable/todoList")
    @ResponseBody
    public TableDataInfo todoList() {

        List<AssetsAllocate> allocateAdminList = allocateService.getAllocateAdminList(new AssetsAllocate());
        List<AssetsBorrow> borrowListAll = borrowService.getBorrowListAll();
        List<AssetsReturn> returnListAll = returnService.getReturnListAll();
        List<AssetsMaintain> maintainListAll = maintainService.getMaintainListAll();
        List<AssetsRepair> repairListAll = repairService.getRepairListAll();
        List<AssetsTransfer> transferListAll = transferService.getTransferListAll();
        List<AssetsAccident> accidentListAll = accidentService.getAccidentListAll();
        List<AssetsCheckTask> checkedAll = checkTaskService.getCheckedAll();
        List<Task> taskList = new ArrayList<>();
        int seq = 0;

        SysUser sysUser = ShiroUtils.getSysUser();
        //不是管理员
        if (!sysUser.isAdmin()) {
            if (checkedAll.size() > 0) {
                for (AssetsCheckTask checkTask : checkedAll) {
                    Task task = new Task();
                    task.setSeqNo(++seq);
                    task.setType("资产盘点");
                    task.setOrderNum(checkTask.getCheckNumber());
                    task.setUserName(checkTask.getUser().getUserName());
                    task.setUserId(checkTask.getUser().getUserId().intValue());
                    task.setParentName(checkTask.getUser().getDept().getParentName());
                    task.setDate(new SimpleDateFormat("yyyy-MM-dd").format(checkTask.getCreateTime()));
                    taskList.add(task);
                }
            }
            startPage();
            return getDataTable(taskList);
        }

        if (checkedAll.size() > 0) {
            Task task = new Task();
            task.setSeqNo(++seq);
            task.setType("资产盘点");
            task.setOrderNum(checkedAll.get(0).getCheckNumber());
            task.setUserName(checkedAll.get(0).getUser().getUserName());
            task.setUserId(checkedAll.get(0).getUser().getUserId().intValue());
            task.setParentName(checkedAll.get(0).getUser().getDept().getParentName());
            task.setDate(new SimpleDateFormat("yyyy-MM-dd").format(checkedAll.get(0).getCreateTime()));
            taskList.add(task);
        }
        if (allocateAdminList.size() > 0) {
            Task task = new Task();
            task.setSeqNo(++seq);
            task.setType("资产领用");
            task.setOrderNum(allocateAdminList.get(0).getAllocateOrderNum());
            task.setUserName(allocateAdminList.get(0).getUser().getUserName());
            task.setUserId(allocateAdminList.get(0).getUser().getUserId().intValue());
            task.setParentName(allocateAdminList.get(0).getUser().getDept().getParentName());
            task.setDate(new SimpleDateFormat("yyyy-MM-dd").format(allocateAdminList.get(0).getCreateTime()));
            taskList.add(task);
        }
        if (borrowListAll.size() > 0) {
            Task task = new Task();
            task.setSeqNo(++seq);
            task.setType("资产借用");
            task.setOrderNum(borrowListAll.get(0).getBorrowOrderNum());
            task.setUserName(borrowListAll.get(0).getUser().getUserName());
            task.setUserId(borrowListAll.get(0).getUser().getUserId().intValue());
            task.setParentName(borrowListAll.get(0).getUser().getDept().getParentName());
            task.setDate(new SimpleDateFormat("yyyy-MM-dd").format(borrowListAll.get(0).getCreateTime()));
            taskList.add(task);
        }
        if (returnListAll.size() > 0) {
            Task task = new Task();
            task.setSeqNo(++seq);
            task.setType("资产归还");
            task.setOrderNum(returnListAll.get(0).getReturnOrderNum());
            task.setUserName(returnListAll.get(0).getUser().getUserName());
            task.setUserId(returnListAll.get(0).getUser().getUserId().intValue());
            task.setParentName(returnListAll.get(0).getUser().getDept().getParentName());
            task.setDate(new SimpleDateFormat("yyyy-MM-dd").format(returnListAll.get(0).getCreateTime()));
            taskList.add(task);
        }
        if (maintainListAll.size() > 0) {
            Task task = new Task();
            task.setSeqNo(++seq);
            task.setType("资产保养");
            task.setOrderNum(maintainListAll.get(0).getMaintainOrderNum());
            task.setUserName(maintainListAll.get(0).getUser().getUserName());
            task.setUserId(maintainListAll.get(0).getUser().getUserId().intValue());
            task.setParentName(maintainListAll.get(0).getUser().getDept().getParentName());
            task.setDate(new SimpleDateFormat("yyyy-MM-dd").format(maintainListAll.get(0).getCreateTime()));
            taskList.add(task);
        }
        if (repairListAll.size() > 0) {
            Task task = new Task();
            task.setSeqNo(++seq);
            task.setType("资产维修");
            task.setOrderNum(repairListAll.get(0).getRepairOrderNum());
            task.setUserName(repairListAll.get(0).getUser().getUserName());
            task.setUserId(repairListAll.get(0).getUser().getUserId().intValue());
            task.setParentName(repairListAll.get(0).getUser().getDept().getParentName());
            task.setDate(new SimpleDateFormat("yyyy-MM-dd").format(repairListAll.get(0).getCreateTime()));
            taskList.add(task);
        }
        if (transferListAll.size() > 0) {
            Task task = new Task();
            task.setSeqNo(++seq);
            task.setType("资产转移");
            task.setOrderNum(transferListAll.get(0).getTransferOrderNum());
            task.setUserName(transferListAll.get(0).getUser().getUserName());
            task.setUserId(transferListAll.get(0).getUser().getUserId().intValue());
            task.setParentName(transferListAll.get(0).getUser().getDept().getParentName());
            task.setDate(new SimpleDateFormat("yyyy-MM-dd").format(transferListAll.get(0).getCreateTime()));
            taskList.add(task);
        }
        if (accidentListAll.size() > 0) {
            Task task = new Task();
            task.setSeqNo(++seq);
            task.setType("资产事故");
            task.setOrderNum(accidentListAll.get(0).getAccidentOrderNum());
            task.setUserName(accidentListAll.get(0).getUser().getUserName());
            task.setUserId(accidentListAll.get(0).getUser().getUserId().intValue());
            task.setParentName(accidentListAll.get(0).getUser().getDept().getParentName());
            task.setDate(new SimpleDateFormat("yyyy-MM-dd").format(accidentListAll.get(0).getCreateTime()));
            taskList.add(task);
        }
        startPage();
        return getDataTable(taskList);

    }

    @PostMapping("/system/mainTable/examineOK/{orderNum}/{userId}")
    @ResponseBody
    public AjaxResult examineOK(@PathVariable("orderNum") String orderNum,
                                @PathVariable("userId") int userId) {
        SysUser currentSysUser = ShiroUtils.getSysUser();
        if (currentSysUser != null) {
            int auditorId = currentSysUser.getUserId().intValue();
            try {
                return toAjax(assetsService.borrowExamine(auditorId, orderNum, userId, AssetsExamineStatus.AGREE.getCode()));
            } catch (Exception e) {
                e.printStackTrace();
                return error();
            }
        }
        return error();
    }

    @PostMapping("/system/mainTable/examineReject/{orderNum}/{userId}")
    @ResponseBody
    public AjaxResult examineReject(@PathVariable("orderNum") String orderNum,
                                    @PathVariable("userId") int userId) {
        SysUser currentSysUser = ShiroUtils.getSysUser();
        if (currentSysUser != null) {
            int auditorId = currentSysUser.getUserId().intValue();
            try {
                return toAjax(assetsService.borrowExamine(auditorId, orderNum, userId, AssetsExamineStatus.REJECT.getCode()));
            } catch (Exception e) {
                e.printStackTrace();
                return error();
            }
        }
        return error();
    }

    @GetMapping("/system/mainTable/moreInfo")
    public String moreInfoIndex() {
        return "adminMoreInfo";
    }

    @PostMapping("/system/mainTable/adminListAll")
    @ResponseBody
    public TableDataInfo adminListAll() {
        List<AssetsAllocate> allocateAdminList = allocateService.getAllocateAdminList(new AssetsAllocate());
        List<AssetsBorrow> borrowListAll = borrowService.getBorrowListAll();
        List<AssetsReturn> returnListAll = returnService.getReturnListAll();
        List<AssetsMaintain> maintainListAll = maintainService.getMaintainListAll();
        List<AssetsRepair> repairListAll = repairService.getRepairListAll();
        List<AssetsTransfer> transferListAll = transferService.getTransferListAll();
        List<AssetsAccident> accidentListAll = accidentService.getAccidentListAll();
        List<AssetsCheckTask> checkTaskAll = checkTaskService.getCheckedAll();
        List<Task> taskList = new ArrayList<>();
        int seq = 0;

        if (checkTaskAll.size() > 0) {
            for (AssetsCheckTask checkTask : checkTaskAll) {
                Task task = new Task();
                task.setSeqNo(++seq);
                task.setType("资产盘点");
                task.setOrderNum(checkTask.getCheckNumber());
                task.setUserName(checkTask.getUser().getUserName());
                task.setUserId(checkTask.getUser().getUserId().intValue());
                task.setParentName(checkTask.getUser().getDept().getParentName());
                task.setDate(new SimpleDateFormat("yyyy-MM-dd").format(checkTask.getCreateTime()));
                taskList.add(task);

            }
        }
        SysUser sysUser = ShiroUtils.getSysUser();
        //不是管理员
        if (!sysUser.isAdmin()) {
            startPage();
            return getDataTable(taskList);
        }


        if (allocateAdminList.size() > 0) {
            for (AssetsAllocate allocate : allocateAdminList) {

                Task task = new Task();
                task.setSeqNo(++seq);
                task.setType("资产领用");
                task.setOrderNum(allocate.getAllocateOrderNum());
                task.setUserName(allocate.getUser().getUserName());
                task.setUserId(allocate.getUser().getUserId().intValue());
                task.setParentName(allocate.getUser().getDept().getParentName());
                task.setDate(new SimpleDateFormat("yyyy-MM-dd").format(allocate.getCreateTime()));
                taskList.add(task);

            }
        }
        if (borrowListAll.size() > 0) {
            for (AssetsBorrow borrow : borrowListAll) {

                Task task = new Task();
                task.setSeqNo(++seq);
                task.setType("资产借用");
                task.setOrderNum(borrow.getBorrowOrderNum());
                task.setUserName(borrow.getUser().getUserName());
                task.setUserId(borrow.getUser().getUserId().intValue());
                task.setParentName(borrow.getUser().getDept().getParentName());
                task.setDate(new SimpleDateFormat("yyyy-MM-dd").format(borrow.getCreateTime()));
                taskList.add(task);

            }
        }
        if (returnListAll.size() > 0) {
            for (AssetsReturn returnInfo : returnListAll) {

                Task task = new Task();
                task.setSeqNo(++seq);
                task.setType("资产归还");
                task.setOrderNum(returnInfo.getReturnOrderNum());
                task.setUserName(returnInfo.getUser().getUserName());
                task.setUserId(returnInfo.getUser().getUserId().intValue());
                task.setParentName(returnInfo.getUser().getDept().getParentName());
                task.setDate(new SimpleDateFormat("yyyy-MM-dd").format(returnInfo.getCreateTime()));
                taskList.add(task);

            }
        }
        if (maintainListAll.size() > 0) {
            for (AssetsMaintain maintain : maintainListAll) {

                Task task = new Task();
                task.setSeqNo(++seq);
                task.setType("资产保养");
                task.setOrderNum(maintain.getMaintainOrderNum());
                task.setUserName(maintain.getUser().getUserName());
                task.setUserId(maintain.getUser().getUserId().intValue());
                task.setParentName(maintain.getUser().getDept().getParentName());
                task.setDate(new SimpleDateFormat("yyyy-MM-dd").format(maintain.getCreateTime()));
                taskList.add(task);

            }
        }
        if (repairListAll.size() > 0) {
            for (AssetsRepair repair : repairListAll) {

                Task task = new Task();
                task.setSeqNo(++seq);
                task.setType("资产维修");
                task.setOrderNum(repair.getRepairOrderNum());
                task.setUserName(repair.getUser().getUserName());
                task.setUserId(repair.getUser().getUserId().intValue());
                task.setParentName(repair.getUser().getDept().getParentName());
                task.setDate(new SimpleDateFormat("yyyy-MM-dd").format(repair.getCreateTime()));
                taskList.add(task);

            }
        }
        if (transferListAll.size() > 0) {
            for (AssetsTransfer transfer : transferListAll) {

                Task task = new Task();
                task.setSeqNo(++seq);
                task.setType("资产转移");
                task.setOrderNum(transfer.getTransferOrderNum());
                task.setUserName(transfer.getUser().getUserName());
                task.setUserId(transfer.getUser().getUserId().intValue());
                task.setParentName(transfer.getUser().getDept().getParentName());
                task.setDate(new SimpleDateFormat("yyyy-MM-dd").format(transfer.getCreateTime()));
                taskList.add(task);

            }
        }
        if (accidentListAll.size() > 0) {
            for (AssetsAccident accident : accidentListAll) {

                Task task = new Task();
                task.setSeqNo(++seq);
                task.setType("资产事故");
                task.setOrderNum(accident.getAccidentOrderNum());
                task.setUserName(accident.getUser().getUserName());
                task.setUserId(accident.getUser().getUserId().intValue());
                task.setParentName(accident.getUser().getDept().getParentName());
                task.setDate(new SimpleDateFormat("yyyy-MM-dd").format(accident.getCreateTime()));
                taskList.add(task);

            }
        }
        startPage();
        return getDataTable(taskList);
    }

}
