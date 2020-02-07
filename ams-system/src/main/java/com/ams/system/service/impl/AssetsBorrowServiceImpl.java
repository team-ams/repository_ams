package com.ams.system.service.impl;

import com.ams.system.domain.AssetsBorrow;
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
    public AssetsBorrow getBorrowById(String borrowId) {
        AssetsBorrow borrowByNumber = borrowMapper.getBorrowById(borrowId);
        return borrowByNumber;
    }

    @Override
    public List<AssetsBorrow> getBorrowListAll() {
        List<AssetsBorrow> borrowListAll = borrowMapper.getBorrowListAll();
        return borrowListAll;
    }

    @Override
    public int insertBorrowList(List<AssetsBorrow> borrowList) {
        return borrowMapper.insertBorrowList(borrowList);
    }

    @Override
    public int returnAssetsList(List<AssetsBorrow> returnList) {
        return borrowMapper.returnAssetsList(returnList);
    }

    @Override
    public int updateBorrowInfo(AssetsBorrow borrow) {
        return borrowMapper.updateBorrowInfo(borrow);
    }

    @Override
    public int deleteByBorrowId(String borrowId) {
        return borrowMapper.deleteByBorrowId(borrowId);
    }
}
