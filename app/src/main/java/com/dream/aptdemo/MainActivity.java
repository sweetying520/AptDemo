package com.dream.aptdemo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.dream.apt_annotation.AptAnnotation;
import com.dream.apt_annotation.MyBindView;
import com.dream.apt_api.MyAptApi;
import com.dream.apt_api.MyButterKnife;
import com.dream.apt_api.MyViewCreatorDelegate;


@AptAnnotation(desc = "我是 MainActivity 上面的注解")
@SuppressLint("NonConstantResourceId,SetTextI18n")
public class MainActivity extends AppCompatActivity {


    @MyBindView(R.id.tv_erdai)
    TextView mTextView;

    @AptAnnotation(desc = "我是 onCreate 上面的注解")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyButterKnife.bind(this);
        mTextView.setText("erdai666");
        MyAptApi.init();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        //1、优先使用我们生成的类去进行 View 的创建
        View view = MyViewCreatorDelegate.getInstance().createView(name, context, attrs);
        if (view != null) {
            return view;
        }
        //2、一些系统的 View ,则走系统的一个创建流程
        return super.onCreateView(name, context, attrs);
    }
}




