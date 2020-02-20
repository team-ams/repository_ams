package com.ams.common.enums;

public enum AssetsStatus {
    /**
     * 正常状态
     */
    NORMAL("0", "闲置"),

    /**
     * 审核中状态
     */
    RESERVE("1", "审核中"),

    /**
     * 在用状态
     */
    USING("2", "在用"),

    /**
     * 维修中状态
     */
    MAINTENANCING("3", "维修中"),

    /**
     * 待报废状态
     */
    TOSCRAPPED("4", "待报废"),

    /**
     * 停用状态
     */
    DISABLE("5", "停用"),

    /**
     * 未知状态
     */
    UNKNOW("-1", "未知");

    private final String code;
    private final String info;

    AssetsStatus(String code, String info) {
        this.code = code;
        this.info = info;
    }

    public static AssetsStatus getStatusByCode(String code) {
        switch (code) {
            case "0":
                return NORMAL;
            case "1":
                return RESERVE;
            case "2":
                return USING;
            case "3":
                return MAINTENANCING;
            case "4":
                return TOSCRAPPED;
            case "5":
                return DISABLE;
            default:
                return UNKNOW;
        }
    }

    public String getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }
}
