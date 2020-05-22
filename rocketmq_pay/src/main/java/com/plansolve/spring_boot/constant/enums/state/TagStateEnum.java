package com.plansolve.statistics.constant.enums.state;

/**
 * @Author: Andrew
 * @Date: 2018/6/1
 * @Description: 标签状态码
 **/

public enum TagStateEnum {

    NORMOL("normal","正常状态"),

    FROZEN("frozen","停用状态");

    private String state;

    private String message;

    TagStateEnum(String state, String message) {
        this.state = state;
        this.message = message;
    }

    public String getState() {
        return state;
    }

    public String getMessage() {
        return message;
    }
}
