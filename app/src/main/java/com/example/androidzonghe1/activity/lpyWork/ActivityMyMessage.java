package com.example.androidzonghe1.activity.lpyWork;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

import com.example.androidzonghe1.ConfigUtil;
import com.example.androidzonghe1.R;
import com.example.androidzonghe1.adapter.lpyWork.RecycleAdapterMyMessage;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ActivityMyMessage extends AppCompatActivity {
    private RecyclerView recyclerView;
    private SmartRefreshLayout refreshLayout;
    private RecycleAdapterMyMessage adapter;
    private final int REFRESH = 0;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case REFRESH: //下拉刷新
                    //重新从客户端请求数据
                    adapter.notifyDataSetChanged();
                    refreshLayout.finishRefresh();
                    break;
                case 10://接收到消息数据
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_message);
        showAllMessages(ConfigUtil.Url+"GetAllMessageServlet?userId=");
        findViews();
        //设置刷新头和加载更多的样式
        refreshLayout.setRefreshHeader(new ClassicsHeader(this));
        //上拉刷新下拉加载更多的事件处理
        setListener();
    }

    public void onClickBack(View view) {
        finish();
    }
    private void findViews(){
        recyclerView = findViewById(R.id.rv_my_message);
        refreshLayout = findViewById(R.id.first_refreshLayout);
        if(ConfigUtil.messages.size() == 0){
            ConfigUtil.initMessages();
        }
        adapter = new RecycleAdapterMyMessage(ConfigUtil.messages);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void setListener(){
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                //下拉刷新时调用
                new Thread(){
                    @Override
                    public void run() {
                        //通过网络访问服务器端
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        //重新从客户端请求数
                        Message message = new Message();
                        message.what = REFRESH;
                        handler.sendMessage(message);
                    }
                }.start();

            }
        });
    }

    //网络流获取消息数据
    public void showAllMessages(String s){
        new Thread(){
            @Override
            public void run() {
                try {
                    URL url = new URL(s);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    //设置http请求方式，get、post、put、...(默认get请求)
                    connection.setRequestMethod("POST");//设置请求方式

                    //从服务器段获取响应
                    InputStream is = connection.getInputStream();
                    byte[] bytes = new byte[512];
                    int len = is.read(bytes);//将数据保存在bytes中，长度保存在len中
                    String resp = new String(bytes,0,len);
                    Log.e("所有消息",resp);

                    //借助Message传递数据
                    Message message = new Message();
                    //设置Message对象的参数
                    message.what = 10;
                    message.obj = resp;
                    //发送Message
                    handler.sendMessage(message);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }
}