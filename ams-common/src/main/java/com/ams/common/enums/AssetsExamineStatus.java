package com.ams.common.enums;

public enum AssetsExamineStatus {
    /**
     * 审核中
     */
    APPROVALING("0", "审核中"),
    /**
     * 审核通过
     */
    AGREE("1", "同意"),
    /**
     * 审核被驳回
     */
    REJECT("2", "驳回"),
    /**
     * 未知状态
     */
    UNKNOW("-1", "未知");

    private String code;
    private String info;

    AssetsExamineStatus(String code, String info) {
        this.code = code;
        this.info = info;
    }

    public static boolean isExamineResult(String statusCode) {
        switch (statusCode) {
            case "0":
            case "1":
            case "2":
                return true;
            default:
                return false;
        }
    }

    public static AssetsExamineStatus getStatusByCode(String code) {
        switch (code) {
            case "0":
                return APPROVALING;
            case "1":
                return AGREE;
            case "2":
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
