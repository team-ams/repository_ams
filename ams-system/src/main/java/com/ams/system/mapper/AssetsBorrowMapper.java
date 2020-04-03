package com.ams.system.mapper;

import com.ams.system.domain.AssetsBorrow;
import com.ams.system.domain.JieYong;
import com.ams.system.domain.LingYong;

import java.util.List;

public interface AssetsBorrowMapper {

    /**
     * 根据用户id查找资产借用信息
     *
     * @param userId
     * @return
     */
    List<AssetsBorrow> getBorrowListByUserId(String userId);

    /**
     * 查找出需要归还的借用信息，用于归还列表的展示（借用审核通过的，且没有归还状态的）
     *
     * @param userId
     * @return
     */
    List<AssetsBorrow> getNeedReturnByUserId(String userId);

    /**
     * 根据单号查找
     *
     * @param orderNum
     * @return
     */
    List<AssetsBorrow> getBorrowListByOrderNum(String orderNum);

    /**
     * 根据id查找借用信息（管理员）
     *
     * @param borrowId
     * @return
     */
    AssetsBorrow getBorrowById(String borrowId);

    /**
     * 根据资产编号和未归还状态确定借用信息
     *
     * @param assetsNumber
     * @return
     */
    AssetsBorrow getBorrowByAssetsNumberAndIsNotReturn(String assetsNumber);

    /**
     * 查找所有资产借用信息
     *
     * @return
     */
    List<AssetsBorrow> getBorrowListAll(AssetsBorrow borrow);

    /**
     * 批量插入资产借用信息
     *
     * @param borrowList
     * @return
     */
    int insertBorrowList(List<AssetsBorrow> borrowList);


    /**
     * 更新资产借用信息
     *
     * @param borrow
     * @return
     */
    int updateBorrowInfo(AssetsBorrow borrow);

    /**
     * 更新资产借用信息（根据借用单号，可批量更新）
     *
     * @param borrow
     * @return
     */
    int updateBorrowByOrderNum(AssetsBorrow borrow);

    /**
     * 根据借用id删除借还信息
     *
     * @param borrowId
     * @return
     */
    int deleteByBorrowId(String borrowId);

    /**
     * 获得审批列表
     *
     * @return
     */
    List<AssetsBorrow> getMyExamineList();

    /**
     * 根据借用单号删除（可批量删除）
     *
     * @param orderNum
     * @return
     */
    int deleteBorrowByOrderNum(String orderNum);

    /**
     * 根据天统计
     *
     * @return
     */
    List<JieYong> getDataGroupByDay();

    /**
     * 根据周统计
     *
     * @return
     */
    List<JieYong> getDataGroupByYear();

    /**
     * 根据月统计
     *
     * @return
     */
    List<JieYong> getDataGroupByMonth();
}
