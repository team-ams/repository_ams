package com.ams.web.controller.assets;

import com.ams.common.annotation.Log;
import com.ams.common.constant.UserConstants;
import com.ams.common.core.controller.BaseController;
import com.ams.common.core.domain.AjaxResult;
import com.ams.common.core.page.TableDataInfo;
import com.ams.common.enums.AssetsExamineStatus;
import com.ams.common.enums.BusinessType;
import com.ams.common.utils.StringUtils;
import com.ams.common.utils.poi.ExcelUtil;
import com.ams.framework.util.ShiroUtils;
import com.ams.system.domain.Assets;
import com.ams.system.domain.AssetsBorrow;
import com.ams.system.domain.AssetsReturn;
import com.ams.system.domain.SysUser;
import com.ams.system.service.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 用户信息
 *
 * @author zengchao
 */
@Controller
@RequestMapping("/assets/return")
public class AssetsReturnController extends BaseController {
    private static List<String> assetsNumbers = new ArrayList<>();
    private static LinkedHashSet<Assets> assetsLinkedHashSet = new LinkedHashSet<>();
    private static final String FID = "8";
    private static final String MSG = "I'm from Server---return";

    private String prefix = "/assets/return";

    @Autowired
    private IAssetsAccountingService accountingService;

    @Autowired
    private IAssetsReturnService returnService;

    @Autowired
    private IAssetsBorrowService borrowService;

    @Autowired
    private AssetsService assetsService;

    @Autowired
    private IAssetsSourceService sourceService;

    @RequiresPermissions("assets:return:view")
    @GetMapping()
    public String assets() {
        SysUser currentSysUser = ShiroUtils.getSysUser();
        if (currentSysUser != null) {
            //当前用户不是管理员，返回领用界面
            if (!currentSysUser.isAdmin()) {
                return prefix + "/return";
            }
        }
        //管理员返回管理员页面
        return prefix + "/returnAdmin";
    }

    @RequiresPermissions("assets:return:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Assets assets) {
        SysUser currentSysUser = ShiroUtils.getSysUser();
        if (currentSysUser != null) {
            //当前系统用户不是管理员
            if (!currentSysUser.isAdmin()) {
                startPage();
                List<AssetsBorrow> returnListFromBorrow = borrowService.getNeedReturnByUserId(currentSysUser.getUserId().toString());
                return getDataTable(returnListFromBorrow);
            }
            //当前系统用户是管理员，待审批信息
            startPage();
            List<AssetsReturn> returnListAll = returnService.getReturnListAll();
            return getDataTable(returnListAll);
        }
        return getDataTable(new ArrayList<>());
    }

    @Log(title = "资产管理", businessType = BusinessType.EXPORT)
    @RequiresPermissions("assets:return:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Assets assets) {
        List<Assets> list = accountingService.getAssetsList(assets);
        ExcelUtil<Assets> util = new ExcelUtil<Assets>(Assets.class);
        return util.exportExcel(list, "资产数据");
    }

    @Log(title = "资产管理", businessType = BusinessType.IMPORT)
    @RequiresPermissions("assets:return:import")
    @PostMapping("/importData")
    @ResponseBody
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception {
        ExcelUtil<Assets> util = new ExcelUtil<Assets>(Assets.class);
        List<Assets> userList = util.importExcel(file.getInputStream());
        String operName = ShiroUtils.getSysUser().getLoginName();
        String message = accountingService.importAssets(userList, updateSupport, operName);
        return AjaxResult.success(message);
    }

    @RequiresPermissions("assets:return:view")
    @GetMapping("/importTemplate")
    @ResponseBody
    public AjaxResult importTemplate() {
        ExcelUtil<Assets> util = new ExcelUtil<Assets>(Assets.class);
        return util.importTemplateExcel("资产数据");
    }


    /**
     * 新增保存资产
     */
    @Log(title = "资产管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@Validated Assets assets) {
        if (UserConstants.USER_NAME_NOT_UNIQUE.equals(accountingService.checkAssetsNumberUnique(assets.getAssetsNumber()))) {
            return error("新增资产'" + assets.getAssetsNumber() + "'失败，资产编号已存在");
        }
        assets.setCreateBy(ShiroUtils.getLoginName());
        return toAjax(accountingService.insertAssets(assets));
    }

