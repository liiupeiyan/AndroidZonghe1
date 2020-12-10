package com.example.androidzonghe1.activity.yjWork;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.androidzonghe1.ConfigUtil;
import com.example.androidzonghe1.R;
import com.example.androidzonghe1.adapter.yyWork.MyOrderAdapter;
import com.example.androidzonghe1.entity.yyWork.Order;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MyOrderActivity extends AppCompatActivity {
    private ListView listView;
    private List<Order> wallets = new ArrayList<>();
    private MyOrderAdapter myOrderAdapter;
    private Toolbar toolbar;
    private TextView tv;
    private StringBuffer stringBuffer;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    try {
                        JSONArray jsonArray = new JSONArray(stringBuffer.toString());
                        for(int i =0;i<jsonArray.length();i++){
                            JSONObject object = new JSONObject(jsonArray.getJSONObject(i).toString());
                            Order order = new Order();
                            order.setId(object.getInt("order_id"));
                            order.setTime(object.getString("time"));
                            order.setBalance(object.getDouble("money")+"");
                            order.setOrderName(object.getString("from")+"-"+object.getString("to"));
                            order.setSpend("￥"+object.getDouble("price"));
                            order.setdName(object.getString("driver_name"));
                            order.setStatus(object.getString("status"));
                            wallets.add(order);
                        }
                        if(wallets.size()!=0 && wallets!=null){
                            setContentView(R.layout.order_status);
                            listView = findViewById(R.id.list_view);
                            myOrderAdapter = new MyOrderAdapter(MyOrderActivity.this,R.layout.order_item,wallets);
                            listView.setAdapter(myOrderAdapter);
                        }
                        Log.e("dddddddd",wallets.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
//                        wallets.clear();
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        toolbar = findViewById(R.id.toolBar);
        tv = findViewById(R.id.tv_tv);

        if (getActionBar() != null){
            getActionBar().hide();
        }
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("MyOrderActivity", "toolbar back onClicked");
                finish();
            }
        });
        //从数据端获取信息
        getOrderInfo();
    }

    private void getOrderInfo() {
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    URL url = new URL(ConfigUtil.xt+"ShowSucessOrderServlet?id="+ConfigUtil.parent.getId());
                    InputStream is = url.openStream();
                    stringBuffer = new StringBuffer();
                    int len = 0;
                    byte[] b = new byte[512];
                    while ((len = is.read(b))!=-1){
                        String str = new String(b,0,len);
                        stringBuffer.append(str);
                    }
//                    getUserMoney();
                    Log.e("查询订单结果",stringBuffer.toString());
                    Message message = new Message();
                    message.what = 1;
                    handler.sendMessage(message);
                    is.close();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}