package com.ams.system.service;

import com.ams.system.domain.AssetsTransfer;

import java.util.List;

public interface IAssetsTransferService {

    /**
     * 根据用户Id查找资产转移信息（用户级别）
     *
     * @param userId
     * @return
     */
    List<AssetsTransfer> getTransferListByUserId(String userId);

    /**
     * 根据id查找资产转移信息
     *
     * @param transferId
     * @return
     */
    AssetsTransfer getTransferById(String transferId);

    /**
     * 查找所有资产转移信息（管理员级别）
     *
     * @return
     */
    List<AssetsTransfer> getTransferListAll();

    /**
     * 批量插入资产转移信息
     *
     * @param transferList
     * @return
     */
    int insertTransferList(List<AssetsTransfer> transferList);


    /**
     * 更新资产转移信息
     *
     * @param transfer
     * @return
     */
    int updateTransferInfo(AssetsTransfer transfer);

    /**
     * 根据转移id删除转移信息
     *
     * @param transferId
     * @return
     */
    int deleteByTransferId(String transferId);
}
