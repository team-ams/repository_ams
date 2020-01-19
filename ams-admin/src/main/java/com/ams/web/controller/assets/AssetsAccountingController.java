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

import java.util.List;

/**
 * 用户信息
 * 
 * @author zengchao
 */
@Controller
@RequestMapping("/assets/accounting")
public class AssetsAccountingController extends BaseController
{
    private String prefix = "/assets/accounting";

    @Autowired
    private ISysUserService userService;

    @Autowired
    private IAssetsAccountingService accountingService;

    @Autowired
    private IAssetsSourceService sourceService;

    @Autowired
    private ISysRoleService roleService;

    @Autowired
    private ISysPostService postService;

    @Autowired
    private SysPasswordService passwordService;

    @RequiresPermissions("assets:accounting:view")
    @GetMapping()
    public String user()
    {
        return prefix + "/accounting";
    }

    @RequiresPermissions("assets:accounting:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Assets assets)
    {
        startPage();
        List<Assets> assetsList = accountingService.getAssetsList(assets);
        return getDataTable(assetsList);
    }

    @Log(title = "用户管理", businessType = BusinessType.EXPORT)
    @RequiresPermissions("assets:accounting:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysUser user)
    {
        List<SysUser> list = userService.selectUserList(user);
        ExcelUtil<SysUser> util = new ExcelUtil<SysUser>(SysUser.class);
        return util.exportExcel(list, "用户数据");
    }

    @Log(title = "用户管理", businessType = BusinessType.IMPORT)
    @RequiresPermissions("assets:accounting:import")
    @PostMapping("/importData")
    @ResponseBody
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        ExcelUtil<Assets> util = new ExcelUtil<Assets>(Assets.class);
        List<Assets> userList = util.importExcel(file.getInputStream());
        String operName = ShiroUtils.getSysUser().getLoginName();
        String message = accountingService.importAssets(userList,updateSupport,operName);
        return AjaxResult.success(message);
    }

    @RequiresPermissions("assets:accounting:view")
    @GetMapping("/importTemplate")
    @ResponseBody
    public AjaxResult importTemplate()
    {
        ExcelUtil<SysUser> util = new ExcelUtil<SysUser>(SysUser.class);
        return util.importTemplateExcel("用户数据");
    }

    /**
     * 新增用户
     */
    @GetMapping("/add")
    public String add(ModelMap mmap)
    {
        mmap.put("roles", roleService.selectRoleAll());
        mmap.put("posts", postService.selectPostAll());
        mmap.put("sources",sourceService.getAssetsSourceAll());
        return prefix + "/add";
    }

    /**
     * 新增保存用户
     */
    @RequiresPermissions("assets:accounting:add")
    @Log(title = "用户管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@Validated Assets assets)
    {
        if (UserConstants.USER_NAME_NOT_UNIQUE.equals(accountingService.checkAssetsNumberUnique(assets.getAssetsNumber())))
        {
            return error("新增资产'" + assets.getAssetsNumber() + "'失败，资产编号已存在");
        }
        assets.setCreateBy(ShiroUtils.getLoginName());
        return toAjax(accountingService.insertAssets(assets));
    }

    /**
     * 修改资产
     */
    @GetMapping("/edit/{assetsNumber}")
    public String edit(@PathVariable("assetsNumber") String assetsNumber, ModelMap mmap)
    {
        mmap.put("assets",accountingService.getAssetsByNumber(assetsNumber));
        return prefix + "/edit";
    }

    /**
     * 修改保存资产
     */
    @RequiresPermissions("assets:accounting:edit")
    @Log(title = "资产管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(@Validated Assets assets)
    {
        assets.setUpdateBy(ShiroUtils.getLoginName());
        return toAjax(accountingService.updateAssets(assets));
    }

    @RequiresPermissions("assets:accounting:resetPwd")
    @Log(title = "重置密码", businessType = BusinessType.UPDATE)
    @GetMapping("/resetPwd/{userId}")
    public String resetPwd(@PathVariable("userId") Long userId, ModelMap mmap)
    {
        mmap.put("user", userService.selectUserById(userId));
        return prefix + "/resetPwd";
    }

    @RequiresPermissions("assets:accounting:resetPwd")
    @Log(title = "重置密码", businessType = BusinessType.UPDATE)
    @PostMapping("/resetPwd")
    @ResponseBody
    public AjaxResult resetPwdSave(SysUser user)
    {
        userService.checkUserAllowed(user);
        user.setSalt(ShiroUtils.randomSalt());
        user.setPassword(passwordService.encryptPassword(user.getLoginName(), user.getPassword(), user.getSalt()));
        if (userService.resetUserPwd(user) > 0)
        {
            if (ShiroUtils.getUserId() == user.getUserId())
            {
                ShiroUtils.setSysUser(userService.selectUserById(user.getUserId()));
            }
            return success();
        }
        return error();
    }

    @RequiresPermissions("assets:accounting:remove")
    @Log(title = "用户管理", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        try
        {
            return toAjax(userService.deleteUserByIds(ids));
        }
        catch (Exception e)
        {
            return error(e.getMessage());
        }
    }

    /**
     * 校验用户名
     */
    @PostMapping("/checkLoginNameUnique")
    @ResponseBody
    public String checkLoginNameUnique(SysUser user)
    {
        return userService.checkLoginNameUnique(user.getLoginName());
    }

    /**
     * 校验手机号码
     */
    @PostMapping("/checkPhoneUnique")
    @ResponseBody
    public String checkPhoneUnique(SysUser user)
    {
        return userService.checkPhoneUnique(user);
    }

    /**
     * 校验email邮箱
     */
    @PostMapping("/checkEmailUnique")
    @ResponseBody
    public String checkEmailUnique(SysUser user)
    {
        return userService.checkEmailUnique(user);
    }

    /**
     * 用户状态修改
     */
    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @RequiresPermissions("assets:accounting:edit")
    @PostMapping("/changeStatus")
    @ResponseBody
    public AjaxResult changeStatus(SysUser user)
    {
        userService.checkUserAllowed(user);
        return toAjax(userService.changeStatus(user));
    }
}