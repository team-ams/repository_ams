package com.ams.system.service.impl;

import com.ams.system.domain.AssetsBorrow;
import com.ams.system.domain.AssetsReturn;
import com.ams.system.domain.GuiHuan;
import com.ams.system.mapper.AssetsBorrowMapper;
import com.ams.system.mapper.AssetsReturnMapper;
import com.ams.system.service.IAssetsBorrowService;
import com.ams.system.service.IAssetsReturnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssetsReturnServiceImpl implements IAssetsReturnService {

    @Autowired
    private AssetsReturnMapper returnMapper;


    @Override
    public List<AssetsReturn> getReturnListByUserId(String userId) {
        return returnMapper.getReturnListByUserId(userId);
    }

    @Override
    public List<AssetsReturn> getReturnListByOrderNum(String orderNum) {
        return returnMapper.getReturnListByOrderNum(orderNum);
    }

    @Override
    public AssetsReturn getReturnById(String returnId) {
        return returnMapper.getReturnById(returnId);
    }

    @Override
    public List<AssetsReturn> getReturnListAll() {
        return returnMapper.getReturnListAll();
    }

    @Override
    public int insertReturnList(List<AssetsReturn> returnList) {
        return returnMapper.insertReturnList(returnList);
    }

    @Override
    public int updateReturnInfo(AssetsReturn returnInfo) {
        return returnMapper.updateReturnInfo(returnInfo);
    }

    @Override
    public int updateReturnByOrderNum(AssetsReturn returnInfo) {
        return returnMapper.updateReturnByOrderNum(returnInfo);
    }

    @Override
    public int deleteByReturnId(String returnId) {
        return returnMapper.deleteByReturnId(returnId);
    }

    @Override
    public List<AssetsReturn> getMyExamineList() {
        return returnMapper.getMyExamineList();
    }

    @Override
    public int deleteReturnByOrderNum(String orderNum) {
        return returnMapper.deleteReturnByOrderNum(orderNum);
    }

    @Override
    public List<GuiHuan> getDataGroupByDay() {
        return returnMapper.getDataGroupByDay();
    }

    @Override
    public List<GuiHuan> getDataGroupByYear() {
        return returnMapper.getDataGroupByYear();
    }

    @Override
    public List<GuiHuan> getDataGroupByMonth() {
        return returnMapper.getDataGroupByMonth();
    }
}
