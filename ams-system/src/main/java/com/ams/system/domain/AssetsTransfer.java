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
     * 转移单号
     */
    private String transferOrderNum;
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
     * 状态
     */
    private String status;
    /**
     * 审核者
     */
    private String auditorId;

    /**
     * 资产详情
     */
    private Assets assets;
    /**
     * 申请人详情
     */
    private SysUser user;
}