package com.ams.framework.web.service;

import com.ams.system.domain.Assets;
import com.ams.system.service.AssetsService;
import com.ams.system.service.IAssetsAccountingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;

@Service("Tips")
public class TipsService {

    @Autowired
    private IAssetsAccountingService accountingService;
    @Autowired
    private AssetsService assetsService;

    public int getNeedMaintainCount() throws ParseException {
        List<Assets> assetsList = accountingService.getAssetsList01(new Assets());
        List<Assets> needMaintainList = assetsService.needMaintainList(assetsList);
        return needMaintainList.size();
    }

    public int getScrappedCount() {
        List<Assets> scrappedStatusAssetsList = accountingService.getScrappedStatusAssetsList();
        return scrappedStatusAssetsList.size();
    }
}
