package com.ams.system.service.impl;

import com.ams.system.domain.AssetsMaintain;
import com.ams.system.mapper.AssetsMaintainMapper;
import com.ams.system.service.IAssetsMaintainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssetsMaintainServiceImpl implements IAssetsMaintainService {

    @Autowired
    private AssetsMaintainMapper maintainMapper;


    @Override
    public List<AssetsMaintain> getMaintainListByUserId(String userId) {
        List<AssetsMaintain> maintainListByUserId = maintainMapper.getMaintainListByUserId(userId);
        return maintainListByUserId;
    }

    @Override
    public AssetsMaintain getMaintainById(String assetsNumber) {
        AssetsMaintain maintainByNumber = maintainMapper.getMaintainById(assetsNumber);
        return maintainByNumber;
    }

    @Override
    public List<AssetsMaintain> getMaintainListAll() {
        List<AssetsMaintain> maintainListAll = maintainMapper.getMaintainListAll();
        return maintainListAll;
    }

    @Override
    public int insertMaintainList(List<AssetsMaintain> maintainList) {
        return maintainMapper.insertMaintainList(maintainList);
    }

    @Override
    public int updateMaintainInfo(AssetsMaintain maintain) {
        return maintainMapper.updateMaintainInfo(maintain);
    }

    @Override
    public int deleteByMaintainId(String maintainId) {
        return maintainMapper.deleteByMaintainId(maintainId);
    }
}
