package com.dream.aptdemo;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.dream.apt_annotation.AptAnnotation;
import com.dream.apt_annotation.MyBindView;
import com.dream.apt_api.MyAptApi;
import com.dream.apt_api.MyButterKnife;


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
}




