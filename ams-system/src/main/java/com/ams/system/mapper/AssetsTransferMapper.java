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
     * 根据维修id删除借还信息
     *
     * @param transferId
     * @return
     */
    int deleteByTransferId(String transferId);
}
