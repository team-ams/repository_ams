package com.ams.system.domain;

import lombok.Data;

@Data
public class Task {

    /**
     * 序号
     */
    private int seqNo;
    /**
     * 任务类型
     */
    private String type;
    /**
     * 单号
     */
    private String orderNum;
    /**
     * 操作人
     */
    private String userName;
    /**
     * 用户ID
     */
    private int userId;
    /**
     * 所属学院
     */
    private String parentName;
    /**
     * 操作时间
     */
    private String date;
}
