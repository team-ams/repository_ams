package com.ams.system.service.impl;

import com.ams.system.domain.AssetsBorrow;
import com.ams.system.domain.JieYong;
import com.ams.system.mapper.AssetsBorrowMapper;
import com.ams.system.service.IAssetsBorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssetsBorrowServiceImpl implements IAssetsBorrowService {

    @Autowired
    private AssetsBorrowMapper borrowMapper;


    @Override
    public List<AssetsBorrow> getBorrowListByUserId(String userId) {
        List<AssetsBorrow> borrowListByUserId = borrowMapper.getBorrowListByUserId(userId);
        return borrowListByUserId;
    }

    @Override
    public List<AssetsBorrow> getNeedReturnByUserId(String userId) {
        return borrowMapper.getNeedReturnByUserId(userId);
    }

    @Override
    public List<AssetsBorrow> getBorrowListByOrderNum(String orderNum) {
        return borrowMapper.getBorrowListByOrderNum(orderNum);
    }

    @Override
    public AssetsBorrow getBorrowById(String borrowId) {
        AssetsBorrow borrowByNumber = borrowMapper.getBorrowById(borrowId);
        return borrowByNumber;
    }

    @Override
    public AssetsBorrow getBorrowByAssetsNumberAndIsNotReturn(String assetsNumber) {
        return borrowMapper.getBorrowByAssetsNumberAndIsNotReturn(assetsNumber);
    }

    @Override
    public List<AssetsBorrow> getBorrowListAll(AssetsBorrow borrow) {
        List<AssetsBorrow> borrowListAll = borrowMapper.getBorrowListAll(borrow);
        return borrowListAll;
    }

    @Override
    public int insertBorrowList(List<AssetsBorrow> borrowList) {
        return borrowMapper.insertBorrowList(borrowList);
    }


    @Override
    public int updateBorrowInfo(AssetsBorrow borrow) {
        return borrowMapper.updateBorrowInfo(borrow);
    }

    @Override
    public int updateBorrowByOrderNum(AssetsBorrow borrow) {
        return borrowMapper.updateBorrowByOrderNum(borrow);
    }

    @Override
    public int deleteByBorrowId(String borrowId) {
        return borrowMapper.deleteByBorrowId(borrowId);
    }

    @Override
    public List<AssetsBorrow> getMyExamineList() {
        return borrowMapper.getMyExamineList();
    }

    @Override
    public int deleteBorrowByOrderNum(String orderNum) {
        return borrowMapper.deleteBorrowByOrderNum(orderNum);
    }

    @Override
    public List<JieYong> getDataGroupByDay() {
        return borrowMapper.getDataGroupByDay();
    }

    @Override
    public List<JieYong> getDataGroupByYear() {
        return borrowMapper.getDataGroupByYear();
    }

    @Override
    public List<JieYong> getDataGroupByMonth() {
        return borrowMapper.getDataGroupByMonth();
    }
}
