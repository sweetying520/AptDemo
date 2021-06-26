package com.dream.aptdemo;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.dream.apt_annotation.MyBindView;
import com.dream.apt_api.MyButterKnife;


//@TestAnnotation(ip = "10.0.34.256",name = "erdai",port = 8888)
@SuppressLint("NonConstantResourceId,SetTextI18n")
public class MainActivity extends AppCompatActivity {


    @MyBindView(R.id.tv_erdai)
    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyButterKnife.bind(this);
        mTextView.setText("erdai666");




//        TestAnnotation annotation = MainActivity.class.getAnnotation(TestAnnotation.class);
//        Log.d("MainActivity", "onCreate: " + annotation.ip() + "  " + annotation.name() + "  " + annotation.port());
    }
}