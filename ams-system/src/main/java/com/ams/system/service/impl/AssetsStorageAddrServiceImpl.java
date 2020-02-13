package com.ams.system.service.impl;

import com.ams.system.domain.AssetsCheckItem;
import com.ams.system.domain.AssetsStorageAddr;
import com.ams.system.mapper.AssetsCheckItemMapper;
import com.ams.system.mapper.AssetsStorageAddrMapper;
import com.ams.system.service.IAssetsCheckItemService;
import com.ams.system.service.IAssetsStorageAddrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssetsStorageAddrServiceImpl implements IAssetsStorageAddrService {

    @Autowired
    private AssetsStorageAddrMapper storageAddrMapper;


    @Override
    public List<String> getAddrNameAll() {
        return storageAddrMapper.getAddrNameAll();
    }
}
