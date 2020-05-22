package com.plansolve.statistics.utils;

import lombok.extern.slf4j.Slf4j;

/**
 * @Author: 高一平
 * @Date: 2018/10/10
 * @Description:
 **/
@Slf4j
public class MathUtil {

    private static final Double MONEY_RANGE = 0.01;

    /**
     * 比较两个金额是否相等
     *
     * @param d1
     * @param d2
     * @return
     */
    public static Boolean equals(Double d1, Double d2) {
        Double result = Math.abs(d1 - d2);
        if (result < MONEY_RANGE) {
            return true;
        } else {
            return false;
        }
    }

}
