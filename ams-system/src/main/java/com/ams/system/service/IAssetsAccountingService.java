package com.ams.system.service;

import com.ams.system.domain.Assets;

import java.util.List;

/**
 * @author zengchao
 * @since 创建时间：2020/1/13 9:13
 */
public interface IAssetsAccountingService {

    /**
     * 检查资产编号是否唯一
     *
     * @param AssetsNumber
     * @return
     */
    public String checkAssetsNumberUnique(String AssetsNumber);

    /**
     * 导入资产数据
     *
     * @param assetsList
     * @param isUpdateSupport
     * @return
     */
    public String importAssets(List<Assets> assetsList, Boolean isUpdateSupport, String operName);

    /**
     * 保存新增资产数据
     *
     * @param assets
     * @return
     */
    public int insertAssets(Assets assets);

    /**
     * 更新资产信息
     *
     * @param assets
     * @return
     */
    public int updateAssets(Assets assets);

    /**
     * 根据分页条件查询资产列表
     *
     * @param assets
     * @return
     */
    public List<Assets> getAssetsList(Assets assets);

}
