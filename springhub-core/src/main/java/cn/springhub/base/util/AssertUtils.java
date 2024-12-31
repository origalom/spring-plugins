package cn.springhub.base.util;

import cn.springhub.base.exception.SystemException;

/**
 * 断言工具类
 * @author AI
 * @date 2024/12/27 10:16
 * @version 1.0
**/
public class AssertUtils {

    /**
     * 断言对象不为null
     * @param object    断言对象
     */
    public static void notNull(Object object) {
        notNull(object, "对象不能为空");
    }

    /**
     * 断言对象不为null
     * @param object    断言对象
     * @param message   提示信息
     */
    public static void notNull(Object object, String message) {
        if (object == null) {
            throw new SystemException(message);
        }
    }

    public static void notEmpty(Object obj, String message) {

    }

}