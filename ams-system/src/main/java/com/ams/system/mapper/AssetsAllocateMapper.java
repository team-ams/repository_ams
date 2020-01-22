package com.ams.system.mapper;

import com.ams.system.domain.AssetsAllocate;

/**
 * @author zengchao
 * @since 创建时间：2020/1/22 17:42
 */
public interface AssetsAllocateMapper {

    /**
     * 新增领用申请记录信息
     * @param allocate
     * @return
     */
    int insert(AssetsAllocate allocate);
}
