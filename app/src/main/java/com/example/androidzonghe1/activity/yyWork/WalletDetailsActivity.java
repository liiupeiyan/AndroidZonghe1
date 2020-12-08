package com.example.androidzonghe1.activity.yyWork;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.androidzonghe1.ConfigUtil;
import com.example.androidzonghe1.R;
import com.example.androidzonghe1.adapter.yyWork.WalletDetailsAdapter;
import com.example.androidzonghe1.entity.yyWork.Order;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WalletDetailsActivity extends AppCompatActivity {
    private ListView listView;
    private List<Order> wallets = new ArrayList<>();
    private WalletDetailsAdapter adapter;
    private StringBuffer stringBuffer;
    private String uMoney;
    ImageView imgBack;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    try {
                        JSONArray jsonArray = new JSONArray(stringBuffer.toString());
                        wallets.clear();
                        for(int i =0;i<jsonArray.length();i++){
                            JSONObject object = new JSONObject(jsonArray.getJSONObject(i).toString());
                            Order order = new Order();
                            order.setTime(object.getString("time"));
                            order.setBalance(object.getDouble("money")+"");
                            order.setOrderName(object.getString("from")+"-"+object.getString("to"));
                            order.setSpend("-"+object.getDouble("price"));
                            order.setdName(object.getString("driver_name"));
                            wallets.add(order);
                        }
                        adapter = new WalletDetailsAdapter(R.layout.wallet_item_list,WalletDetailsActivity.this,wallets);
                        listView.setAdapter(adapter);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent intent = new Intent(WalletDetailsActivity.this,WalletActivity.class);
                                intent.putExtra("name",wallets.get(position).getOrderName());
                                intent.putExtra("spend",wallets.get(position).getSpend());
                                intent.putExtra("time",wallets.get(position).getTime());
                                intent.putExtra("balance",wallets.get(position).getBalance());
                                intent.putExtra("driver",wallets.get(position).getdName());
                                startActivity(intent);
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_details);
        listView = findViewById(R.id.wallet_list);
        imgBack = findViewById(R.id.img_back);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //根据用户id获取订单信息
        getOrderInfo();
    }


    private void getOrderInfo() {
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    URL url = new URL(ConfigUtil.xt+"ShowWalletServlet?id="+ConfigUtil.parent.getId());
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
    public void getUserMoney(){
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    URL url = new URL(ConfigUtil.xt+"QueryMoneyServlet");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    OutputStream os = connection.getOutputStream();
                    os.write((ConfigUtil.parent.getId()+"").getBytes());
                    InputStream is =connection.getInputStream();
                    byte[] buffer = new byte[512];
                    int len = 0;
                    if((len = is.read(buffer))!=-1){
                        uMoney = new String(buffer,0,len,"UTF-8");
                    }
                    Message message = new Message();
                    message.what=1;
                    handler.sendMessage(message);
                    os.close();
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