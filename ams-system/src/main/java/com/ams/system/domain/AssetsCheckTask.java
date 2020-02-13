package com.ams.system.domain;

import com.ams.common.core.domain.BaseEntity;
import lombok.Data;

import java.util.List;

@Data
public class AssetsCheckTask extends BaseEntity {

    /**
     * '盘点id'
     */
    private Integer taskId;
    /**
     * '盘点单号'
     */
    private String checkNumber;
    /**
     * '盘点负责人'
     */
    private int checkUserId;
    /**
     * 盘点地址
     */
    private String checkAddr;
    /**
     * '应盘数量'
     */
    private int targetNum;
    /**
     * '盘盈'
     */
    private Integer checkProfit;
    /**
     * '盘亏'
     */
    private Integer checkLoss;
    /**
     * '是否盘点（0：未盘点 1：已盘点）'
     */
    private String isCheck;
    /**
     * 已盘点后状态
     */
    private String checkStatus;
    /**
     * 详细盘点资产项
     */
    private List<AssetsCheckItem> checkItems;
    /**
     * 用户
     */
    private SysUser user;

}