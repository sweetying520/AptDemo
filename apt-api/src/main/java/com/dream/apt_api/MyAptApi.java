package com.dream.apt_api;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * function: apt 生成代码 api 封装给上层使用
 *
 * @author zy
 * @since 2021/6/27
 */
public class MyAptApi {


    @SuppressWarnings("all")
    public static void init() {
        try {
            Class c = Class.forName("com.dream.aptdemo.HelloWorld");
            Constructor declaredConstructor = c.getDeclaredConstructor();
            Object o = declaredConstructor.newInstance();
            Method test = c.getDeclaredMethod("test", String.class);
            test.invoke(o, "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
