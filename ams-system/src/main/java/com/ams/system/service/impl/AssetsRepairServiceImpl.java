package com.ams.system.service.impl;

import com.ams.system.domain.AssetsRepair;
import com.ams.system.domain.WeiXiu;
import com.ams.system.mapper.AssetsRepairMapper;
import com.ams.system.service.IAssetsRepairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssetsRepairServiceImpl implements IAssetsRepairService {

    @Autowired
    private AssetsRepairMapper repairMapper;


    @Override
    public List<AssetsRepair> getRepairListByUserId(String userId) {
        List<AssetsRepair> repairListByUserId = repairMapper.getRepairListByUserId(userId);
        return repairListByUserId;
    }

    @Override
    public AssetsRepair getRepairById(String assetsNumber) {
        AssetsRepair repairByNumber = repairMapper.getRepairById(assetsNumber);
        return repairByNumber;
    }

    @Override
    public List<AssetsRepair> getRepairListAll() {
        List<AssetsRepair> repairListAll = repairMapper.getRepairListAll();
        return repairListAll;
    }

    @Override
    public int insertRepairList(List<AssetsRepair> repairList) {
        return repairMapper.insertRepairList(repairList);
    }

    @Override
    public int updateRepairInfo(AssetsRepair repair) {
        return repairMapper.updateRepairInfo(repair);
    }

    @Override
    public int updateRepairByOrderNum(AssetsRepair repair) {
        return repairMapper.updateRepairByOrderNum(repair);
    }

    @Override
    public int deleteByRepairId(String repairId) {
        return repairMapper.deleteByRepairId(repairId);
    }

    @Override
    public List<AssetsRepair> getRepairListByOrderNum(String orderNum) {
        return repairMapper.getRepairListByOrderNum(orderNum);
    }

    @Override
    public List<AssetsRepair> getMyExamineList() {
        return repairMapper.getMyExamineList();
    }

    @Override
    public int deleteRepairByOrderNum(String orderNum) {
        return repairMapper.deleteRepairByOrderNum(orderNum);
    }

    @Override
    public List<WeiXiu> getCountAndFeesGroupByDate() {
        return repairMapper.getCountAndFeesGroupByDate();
    }
}
