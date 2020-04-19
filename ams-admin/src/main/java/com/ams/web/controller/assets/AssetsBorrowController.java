package com.ams.web.controller.assets;

import com.ams.common.annotation.Log;
import com.ams.common.constant.UserConstants;
import com.ams.common.core.controller.BaseController;
import com.ams.common.core.domain.AjaxResult;
import com.ams.common.core.page.TableDataInfo;
import com.ams.common.enums.AssetsExamineStatus;
import com.ams.common.enums.AssetsStatus;
import com.ams.common.enums.BusinessType;
import com.ams.common.utils.StringUtils;
import com.ams.common.utils.poi.ExcelUtil;
import com.ams.framework.util.ShiroUtils;
import com.ams.system.domain.Assets;
import com.ams.system.domain.AssetsAllocate;
import com.ams.system.domain.AssetsBorrow;
import com.ams.system.domain.SysUser;
import com.ams.system.service.AssetsService;
import com.ams.system.service.IAssetsAccountingService;
import com.ams.system.service.IAssetsBorrowService;
import com.ams.system.service.IAssetsSourceService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

/**
 * 用户信息
 *
 * @author zengchao
 */
@Controller
@RequestMapping("/assets/borrow")
public class AssetsBorrowController extends BaseController {
    private static List<String> assetsNumbers = new ArrayList<>();
    private static LinkedHashSet<Assets> assetsLinkedHashSet = new LinkedHashSet<>();
    private static final String FID = "1";
    private static final String MSG = "I'm from Server---borrow";

    private String prefix = "/assets/borrow";

    @Autowired
    private IAssetsAccountingService accountingService;

    @Autowired
    private IAssetsBorrowService borrowService;

    @Autowired
    private AssetsService assetsService;

    @Autowired
    private IAssetsSourceService sourceService;

    @RequiresPermissions("assets:borrow:view")
    @GetMapping()
    public String assets() {
        SysUser currentSysUser = ShiroUtils.getSysUser();
        if (currentSysUser != null) {
            //当前用户不是管理员，返回领用界面
            if (!currentSysUser.isAdmin()) {
                return prefix + "/borrow";
            }
        }
        //管理员返回管理员页面
        return prefix + "/borrowAdmin";
    }

    @RequiresPermissions("assets:borrow:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(AssetsBorrow borrow,Assets assets) {
        SysUser currentSysUser = ShiroUtils.getSysUser();
        if (currentSysUser != null) {
            //当前系统用户不是管理员，可借用的资产信息
            if (!currentSysUser.isAdmin()) {
                startPage();
                List<Assets> assetsList0 = accountingService.getAssetsList0(assets);
                return getDataTable(assetsList0);
            }
            //当前系统用户是管理员，待审批信息
            startPage();
            List<AssetsBorrow> borrowListAll = borrowService.getBorrowListAll(borrow);
            return getDataTable(borrowListAll);
        }
        return getDataTable(new ArrayList<>());
    }

    @Log(title = "资产管理", businessType = BusinessType.EXPORT)
    @RequiresPermissions("assets:borrow:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Assets assets) {
        List<Assets> list = accountingService.getAssetsList(assets);
        ExcelUtil<Assets> util = new ExcelUtil<Assets>(Assets.class);
        return util.exportExcel(list, "资产数据");
    }

    @Log(title = "资产管理", businessType = BusinessType.IMPORT)
    @RequiresPermissions("assets:borrow:import")
    @PostMapping("/importData")
    @ResponseBody
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception {
        ExcelUtil<Assets> util = new ExcelUtil<Assets>(Assets.class);
        List<Assets> userList = util.importExcel(file.getInputStream());
        String operName = ShiroUtils.getSysUser().getLoginName();
        String message = accountingService.importAssets(userList, updateSupport, operName);
        return AjaxResult.success(message);
    }

    @RequiresPermissions("assets:borrow:view")
    @GetMapping("/importTemplate")
    @ResponseBody
    public AjaxResult importTemplate() {
        ExcelUtil<Assets> util = new ExcelUtil<Assets>(Assets.class);
        return util.importTemplateExcel("资产数据");
    }


    /**
     * 新增保存资产
     */
    @RequiresPermissions("assets:borrow:add")
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
    @GetMapping("/edit/{borrowId}")
    public String edit(@PathVariable("borrowId") String borrowId, ModelMap mmap) {
        mmap.put("borrow", borrowService.getBorrowById(borrowId));
        return prefix + "/edit";
    }

    /**
     * 修改保存资产
     */
    @Log(title = "资产管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(@Validated AssetsBorrow borrow) {
        borrow.setUpdateBy(ShiroUtils.getLoginName());
        int i = borrowService.updateBorrowByOrderNum(borrow);
        return toAjax(borrowService.updateBorrowByOrderNum(borrow));
    }


