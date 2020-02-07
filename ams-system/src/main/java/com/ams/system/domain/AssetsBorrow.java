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
     * '资产编号'
     */
    private String assetsNumber;
    /**
     * '借用人ID'
     */
    private int borrowUserId;
    /**
     * '是否归还（0：未归还，1：已归还）'
     */
    private String isReturn;
    /**
     * '安装地点'
     */
    private String installationAddr;
    /**
     * 借用时间
     */
    private String borrowTime;
    /**
     * 归还时间
     */
    private String returnTime;
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
