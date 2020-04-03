package com.ams.web.controller.assets;

import com.ams.common.core.controller.BaseController;
import com.ams.common.core.domain.AjaxResult;
import com.ams.common.core.page.TableDataInfo;
import com.ams.common.enums.AssetsStatus;
import com.ams.system.domain.*;
import com.ams.system.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 报废
 *
 * @author zengchao
 */
@Controller
@RequestMapping("/assets/alarm")
public class AssetsAlarmController extends BaseController {
    private String prefix = "/assets/alarm";

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


    @GetMapping()
    public String alarm() {
        return prefix + "/alarm";
    }

    /**
     * 待报废列表
     *
     * @return
     */
    @PostMapping("list")
    @ResponseBody
    public TableDataInfo alarmList() {
        startPage();
        List<Assets> scrappedStatusAssetsList = accountingService.getScrappedStatusAssetsList();
        return getDataTable(scrappedStatusAssetsList);
    }


    @GetMapping("/scrapped")
    public String scrapped() {
        return prefix + "/scrapped";
    }

    /**
     * 已停用列表
     *
     * @return
     */
    @PostMapping("/scrappedList")
    @ResponseBody
    public TableDataInfo scrappedList() {
        startPage();
        List<Assets> disableStatusAssetsList = accountingService.getDisableStatusAssetsList();
        return getDataTable(disableStatusAssetsList);
    }

    /**
     * 删除待报废信息
     *
     * @param assetsNumber
     * @return
     */
    @PostMapping("/remove/{assetsNumber}")
    @ResponseBody
    public AjaxResult remove(@PathVariable("assetsNumber") String assetsNumber) {
        Assets assetsByNumber = accountingService.getAssetsByNumber(assetsNumber);
        assetsByNumber.setUseStatus(AssetsStatus.NORMAL.getCode());
        List<Assets> list = new ArrayList<>();
        list.add(assetsByNumber);
        return toAjax(accountingService.updateAssetsLists(list));
    }

    /**
     * 停用资产（报废）
     *
     * @param assetsNumber
     * @return
     */
    @PostMapping("/disable/{assetsNumber}")
    @ResponseBody
    public AjaxResult disableAssets(@PathVariable("assetsNumber") String assetsNumber) {
        Assets assetsByNumber = accountingService.getAssetsByNumber(assetsNumber);
        assetsByNumber.setUseStatus(AssetsStatus.DISABLE.getCode());
        List<Assets> list = new ArrayList<>();
        list.add(assetsByNumber);
        return toAjax(accountingService.updateAssetsLists(list));
    }


}