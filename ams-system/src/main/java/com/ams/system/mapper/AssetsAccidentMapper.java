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
     * 查找所有资产维修信息
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
     * 根据维修id删除借还信息
     *
     * @param accidentId
     * @return
     */
    int deleteByAccidentId(String accidentId);
}
