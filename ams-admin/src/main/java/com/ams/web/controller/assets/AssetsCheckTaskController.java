package com.ams.web.controller.assets;

import com.ams.common.annotation.Log;
import com.ams.common.core.controller.BaseController;
import com.ams.common.core.domain.AjaxResult;
import com.ams.common.core.page.TableDataInfo;
import com.ams.common.enums.BusinessType;
import com.ams.common.utils.StringUtils;
import com.ams.common.utils.poi.ExcelUtil;
import com.ams.framework.util.ShiroUtils;
import com.ams.system.domain.Assets;
import com.ams.system.domain.AssetsCheckItem;
import com.ams.system.domain.AssetsCheckTask;
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

/**
 * 用户信息
 *
 * @author zengchao
 */
@Controller
@RequestMapping("/assets/checkTask")
public class AssetsCheckTaskController extends BaseController {
    private static List<String> assetsNumbers = new ArrayList<>();
    private static LinkedHashSet<Assets> assetsLinkedHashSet = new LinkedHashSet<>();
    private static LinkedHashSet<UserFormModel> users = new LinkedHashSet<UserFormModel>();
    private static final String FID = "6";
    private static final String MSG = "I'm from Server -----checkTask";

    private String prefix = "/assets/checkTask";

    @Autowired
    private IAssetsAccountingService accountingService;

    @Autowired
    private IAssetsCheckTaskService checkTaskService;

    @Autowired
    private IAssetsCheckItemService checkItemService;

    @Autowired
    private ISysUserService userService;

    @Autowired
    private AssetsService assetsService;

    @RequiresPermissions("assets:checkTask:view")
    @GetMapping()
    public String assets() {
        SysUser currentSysUser = ShiroUtils.getSysUser();
        if (currentSysUser != null) {
            //当前系统用户不是管理员，个人盘点任务
            if (!currentSysUser.isAdmin()) {
                return prefix + "/userCheckTask";
            }
        }
        if (users.size() > 0) {
            users.clear();
        }
        List<SysUser> userList = userService.selectUserList(new SysUser());
        for (SysUser user : userList) {
            UserFormModel model = new UserFormModel(
                    user.getUserId().intValue(),
                    user.getDeptId().intValue(),
                    user.getLoginName(),
                    user.getCardNumber());
            users.add(model);
        }
        return prefix + "/checkTask";
    }

    @RequiresPermissions("assets:checkTask:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(AssetsCheckTask checkTask) {
        SysUser currentSysUser = ShiroUtils.getSysUser();
        if (currentSysUser != null) {
            //当前系统用户不是管理员，个人保养信息
            if (!currentSysUser.isAdmin()) {
                //注意这步一定要在startPage()前面执行
                List<AssetsCheckTask> checkTaskListByUserId = checkTaskService.getCheckTaskByUserId(currentSysUser.getUserId().intValue());
                startPage();
                return getDataTable(checkTaskListByUserId);
            }
            //当前系统用户是管理员，全部信息
            //注意这步一定要在startPage()前面执行
            List<AssetsCheckTask> checkTaskListAll = checkTaskService.getCheckTaskAll(checkTask);
            startPage();
            return getDataTable(checkTaskListAll);

        }
        return getDataTable(new ArrayList<>());
    }

    @GetMapping("/checkRecord")
    public String checkRecord() {
        return prefix + "/myCheckTask";
    }

    @PostMapping("/checkRecordList")
    @ResponseBody
    public TableDataInfo checkRecordList() {
        SysUser currentSysUser = ShiroUtils.getSysUser();
        List<AssetsCheckTask> checkRecordByUserId = checkTaskService.getCheckRecordByUserId(currentSysUser.getUserId().intValue());
        startPage();
        return getDataTable(checkRecordByUserId);
    }

    @Log(title = "资产管理", businessType = BusinessType.EXPORT)
    @RequiresPermissions("assets:checkTask:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Assets assets) {
        List<Assets> list = accountingService.getAssetsList(assets);
        ExcelUtil<Assets> util = new ExcelUtil<Assets>(Assets.class);
        return util.exportExcel(list, "资产数据");
    }

