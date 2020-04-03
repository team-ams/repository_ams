package com.ams.system.service.impl;

import com.ams.system.domain.AssetsAccident;
import com.ams.system.mapper.AssetsAccidentMapper;
import com.ams.system.service.IAssetsAccidentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssetsAccidentServiceImpl implements IAssetsAccidentService {

    @Autowired
    private AssetsAccidentMapper accidentMapper;


    @Override
    public List<AssetsAccident> getAccidentListByUserId(String userId) {
        List<AssetsAccident> accidentListByUserId = accidentMapper.getAccidentListByUserId(userId);
        return accidentListByUserId;
    }

    @Override
    public AssetsAccident getAccidentById(String assetsNumber) {
        AssetsAccident accidentByNumber = accidentMapper.getAccidentById(assetsNumber);
        return accidentByNumber;
    }

    @Override
    public List<AssetsAccident> getAccidentListAll(AssetsAccident accident) {
        List<AssetsAccident> accidentListAll = accidentMapper.getAccidentListAll(accident);
        return accidentListAll;
    }

    @Override
    public int insertAccidentList(List<AssetsAccident> accidentList) {
        return accidentMapper.insertAccidentList(accidentList);
    }

    @Override
    public int updateAccidentInfo(AssetsAccident accident) {
        return accidentMapper.updateAccidentInfo(accident);
    }

    @Override
    public int updateAccidentByOrderNum(AssetsAccident accident) {
        return accidentMapper.updateAccidentByOrderNum(accident);
    }

    @Override
    public int deleteByAccidentId(String accidentId) {
        return accidentMapper.deleteByAccidentId(accidentId);
    }

    @Override
    public List<AssetsAccident> getAccidentListByOrderNum(String orderNum) {
        return accidentMapper.getAccidentListByOrderNum(orderNum);
    }

    @Override
    public List<AssetsAccident> getMyExamineList() {
        return accidentMapper.getMyExamineList();
    }

    @Override
    public int deleteAccidentByOrderNum(String orderNum) {
        return accidentMapper.deleteAccidentByOrderNum(orderNum);
    }
}
