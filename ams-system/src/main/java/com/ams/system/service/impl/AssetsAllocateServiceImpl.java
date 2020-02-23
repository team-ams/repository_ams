package com.ams.system.service.impl;

import com.ams.common.enums.AssetsExamineStatus;
import com.ams.common.enums.AssetsStatus;
import com.ams.system.domain.AssetsAllocate;
import com.ams.system.domain.LingYong;
import com.ams.system.mapper.AssetsAllocateMapper;
import com.ams.system.service.IAssetsAllocateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zengchao
 * @since 创建时间：2020/1/22 17:40
 */
@Service
public class AssetsAllocateServiceImpl implements IAssetsAllocateService {

    @Autowired
    private AssetsAllocateMapper allocateMapper;

    @Override
    public int insertAllocate(AssetsAllocate allocate) {
        int rows = allocateMapper.insert(allocate);
        return rows;
    }

    @Override
    public int insertAllocateByList(List<AssetsAllocate> allocateList) {
        return allocateMapper.insertByList(allocateList);
    }

    @Override
    public List<AssetsAllocate> getAllocateAdminList(AssetsAllocate assetsAllocate) {
        List<AssetsAllocate> allocateAdminList = allocateMapper.getAllocateAdminList(assetsAllocate);
        for (AssetsAllocate allocate : allocateAdminList) {
            //映射 资产 使用状态
            allocate.getAssets().setUseStatus(AssetsStatus.getStatusByCode(allocate.getAssets().getUseStatus()).getInfo());
            //映射 资产领用 状态
            allocate.setStatus(AssetsExamineStatus.getStatusByCode(allocate.getStatus()).getInfo());
        }
        return allocateAdminList;
    }

    @Override
    public List<AssetsAllocate> getMyAllocateExamineList() {
        List<AssetsAllocate> myAllocateExamineList = allocateMapper.getMyAllocateExamineList();
        for (AssetsAllocate allocate : myAllocateExamineList) {
            //映射 资产 使用状态
            allocate.getAssets().setUseStatus(AssetsStatus.getStatusByCode(allocate.getAssets().getUseStatus()).getInfo());
            //映射 资产领用 状态
            allocate.setStatus(AssetsAllocate.getExamineStatusInfo(allocate.getStatus()));

        }
        return myAllocateExamineList;
    }

    @Override
    public List<AssetsAllocate> getMyAllocateListByUserId(int userId) {
        List<AssetsAllocate> myAllocateListByUserId = allocateMapper.getMyAllocateListByUserId(userId);
        for (AssetsAllocate allocate : myAllocateListByUserId) {
            //映射 资产 使用状态
            allocate.getAssets().setUseStatus(AssetsStatus.getStatusByCode(allocate.getAssets().getUseStatus()).getInfo());
            //映射 资产领用 状态
            allocate.setStatus(AssetsExamineStatus.getStatusByCode(allocate.getStatus()).getInfo());
        }
        return myAllocateListByUserId;
    }

    @Override
    public AssetsAllocate getAllocateByAllocateId(int allocateId) {
        return allocateMapper.getAllocateByAllocateId(allocateId);
    }

    @Override
    public List<AssetsAllocate> getAssetsAllocateBy(String orderNum) {
        return allocateMapper.getAssetsAllocateBy(orderNum);
    }

    @Override
    public int updateAssetsAllocate(AssetsAllocate assetsAllocate) {
        return allocateMapper.updateAssetsAllocate(assetsAllocate);
    }

    @Override
    public int deleteAllocateByOrderNum(String orderNum) {
        return allocateMapper.deleteAllocateByOrderNum(orderNum);
    }

    @Override
    public List<LingYong> getDataGroupByDay() {
        return allocateMapper.getDataGroupByDay();
    }

    @Override
    public List<LingYong> getDataGroupByYear() {
        return allocateMapper.getDataGroupByYear();
    }

    @Override
    public List<LingYong> getDataGroupByMonth() {
        return allocateMapper.getDataGroupByMonth();
    }
}
