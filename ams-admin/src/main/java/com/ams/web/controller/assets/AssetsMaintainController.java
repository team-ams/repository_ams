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
import com.ams.system.domain.AssetsMaintain;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 用户信息
 *
 * @author zengchao
 */
@Controller
@RequestMapping("/assets/maintain")
public class AssetsMaintainController extends BaseController {
    private static List<String> assetsNumbers = new ArrayList<>();
    private static LinkedHashSet<Assets> assetsLinkedHashSet = new LinkedHashSet<>();
    private static final String FID = "2";
    private static final String MSG = "I'm from Server -----maintain";

    private String prefix = "/assets/maintain";

    @Autowired
    private IAssetsAccountingService accountingService;

    @Autowired
    private IAssetsMaintainService maintainService;

    @Autowired
    private AssetsService assetsService;

    @RequiresPermissions("assets:maintain:view")
    @GetMapping()
    public String assets() {
        SysUser currentSysUser = ShiroUtils.getSysUser();
        if (currentSysUser != null) {
            //当前用户不是管理员，返回领用界面
            if (!currentSysUser.isAdmin()) {
                return prefix + "/maintain";
            }
        }
        //管理员返回管理员页面
        return prefix + "/maintainAdmin";
    }

    @RequiresPermissions("assets:maintain:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(AssetsMaintain maintain, Assets assets) throws ParseException {
        SysUser currentSysUser = ShiroUtils.getSysUser();
        if (currentSysUser != null) {
            //当前系统用户不是管理员，个人保养信息
            if (!currentSysUser.isAdmin()) {
                List<Assets> assetsList01 = accountingService.getAssetsList01(assets);
                startPage();
                List<Assets> needMaintainList = assetsService.needMaintainList(assetsList01);
                return getDataTable(needMaintainList);
            }
            //当前系统用户是管理员，全部信息
            List<AssetsMaintain> maintainListAll = maintainService.getMaintainListAll(maintain);
            startPage();
            return getDataTable(maintainListAll);
        }
        return getDataTable(new ArrayList<>());
    }

    @Log(title = "资产管理", businessType = BusinessType.EXPORT)
    @RequiresPermissions("assets:maintain:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Assets assets) {
        List<Assets> list = accountingService.getAssetsList(assets);
        ExcelUtil<Assets> util = new ExcelUtil<Assets>(Assets.class);
        return util.exportExcel(list, "资产数据");
    }

    @Log(title = "资产管理", businessType = BusinessType.IMPORT)
    @RequiresPermissions("assets:maintain:import")
    @PostMapping("/importData")
    @ResponseBody
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception {
        ExcelUtil<Assets> util = new ExcelUtil<Assets>(Assets.class);
        List<Assets> userList = util.importExcel(file.getInputStream());
        String operName = ShiroUtils.getSysUser().getLoginName();
        String message = accountingService.importAssets(userList, updateSupport, operName);
        return AjaxResult.success(message);
    }

    @RequiresPermissions("assets:maintain:view")
    @GetMapping("/importTemplate")
    @ResponseBody
    public AjaxResult importTemplate() {
        ExcelUtil<Assets> util = new ExcelUtil<Assets>(Assets.class);
        return util.importTemplateExcel("资产数据");
    }


    /**
     * 修改资产
     */
    @GetMapping("/edit/{maintainId}")
    public String edit(@PathVariable("maintainId") String maintainId, ModelMap mmap) {
        mmap.put("maintain", maintainService.getMaintainById(maintainId));
        return prefix + "/edit";
    }

    /**
     * 修改保存资产
     */
    @Log(title = "资产管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(@Validated AssetsMaintain maintain) {
        maintain.setUpdateBy(ShiroUtils.getLoginName());
        return toAjax(maintainService.updateMaintainByOrderNum(maintain));
    }


