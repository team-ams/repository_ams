package com.ams.web.controller.assets;

import com.ams.common.core.websocket.*;
import com.ams.common.core.controller.BaseController;
import com.ams.common.core.domain.AjaxResult;
import com.ams.common.core.domain.RestResult;
import com.ams.common.core.page.TableDataInfo;
import com.ams.common.enums.AssetsAllocateStatus;
import com.ams.framework.util.ShiroUtils;
import com.ams.system.domain.Assets;
import com.ams.system.domain.AssetsAllocate;
import com.ams.system.domain.SysUser;
import com.ams.system.service.AssetsService;
import com.ams.system.service.IAssetsAccountingService;
import com.ams.system.service.IAssetsAllocateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

/**
 * @author zengchao
 */
@Controller
@RequestMapping("/assets/allocate")
public class AssetsAllocateController extends BaseController {

    private static List<String> assetsNumbers = new ArrayList<>();
    private static LinkedHashSet<Assets> assetsLinkedHashSet = new LinkedHashSet<>();

    private String prefix = "/assets/allocate";

    @Autowired
    private IAssetsAccountingService accountingService;
    @Autowired
    private IAssetsAllocateService allocateService;
    @Autowired
    private AssetsService assetsService;

    @GetMapping()
    public String allocate() {
        SysUser currentSysUser = ShiroUtils.getSysUser();
        if (currentSysUser != null) {
            //当前用户不是管理员，返回领用界面
            if (!currentSysUser.isAdmin()) {
                return prefix + "/allocate";
            }
        }
        //管理员返回管理员页面
        return prefix + "/allocateAdmin";
    }

    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Assets assets) {
        SysUser currentSysUser = ShiroUtils.getSysUser();
        if (currentSysUser != null) {
            //当前系统用户不是管理员，资产领用信息
            if (!currentSysUser.isAdmin()) {
                startPage();
                List<Assets> assetsList0 = accountingService.getAssetsList0(assets);
                return getDataTable(assetsList0);
            }
            //当前系统用户是管理员，待审批信息
            startPage();
            List<AssetsAllocate> allocateAdminList = allocateService.getAllocateAdminList();
            return getDataTable(allocateAdminList);
        }
        return getDataTable(new ArrayList<>());
    }

    @PostMapping("/allocate/{assetsNumber}")
    @ResponseBody
    public AjaxResult allocate(@PathVariable("assetsNumber") String assetsNumber) {

        String[] assetsNumbers = assetsNumber.split(",");
        SysUser currentSysUser = ShiroUtils.getSysUser();
        if (currentSysUser != null) {
            int allocateUserId = currentSysUser.getUserId().intValue();
            try {
                return toAjax(assetsService.allocateAssets(allocateUserId, assetsNumbers));
            } catch (Exception e) {
                e.printStackTrace();
                return error();
            }
        }
        return error();
    }


    @GetMapping("/myAllocate")
    public String myAllocate() {
        return prefix + "/myAllocate";
    }

    @PostMapping("/myAllocateList")
    @ResponseBody
    public TableDataInfo myAllocateTable() {
        //获取当前系统用户id
        SysUser currentUser = ShiroUtils.getSysUser();
        if (currentUser == null) {
            return getDataTable(new ArrayList<>());
        }
        startPage();
        List<AssetsAllocate> myAllocateListByUserId = allocateService.getMyAllocateListByUserId(currentUser.getUserId().intValue());
        return getDataTable(myAllocateListByUserId);
    }

    @GetMapping("/myAllocateExamine")
    public String myAllocateExamine() {
        return prefix + "/myAllocateExamine";
    }

    @PostMapping("/myAllocateExamineList")
    @ResponseBody
    public TableDataInfo myAllocateExamineList() {
        startPage();
        return getDataTable(allocateService.getMyAllocateExamineList());
    }

    @PostMapping("/allocateExamineOK/{assetsNumber}/{allocateUserId}")
    @ResponseBody
    public AjaxResult allocateExamineOK(@PathVariable("assetsNumber") String assetsNumber,
                                        @PathVariable("allocateUserId") int allocateUserId) {
        SysUser currentSysUser = ShiroUtils.getSysUser();
        if (currentSysUser != null) {
            int auditorId = currentSysUser.getUserId().intValue();
            try {
                return toAjax(assetsService.allocateExamine(auditorId, assetsNumber, allocateUserId, AssetsAllocateStatus.AGREE.getCode()));
            } catch (Exception e) {
                e.printStackTrace();
                return error();
            }
        }
        return error();
    }

    @PostMapping("/allocateExamineReject/{assetsNumber}/{allocateUserId}")
    @ResponseBody
    public AjaxResult allocateExamineReject(@PathVariable("assetsNumber") String assetsNumber,
                                            @PathVariable("allocateUserId") int allocateUserId) {
        SysUser currentSysUser = ShiroUtils.getSysUser();
        if (currentSysUser != null) {
            int auditorId = currentSysUser.getUserId().intValue();
            try {
                return toAjax(assetsService.allocateExamine(auditorId, assetsNumber, allocateUserId, AssetsAllocateStatus.REJECT.getCode()));
            } catch (Exception e) {
                e.printStackTrace();
                return error();
            }
        }
        return error();
    }

    /**
     * 点击“资产领用”后调用该方法
     *
     * @return
     */
    @GetMapping("/allocateParent/{cid}")
    public String allocateParent(@PathVariable("cid") String cid,HttpServletRequest request,ModelMap mmap) {
        mmap.put("basePath", request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/");
        mmap.put("cid", cid);
        //todo 要求MFRC522发送卡号
        return prefix + "/allocateAssets";
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
        pushToWeb("22","I'm from Server");
        mmap.put("content", "传过来的：" + assetsNumber);
        return prefix + "/allocate";
    }

    @PostMapping("/allocateList")
    @ResponseBody
    public TableDataInfo allocateList() {
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

    @PostMapping("/allocateConfirm")
    @ResponseBody
    public AjaxResult allocateConfirm(String numbers){
        System.out.println("############"+numbers);
        //清除卡号，复位
        assetsNumbers.clear();
        //清空表格信息，复位，避免下次把之前的数据重新显示出来
        assetsLinkedHashSet.clear();
        //点击“确认”后 通知前端页面刷新表格
        pushToWeb("22","I'm from Server Please refresh table");
        return allocate(numbers);

    }


    /**
     * @param cid
     * @return
     */
    @GetMapping("/socket/{cid}")
    public ModelAndView socket(@PathVariable String cid, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView(prefix + "/webSocket.html");
        mav.addObject("basePath", request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/");
        mav.addObject("cid", cid);
        return mav;
    }

}
