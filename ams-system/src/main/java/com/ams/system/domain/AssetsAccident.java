package com.ams.system.domain;

import com.ams.common.core.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class AssetsAccident extends BaseEntity {

    /**
     * '事故ID'
     */
    private Integer accidentId;
    /**
     * '资产编号'
     */
    private String assetsNumber;
    /**
     * '事故上报人ID'
     */
    private int reportUserId;
    /**
     * '保养后状态（0：正常，1：故障）'
     */
    private String maintainStatus;
    /**
     * '事故发生部门'
     */
    private String accidentDept;
    /**
     * '事故级别（1：一般事故，2：中等事故，3：重大事故）'
     */
    private String accidentGrade;
    /**
     * '经济损失'
     */
    private String economicLosses;
    /**
     * '故障描述'
     */
    private String accidentRemark;
    /**
     * '防范总结'
     */
    private String summary;
    /**
     * '事故发生日期'
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date accidentTime;


}

