package com.ams.system.service;

import com.ams.system.domain.AssetsAccident;

import java.util.List;

public interface IAssetsAccidentService {

    /**
     * 根据用户Id查找资产保养信息（用户级别）
     *
     * @param userId
     * @return
     */
    List<AssetsAccident> getAccidentListByUserId(String userId);

    /**
     * 根据id查找资产保养信息
     *
     * @param accidentId
     * @return
     */
    AssetsAccident getAccidentById(String accidentId);

    /**
     * 查找所有资产保养信息（管理员级别）
     *
     * @return
     */
    List<AssetsAccident> getAccidentListAll();

    /**
     * 批量插入资产保养信息
     *
     * @param accidentList
     * @return
     */
    int insertAccidentList(List<AssetsAccident> accidentList);


    /**
     * 更新资产保养信息
     *
     * @param accident
     * @return
     */
    int updateAccidentInfo(AssetsAccident accident);

    /**
     * 根据保养id删除保养信息
     *
     * @param accidentId
     * @return
     */
    int deleteByAccidentId(String accidentId);
}
