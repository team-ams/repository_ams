package com.ams.system.service.impl;

import com.ams.system.domain.AssetsAllocate;
import com.ams.system.mapper.AssetsAllocateMapper;
import com.ams.system.service.IAssetsAllocateService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author zengchao
 * @since 创建时间：2020/1/22 17:40
 */
public class AssetsAllocateServiceImpl implements IAssetsAllocateService {

    @Autowired
    private AssetsAllocateMapper allocateMapper;
    @Override
    public int insertAllocate(AssetsAllocate allocate) {
        int rows = allocateMapper.insert(allocate);
        return rows;
    }
}
