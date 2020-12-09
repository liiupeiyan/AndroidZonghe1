package com.example.androidzonghe1.activity.yyWork;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.androidzonghe1.R;

public class WalletActivity extends AppCompatActivity {
    private String name;
    private String spend;
    private String balance;
    private String time;
    private TextView oName;
    private TextView oSpend;
    private TextView oTime;
    private TextView oNo;
    private TextView oBalance;
    private TextView oDriver;
    private String driver;
    ImageView imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet2);
        getViews();
        Intent response = getIntent();
        name = response.getStringExtra("name");
        spend = response.getStringExtra("spend");
        balance = response.getStringExtra("balance");
        time = response.getStringExtra("time");
        driver = response.getStringExtra("driver");
        //查询数据库获取信息 获取司机和订单编号
        oName.setText(name);
        oSpend.setText(spend);
        oBalance.setText(balance);
        oTime.setText(time);
        oDriver.setText(driver);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getViews() {
        oName = findViewById(R.id.order_name);
        oSpend = findViewById(R.id.order_spend);
        oTime = findViewById(R.id.order_time);
        oNo = findViewById(R.id.order_no);
        oBalance = findViewById(R.id.bala);
        oDriver = findViewById(R.id.driver);
        imgBack = findViewById(R.id.img_back);
    }
}