package com.ams.common.enums;

public enum AssetsCheckStatus {
    /**
     * 审核中
     */
    APPROVALING("1", "审核中"),
    /**
     * 审核通过
     */
    AGREE("2", "通过"),
    /**
     * 审核被驳回
     */
    REJECT("3", "驳回"),
    /**
     * 未知状态
     */
    UNKNOW("-1", "未知");

    private String code;
    private String info;

    AssetsCheckStatus(String code, String info) {
        this.code = code;
        this.info = info;
    }

    public static boolean isExamineResult(String statusCode) {
        switch (statusCode) {
            case "1":
            case "2":
            case "3":
                return true;
            default:
                return false;
        }
    }

    public static AssetsCheckStatus getStatusByCode(String code) {
        switch (code) {
            case "1":
                return APPROVALING;
            case "2":
                return AGREE;
            case "3":
                return REJECT;
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
