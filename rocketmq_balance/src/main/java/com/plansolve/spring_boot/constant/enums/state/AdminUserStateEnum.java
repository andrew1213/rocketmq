package com.plansolve.statistics.constant.enums.state;

/**
 * @Author: 高一平
 * @Date: 2018/6/1
 * @Description: 用户状态码
 **/

public enum AdminUserStateEnum implements StateEnum {
    NORMAL("normal","正常状态"),
    DELETED("deleted","已删除"),
    FROZEN("frozen","已冻结");

    private String state; // 用户状态

    private String message; // 相关状态详情

    AdminUserStateEnum(String state, String message) {
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