    /**
     * 修改资产
     */
    @GetMapping("/edit/{returnId}")
    public String edit(@PathVariable("returnId") String returnId, ModelMap mmap) {
        mmap.put("return", returnService.getReturnById(returnId));
        return prefix + "/edit";
    }

    /**
     * 修改保存资产
     */
    @Log(title = "资产管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(@Validated AssetsReturn returnInfo) {
        returnInfo.setUpdateBy(ShiroUtils.getLoginName());
        return toAjax(returnService.updateReturnByOrderNum(returnInfo));
    }


    @Log(title = "用户管理", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        AssetsReturn returnById = returnService.getReturnById(ids);
        if (returnById == null) {
            return error();
        }
        try {
            //根据单号批量删除
            return toAjax(returnService.deleteReturnByOrderNum(returnById.getReturnOrderNum()));
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

    @PostMapping("/childTableList")
    @ResponseBody
    public TableDataInfo childTableList(HttpServletRequest request) {
        String orderNum = request.getParameter("orderNum");
        if (StringUtils.isNotEmpty(orderNum)) {

            List<AssetsReturn> assetsReturnBy = returnService.getReturnListByOrderNum(orderNum);
            startPage();
            return getDataTable(assetsReturnBy);
        }
        return getDataTable(new ArrayList<>());
    }

    @PostMapping("/borrowChildTableList")
    @ResponseBody
    public TableDataInfo borrowChildTableList(HttpServletRequest request) {
        String orderNum = request.getParameter("orderNum");
        if (StringUtils.isNotEmpty(orderNum)) {

            List<AssetsBorrow> assetsBorrowBy = borrowService.getBorrowListByOrderNum(orderNum);
            startPage();
            return getDataTable(assetsBorrowBy);
        }
        return getDataTable(new ArrayList<>());
    }

    @GetMapping("/return/{assetsNumber}")
    public String returnAssets(@PathVariable("assetsNumber") String assetsNumber, ModelMap mmap) {
        mmap.put("assetsNumber", assetsNumber);
        return prefix + "/returnAssets2";
    }


    @GetMapping("/batchReturn/{borrowOrderNum}")
    public String batchReturn(@PathVariable("borrowOrderNum") String borrowOrderNum, ModelMap mmap) {
        mmap.put("borrowOrderNum", borrowOrderNum);
        return prefix + "/batchReturn";
    }

    /**
     * 新增（模态框）
     *
     * @param fid
     * @param mmap
     * @param request
     * @return
     */
    @GetMapping("/returnModal/{fid}")
    public String returnModal(@PathVariable("fid") String fid, ModelMap mmap, HttpServletRequest request) {
        mmap.put("tableData", "info");
        mmap.put("basePath", request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/");
        mmap.put("fid", fid);
        return prefix + "/returnAssets";
    }

    /**
     * MFRC522通过ESP8266将ID卡号发送过来，提供给esp8266调用的接口
     *
     * @param request
     */
    @RequestMapping("/getMsg")
    @ResponseBody
    public String getMsg(HttpServletRequest request, ModelMap mmap) {
        Map<String, String> parameterMap = getParameterMap();
        String assetsNumber = parameterMap.get("assetsNumber");
        assetsNumbers.add(assetsNumber);
        System.out.println(parameterMap);

        //通知前端页面有新数据
        pushToWeb(FID, MSG);
        mmap.put("content", "传过来的：" + assetsNumber);
        return prefix + "/returnAssets";
    }


    @PostMapping("/returnList")
    @ResponseBody
    public TableDataInfo returnList() {
        if (assetsNumbers.size() == 0) {
            return getDataTable(new ArrayList<>());
            //todo 待定等待卡号发送过来
        }
        //已获取到卡号,查找数据库得到详情
        List<Assets> assetsList = accountingService.getAssetsByNumberList(assetsNumbers);
        for (Assets assets : assetsList) {
            assetsLinkedHashSet.add(assets);
        }
        //将LinkedHashSet转换成List
        List<Assets> dataLists = new ArrayList<Assets>(assetsLinkedHashSet);
        return getDataTable(dataLists);
    }

    @PostMapping("/returnConfirm")
    @ResponseBody
    public AjaxResult returnConfirm(AssetsReturn returnData) {
        //清除卡号，复位
        assetsNumbers.clear();
        //清空表格信息，复位，避免下次把之前的数据重新显示出来
        assetsLinkedHashSet.clear();
        //点击“确认”后 通知前端页面刷新表格
        pushToWeb(FID, MSG);
        return returnSave(returnData);

    }

    @PostMapping("/batchReturnConfirm")
    @ResponseBody
    public AjaxResult batchReturnConfirm(HttpServletRequest request) {
        //清除卡号，复位
        assetsNumbers.clear();
        //清空表格信息，复位，避免下次把之前的数据重新显示出来
        assetsLinkedHashSet.clear();
        //点击“确认”后 通知前端页面刷新表格
        pushToWeb(FID, MSG);
        String borrowOrderNum = request.getParameter("borrowOrderNum");
        String returnTime = request.getParameter("ReturnTime");
        String remark = request.getParameter("remark");
        if (StringUtils.isEmpty(borrowOrderNum) || StringUtils.isEmpty(returnTime) || remark == null) {
            return error();
        }
        List<AssetsBorrow> borrowList = borrowService.getBorrowListByOrderNum(borrowOrderNum);
        if (borrowList.size() == 0) {
            return error();
        }
        List<String> assetsNumberList = borrowList.stream().map(AssetsBorrow::getAssetsNumber).collect(Collectors.toList());
        String commaSeparated = assetsNumberList.stream().collect(Collectors.joining(","));
        AssetsReturn returnData = new AssetsReturn();
        returnData.setAssetsNumber(commaSeparated);
        returnData.setReturnTime(returnTime);
        returnData.setRemark(remark);
        return returnSave(returnData);

    }

    public AjaxResult returnSave(AssetsReturn returnData) {
        if (returnData == null) {
            return error();
        }
        SysUser currentSysUser = ShiroUtils.getSysUser();
        if (currentSysUser != null) {
            int returnUserId = currentSysUser.getUserId().intValue();
            String createBy = currentSysUser.getLoginName();
            try {
                return toAjax(assetsService.returnAssets(returnUserId, createBy, returnData));
            } catch (Exception e) {
                e.printStackTrace();
                return error();
            }
        }
        return error();
    }

    @GetMapping("/myReturn")
    public String myAllocate() {
        return prefix + "/myReturn";
    }

    @PostMapping("/myReturnList")
    @ResponseBody
    public TableDataInfo myAllocateTable() {
        //获取当前系统用户id
        SysUser currentUser = ShiroUtils.getSysUser();
        if (currentUser == null) {
            return getDataTable(new ArrayList<>());
        }
        startPage();
        List<AssetsReturn> myReturnListByUserId = returnService.getReturnListByUserId(String.valueOf(currentUser.getUserId()));
        return getDataTable(myReturnListByUserId);
    }

    @GetMapping("/myExamine")
    public String myExamine() {
        return prefix + "/myExamine";
    }

    @PostMapping("/myExamineList")
    @ResponseBody
    public TableDataInfo myExamineList() {
        startPage();
        return getDataTable(returnService.getMyExamineList());
    }

    @PostMapping("/examineOK/{orderNum}/{userId}")
    @ResponseBody
    public AjaxResult examineOK(@PathVariable("orderNum") String orderNum,
                                @PathVariable("userId") int userId) {
        SysUser currentSysUser = ShiroUtils.getSysUser();
        if (currentSysUser != null) {
            int auditorId = currentSysUser.getUserId().intValue();
            try {
                return toAjax(assetsService.returnExamine(auditorId, orderNum, userId, AssetsExamineStatus.AGREE.getCode()));
            } catch (Exception e) {
                e.printStackTrace();
                return error();
            }
        }
        return error();
    }

    @PostMapping("/examineReject/{orderNum}/{userId}")
    @ResponseBody
    public AjaxResult examineReject(@PathVariable("orderNum") String orderNum,
                                    @PathVariable("userId") int userId) {
        SysUser currentSysUser = ShiroUtils.getSysUser();
        if (currentSysUser != null) {
            int auditorId = currentSysUser.getUserId().intValue();
            try {
                return toAjax(assetsService.returnExamine(auditorId, orderNum, userId, AssetsExamineStatus.REJECT.getCode()));
            } catch (Exception e) {
                e.printStackTrace();
                return error();
            }
        }
        return error();
    }

}