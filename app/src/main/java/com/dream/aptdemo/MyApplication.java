package com.dream.aptdemo;

import android.app.Application;

import com.dream.apt_annotation.ViewCreator;

/**
 * function: 应用入口
 *
 * @author zy
 * @since 2021/6/29
 */


@ViewCreator
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
