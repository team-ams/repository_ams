package com.ams.system.domain;

import com.ams.common.core.domain.BaseEntity;
import lombok.Data;

@Data
public class AssetsSource extends BaseEntity {
    /**
     * 资产来源ID
     */
    private Integer sourceId;
    /**
     * 资产来源名称
     */
    private String sourceName;
    /**
     * 显示顺序
     */
    private int sourceSort;
    /**
     * 状态（0正常  1停用）
     */
    private String status;
}