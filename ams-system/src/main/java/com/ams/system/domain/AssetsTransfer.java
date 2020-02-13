package com.ams.system.domain;

import com.ams.common.core.domain.BaseEntity;
import lombok.Data;

@Data
public class AssetsTransfer extends BaseEntity {

    /**
     * '转移ID'
     */
    private Integer transferId;
    /**
     * '资产编号'
     */
    private String assetsNumber;
    /**
     * '负责人ID'
     */
    private int transferUserId;
    /**
     * '原存放地点'
     */
    private String originalAddr;
    /**
     * '现在存放地点'
     */
    private String presentAddr;
    /**
     * '转移日期'
     */
    private String transferTime;

    /**
     * 资产详情
     */
    private Assets assets;
    /**
     * 申请人详情
     */
    private SysUser user;
}