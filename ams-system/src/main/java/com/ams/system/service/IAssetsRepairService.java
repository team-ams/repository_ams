package com.ams.system.service;

import com.ams.system.domain.AssetsRepair;

import java.util.List;

public interface IAssetsRepairService {

    /**
     * 根据用户Id查找资产保养信息（用户级别）
     *
     * @param userId
     * @return
     */
    List<AssetsRepair> getRepairListByUserId(String userId);

    /**
     * 根据id查找资产保养信息
     *
     * @param repairId
     * @return
     */
    AssetsRepair getRepairById(String repairId);

    /**
     * 查找所有资产保养信息（管理员级别）
     *
     * @return
     */
    List<AssetsRepair> getRepairListAll();

    /**
     * 批量插入资产保养信息
     *
     * @param repairList
     * @return
     */
    int insertRepairList(List<AssetsRepair> repairList);


    /**
     * 更新资产保养信息
     *
     * @param repair
     * @return
     */
    int updateRepairInfo(AssetsRepair repair);

    /**
     * 根据保养id删除保养信息
     *
     * @param repairId
     * @return
     */
    int deleteByRepairId(String repairId);
}
