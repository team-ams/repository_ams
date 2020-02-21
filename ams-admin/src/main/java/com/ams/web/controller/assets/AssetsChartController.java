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
import com.ams.system.domain.Assets;
import com.ams.system.domain.LingYong;
import com.ams.system.domain.RuKu;
import com.ams.system.service.IAssetsAccountingService;
import com.ams.system.service.IAssetsAllocateService;
import com.ams.system.service.IAssetsSourceService;
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


    @GetMapping("/ruKu")
    public String ruKuIndex() {
        return prefix + "/accountingChart";
    }

    @PostMapping("/ruKu")
    @ResponseBody
    public List<RuKu> ruKu(HttpServletRequest request) {
        String params = request.getParameter("params");
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

}