    @Log(title = "用户管理", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        AssetsBorrow borrow = borrowService.getBorrowById(ids);
        if (borrow == null) {
            return error();
        }
        try {
            return toAjax(assetsService.borrowDelete(borrow.getBorrowOrderNum()));
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

    @PostMapping("/childTableList")
    @ResponseBody
    public TableDataInfo childTableList(HttpServletRequest request) {
        String orderNum = request.getParameter("orderNum");
        if (StringUtils.isNotEmpty(orderNum)) {

            List<AssetsBorrow> assetsBorrowBy = borrowService.getBorrowListByOrderNum(orderNum);
            startPage();
            return getDataTable(assetsBorrowBy);
        }
        return getDataTable(new ArrayList<>());
    }

    @GetMapping("/borrow/{assetsNumber}")
    public String borrow(@PathVariable("assetsNumber") String assetsNumber, ModelMap mmap) {
        mmap.put("assetsNumber", assetsNumber);
        return prefix + "/borrowAssets2";
    }

    /**
     * 新增（模态框）
     *
     * @param fid
     * @param mmap
     * @param request
     * @return
     */
    @GetMapping("/borrowModal/{fid}")
    public String borrowModal(@PathVariable("fid") String fid, ModelMap mmap, HttpServletRequest request) {
        mmap.put("tableData", "info");
        mmap.put("basePath", request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/");
        mmap.put("fid", fid);
        return prefix + "/borrowAssets";
    }

    /**
     * MFRC522通过ESP8266将ID卡号发送过来，提供给esp8266调用的接口
     *
     * @param request
     */
    @RequestMapping("/getMsg")
    @ResponseBody
    public AjaxResult getMsg(HttpServletRequest request, ModelMap mmap) {
        Map<String, String> parameterMap = getParameterMap();
        String assetsNumber = parameterMap.get("assetsNumber");
        String assetsNumber1 = request.getParameter("assetsNumber");
        assetsNumbers.add(assetsNumber);
        System.out.println(parameterMap);

        //通知前端页面有新数据
        pushToWeb(FID, MSG);
        mmap.put("content", "传过来的：" + assetsNumber);
        return AjaxResult.success("Success",prefix + "/borrowAssets");

    }

    @RequestMapping("/getInfo")
    @ResponseBody
    public String getInfo(HttpServletRequest request) throws UnsupportedEncodingException {
        Map<String, String> parameterMap = getParameterMap();
        String assetsNumber = parameterMap.get("assetsNumber");
        String assetsNumber1 = request.getParameter("assetsNumber");
        Assets assetsByNumber = accountingService.getAssetsByNumber(assetsNumber);
        StringBuilder sb = new StringBuilder();
        sb.append("##.");
        String number = assetsByNumber.getAssetsNumber();
        sb.append(string2HexString(number));
        sb.append(".");
        String assetsName = assetsByNumber.getAssetsName();
        sb.append(string2HexString(assetsName));
        sb.append(".");
        String assetsType = assetsByNumber.getAssetsType();
        sb.append(string2HexString(assetsType));
        sb.append(".");
        String storageUnit = assetsByNumber.getStorageUnit();
        sb.append(string2HexString(storageUnit));
        sb.append(".");
        String storageAddr = assetsByNumber.getStorageAddr();
        sb.append(string2HexString(storageAddr));
        sb.append(".");
        String assetsSource = assetsByNumber.getAssetsSource();
        sb.append(string2HexString(assetsSource));
        sb.append(".");
        String useStatus = AssetsStatus.getStatusByCode(assetsByNumber.getUseStatus()).getInfo();
        sb.append(string2HexString(useStatus));
        sb.append(".");
        String assetsBrand = assetsByNumber.getAssetsBrand();
        sb.append(string2HexString(assetsBrand));
        sb.append(".");
        String purchaseDate = assetsByNumber.getPurchaseDate();
        sb.append(string2HexString(purchaseDate));

        assetsNumbers.add(assetsNumber);
        System.out.println(parameterMap);
        System.out.println("*******->"+sb);
        return sb.toString();
        //return "##借用,成功,！";
    }
    public String string2HexString(String str) throws UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder();
        byte[] gbkBytes = str.getBytes("GBK");
        for (int i=0;i<gbkBytes.length;i++){
            sb.append("0x");
            sb.append(String.format("%02X",gbkBytes[i]));
            if(i<gbkBytes.length-1) {
                sb.append(".");
            }
        }
        return sb.toString();
    }



    @PostMapping("/borrowList")
    @ResponseBody
    public TableDataInfo borrowList() {
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

    @PostMapping("/borrowConfirm")
    @ResponseBody
    public AjaxResult borrowConfirm(AssetsBorrow borrowData) {
        //清除卡号，复位
        assetsNumbers.clear();
        //清空表格信息，复位，避免下次把之前的数据重新显示出来
        assetsLinkedHashSet.clear();
        //点击“确认”后 通知前端页面刷新表格
        pushToWeb(FID, MSG);
        return borrowSave(borrowData);

    }

    public AjaxResult borrowSave(AssetsBorrow borrowData) {
        if (borrowData == null) {
            return error();
        }
        SysUser currentSysUser = ShiroUtils.getSysUser();
        if (currentSysUser != null) {
            int borrowUserId = currentSysUser.getUserId().intValue();
            String createBy = currentSysUser.getLoginName();
            try {
                int res = assetsService.borrowAssets(borrowUserId, createBy, borrowData);
                if(res == -1){
                   return AjaxResult.error("该设备状态异常，操作失败！");
                }
                return toAjax(res);
            } catch (Exception e) {
                e.printStackTrace();
                return error();
            }
        }
        return error();
    }

    @GetMapping("/myBorrow")
    public String myAllocate() {
        return prefix + "/myBorrow";
    }

    @PostMapping("/myBorrowList")
    @ResponseBody
    public TableDataInfo myAllocateTable() {
        //获取当前系统用户id
        SysUser currentUser = ShiroUtils.getSysUser();
        if (currentUser == null) {
            return getDataTable(new ArrayList<>());
        }
        startPage();
        List<AssetsBorrow> myBorrowListByUserId = borrowService.getBorrowListByUserId(String.valueOf(currentUser.getUserId()));
        return getDataTable(myBorrowListByUserId);
    }

    @GetMapping("/myExamine")
    public String myExamine() {
        return prefix + "/myExamine";
    }

    @PostMapping("/myExamineList")
    @ResponseBody
    public TableDataInfo myExamineList() {
        startPage();
        return getDataTable(borrowService.getMyExamineList());
    }

    @PostMapping("/examineOK/{orderNum}/{userId}")
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

    @PostMapping("/examineReject/{orderNum}/{userId}")
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

}