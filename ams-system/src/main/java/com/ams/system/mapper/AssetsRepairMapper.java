package com.ams.system.mapper;

import com.ams.system.domain.AssetsRepair;

import java.util.List;

public interface AssetsRepairMapper {

    /**
     * 根据用户id查找资产维修信息
     *
     * @param userId
     * @return
     */
    List<AssetsRepair> getRepairListByUserId(String userId);

    /**
     * 根据id查找维修信息（管理员）
     *
     * @param repairId
     * @return
     */
    AssetsRepair getRepairById(String repairId);

    /**
     * 查找所有资产维修信息
     *
     * @return
     */
    List<AssetsRepair> getRepairListAll();

    /**
     * 批量插入资产维修信息
     *
     * @param repairList
     * @return
     */
    int insertRepairList(List<AssetsRepair> repairList);



    /**
     * 更新资产维修信息
     *
     * @param repair
     * @return
     */
    int updateRepairInfo(AssetsRepair repair);

    /**
     * 根据维修id删除借还信息
     *
     * @param repairId
     * @return
     */
    int deleteByRepairId(String repairId);
}
