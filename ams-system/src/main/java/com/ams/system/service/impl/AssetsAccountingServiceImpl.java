package com.ams.system.service.impl;

import com.ams.common.constant.AssetsConstants;
import com.ams.common.core.text.Convert;
import com.ams.common.enums.AssetsStatus;
import com.ams.common.exception.BusinessException;
import com.ams.common.utils.StringUtils;
import com.ams.system.domain.Assets;
import com.ams.system.domain.RuKu;
import com.ams.system.domain.ZiChan;
import com.ams.system.mapper.AssetsAccountingMapper;
import com.ams.system.service.IAssetsAccountingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author zengchao
 * @since 创建时间：2020/1/13 9:18
 */
@Service
public class AssetsAccountingServiceImpl implements IAssetsAccountingService {

    @Autowired
    private static final Logger log = LoggerFactory.getLogger(AssetsAccountingServiceImpl.class);

    @Autowired
    private AssetsAccountingMapper assetsAccountingMapper;


    @Override
    public String checkAssetsNumberUnique(String assetsNumber) {
        int counts = assetsAccountingMapper.checkAssetsNumberUnique(assetsNumber);
        if (counts > 0) {
            return AssetsConstants.ASSETS_NAME_NOT_UNIQUE;
        }
        return AssetsConstants.ASSETS_NAME_UNIQUE;
    }

    @Override
    public String importAssets(List<Assets> assetsList, Boolean isUpdateSupport, String operName) {
        if (StringUtils.isNull(assetsList) || assetsList.size() == 0) {
            throw new BusinessException("导入资产信息不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (Assets assets : assetsList) {
            try {
                // 验证是否存在这个资产
                Assets a = assetsAccountingMapper.getAssetsByAssetsNumber(assets.getAssetsNumber());
                if (StringUtils.isNull(a)) {
                    assets.setCreateBy(operName);
                    this.insertAssets(assets);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、资产 " + assets.getAssetsNumber() + " 导入成功");
                } else if (isUpdateSupport) {
                    assets.setUpdateBy(operName);
                    List<Assets> updateList = new ArrayList<>();
                    updateList.add(assets);
                    this.updateAssetsLists(updateList);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、资产 " + assets.getAssetsNumber() + " 更新成功");
                } else {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、资产 " + assets.getAssetsNumber() + " 已存在");
                }
            } catch (Exception e) {
                failureNum++;
                String msg = "<br/>" + failureNum + "、资产 " + assets.getAssetsNumber() + " 导入失败：";
                failureMsg.append(msg + e.getMessage());
                log.error(msg, e);
            }
        }
        if (failureNum > 0) {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new BusinessException(failureMsg.toString());
        } else {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }

    @Override
    public int insertAssets(Assets assets) {
        int rows = assetsAccountingMapper.insertAssets(assets);
        return rows;
    }

    /**
     * 批量更新资产信息（可单条）
     *
     * @param assetsList
     * @return
     */
    @Override
    public int updateAssetsLists(List<Assets> assetsList) {
        return assetsAccountingMapper.updateAssetsList(assetsList);
    }


    @Override
    public List<Assets> getAssetsList(Assets assets) {
        List<Assets> assetsList = assetsAccountingMapper.getAssetsList(assets);
        for (Assets item : assetsList) {
            item.setUseStatus(AssetsStatus.getStatusByCode(item.getUseStatus()).getInfo());
        }
        return assetsList;
    }

    @Override
    public List<Assets> getAssetsList0(Assets assets) {
        List<Assets> assetsList0 = assetsAccountingMapper.getAssetsList0(assets);
        for (Assets item : assetsList0) {
            item.setUseStatus(AssetsStatus.getStatusByCode(item.getUseStatus()).getInfo());
        }
        return assetsList0;
    }

    /**
     * 根据资产编号查找资产信息（可批量查询）
     *
     * @param assetsNumbers
     * @return
     */
    @Override
    public List<Assets> getAssetsByNumbers(String[] assetsNumbers) {
        return assetsAccountingMapper.getAssetsByNumbers(assetsNumbers);
    }

    @Override
    public Assets getAssetsByNumber(String assetsNumber) {
        return assetsAccountingMapper.getAssetsByAssetsNumber(assetsNumber);
    }

    /**
     * 批量删除资产信息
     *
     * @param numbers
     * @return
     * @throws BusinessException
     */
    @Override
    public int deleteAssetsByNumbers(String numbers) throws BusinessException {
        String[] assetsNumbers = Convert.toStrArray(numbers);
        return assetsAccountingMapper.deleteAssetsByNumbers(assetsNumbers);
    }

    @Override
    public List<Assets> getAssetsByNumberList(List<String> assetsNumbers) {
        List<Assets> assetsList = assetsAccountingMapper.getAssetsByNumberList(assetsNumbers);
        for (Assets assets : assetsList) {
            assets.setUseStatus(AssetsStatus.getStatusByCode(assets.getUseStatus()).getInfo());
        }
        return assetsList;
    }

    @Override
    public int getCountByStorageAddr(String storageAddr) {
        return assetsAccountingMapper.getCountByStorageAddr(storageAddr);
    }

    @Override
    public List<String> getAssetsNumberListByStorageAddr(String storageAddr) {
        return assetsAccountingMapper.getAssetsNumberListByStorageAddr(storageAddr);
    }

    @Override
    public List<String> getStorageAddrAll() {
        return assetsAccountingMapper.getStorageAddrAll();
    }

    @Override
    public List<RuKu> getCountGroupByName(String startTime,String endTime) {
        return assetsAccountingMapper.getCountGroupByName(startTime,endTime);
    }

    @Override
    public List<ZiChan> getCountByUseStatus() {
        return assetsAccountingMapper.getCountByUseStatus();
    }

}
