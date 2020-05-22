package com.plansolve.statistics.constant.enums.type;

/**
 * @Author: Andrew
 * @Date: 2018/6/18
 * @Description: 数据平台类型
 **/

public enum PlatformTypeEnum implements TypeEnum {
    APP_PLAT("app","农小满APP"),

    MP_PLAT("mp","微信公众号"),

    APPLET_PLAT("applet","微信小程序");

    private String type; // 日志类型

    private String message; // 类型说明

    PlatformTypeEnum(String type, String message) {
        this.type = type;
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }

}
