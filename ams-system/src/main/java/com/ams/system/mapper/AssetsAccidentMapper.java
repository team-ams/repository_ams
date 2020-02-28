package com.ams.system.mapper;

import com.ams.system.domain.AssetsAccident;

import java.util.List;

public interface AssetsAccidentMapper {

    /**
     * 根据用户id查找资产维修信息
     *
     * @param userId
     * @return
     */
    List<AssetsAccident> getAccidentListByUserId(String userId);

    /**
     * 根据id查找维修信息（管理员）
     *
     * @param accidentId
     * @return
     */
    AssetsAccident getAccidentById(String accidentId);

    /**
     * 查找所有资产事故信息
     *
     * @return
     */
    List<AssetsAccident> getAccidentListAll();

    /**
     * 批量插入资产维修信息
     *
     * @param accidentList
     * @return
     */
    int insertAccidentList(List<AssetsAccident> accidentList);



    /**
     * 更新资产维修信息
     *
     * @param accident
     * @return
     */
    int updateAccidentInfo(AssetsAccident accident);

    /**
     * 更新资产事故信息（根据事故单号，可批量更新）
     *
     * @param accident
     * @return
     */
    int updateAccidentByOrderNum(AssetsAccident accident);

    /**
     * 根据维修id删除借还信息
     *
     * @param accidentId
     * @return
     */
    int deleteByAccidentId(String accidentId);


    /**
     * 根据单号查找
     * @param orderNum
     * @return
     */
    List<AssetsAccident> getAccidentListByOrderNum(String orderNum);

    /**
     * 获得审批列表
     *
     * @return
     */
    List<AssetsAccident> getMyExamineList();

    /**
     * 根据事故单号删除（可批量删除）
     *
     * @param orderNum
     * @return
     */
    int deleteAccidentByOrderNum(String orderNum);
}
