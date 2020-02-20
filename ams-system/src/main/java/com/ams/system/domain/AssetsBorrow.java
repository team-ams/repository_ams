package com.ams.system.domain;

import com.ams.common.core.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class AssetsBorrow extends BaseEntity {

    /**
     * '借还表ID'
     */
    private Integer borrowId;
    /**
     * 借用单号
     */
    private String borrowOrderNum;
    /**
     * '资产编号'
     */
    private String assetsNumber;
    /**
     * '借用人ID'
     */
    private int borrowUserId;
    /**
     * 借用时间
     */
    private String borrowTime;
    /**
     * 预计归还时间
     */
    private String returnTime;
    /**
     * '录入人'
     */
    private String enterPerson;
    /**
     * '是否归还（0：未归还 1：归还审核中 2：已归还）'
     */
    private String isReturn;

    /**
     * 状态
     */
    private String status;
    /**
     * 审核者
     */
    private int auditorId;
    /**
     * 资产详情
     */
    private Assets assets;
    /**
     * 申请人详情
     */
    private SysUser user;

    public static String getIsReturnNameByCode(String isReturnCode) {
        return "0".equals(isReturnCode) ? "未归还" : "已归还";
    }
}
