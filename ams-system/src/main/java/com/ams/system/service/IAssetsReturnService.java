package com.ams.system.service;

import com.ams.system.domain.AssetsBorrow;
import com.ams.system.domain.AssetsReturn;
import com.ams.system.domain.GuiHuan;
import com.ams.system.domain.JieYong;

import java.util.List;

public interface IAssetsReturnService {

    /**
     * 根据用户Id查找资产归还信息（用户级别）
     *
     * @param userId
     * @return
     */
    List<AssetsReturn> getReturnListByUserId(String userId);

    /**
     * 根据单号查找
     *
     * @param orderNum
     * @return
     */
    List<AssetsReturn> getReturnListByOrderNum(String orderNum);

    /**
     * 根据id查找资产归还信息（管理员级别）
     *
     * @param returnId
     * @return
     */
    AssetsReturn getReturnById(String returnId);

    /**
     * 查找所有资产归还信息
     *
     * @return
     */
    List<AssetsReturn> getReturnListAll();

    /**
     * 批量插入资产归还信息
     *
     * @param returnList
     * @return
     */
    int insertReturnList(List<AssetsReturn> returnList);


    /**
     * 更新资产归还信息
     *
     * @param returnInfo
     * @return
     */
    int updateReturnInfo(AssetsReturn returnInfo);

    /**
     * 更新资产归还信息（根据归还单号，可批量更新）
     *
     * @param returnInfo
     * @return
     */
    int updateReturnByOrderNum(AssetsReturn returnInfo);

    /**
     * 根据归还id删除借还信息
     *
     * @param returnId
     * @return
     */
    int deleteByReturnId(String returnId);

    /**
     * 获得审批列表
     *
     * @return
     */
    List<AssetsReturn> getMyExamineList();

    /**
     * 根据归还单号删除（可批量删除）
     *
     * @param orderNum
     * @return
     */
    int deleteReturnByOrderNum(String orderNum);

    /**
     * 根据天统计
     *
     * @return
     */
    List<GuiHuan> getDataGroupByDay();

    /**
     * 根据周统计
     *
     * @return
     */
    List<GuiHuan> getDataGroupByYear();

    /**
     * 根据月统计
     *
     * @return
     */
    List<GuiHuan> getDataGroupByMonth();
}
