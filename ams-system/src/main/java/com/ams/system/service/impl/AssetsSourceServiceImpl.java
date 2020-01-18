package com.ams.system.service.impl;

import com.ams.system.domain.AssetsSource;
import com.ams.system.mapper.AssetsSourceMapper;
import com.ams.system.service.IAssetsSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssetsSourceServiceImpl implements IAssetsSourceService {
    @Autowired
    private AssetsSourceMapper assetsSourceMapper;

    /**
     * 获取所有资产来源方式
     *
     * @return
     */
    @Override
    public List<AssetsSource> getAssetsSourceAll() {
        return assetsSourceMapper.getAssetsSourceAll();
    }
}
