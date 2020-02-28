package com.ams.system.domain;

import lombok.Data;

@Data
public class AssetsInfo {

    /**
     * 资产编号Mapper映射作为id
     */
    private String assetsNumber;
    /**
     * 总资产数量
     */
    private int zongzichanCount;
    /**
     * 总资产金额
     */
    private double zongzichanSum;
    /**
     * 闲置资产数量
     */
    private int xianzhiCount;
    /**
     * 闲置资产金额
     */
    private double xianzhiSum;
    /**
     * 使用资产数量
     */
    private int shiyongCount;
    /**
     * 使用资产金额
     */
    private double shiyongSum;
    /**
     * 待报废资产数量
     */
    private int daibaofeiCount;
    /**
     * 待报废资产金额
     */
    private double daibaofeiSum;
    /**
     * 处置资产数量
     */
    private int chuzhiCount;
    /**
     * 处置资产金额
     */
    private double chuzhiSum;
}
