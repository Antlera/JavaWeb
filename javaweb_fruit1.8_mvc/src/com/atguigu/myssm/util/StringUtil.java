package com.atguigu.myssm.util;

/**
 * @author by Antlers Email:3219599757@qq.com
 * @Description
 * @date 2022/7/22.
 * @package_name com.atguigu.myssm.util
 **/
public class StringUtil {
    public static boolean isEmpty(String str){
        return str == null || "".equals(str);
    }
    public static boolean isNotEmpty(String str){
        return !isEmpty(str);
    }
}
