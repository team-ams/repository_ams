package com.ams.system.domain;

import com.ams.common.core.domain.BaseEntity;
import com.ams.common.enums.AssetsExamineStatus;
import lombok.Data;

import java.util.List;

/**
 * @author zengchao
 * @since 创建时间：2020/1/22 17:31
 */
@Data
public class AssetsAllocate extends BaseEntity {
    /**
     * 资产领用ID
     */
    private Integer allocateId;
    /**
     * 领用单号
     */
    private String allocateOrderNum;

    /**
     * 资产编号
     */
    private String assetsNumber;
    /**
     * 用户ID
     */
    private int userId;
    /**
     * 审批者ID
     */
    private int auditorId;
    /**
     * 资产领用状态（0：审核中，1：审核通过，2：驳回）
     */
    private String status;
    /**
     * 资产详情
     */
    private Assets assets;
    /**
     * 申请人详情
     */
    private SysUser user;
    /**
     * 审核人详情
     */
    private SysUser auditor;

    public static String getExamineStatusInfo(String examineStatusNumber) {
        return AssetsExamineStatus.AGREE.getCode().equals(examineStatusNumber) ? "已同意" : "已驳回";
    }
}