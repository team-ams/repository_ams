package com.ams.system.service;

import com.ams.system.domain.AssetsMaintain;
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
     * 更新资产维修信息（根据维修单号，可批量更新）
     *
     * @param repair
     * @return
     */
    int updateRepairByOrderNum(AssetsRepair repair);

    /**
     * 根据保养id删除保养信息
     *
     * @param repairId
     * @return
     */
    int deleteByRepairId(String repairId);
    /**
     * 根据单号查找
     * @param orderNum
     * @return
     */
    List<AssetsRepair> getRepairListByOrderNum(String orderNum);

    /**
     * 获得审批列表
     *
     * @return
     */
    List<AssetsRepair> getMyExamineList();

    /**
     * 根据维修单号删除（可批量删除）
     *
     * @param orderNum
     * @return
     */
    int deleteRepairByOrderNum(String orderNum);
}
