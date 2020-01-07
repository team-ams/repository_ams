package com.ams.system.domain;

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
    private Long assetsId;

    /**资产编号*/
    private String assetsNumber;

    /**资产名称*/
    private String assetsName;

    /**资产性质*/
    private String assetsNature;

    /**资产类别*/
    private String assetsType;

    /**数量*/
    private Long assetsAmount;

    /**计量单位*/
    private String measuringUnit;

    /**单价*/
    private float assets_price;

    /**保管单位*/
    private String storageUnit;

    /**保管部门*/
    private String storageDepartment;

    /**保管人*/
    private String custodian;

    /**使用人*/
    private String user;

    /**存放地点*/
    private String storageAddr;

    /**使用情况(0：正常  1：停用  2：维修中)*/
    private String useStatus;

    /**资产来源(0：购置  1：赠送)*/
    private String assetsSource;

    /**使用年限*/
    private String usefulLife;

    /**残值率*/
    private float residualRate;

    /**是否盘点(0：是  1：否)*/
    private String checkStatus;

    /**规格*/
    private String assetsFormat;

    /**型号*/
    private String assetsModel;

    /**品牌*/
    private String assetsBrand;

    /**购入日期*/
    private String purchaseDate;

    /**出厂日期*/
    private String manufactureDate;

    /**保修日期*/
    private String warrantyDate;

    /**备注*/
    private String mark;


}