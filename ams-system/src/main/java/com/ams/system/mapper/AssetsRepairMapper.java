package com.ams.system.mapper;

import com.ams.system.domain.AssetsMaintain;
import com.ams.system.domain.AssetsRepair;
import com.ams.system.domain.RuKu;
import com.ams.system.domain.WeiXiu;
import org.apache.ibatis.annotations.Param;

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
    List<AssetsRepair> getRepairListAll(AssetsRepair repair);

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
     * 更新资产维修信息（根据维修单号，可批量更新）
     *
     * @param repair
     * @return
     */
    int updateRepairByOrderNum(AssetsRepair repair);

    /**
     * 根据维修id删除借还信息
     *
     * @param repairId
     * @return
     */
    int deleteByRepairId(String repairId);

    /**
     * 根据单号查找
     *
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

    /**
     * 按月份统计维修金额和维修数量
     *
     * @return
     */
    List<WeiXiu> getCountAndFeesGroupByDate();
}
