package com.ams.system.domain;

import com.ams.common.core.domain.BaseEntity;
import lombok.Data;

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

}