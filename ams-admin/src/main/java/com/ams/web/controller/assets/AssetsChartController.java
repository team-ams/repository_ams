package com.ams.web.controller.assets;

import com.alibaba.fastjson.JSONArray;
import com.ams.common.annotation.Log;
import com.ams.common.constant.UserConstants;
import com.ams.common.core.controller.BaseController;
import com.ams.common.core.domain.AjaxResult;
import com.ams.common.core.page.TableDataInfo;
import com.ams.common.enums.BusinessType;
import com.ams.common.json.JSONObject;
import com.ams.common.utils.poi.ExcelUtil;
import com.ams.framework.util.ShiroUtils;
import com.ams.system.domain.*;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 报表
 *
 * @author zengchao
 */
@Controller
@RequestMapping("/assets/chart")
public class AssetsChartController extends BaseController {
    private String prefix = "/assets/chart";

    @Autowired
    private IAssetsAccountingService accountingService;
    @Autowired
    private IAssetsAllocateService allocateService;
    @Autowired
    private IAssetsBorrowService borrowService;
    @Autowired
    private IAssetsReturnService returnService;
    @Autowired
    private IAssetsRepairService repairService;
    @Autowired
    private IAssetsCheckTaskService checkTaskService;


    @GetMapping("/ruKu")
    public String ruKuIndex() {
        return prefix + "/accountingChart";
    }

    @PostMapping("/ruKu")
    @ResponseBody
    public List<RuKu> ruKu(HttpServletRequest request) {
        String startTime = request.getParameter("params[beginTime]");
        String endTime = request.getParameter("params[endTime]");


        //获得分类数据   可根据时间查询
        List<RuKu> countGroupByName = accountingService.getCountGroupByName(startTime, endTime);

        return countGroupByName;
    }

    @GetMapping("/lingYong")
    public String lingYongIndex() {
        return prefix + "/allocateChart";
    }

    @PostMapping("/lingYong")
    @ResponseBody
    public Map<String, List<LingYong>> lingYong() {

        Map<String, List<LingYong>> listMap = new HashMap<>();

        List<LingYong> dataGroupByDay = allocateService.getDataGroupByDay();
        List<LingYong> dataGroupByYear = allocateService.getDataGroupByYear();
        List<LingYong> dataGroupByMonth = allocateService.getDataGroupByMonth();
        listMap.put("dataGroupByDay", dataGroupByDay);
        listMap.put("dataGroupByYear", dataGroupByYear);
        listMap.put("dataGroupByMonth", dataGroupByMonth);

        return listMap;
    }

    @GetMapping("/jieHuan")
    public String jieHuanIndex() {
        return prefix + "/borrowReturnChart";
    }

    @PostMapping("/jieYong")
    @ResponseBody
    public Map<String, List<JieYong>> jieYong() {

        Map<String, List<JieYong>> listMap = new HashMap<>();

        List<JieYong> dataGroupByDay = borrowService.getDataGroupByDay();
        List<JieYong> dataGroupByYear = borrowService.getDataGroupByYear();
        List<JieYong> dataGroupByMonth = borrowService.getDataGroupByMonth();
        listMap.put("dataGroupByDay", dataGroupByDay);
        listMap.put("dataGroupByYear", dataGroupByYear);
        listMap.put("dataGroupByMonth", dataGroupByMonth);

        return listMap;
    }
    @PostMapping("/guiHuan")
    @ResponseBody
    public Map<String, List<GuiHuan>> guiHuan() {

        Map<String, List<GuiHuan>> listMap = new HashMap<>();

        List<GuiHuan> dataGroupByDay = returnService.getDataGroupByDay();
        List<GuiHuan> dataGroupByYear = returnService.getDataGroupByYear();
        List<GuiHuan> dataGroupByMonth = returnService.getDataGroupByMonth();
        listMap.put("dataGroupByDay", dataGroupByDay);
        listMap.put("dataGroupByYear", dataGroupByYear);
        listMap.put("dataGroupByMonth", dataGroupByMonth);

        return listMap;
    }

    @GetMapping("/weiXiu")
    public String weiXiuIndex() {
        return prefix + "/repairChart";
    }

    @PostMapping("/weiXiu")
    @ResponseBody
    public List<WeiXiu> weiXiu() {

        List<WeiXiu> weiXiuList = repairService.getCountAndFeesGroupByDate();


        return weiXiuList;
    }

    @GetMapping("/panDian")
    public String panDianIndex() {
        return prefix + "/checkChart";
    }

    @PostMapping("/panDian")
    @ResponseBody
    public List<AssetsCheckTask> panDian(HttpServletRequest request) {
        String startTime = request.getParameter("params[beginTime]");
        String endTime = request.getParameter("params[endTime]");
        List<AssetsCheckTask> assetsCheckTasks = checkTaskService.staticCheckTaskByDate(startTime, endTime);
        return assetsCheckTasks;
    }

    @GetMapping("/ziChan")
    public String ziChanIndex() {
        return prefix + "/assetsChart";
    }

    @PostMapping("/ziChan")
    @ResponseBody
    public List<ZiChan> ziChan() {

        List<ZiChan> useStatusList = accountingService.getCountByUseStatus();

        return useStatusList;
    }

}