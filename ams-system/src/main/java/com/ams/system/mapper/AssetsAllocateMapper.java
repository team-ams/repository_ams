package com.ams.system.mapper;

import com.ams.system.domain.Assets;
import com.ams.system.domain.AssetsAllocate;
import com.ams.system.domain.LingYong;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zengchao
 * @since 创建时间：2020/1/22 17:42
 */
public interface AssetsAllocateMapper {

    /**
     * 新增领用申请记录信息
     *
     * @param allocate
     * @return
     */
    int insert(AssetsAllocate allocate);

    /**
     * 批量新增领用申请记录信息
     *
     * @param allocateList
     * @return
     */
    int insertByList(List<AssetsAllocate> allocateList);

    /**
     * 查询待管理员审核资产领用信息
     *
     * @return
     */
    List<AssetsAllocate> getAllocateAdminList(AssetsAllocate allocate);

    /**
     * 查询管理员审批记录信息
     *
     * @return
     */
    List<AssetsAllocate> getMyAllocateExamineList();

    /**
     * 根据用户和资产编号查询状态为审核中的资产领用
     *
     * @param orderNum
     * @return
     */
    List<AssetsAllocate> getAssetsAllocateBy(@Param("orderNum") String orderNum);

    /**
     * 根据userId查找 “我的资产领用” 领用信息
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
     * 更新资产领用信息
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
