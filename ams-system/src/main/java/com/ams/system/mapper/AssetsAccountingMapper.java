package com.ams.system.mapper;

import com.ams.system.domain.Assets;

import java.util.List;

/**
 * @author zengchao
 * @since 创建时间：2020/1/13 14:26
 */
public interface AssetsAccountingMapper {

    /**
     * 通过资产编号获取资产信息
     *
     * @param assetsNumber
     * @return
     */
    public Assets getAssetsByAssetsNumber(String assetsNumber);


    /**
     * 新增用户信息
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
     * 根据分页信息查询资产信息
     *
     * @param assets
     * @return
     */
    public List<Assets> getAssetsList(Assets assets);
}
