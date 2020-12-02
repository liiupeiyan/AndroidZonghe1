package com.example.androidzonghe1.activity.yyWork;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.androidzonghe1.R;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Locale;

public class OrderDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tvGo;
    private TextView tvBack;
    private TextView tvWeek;
    private TextView tvHope;
    private DateFormat format;
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        getViews();
        tvHope.setOnClickListener(this);
    }

    private void getViews() {
        tvGo = findViewById(R.id.tv_to_sch);
        tvBack = findViewById(R.id.tv_left_sch);
        tvWeek = findViewById(R.id.tv_week);
        tvHope = findViewById(R.id.tv_hope);
        format = DateFormat.getDateTimeInstance();
        calendar = Calendar.getInstance(Locale.CHINA);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_hope://期望时间
                showDatePickerDialog(this, 4, tvHope, tvWeek, calendar);
                break;
            case R.id.tv_to_sch:
                showTimePickerDialog(this,2,tvGo,calendar);
                break;
        }
    }

    /**
     * 日期选择
     *
     * @param activity
     * @param themeResId
     * @param tv
     * @param calendar
     */
    public static void showDatePickerDialog(Activity activity, int themeResId, final TextView tv, final TextView week, Calendar calendar) {
        // 直接创建一个DatePickerDialog对话框实例，并将它显示出来
        new DatePickerDialog(activity, themeResId, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                String i = "";
//                if(1==calendar.get(Calendar.DAY_OF_WEEK)){
//                    i ="天";
//                }else if("2".equals(mWay)){
//                    mWay ="一";
//                }else if("3".equals(mWay)){
//                    mWay ="二";
//                }else if("4".equals(mWay)){
//                    mWay ="三";
//                }else if("5".equals(mWay)){
//                    mWay ="四";
//                }else if("6".equals(mWay)){
//                    mWay ="五";
//                }else if("7".equals(mWay)){
//                    mWay ="六";
//                }
                tv.setText(year + "-" + (month + 1) + "-" + dayOfMonth + calendar.get(Calendar.DAY_OF_WEEK));
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    /**
     * 时间选择
     *
     * @param activity
     * @param themeResId
     * @param tv
     * @param calendar
     */
    public static void showTimePickerDialog(Activity activity, int themeResId, final TextView tv, Calendar calendar) {
        new TimePickerDialog(activity, themeResId, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                tv.setText(hourOfDay+":"+minute);
            }
        }, calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),true).show();
    }
}