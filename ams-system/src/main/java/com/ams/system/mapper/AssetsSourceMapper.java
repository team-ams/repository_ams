package com.ams.system.mapper;

import com.ams.system.domain.AssetsSource;

import java.util.List;

public interface AssetsSourceMapper {

    /**
     * 获得所有资产来源方式
     *
     * @return
     */
    public List<AssetsSource> getAssetsSourceAll();
}