    @Log(title = "资产管理", businessType = BusinessType.IMPORT)
    @RequiresPermissions("assets:checkTask:import")
    @PostMapping("/importData")
    @ResponseBody
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception {
        ExcelUtil<Assets> util = new ExcelUtil<Assets>(Assets.class);
        List<Assets> userList = util.importExcel(file.getInputStream());
        String operName = ShiroUtils.getSysUser().getLoginName();
        String message = accountingService.importAssets(userList, updateSupport, operName);
        return AjaxResult.success(message);
    }

    @RequiresPermissions("assets:checkTask:view")
    @GetMapping("/importTemplate")
    @ResponseBody
    public AjaxResult importTemplate() {
        ExcelUtil<Assets> util = new ExcelUtil<Assets>(Assets.class);
        return util.importTemplateExcel("资产数据");
    }

    /**
     * 修改资产
     */
    @GetMapping("/edit/{checkTaskId}")
    public String edit(@PathVariable("checkTaskId") String taskId, ModelMap mmap) {
        mmap.put("checkTask", checkTaskService.getCheckTaskByTaskId(Integer.parseInt(taskId)));
        return prefix + "/edit";
    }

    /**
     * 修改保存资产
     */
    @RequiresPermissions("assets:checkTask:edit")
    @Log(title = "资产管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(@Validated AssetsCheckTask checkTask) {
        //checkTask.setCheckUserId(checkTask.getCh);
        checkTask.setUpdateBy(ShiroUtils.getLoginName());
        return toAjax(checkTaskService.updateCheckTask(checkTask));
    }


