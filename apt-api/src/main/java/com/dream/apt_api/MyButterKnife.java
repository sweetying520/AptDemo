package com.dream.apt_api;

import android.app.Activity;

import java.lang.reflect.Constructor;

/**
 * function: waiting for add
 *
 * @author zy
 * @since 2021/6/23
 */
public class MyButterKnife {

    @SuppressWarnings({"all"})
    public static void bind(Activity activity) {
        try {
            //获取"当前的activity类名+Binding"的class对象
            Class bindingClass = Class.forName(activity.getClass().getCanonicalName() + "Binding");
            //获取class对象的构造方法，该构造方法的参数为当前的activity对象
            Constructor constructor = bindingClass.getDeclaredConstructor(activity.getClass());
            //调用构造方法
            constructor.newInstance(activity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
