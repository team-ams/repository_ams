package com.ams.system.domain;

import lombok.Data;

@Data
public class WeiXiu {
    /**
     * 维修时间
     */
    private String repairDate;
    /**
     * 金额
     */
    private String totalMoney;
    /**
     * 数量
     */
    private String repairCount;
}
