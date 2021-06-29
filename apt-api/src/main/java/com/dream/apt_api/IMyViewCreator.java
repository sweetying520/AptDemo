package com.dream.apt_api;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * function: View 创建接口层
 *
 * @author zy
 * @since 2021/6/29
 */
public interface IMyViewCreator {


    /**
     * 通过 new 的方式创建 View
     *
     * @param name 控件名称
     * @param context 上下文
     * @param attributeSet 属性
     */
    View createView(String name, Context context, AttributeSet attributeSet);
}