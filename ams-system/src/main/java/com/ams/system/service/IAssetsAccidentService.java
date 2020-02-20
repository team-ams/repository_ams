package com.ams.system.service;

import com.ams.system.domain.AssetsAccident;
import com.ams.system.domain.AssetsAccident;
import com.ams.system.domain.AssetsBorrow;

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
     * 更新资产事故信息（根据事故单号，可批量更新）
     *
     * @param accident
     * @return
     */
    int updateAccidentByOrderNum(AssetsAccident accident);

    /**
     * 根据保养id删除保养信息
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
