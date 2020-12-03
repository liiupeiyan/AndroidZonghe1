package com.example.androidzonghe1.activity.yyWork;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.androidzonghe1.ConfigUtil;
import com.example.androidzonghe1.Fragment.lpyWork.FragmentDriver;
import com.example.androidzonghe1.R;
import com.example.androidzonghe1.activity.lpyWork.MyTheActivity;
import com.example.androidzonghe1.adapter.rjxWork.DriverAdapter;
import com.example.androidzonghe1.entity.lpyWork.Driver;
import com.example.androidzonghe1.entity.yyWork.DriverOrder;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class OrderDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnDriver;
    private ImageView driverImg;
    private TextView driverName;
    private TextView chooseState;
    private Button chooseDriver;
    private Button driverInfo;
    private DriverOrder order=new DriverOrder();
    private ImageView inF;
    private ImageView outS;
    private TextView from;
    private TextView to;
    private TextView inF0;
    private TextView outS0;
    private LinearLayout in;
    private LinearLayout out;
    private TextView tvGo;
    private TextView tvBack;
    private TextView tvWeek;
    private TextView tvHope;
    private TextView tvSpend;
    private TextView tvPrice;
    private Button add;
    private DateFormat format;
    private Calendar calendar;
    private String time;
    private String week;
    private double distance;
    private String pwd;
    private List<Driver> drivers = new ArrayList<Driver>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_order);
        getViews();
//        //选择司机
        chooseDriver.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //跳转到司机展示页，客户自主找寻空闲司机
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(OrderDetailsActivity.this);
                View v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.driver_list_item,null);
                Button btnCancel = v.findViewById(R.id.btn_cancel);
                Button btnConfirm = v.findViewById(R.id.btn_confirm);
                DriverAdapter driverAdapter = new DriverAdapter(drivers,R.layout.item_recycleview_driver,getApplicationContext());
                ListView listView = v.findViewById(R.id.lv_driver);
                listView.setAdapter(driverAdapter);
                //取消按钮
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        driverName.setText("小伴接送员");
                        chooseState.setText("未选择");
                        bottomSheetDialog.dismiss();
                    }
                });
                //确定按钮
                btnConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        driverName.setText("张师傅");
                        chooseState.setText("已选择");
                        bottomSheetDialog.dismiss();

                    }
                });
                bottomSheetDialog.setContentView(view);
                bottomSheetDialog.setCancelable(true);
                bottomSheetDialog.setCanceledOnTouchOutside(true);
                bottomSheetDialog.show();
            }
        });
        //获取从FragmentLaunchRoute传递过来的数据：起点  终点；写入from和to控件中
        Bundle bundle =  getIntent().getExtras().getBundle("lrInfo");
        from.setText(bundle.getString("stName"));
        to.setText(bundle.getString("enName"));
        //获取从FragmentLaunchRoute传递过来的数据: 起点和终点的经纬度。写入tvSpend中
//        distance = CaculateDistance.GetDistance(38.002119,114.520159,37.984026,114.528652);
        distance = bundle.getDouble("distance");
        double gl = Math.round( distance / 100d) / 10d;
        tvSpend.setText(gl+"");
        //计算价格，每公里15元写入tvPrice中
        tvPrice.setText(gl*15+"");
        tvHope.setOnClickListener(this);
        tvGo.setOnClickListener(this);
        tvBack.setOnClickListener(this);
        in.setOnClickListener(this);
        out.setOnClickListener(this);
        add.setOnClickListener(this);
//        btnDriver.setOnClickListener(this);
        //2
        order.setFrom(from.getText()+"");
        //3
        order.setTo(to.getText()+"");
        //4通过自主找寻司机
