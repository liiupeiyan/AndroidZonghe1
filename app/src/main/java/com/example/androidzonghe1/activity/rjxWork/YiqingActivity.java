package com.example.androidzonghe1.activity.rjxWork;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.androidzonghe1.R;

public class YiqingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获取ActionBar
        ActionBar actionBar = getSupportActionBar();
        //隐藏ActionBar
        actionBar.hide();
        setContentView(R.layout.activity_yiqing);
        ImageView pic6 = findViewById(R.id.pic6);
        pic6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(YiqingActivity.this, ServiceActivity.class);
                startActivity(intent);
            }
        });
    }
}