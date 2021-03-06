package com.ams.system.domain;

import com.ams.common.core.domain.BaseEntity;
import lombok.Data;

@Data
public class AssetsMaintain extends BaseEntity {

    /**
     * '保养表ID'
     */
    private Integer maintainId;
    /**
     * 保养单号
     */
    private String maintainOrderNum;
    /**
     * '资产编号'
     */
    private String assetsNumber;
    /**
     * '保养人ID'
     */
    private int maintainUserId;
    /**
     * 保养名称（0：常规检查 1：抢修）
     */
    private String maintainName;
    /**
     * '保养后状态（0：正常，1：故障）'
     */
    private String maintainStatus;
    /**
     * 保养时间
     */
    private String maintainTime;
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
