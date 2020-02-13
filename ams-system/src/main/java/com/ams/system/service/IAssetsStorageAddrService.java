package com.ams.system.service;

import com.ams.system.domain.AssetsCheckItem;

import java.util.List;

public interface IAssetsStorageAddrService {

    /**
     * 获取所有地址名称
     *
     * @return
     */
    List<String> getAddrNameAll();
}
