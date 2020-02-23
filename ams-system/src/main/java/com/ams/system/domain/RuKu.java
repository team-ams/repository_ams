package com.ams.system.domain;

import lombok.Data;

@Data
public class RuKu {

    /**
     * 资产名称---->改成资产类别assetsType
     */
    private String assetsName;
    /**
     * 金额
     */
    private String totalMoney;
    /**
     * 数量
     */
    private String assetsCount;
}
