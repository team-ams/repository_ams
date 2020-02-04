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
    public int updateAssetsList(List<Assets> assets);

    /**
     * 根据分页信息查询资产信息
     *
     * @param assets
     * @return
     */
    public List<Assets> getAssetsList(Assets assets);


    /**
     * 根据分页信息查询资产信息（状态为正常的资产）
     *
     * @param assets
     * @return
     */
    public List<Assets> getAssetsList0(Assets assets);

    /**
     * 检查资产编号是否唯一
     *
     * @param assetsNumber
     * @return
     */
    public int checkAssetsNumberUnique(String assetsNumber);

    /**
     * 根据assetsId查找资产
     *
     * @param assetsNumber
     * @return
     */
    public List<Assets> getAssetsByNumbers(String[] assetsNumber);

    /**
     * 批量删除资产信息
     *
     * @param numbers
     * @return
     */
    public int deleteAssetsByNumbers(String[] numbers);

    /**
     * 根据传入的资产编号（卡号）查找资产信息
     *
     * @param assetsNumbers
     * @return
     */
    public List<Assets> getAssetsByNumberList(List<String> assetsNumbers);

}