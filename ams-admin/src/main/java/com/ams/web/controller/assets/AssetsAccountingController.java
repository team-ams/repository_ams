package com.ams.web.controller.assets;

import com.ams.common.annotation.Log;
import com.ams.common.constant.UserConstants;
import com.ams.common.core.controller.BaseController;
import com.ams.common.core.domain.AjaxResult;
import com.ams.common.core.page.TableDataInfo;
import com.ams.common.enums.BusinessType;
import com.ams.common.utils.poi.ExcelUtil;
import com.ams.framework.shiro.service.SysPasswordService;
import com.ams.framework.util.ShiroUtils;
import com.ams.system.domain.Assets;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 用户信息
 *
 * @author zengchao
 */
@Controller
@RequestMapping("/assets/accounting")
public class AssetsAccountingController extends BaseController {
    private static List<String> assetsNumbers = new ArrayList<>();
    private static final String FID = "9";
    private static final String MSG = "I'm from Server---accounting";
    private String prefix = "/assets/accounting";

    @Autowired
    private IAssetsAccountingService accountingService;

    @Autowired
    private IAssetsSourceService sourceService;

    @RequiresPermissions("assets:accounting:view")
    @GetMapping()
    public String assets() {
        return prefix + "/accounting";
    }

    @RequiresPermissions("assets:accounting:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Assets assets) {
        List<Assets> assetsList = accountingService.getAssetsList(assets);
        startPage();
        return getDataTable(assetsList);
    }

    @Log(title = "资产管理", businessType = BusinessType.EXPORT)
    @RequiresPermissions("assets:accounting:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Assets assets) {
        List<Assets> list = accountingService.getAssetsList(assets);
        ExcelUtil<Assets> util = new ExcelUtil<Assets>(Assets.class);
        return util.exportExcel(list, "资产数据");
    }

    @Log(title = "资产管理", businessType = BusinessType.IMPORT)
    @RequiresPermissions("assets:accounting:import")
    @PostMapping("/importData")
    @ResponseBody
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception {
        ExcelUtil<Assets> util = new ExcelUtil<Assets>(Assets.class);
        List<Assets> assetsList = util.importExcel(file.getInputStream());
        String operName = ShiroUtils.getSysUser().getLoginName();
        String message = accountingService.importAssets(assetsList, updateSupport, operName);
        return AjaxResult.success(message);
    }

    @RequiresPermissions("assets:accounting:view")
    @GetMapping("/importTemplate")
    @ResponseBody
    public AjaxResult importTemplate() {
        ExcelUtil<Assets> util = new ExcelUtil<Assets>(Assets.class);
        return util.importTemplateExcel("资产数据");
    }

    /**
     * 新增资产
     */
    @GetMapping("/add")
    public String add(ModelMap mmap,HttpServletRequest request) {
        mmap.put("sources", sourceService.getAssetsSourceAll());
        mmap.put("basePath", request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/");
        mmap.put("fid", FID);
        return prefix + "/add";
    }

    /**
     * 新增保存资产
     */
    @RequiresPermissions("assets:accounting:add")
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
    @GetMapping("/edit/{assetsNumber}")
    public String edit(@PathVariable("assetsNumber") String assetsNumber, ModelMap mmap) {
        mmap.put("assets", accountingService.getAssetsByNumber(assetsNumber));
        return prefix + "/edit";
    }

    /**
     * 修改保存资产
     */
    @RequiresPermissions("assets:accounting:edit")
    @Log(title = "资产管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(@Validated Assets assets) {
        assets.setUpdateBy(ShiroUtils.getLoginName());
        List<Assets> updateList = new ArrayList<>();
        updateList.add(assets);
        return toAjax(accountingService.updateAssetsLists(updateList));
    }


    @RequiresPermissions("assets:accounting:remove")
    @Log(title = "用户管理", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        try {
            return toAjax(accountingService.deleteAssetsByNumbers(ids));
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

    /**
     * MFRC522通过ESP8266将ID卡号发送过来，提供给esp8266调用的接口
     *
     * @param request
     */
    @RequestMapping("/getMsg")
    @ResponseBody
    public String getMsg(HttpServletRequest request) {
        Map<String, String> parameterMap = getParameterMap();
        String assetsNumber = parameterMap.get("assetsNumber");
        String assetsNumber1 = request.getParameter("assetsNumber");

        if(assetsNumber != null && !assetsNumber.isEmpty()){
            //通知前端页面有新数据
            pushToWeb(FID, assetsNumber);
            System.out.println(parameterMap);
            //return AjaxResult.success("Success",prefix + "/inputAssetsNumber");
            return "input success!";
        }
        System.out.println("ERROR---:"+parameterMap);
        //return AjaxResult.error("Error");
        return "Error";
    }


}