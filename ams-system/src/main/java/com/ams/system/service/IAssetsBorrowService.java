package com.ams.system.service;

import com.ams.system.domain.AssetsBorrow;

import java.util.List;

public interface IAssetsBorrowService {

    /**
     * 根据用户Id查找资产借用信息（用户级别）
     *
     * @param userId
     * @return
     */
    List<AssetsBorrow> getBorrowListByUserId(String userId);

    /**
     * 根据id查找资产借用信息（管理员级别）
     *
     * @param borrowId
     * @return
     */
    AssetsBorrow getBorrowById(String borrowId);

    /**
     * 查找所有资产借用信息
     *
     * @return
     */
    List<AssetsBorrow> getBorrowListAll();

    /**
     * 批量插入资产借用信息
     *
     * @param borrowList
     * @return
     */
    int insertBorrowList(List<AssetsBorrow> borrowList);

    /**
     * 批量归还资产
     *
     * @param returnList
     * @return
     */
    int returnAssetsList(List<AssetsBorrow> returnList);

    /**
     * 更新资产借用信息
     *
     * @param borrow
     * @return
     */
    int updateBorrowInfo(AssetsBorrow borrow);

    /**
     * 根据借用id删除借还信息
     *
     * @param borrowId
     * @return
     */
    int deleteByBorrowId(String borrowId);
}
