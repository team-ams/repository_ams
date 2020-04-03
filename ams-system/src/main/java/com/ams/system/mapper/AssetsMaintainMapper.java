package com.ams.system.mapper;

import com.ams.system.domain.AssetsBorrow;
import com.ams.system.domain.AssetsMaintain;
import com.ams.system.domain.AssetsReturn;

import java.util.List;

public interface AssetsMaintainMapper {

    /**
     * 根据用户id查找资产保养信息
     *
     * @param userId
     * @return
     */
    List<AssetsMaintain> getMaintainListByUserId(String userId);

    /**
     * 根据id查找保养信息（管理员）
     *
     * @param maintainId
     * @return
     */
    AssetsMaintain getMaintainById(String maintainId);

    /**
     * 查找所有资产保养信息
     *
     * @return
     */
    List<AssetsMaintain> getMaintainListAll(AssetsMaintain maintain);

    /**
     * 批量插入资产保养信息
     *
     * @param maintainList
     * @return
     */
    int insertMaintainList(List<AssetsMaintain> maintainList);



    /**
     * 更新资产保养信息
     *
     * @param maintain
     * @return
     */
    int updateMaintainInfo(AssetsMaintain maintain);

    /**
     * 更新资产保养信息（根据保养单号，可批量更新）
     *
     * @param maintain
     * @return
     */
    int updateMaintainByOrderNum(AssetsMaintain maintain);

    /**
     * 根据保养id删除借还信息
     *
     * @param maintainId
     * @return
     */
    int deleteByMaintainId(String maintainId);

    /**
     * 根据单号查找
     * @param orderNum
     * @return
     */
    List<AssetsMaintain> getMaintainListByOrderNum(String orderNum);

    /**
     * 获得审批列表
     *
     * @return
     */
    List<AssetsMaintain> getMyExamineList();

    /**
     * 根据保养单号删除（可批量删除）
     *
     * @param orderNum
     * @return
     */
    int deleteMaintainByOrderNum(String orderNum);
}
