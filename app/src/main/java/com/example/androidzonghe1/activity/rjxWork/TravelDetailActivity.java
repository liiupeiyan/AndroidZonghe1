package com.example.androidzonghe1.activity.rjxWork;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.androidzonghe1.ConfigUtil;
import com.example.androidzonghe1.R;
import com.example.androidzonghe1.entity.yyWork.DriverOrder;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Locale;

public class TravelDetailActivity extends AppCompatActivity {
    private ImageView driverImg;
    public  static TextView driverName;
    private TextView chooseState;
    private Button callDriver;
    private Button driverVideo;
    private ImageView overLine;
    private ImageView iv_over;
    private TextView tv_over;
    private TextView tvMile;
    private TextView tvPrice;
    private TextView tvWeek;
    private TextView from;
    private TextView to;
    private ImageView inF;
    private ImageView outS;
    private TextView inF0;
    private TextView outS0;
    private TextView tvGo;
    private TextView tvBack;
    private Button over;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_detail);
        findViews();
        //网络操作
        getTravelDetails(ConfigUtil.Url);

        setListener();
    }

    private void findViews(){
        driverImg = findViewById(R.id.iv_order_driver_img);
        driverName = findViewById(R.id.tv_order_driver_name);
        callDriver = findViewById(R.id.btn_call_driver);
        driverVideo = findViewById(R.id.btn_look_driver);
        from = findViewById(R.id.tv_from);
        to = findViewById(R.id.tv_to);
        overLine = findViewById(R.id.iv_over_driver_line);
        iv_over = findViewById(R.id.iv_over);
        tv_over = findViewById(R.id.tv_over);
        tvMile = findViewById(R.id.tv_mile);
        tvPrice = findViewById(R.id.tv_price);
        tvWeek = findViewById(R.id.tv_week);
        inF = findViewById(R.id.iv_in_f);
        outS = findViewById(R.id.iv_in_s);
        inF0 = findViewById(R.id.tv_in_f);
        outS0=findViewById(R.id.tv_in_s);
        tvGo = findViewById(R.id.tv_to_sch);
        tvBack = findViewById(R.id.tv_left_sch);
        over = findViewById(R.id.btn_over_order);
    }

    //注册监听器
    private void setListener(){
        //呼叫司机
        callDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        //视频监控
        driverVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        //结束行程
        over.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //改变行程状态
                overLine.setImageResource(R.drawable.hline2);
                iv_over.setImageResource(R.drawable.spot1);
                tv_over.setTextColor(Color.RED);
            }
        });

    }

    private void getTravelDetails(String s){
        new Thread(){
            @Override
            public void run() {

            }
        };
    }
}