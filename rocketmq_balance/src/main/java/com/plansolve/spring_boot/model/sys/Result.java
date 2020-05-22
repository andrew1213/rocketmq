package com.plansolve.statistics.model.sys;

import java.io.Serializable;

/**
 * @Author: 高一平
 * @Date: 2018/6/1
 * @Description: 客户端返回对象
 **/

public class Result<T> implements Serializable {

    private Integer rtState;

    private String msg;

    private T rtData;

    public Result() {
    }

    public Result(Integer rtState, String msg) {
        this.rtState = rtState;
        this.msg = msg;
    }

    public Result(Integer rtState, String msg, T rtData) {
        this.rtState = rtState;
        this.msg = msg;
        this.rtData = rtData;
    }

    public Integer getRtState() {
        return rtState;
    }

    public void setRtState(Integer rtState) {
        this.rtState = rtState;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getRtData() {
        return rtData;
    }

    public void setRtData(T rtData) {
        this.rtData = rtData;
    }

    @Override
    public String toString() {
        return "Result{" +
                "rtState=" + rtState +
                ", msg='" + msg + '\'' +
                ", rtData=" + rtData +
                '}';
    }
}
