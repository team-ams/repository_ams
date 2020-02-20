package com.ams.system.domain;

import com.ams.common.core.domain.BaseEntity;
import lombok.Data;

@Data
public class AssetsReturn extends BaseEntity {
    /**
     * '归还id'
     */
    private int returnId;
    /**
     * '归还单号'
     */
    private String returnOrderNum;
    /**
     * '资产编号'
     */
    private String assetsNumber;
    /**
     * '归还人员id'
     */
    private int returnUserId;
    /**
     * '归还日期'
     */
    private String returnTime;
    /**
     * '录入人'
     */
    private String enterPerson;
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
