package com.ams.common.enums;

public enum AssetsStatus {
    /**
     * 正常状态
     */
    NORMAL("0", "正常"),
    /**
     * 待审核状态
     */
    TOAPPROVAL("1", "审核中"),
    /**
     * 已领用状态
     */
    ALLOCATE("2", "已领用"),
    /**
     * 已外借状态
     */
    BORROWED("3", "已外借"),
    /**
     * 维修中状态
     */
    MAINTENANCING("4", "维修中"),
    /**
     * 停用状态
     */
    DISABLE("5", "停用"),
    /**
     * 未知状态
     */
    UNKNOW("-1","未知");

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
                return TOAPPROVAL;
            case "2":
                return ALLOCATE;
            case "3":
                return BORROWED;
            case "4":
                return MAINTENANCING;
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
