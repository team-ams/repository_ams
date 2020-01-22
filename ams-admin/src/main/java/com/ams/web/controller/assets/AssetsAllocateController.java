package com.ams.web.controller.assets;

import com.ams.common.core.controller.BaseController;
import com.ams.common.core.domain.AjaxResult;
import com.ams.common.core.page.TableDataInfo;
import com.ams.common.enums.AssetsStutus;
import com.ams.system.domain.Assets;
import com.ams.system.service.IAssetsAccountingService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zengchao
 */
@Controller
@RequestMapping("/assets/allocate")
public class AssetsAllocateController extends BaseController {

    private String prefix = "/assets/allocate";

    @Autowired
    private IAssetsAccountingService accountingService;

    @RequiresPermissions("assets:allocate:view")
    @GetMapping()
    public String allocate() {
        return prefix + "/allocate";
    }

    @RequiresPermissions("assets:allocate:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Assets assets) {
        startPage();
        List<Assets> assetsList0 = accountingService.getAssetsList0(assets);
        return getDataTable(assetsList0);
    }

    @RequiresPermissions("assets:allocate:allocate")
    @PostMapping("/allocate/{assetsNumber}")
    @ResponseBody
    public AjaxResult allocate(@PathVariable("assetsNumber") String assetsNumber) {
        Assets update = accountingService.getAssetsByNumber(assetsNumber);
        if (update == null) {
            return AjaxResult.error("资产信息错误！");
        }
        update.setUseStatus(AssetsStutus.TOAPPROVAL.getCode());
        try {

            return toAjax(accountingService.updateAssets(update));
        } catch (Exception e) {
            return error(e.getMessage());
        }

    }


}
