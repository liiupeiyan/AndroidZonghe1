package com.example.androidzonghe1.activity.lsbWork;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidzonghe1.ConfigUtil;
import com.example.androidzonghe1.R;
import com.example.androidzonghe1.activity.yjWork.LoginActivity;
import com.example.androidzonghe1.adapter.lsbWork.KidsAdapter;
import com.example.androidzonghe1.entity.xtWork.Child;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class KidsActivity extends AppCompatActivity {

    ImageView imgBack;
    RecyclerView recyclerView;
    Button btnInsert;
    KidsAdapter kidsAdapter;
    List<Child> childs = new ArrayList<>();
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 1://接收到孩子信息
                    String jsonStr = (String) msg.obj;
                    Gson gson = new Gson();
                    Type collectionType = new TypeToken<ArrayList<Child>>(){}.getType();
                    childs = gson.fromJson(jsonStr,collectionType);
                    LinearLayoutManager manager = new LinearLayoutManager(getBaseContext());
                    recyclerView.setLayoutManager(manager);
                    kidsAdapter = new KidsAdapter(getApplicationContext(),childs);
                    kidsAdapter.setOnItemClickListener(new KidsAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(RecyclerView parent, View view, int position, Child data) {
                            Log.e("KidsActivity", "item onClick position:" + position);
                            Intent request = new Intent(getApplicationContext(), SearchActivity.class);
                            startActivityForResult(request, 1);
                        }
                    });
                    recyclerView.setAdapter(kidsAdapter);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kids);
        recyclerView = findViewById(R.id.list_view);
        imgBack = findViewById(R.id.img_back);
        btnInsert = findViewById(R.id.btn_insert);
        //网络连接
        getChildInfo(ConfigUtil.Url+"GetChildServlet?id="+ConfigUtil.parent.getId() );
        //点击添加按钮
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("KidsActivity", "btnInsert onClick");
                kidsAdapter.insertData();
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("KidsActivity", "imgBack onClick");
                finish();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 1:

                break;
        }
    }

    public void getChildInfo(final String s){
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    URL url = new URL(s);
                    Log.e("urllll",s);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    //设置http请求方式，get、post、put、...(默认get请求)
                    connection.setRequestMethod("POST");//设置请求方式

                    //从服务器段获取响应
                    InputStream is = connection.getInputStream();
                    byte[] bytes = new byte[512];
                    int len = is.read(bytes);//将数据保存在bytes中，长度保存在len中
                    String resp = new String(bytes,0,len);
                    Log.e("所有孩子",resp);

                    //借助Message传递数据
                    Message message = new Message();
                    //设置Message对象的参数
                    message.what = 1;
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