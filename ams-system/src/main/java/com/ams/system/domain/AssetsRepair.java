package com.ams.system.domain;

import com.ams.common.core.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class AssetsRepair extends BaseEntity {
    /**
     * '维修ID'
     */
    private Integer repairId;
    /**
     * '资产编号'
     */
    private String assetsNumber;
    /**
     * '申请人ID'
     */
    private int userId;
    /**
     * '修复状态（1：A，2：B，3：C）'
     */
    private String repairStatus;
    /**
     * '安装地点'
     */
    private String installationAddr;
    /**
     * '修复级别（1：部件维修，2：中修，3：大修）'
     */
    private String repairGrade;
    /**
     * '维修费用'
     */
    private String repairFees;
    /**
     * '维修费用'
     */
    private String accidentRemark;
    /**
     * '送修日期'
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date sentTime;
    /**
     * '维修日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date repairTime;

}