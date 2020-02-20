package com.ams.system.mapper;

import com.ams.system.domain.AssetsTransfer;

import java.util.List;

public interface AssetsTransferMapper {

    /**
     * 根据用户id查找资产维修信息
     *
     * @param userId
     * @return
     */
    List<AssetsTransfer> getTransferListByUserId(String userId);

    /**
     * 根据id查找维修信息（管理员）
     *
     * @param transferId
     * @return
     */
    AssetsTransfer getTransferById(String transferId);

    /**
     * 查找所有资产维修信息
     *
     * @return
     */
    List<AssetsTransfer> getTransferListAll();

    /**
     * 批量插入资产维修信息
     *
     * @param transferList
     * @return
     */
    int insertTransferList(List<AssetsTransfer> transferList);



    /**
     * 更新资产维修信息
     *
     * @param transfer
     * @return
     */
    int updateTransferInfo(AssetsTransfer transfer);

    /**
     * 更新资产转移信息（根据事故单号，可批量更新）
     *
     * @param transfer
     * @return
     */
    int updateTransferByOrderNum(AssetsTransfer transfer);

    /**
     * 根据维修id删除借还信息
     *
     * @param transferId
     * @return
     */
    int deleteByTransferId(String transferId);
    /**
     * 根据单号查找
     * @param orderNum
     * @return
     */
    List<AssetsTransfer> getTransferListByOrderNum(String orderNum);

    /**
     * 获得审批列表
     *
     * @return
     */
    List<AssetsTransfer> getMyExamineList();

    /**
     * 根据转移单号删除（可批量删除）
     *
     * @param orderNum
     * @return
     */
    int deleteTransferByOrderNum(String orderNum);
}