//        order.setDriver(tvDriver.getText()+"");
        order.setPrice(Double.parseDouble(tvPrice.getText()+""));

    }

    private void getViews() {
        from = findViewById(R.id.tv_from);
        to = findViewById(R.id.tv_to);
        inF = findViewById(R.id.iv_in_f);
        outS = findViewById(R.id.iv_in_s);
        inF0 = findViewById(R.id.tv_in_f);
        outS0=findViewById(R.id.tv_in_s);
        in = findViewById(R.id.lin_in_sch);
        out = findViewById(R.id.lin_out_sch);
        tvGo = findViewById(R.id.tv_to_sch);
        tvBack = findViewById(R.id.tv_left_sch);
        tvWeek = findViewById(R.id.tv_week);
        tvHope = findViewById(R.id.tv_hope);
        format = DateFormat.getDateTimeInstance();
        calendar = Calendar.getInstance(Locale.CHINA);
        add = findViewById(R.id.btn_add_order);
        btnDriver = findViewById(R.id.btn_find_driver);
        driverImg = findViewById(R.id.iv_order_driver_img);
        driverName = findViewById(R.id.tv_order_driver_name);
        chooseDriver = findViewById(R.id.btn_find_driver);
        chooseState = findViewById(R.id.tv_order_choose_state);
//        btnDriver
//        tvSpend = findViewById(R.id.tv_spend);
        tvPrice = findViewById(R.id.tv_price);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_hope://期望时间
                showDatePickerDialog(this, 4, tvHope,tvWeek, calendar);
                //获取周几
                break;
            case R.id.tv_to_sch:
                showTimePickerDialog(this,2,tvGo,calendar);
                break;
            case R.id.tv_left_sch:
                showTimePickerDialog(this,2,tvBack,calendar);
                break;
            case R.id.lin_in_sch:
                //入校
                order.setAddress("入校");
                outS.setImageResource(R.drawable.yes1);
                outS0.setTextColor(getResources().getColor(R.color.gray));
                inF.setImageResource(R.drawable.yes);
                inF0.setTextColor(getResources().getColor(R.color.myColor));
                //5
                break;
            case R.id.lin_out_sch:
                //离校
                order.setAddress("离校");
                inF.setImageResource(R.drawable.yes1);
                inF0.setTextColor(getResources().getColor(R.color.gray));
                outS.setImageResource(R.drawable.yes);
                outS0.setTextColor(getResources().getColor(R.color.myColor));
                break;
            case R.id.btn_add_order:
                //出现输入支付密码框。密码输入成功后进入钱包界面
                //进行密码判断。从数据库查询该用户的密码后进行判断，相等的话进入TestActivity。将得到的密码赋给 pwd
                FragmentManager manager = getSupportFragmentManager();
                final PayPasswordDialog dialog=new PayPasswordDialog(OrderDetailsActivity.this,R.style.mydialog,pwd,manager,this,1);
                dialog.setDialogClick(new PayPasswordDialog.DialogClick() {
                    @Override
                    public void doConfirm(String password) {
                        dialog.dismiss();
                        Log.e("输入密码为：",password);
                    }
                });
                dialog.show();
                break;
            case R.id.btn_find_driver:
                //跳转到司机展示页，客户自主找寻空闲司机
                ConfigUtil.flagChooseDriver = true;
                Intent intent = new Intent(OrderDetailsActivity.this, MyTheActivity.class);
                startActivityForResult(intent,1);
                break;
            case R.id.btn_order_driver_info:
                //司机详细信息
                break;
        }
    }
    //将对象转化为json字符串
    public String toStr(DriverOrder order){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("from",order.getFrom());
            jsonObject.put("to",order.getTo());
            jsonObject.put("driver",order.getDriver());
            jsonObject.put("time",order.getTime());
            jsonObject.put("address",order.getAddress());
            jsonObject.put("date",order.getDate());
            jsonObject.put("price",order.getPrice());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    /**
     * 日期选择
     *
     * @param activity
     * @param themeResId
     * @param tv
     * @param calendar
     */
    public void showDatePickerDialog(Activity activity, int themeResId, final TextView tv,final TextView week0,Calendar calendar) {
        // 直接创建一个DatePickerDialog对话框实例，并将它显示出来
        new DatePickerDialog(activity, themeResId, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                tv.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
                order.setDate(year + "-" + (month + 1) + "-" + dayOfMonth);
                Log.e("日期",order.getDate());
                week = "周";
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Calendar c = Calendar.getInstance();
                try {
                    c.setTime(format.parse(year+"-"+(month+1)+"-"+dayOfMonth));
                    week = getWeek(week,c);
                    week0.setText(week);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private static String getWeek(String week,Calendar c) {
        if (c.get(Calendar.DAY_OF_WEEK) == 1) {
            week += "天";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 2) {
            week += "一";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 3) {
            week += "二";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 4) {
            week += "三";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 5) {
            week += "四";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 6) {
            week += "五";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 7) {
            week += "六";
        }
        return week;
    }

    /**
     * 时间选择
     *
     * @param activity
     * @param themeResId
     * @param tv
     * @param calendar
     */
    public void showTimePickerDialog(Activity activity, int themeResId, final TextView tv, Calendar calendar) {
        new TimePickerDialog(activity, themeResId, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                if(minute==0){
                    tv.setText(hourOfDay+":"+minute+"0");
                }else{
                    tv.setText(hourOfDay+":"+minute);
                }
                //6
                order.setTime(hourOfDay+":"+minute);
                Log.e("时间",order.getTime());
            }
        }, calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),true).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}