package com.ams.common.enums;

/**
 * @author zengchao
 */
public enum AssetsStutus {
    /**
     * 正常状态
     */
    NORMAL("0", "正常"),
    /**
     * 待审核状态
     */
    TOAPPROVAL("1", "待审核"),
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
    DISABLE("5", "停用");

    private final String code;
    private final String info;

    AssetsStutus(String code, String info) {
        this.code = code;
        this.info = info;
    }

    public static AssetsStutus getStatusByCode(String code) {
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
                return NORMAL;
        }
    }

    public String getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }
}
