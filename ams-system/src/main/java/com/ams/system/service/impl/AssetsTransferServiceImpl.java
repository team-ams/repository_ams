package com.ams.system.service.impl;

import com.ams.system.domain.AssetsTransfer;
import com.ams.system.mapper.AssetsTransferMapper;
import com.ams.system.service.IAssetsTransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssetsTransferServiceImpl implements IAssetsTransferService {

    @Autowired
    private AssetsTransferMapper transferMapper;


    @Override
    public List<AssetsTransfer> getTransferListByUserId(String userId) {
        List<AssetsTransfer> transferListByUserId = transferMapper.getTransferListByUserId(userId);
        return transferListByUserId;
    }

    @Override
    public AssetsTransfer getTransferById(String assetsNumber) {
        AssetsTransfer transferByNumber = transferMapper.getTransferById(assetsNumber);
        return transferByNumber;
    }

    @Override
    public List<AssetsTransfer> getTransferListAll() {
        List<AssetsTransfer> transferListAll = transferMapper.getTransferListAll();
        return transferListAll;
    }

    @Override
    public int insertTransferList(List<AssetsTransfer> transferList) {
        return transferMapper.insertTransferList(transferList);
    }

    @Override
    public int updateTransferInfo(AssetsTransfer transfer) {
        return transferMapper.updateTransferInfo(transfer);
    }

    @Override
    public int updateTransferByOrderNum(AssetsTransfer transfer) {
        return transferMapper.updateTransferByOrderNum(transfer);
    }

    @Override
    public int deleteByTransferId(String transferId) {
        return transferMapper.deleteByTransferId(transferId);
    }

    @Override
    public List<AssetsTransfer> getTransferListByOrderNum(String orderNum) {
        return transferMapper.getTransferListByOrderNum(orderNum);
    }

    @Override
    public List<AssetsTransfer> getMyExamineList() {
        return transferMapper.getMyExamineList();
    }

    @Override
    public int deleteTransferByOrderNum(String orderNum) {
        return transferMapper.deleteTransferByOrderNum(orderNum);
    }
}
