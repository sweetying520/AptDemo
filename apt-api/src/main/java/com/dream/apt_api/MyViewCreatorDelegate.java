package com.dream.apt_api;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * function: View 创建代理类，提供给上层调用
 *
 * @author zy
 * @since 2021/6/29
 */
public class MyViewCreatorDelegate implements IMyViewCreator{

    private IMyViewCreator mIMyViewCreator;

    //================================== 单例 start =====================================
    @SuppressWarnings("all")
    private MyViewCreatorDelegate(){
        try {
            // 通过反射拿到 Apt 生成的类
            Class aClass = Class.forName("com.dream.aptdemo.MyViewCreatorImpl");
            mIMyViewCreator = (IMyViewCreator) aClass.newInstance();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public static MyViewCreatorDelegate getInstance(){
        return Holder.MY_VIEW_CREATOR_DELEGATE;
    }

    private static final class Holder{
        private static final MyViewCreatorDelegate MY_VIEW_CREATOR_DELEGATE = new MyViewCreatorDelegate();
    }
    //================================== 单例 end =======================================


    /**
     * 通过生成的类创建 View
     *
     * @param name 控件名称
     * @param context 上下文
     * @param attributeSet 属性
     * @return View
     */
    @Override
    public View createView(String name, Context context, AttributeSet attributeSet) {
        if(mIMyViewCreator != null){
            return mIMyViewCreator.createView(name, context, attributeSet);
        }
        return null;
    }
}