    @Log(title = "用户管理", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        AssetsMaintain maintain = maintainService.getMaintainById(ids);
        if (maintain == null) {
            return error();
        }
        try {
            //根据单号批量删除
            return toAjax(maintainService.deleteMaintainByOrderNum(maintain.getMaintainOrderNum()));
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

    @PostMapping("/childTableList")
    @ResponseBody
    public TableDataInfo childTableList(HttpServletRequest request) {
        String orderNum = request.getParameter("orderNum");
        if (StringUtils.isNotEmpty(orderNum)) {

            List<AssetsMaintain> assetsMaintainBy = maintainService.getMaintainListByOrderNum(orderNum);
            startPage();
            return getDataTable(assetsMaintainBy);
        }
        return getDataTable(new ArrayList<>());
    }

    @GetMapping("/maintain/{assetsNumber}")
    public String borrow(@PathVariable("assetsNumber") String assetsNumber, ModelMap mmap) {
        mmap.put("assetsNumber", assetsNumber);
        return prefix + "/maintainAssets2";
    }

    /**
     * 新增（模态框）
     *
     * @param fid
     * @param mmap
     * @param request
     * @return
     */
    @GetMapping("/maintainModal/{fid}")
    public String maintainModal(@PathVariable("fid") String fid, ModelMap mmap, HttpServletRequest request) {
        mmap.put("basePath", request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/");
        mmap.put("fid", fid);
        return prefix + "/maintainAssets";
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
        return prefix + "/maintainAssets";
    }


    @PostMapping("/maintainList")
    @ResponseBody
    public TableDataInfo maintainList() {
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

    @PostMapping("/maintainConfirm")
    @ResponseBody
    public AjaxResult maintainConfirm(AssetsMaintain maintainData) {
        //清除卡号，复位
        assetsNumbers.clear();
        //清空表格信息，复位，避免下次把之前的数据重新显示出来
        assetsLinkedHashSet.clear();
        //点击“确认”后 通知前端页面刷新表格
        pushToWeb(FID, MSG);
        return maintainSave(maintainData);

    }

    public AjaxResult maintainSave(AssetsMaintain maintainData) {
        if (maintainData == null) {
            return error();
        }
        SysUser currentSysUser = ShiroUtils.getSysUser();
        if (currentSysUser != null) {
            int allocateUserId = currentSysUser.getUserId().intValue();
            String createBy = currentSysUser.getLoginName();
            try {
                return toAjax(assetsService.maintainAssets(allocateUserId, createBy, maintainData));
            } catch (Exception e) {
                e.printStackTrace();
                return error();
            }
        }
        return error();
    }

    @GetMapping("/myMaintain")
    public String myAllocate() {
        return prefix + "/myMaintain";
    }

    @PostMapping("/myMaintainList")
    @ResponseBody
    public TableDataInfo myAllocateTable() {
        //获取当前系统用户id
        SysUser currentUser = ShiroUtils.getSysUser();
        if (currentUser == null) {
            return getDataTable(new ArrayList<>());
        }
        startPage();
        List<AssetsMaintain> myMaintainListByUserId = maintainService.getMaintainListByUserId(String.valueOf(currentUser.getUserId()));
        return getDataTable(myMaintainListByUserId);
    }

    @GetMapping("/myExamine")
    public String myExamine() {
        return prefix + "/myExamine";
    }

    @PostMapping("/myExamineList")
    @ResponseBody
    public TableDataInfo myExamineList() {
        startPage();
        return getDataTable(maintainService.getMyExamineList());
    }

    @PostMapping("/examineOK/{orderNum}/{userId}")
    @ResponseBody
    public AjaxResult examineOK(@PathVariable("orderNum") String orderNum,
                                @PathVariable("userId") int userId) {
        SysUser currentSysUser = ShiroUtils.getSysUser();
        if (currentSysUser != null) {
            int auditorId = currentSysUser.getUserId().intValue();
            try {
                return toAjax(assetsService.maintainExamine(auditorId, orderNum, userId, AssetsExamineStatus.AGREE.getCode()));
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
                return toAjax(assetsService.maintainExamine(auditorId, orderNum, userId, AssetsExamineStatus.REJECT.getCode()));
            } catch (Exception e) {
                e.printStackTrace();
                return error();
            }
        }
        return error();
    }
}