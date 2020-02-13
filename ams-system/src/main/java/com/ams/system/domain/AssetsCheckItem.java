package com.ams.system.domain;

import com.ams.common.core.domain.BaseEntity;
import lombok.Data;

@Data
public class AssetsCheckItem extends BaseEntity {

    /**
     * 盘点id
     */
    private Integer itemId;
    /**
     * 盘点单号
     */
    private String checkNumber;
    /**
     * 资产编号
     */
    private String assetsNumber;
    /**
     * 资产详情
     */
    private Assets assets;


}