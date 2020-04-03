package com.ams.system.service;

import com.ams.system.domain.*;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

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
     * @param assetsList
     * @return
     */
    public int updateAssetsLists(List<Assets> assetsList);

    /**
     * 根据分页条件查询资产列表（全部）
     *
     * @param assets
     * @return
     */
    public List<Assets> getAssetsList(Assets assets);

    /**
     * 根据分页条件查询资产列表（状态为正常的资产）
     *
     * @param assets
     * @return
     */
    public List<Assets> getAssetsList0(Assets assets);

    /**
     * 获取状态为闲置和审核中的资产信息（状态为正常的资产）
     *
     * @return
     */
    List<Assets> getAssetsList01(Assets assets);

    /**
     * 获取状态为待报废的资产信息
     *
     * @return
     */
    List<Assets> getScrappedStatusAssetsList();

    /**
     * 获取状态为停用的资产信息
     *
     * @return
     */
    List<Assets> getDisableStatusAssetsList();


    /**
     * 根据资产编号查找资产（可批量查询）
     *
     * @param assetsNumbers
     * @return
     */
    public List<Assets> getAssetsByNumbers(String[] assetsNumbers);

    /**
     * 根据资产编号查找资产(单条）
     *
     * @param assetsNumber
     * @return
     */
    Assets getAssetsByNumber(String assetsNumber);

    /**
     * 批量删除资产信息
     *
     * @param numbers
     * @return
     * @throws Exception
     */
    public int deleteAssetsByNumbers(String numbers) throws Exception;

    /**
     * 根据传入的资产编号（卡号）查找资产信息
     *
     * @param assetsNumbers
     * @return
     */
    public List<Assets> getAssetsByNumberList(List<String> assetsNumbers);

    /**
     * 根据资产 存放地址 统计数量（用于盘点的应盘数量）
     *
     * @param storageAddr
     * @return
     */
    int getCountByStorageAddr(String storageAddr);

    /**
     * 根据资产 存放地址 获得资产编号（用于盘点）
     *
     * @param storageAddr
     * @return
     */
    List<String> getAssetsNumberListByStorageAddr(String storageAddr);

    /**
     * 查找所有资产 存放地址（去重）
     *
     * @return
     */
    List<String> getStorageAddrAll();

    /**
     * 根据资产名称统金额
     *
     * @return
     */
    List<RuKu> getCountGroupByName(String startTime,String endTime);

    /**
     * 统计库存资产的使用状态
     *
     * @return
     */
    List<ZiChan> getCountByUseStatus();

    /**
     * 总资产信息
     * @return
     */
    AssetsInfo getZongZiChanInfo();
    /**
     * 闲置资产信息
     * @return
     */
    AssetsInfo getXianZhiInfo();
    /**
     * 使用资产信息
     * @return
     */
    AssetsInfo getShiYongInfo();
    /**
     * 待报废资产信息
     * @return
     */
    AssetsInfo getDaiBaoFeiInfo();
    /**
     * 处置资产信息
     * @return
     */
    AssetsInfo getChuZhiInfo();
}
