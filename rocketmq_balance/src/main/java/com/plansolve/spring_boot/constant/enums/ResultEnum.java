package com.plansolve.statistics.constant.enums;

import com.plansolve.statistics.constant.enums.code.CodeEnum;

/**
 * @Author: 高一平
 * @Date: 2018/6/1
 * @Description: 返回数据状态码
 **/

public enum ResultEnum implements CodeEnum {
    SUCCESS(200, "成功"),
    PARAM_ERROR(601, "参数错误"),
    LOGIN_ERROR(602, "登录信息不正确"),
    PERMISSION_ERROR(603, "该用户不具有此操作权限"),
    SERVER_BUSY_ERROR(604, "服务器忙"),
    ADD_REPEAT_ERROR(605, "重复创建错误"),
    NOT_EXIST(606, "目标信息不存在");

    private Integer code;

    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}