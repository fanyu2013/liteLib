package com.fanyu.litelibrary.util;

import java.math.BigDecimal;

/**
 *
 * Created by fanyu on 16/12/12.
 */
public class DoubleNumUtil {

    public static Double keep2(Double number) {
        BigDecimal b = new BigDecimal(number);
        double f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return f1;
    }

    public static Double keep1(Double number) {
        BigDecimal b = new BigDecimal(number);
        double f1 = b.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
        return f1;
    }

    public static Double add(Double val1, Double val2){
        BigDecimal bd1=BigDecimal. valueOf(val1);
        BigDecimal bd2=BigDecimal. valueOf(val2);
        BigDecimal bd3;
        bd3 = bd1.add(bd2);
        return bd3.doubleValue();
    }

    public static Double subtract(Double val1, Double val2){
        BigDecimal bd1=BigDecimal. valueOf(val1);
        BigDecimal bd2=BigDecimal. valueOf(val2);
        BigDecimal bd3;
        bd3 = bd1.subtract(bd2);
        return bd3.doubleValue();
    }

    public static Double multiply(Double val1, Double val2){
        BigDecimal bd1=BigDecimal. valueOf(val1);
        BigDecimal bd2=BigDecimal. valueOf(val2);
        BigDecimal bd3;
        bd3 = bd1.multiply(bd2);
        return bd3.doubleValue();
    }

    public static Double divide(Double val1, Double val2){
        BigDecimal bd1=BigDecimal. valueOf(val1);
        BigDecimal bd2=BigDecimal. valueOf(val2);
        BigDecimal bd3;
        bd3 = bd1.divide(bd2);
        return bd3.doubleValue();
    }
}
