package com.ams.system.domain;

import com.ams.common.core.domain.BaseEntity;
import lombok.Data;

@Data
public class AssetsStorageAddr extends BaseEntity {

    /**
     * '地点id'
     */
    private Integer addrId;
    /**
     * '地址编号'
     */
    private int addrNo;
    /**
     * '地址名称'
     */
    private String addrName;


}