package com.example.androidzonghe1.activity.rjxWork;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.androidzonghe1.R;

public class ServiceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获取ActionBar
//        ActionBar actionBar = getSupportActionBar();
        //隐藏ActionBar
//        actionBar.hide();
        setContentView(R.layout.activity_service);
    }
}