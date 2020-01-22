package com.ams.system.domain;

import com.ams.common.annotation.Excel;
import com.ams.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * 资产对象 assets
 *
 * @author zengchao
 */
@Data
public class Assets extends BaseEntity {
    private static final long serialVersionUID = 1L;


    /**资产ID*/
    @Excel(name = "资产序号",cellType = Excel.ColumnType.NUMERIC)
    private Long assetsId;

    /**资产编号*/
    @Excel(name = "资产编号",cellType = Excel.ColumnType.NUMERIC)
    private String assetsNumber;

    /**资产名称*/
    @Excel(name = "资产名称")
    private String assetsName;

    /**资产性质*/
    @Excel(name = "资产性质")
    private String assetsNature;

    /**资产类别*/
    @Excel(name = "资产类别")
    private String assetsType;

    /**数量*/
    @Excel(name = "数量")
    private Long assetsAmount;

    /**计量单位*/
    @Excel(name = "计量单位")
    private String measuringUnit;

    /**单价*/
    @Excel(name = "单价")
    private float assetsPrice;

    /**保管单位*/
    @Excel(name = "保管单位")
    private String storageUnit;

    /**保管部门*/
    @Excel(name = "保管部门")
    private String storageDepartment;

    /**保管人*/
    @Excel(name = "保管人")
    private String custodian;

    /**使用人*/
    @Excel(name = "使用人")
    private String user;

    /**存放地点*/
    @Excel(name = "存放地点")
    private String storageAddr;

    /**使用情况(0：正常 1：已领用 2：已外借 3：维修中 4：停用)*/
    @Excel(name = "使用情况",type = Excel.Type.EXPORT,readConverterExp = "0=正常,1=已领用,2=已外借,3=维修中,4=停用")
    private String useStatus;

    /**资产来源(0：购置  1：赠送  3：未知)*/
    @Excel(name = "资产来源")
    private String assetsSource;

    /**使用年限*/
    @Excel(name = "使用年限")
    private String usefulLife;

    /**残值率*/
    @Excel(name = "残值率")
    private float residualRate;

    /**是否盘点(0：是  1：否)*/
    @Excel(name = "是否盘点")
    private String checkStatus;

    /**规格*/
    @Excel(name = "规格")
    private String assetsFormat;

    /**型号*/
    @Excel(name = "型号")
    private String assetsModel;

    /**品牌*/
    @Excel(name = "品牌")
    private String assetsBrand;

    /**购入日期*/
    @Excel(name = "购入日期")
    private String purchaseDate;

    /**出厂日期*/
    @Excel(name = "出厂日期")
    private String manufactureDate;

    /**保修日期*/
    @Excel(name = "保修日期")
    private String warrantyDate;

    /**删除标志（0：正常  2：已删除）*/
    private String delFlag;

    /**备注*/
    @Excel(name = "备注",type = Excel.Type.EXPORT)
    private String mark;




}