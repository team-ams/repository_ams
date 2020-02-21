package com.ams.system.service;

import com.ams.system.domain.AssetsAllocate;
import com.ams.system.domain.LingYong;

import java.util.List;

/**
 * @author zengchao
 * @since 创建时间：2020/1/22 17:37
 */
public interface IAssetsAllocateService {

    /**
     * 新增资产领用
     *
     * @param allocate
     * @return
     */
    int insertAllocate(AssetsAllocate allocate);

    /**
     * 批量增加资产领用记录
     *
     * @param allocateList
     * @return
     */
    int insertAllocateByList(List<AssetsAllocate> allocateList);

    /**
     * 资产待审批记录 管理员
     *
     * @return
     */
    List<AssetsAllocate> getAllocateAdminList();

    /**
     * 查询管理员资产审批记录
     *
     * @return
     */
    List<AssetsAllocate> getMyAllocateExamineList();

    /**
     * 根据userId查询  “我的资产领用"  信息
     *
     * @param userId
     * @return
     */
    List<AssetsAllocate> getMyAllocateListByUserId(int userId);

    /**
     * 根据领用Id获得领用信息
     *
     * @param allocateId
     * @return
     */
    AssetsAllocate getAllocateByAllocateId(int allocateId);

    /**
     * 根据领用单号查找资产领用
     *
     * @param orderNum
     * @return
     */
    List<AssetsAllocate> getAssetsAllocateBy(String orderNum);


    /**
     * 更新资产领用
     *
     * @param assetsAllocate
     * @return
     */
    int updateAssetsAllocate(AssetsAllocate assetsAllocate);

    /**
     * 根据领用单号删除（可批量删除）
     *
     * @param orderNum
     * @return
     */
    int deleteAllocateByOrderNum(String orderNum);

    /**
     * 根据天统计
     *
     * @return
     */
    List<LingYong> getDataGroupByDay();

    /**
     * 根据周统计
     *
     * @return
     */
    List<LingYong> getDataGroupByYear();

    /**
     * 根据月统计
     *
     * @return
     */
    List<LingYong> getDataGroupByMonth();

}
