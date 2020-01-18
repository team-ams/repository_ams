package com.ams.system.service;

import com.ams.system.domain.AssetsSource;

import java.util.List;

public interface IAssetsSourceService {

    /**
     * 查询所有资产来源方式
     *
     * @return
     */
    public List<AssetsSource> getAssetsSourceAll();
}