    @RequiresPermissions("assets:checkTask:remove")
    @Log(title = "盘点管理", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        try {
            return toAjax(checkTaskService.deleteCheckTaskByTaskId(ids));
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

    @PostMapping("/adminExamineList")
    @ResponseBody
    public TableDataInfo adminExamineList() {
        List<AssetsCheckTask> checkedAll = checkTaskService.getCheckedAll();
        startPage();
        return getDataTable(checkedAll);
    }

    @GetMapping("adminExamine")
    public String adminExamine() {
        return prefix + "/adminExamine";
    }

    /**
     * 新增（模态框）
     *
     * @param fid
     * @param mmap
     * @param request
     * @return
     */
    @GetMapping("/checkBegin/{fid}/{checkTaskId}")
    public String checkTaskModal(@PathVariable("fid") String fid,
                                 @PathVariable("checkTaskId") String checkTaskId,
                                 ModelMap mmap,
                                 HttpServletRequest request) {
        //打开模态窗口时先清除上次残留的数据
        if (assetsNumbers.size() > 0) {
            assetsNumbers.clear();
        }
        if (assetsLinkedHashSet.size() > 0) {
            assetsLinkedHashSet.clear();
        }
        mmap.put("basePath", request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/");
        mmap.put("fid", fid);
        mmap.put("checkTask", checkTaskService.getCheckTaskByTaskId(Integer.parseInt(checkTaskId)));
        return prefix + "/checkAssets";
    }

    /**
     * 新增（模态框）
     *
     * @param fid
     * @param mmap
     * @param request
     * @return
     */
    @GetMapping("/reCheck/{fid}/{checkTaskId}")
    public String reCheckModal(@PathVariable("fid") String fid,
                               @PathVariable("checkTaskId") String checkTaskId,
                               ModelMap mmap,
                               HttpServletRequest request) {
        //打开模态窗口时先清除上次残留的数据
        if (assetsNumbers.size() > 0) {
            assetsNumbers.clear();
        }
        if (assetsLinkedHashSet.size() > 0) {
            assetsLinkedHashSet.clear();
        }
        mmap.put("basePath", request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/");
        mmap.put("fid", fid);
        mmap.put("checkTask", checkTaskService.getCheckTaskByTaskId(Integer.parseInt(checkTaskId)));
        return prefix + "/reCheckAssets";
    }

    /**
     * MFRC522通过ESP8266将ID卡号发送过来，提供给esp8266调用的接口
     *
     * @param request
     */
    @RequestMapping("/getMsg/{fid}")
    @ResponseBody
    public String getMsg(@PathVariable("fid") String fid, HttpServletRequest request, ModelMap mmap) {
        Map<String, String> parameterMap = getParameterMap();
        String assetsNumber = parameterMap.get("assetsNumber");
        assetsNumbers.add(assetsNumber);
        System.out.println(parameterMap);

        //通知前端页面有新数据
        pushToWeb(fid, MSG);
        mmap.put("content", "传过来的：" + assetsNumber);
        return prefix + "";
    }


    @PostMapping("/checkAssetsList")
    @ResponseBody
    public TableDataInfo checkTaskList() {
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

    @PostMapping("/checkConfirm")
    @ResponseBody
    public AjaxResult checkConfirm(HttpServletRequest request) {
        //清除卡号，复位
        assetsNumbers.clear();
        //清空表格信息，复位，避免下次把之前的数据重新显示出来
        assetsLinkedHashSet.clear();
        //获得当前用户登录名 作 更新者
        SysUser currentSysUser = ShiroUtils.getSysUser();
        String updateBy = currentSysUser.getLoginName();
        //盘点得到的资产编号
        String assetsNumbers = request.getParameter("assetsNumber");
        if (assetsNumbers == null || assetsNumbers.equals("")) {
            return error();
        }
        String[] split = assetsNumbers.split(",");
        List<String> checkResultList = Arrays.asList(split);
        //盘点任务id
        String taskId = request.getParameter("taskId");
        if (taskId == null || taskId.equals("")) {
            return error();
        }
        try {
            return toAjax(assetsService.checkFinish(Integer.parseInt(taskId), checkResultList, updateBy));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return error();
    }

    @PostMapping("/reCheckConfirm")
    @ResponseBody
    public AjaxResult reCheckConfirm(HttpServletRequest request) {
        //清除卡号，复位
        assetsNumbers.clear();
        //清空表格信息，复位，避免下次把之前的数据重新显示出来
        assetsLinkedHashSet.clear();
        String checkNumber = request.getParameter("checkNumber");
        if (checkNumber == null || checkNumber.equals("")) {
            return error();
        }
        //删掉上次盘点 为盘盈状态的盘点项
        checkItemService.deleteCheckProfitItemByCheckNumber(checkNumber);
        //获得当前用户登录名 作 更新者
        SysUser currentSysUser = ShiroUtils.getSysUser();
        String updateBy = currentSysUser.getLoginName();
        //盘点得到的资产编号
        String assetsNumbers = request.getParameter("assetsNumber");
        if (assetsNumbers == null || assetsNumbers.equals("")) {
            return error();
        }
        String[] split = assetsNumbers.split(",");
        List<String> checkResultList = Arrays.asList(split);
        //盘点任务id
        String taskId = request.getParameter("taskId");
        if (taskId == null || taskId.equals("")) {
            return error();
        }
        try {
            return toAjax(assetsService.checkFinish(Integer.parseInt(taskId), checkResultList, updateBy));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return error();
    }


    @PostMapping("closeModal")
    public void closeModal() {
        //清除卡号，复位
        assetsNumbers.clear();
        //清空表格信息，复位，避免下次把之前的数据重新显示出来
        assetsLinkedHashSet.clear();
    }

    @PostMapping("/checkTaskConfirm")
    @ResponseBody
    public AjaxResult checkTaskConfirm(HttpServletRequest request) {

        String loginName = request.getParameter("checkUserId");
        SysUser user = userService.selectUserByLoginName(loginName);
        String checkUserId = user.getUserId().toString();
        String checkAddr = request.getParameter("checkAddr");
        return checkTaskSave(checkUserId, checkAddr);

    }

    public AjaxResult checkTaskSave(String checkUserId, String checkAddr) {
        if (checkUserId == null || checkAddr == null) {
            return error();
        }
        SysUser currentSysUser = ShiroUtils.getSysUser();
        if (currentSysUser != null) {
            String createBy = currentSysUser.getLoginName();
            try {
                return toAjax(assetsService.checkTaskAssets(createBy, checkUserId, checkAddr));
            } catch (Exception e) {
                e.printStackTrace();
                return error();
            }
        }
        return error();
    }

    @GetMapping("/checkDetails/{checkNumber}")
    public String checkDetails(@PathVariable("checkNumber") String checkNumber, ModelMap mmap) {
        mmap.put("checkNumber", checkNumber);
        return prefix + "/checkDetails";
    }

    @PostMapping("/checkDetailsList/{checkNumber}")
    @ResponseBody
    public TableDataInfo checkDetailsList(@PathVariable("checkNumber") String checkNumber) {
        List<AssetsCheckItem> checkItemList = checkItemService.getCheckItemByCheckNumber(checkNumber);
        startPage();
        return getDataTable(checkItemList);
    }

    @PostMapping("/childTableList")
    @ResponseBody
    public TableDataInfo childTableList(HttpServletRequest request) {
        String checkNumber = request.getParameter("checkNumber");
        if (StringUtils.isNotEmpty(checkNumber)) {

            List<AssetsCheckItem> checkItemList = checkItemService.getCheckItemByCheckNumber(checkNumber);
            startPage();
            return getDataTable(checkItemList);
        }
        return getDataTable(new ArrayList<>());
    }

    /**
     * 盘点通过
     *
     * @param taskId
     * @return
     */
    @PostMapping("/checkOK/{taskId}")
    @ResponseBody
    public AjaxResult checkOK(@PathVariable("taskId") String taskId) {
        if (taskId.equals("")) {
            return error();
        }
        try {
            return toAjax(assetsService.checkExamineOK(taskId));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return error();
    }

    /**
     * 盘点驳回
     *
     * @param taskId
     * @return
     */
    @PostMapping("/checkReject/{taskId}")
    @ResponseBody
    public AjaxResult checkReject(@PathVariable("taskId") String taskId) {
        if (taskId.equals("")) {
            return error();
        }
        try {
            return toAjax(assetsService.checkExamineReject(taskId));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return error();
    }

    /**
     * 获取用户数据
     */
    @GetMapping("/userModel")
    @ResponseBody
    public AjaxResult userModel() {
        AjaxResult ajax = new AjaxResult();
        ajax.put("code", 200);
        ajax.put("value", users);
        return ajax;
    }

    /**
     * 获取数据集合
     */
    @GetMapping("/collection")
    @ResponseBody
    public AjaxResult collection() {

        List<String> addrNameList = accountingService.getStorageAddrAll();
        AjaxResult ajax = new AjaxResult();
        ajax.put("value", addrNameList);
        return ajax;
    }


    class UserFormModel {
        /**
         * 用户ID
         */
        private int userId;

        /**
         * 用户部门编号
         */
        private int deptId;

        /**
         * 用户姓名
         */
        private String loginName;

        /**
         * 用户卡号
         */
        private String cardNumber;

        public UserFormModel() {
        }

        public UserFormModel(int userId, int deptId, String loginName, String cardNumber) {
            this.userId = userId;
            this.deptId = deptId;
            this.loginName = loginName;
            this.cardNumber = cardNumber;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getDeptId() {
            return deptId;
        }

        public void setDeptId(int deptId) {
            this.deptId = deptId;
        }

        public String getLoginName() {
            return loginName;
        }

        public void setLoginName(String loginName) {
            this.loginName = loginName;
        }

        public String getCardNumber() {
            return cardNumber;
        }

        public void setCardNumber(String cardNumber) {
            this.cardNumber = cardNumber;
        }
    }

}

