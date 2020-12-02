package com.example.androidzonghe1.activity.lsbWork;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidzonghe1.ConfigUtil;
import com.example.androidzonghe1.R;
import com.example.androidzonghe1.adapter.lsbWork.KidsAdapter;
import com.example.androidzonghe1.entity.xtWork.Child;

import java.io.IOException;
import java.io.InputStream;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kids);

        recyclerView = findViewById(R.id.list_view);
        imgBack = findViewById(R.id.img_back);
        btnInsert = findViewById(R.id.btn_insert);

        //网络连接
        getChildInfo(ConfigUtil.Url );

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("KidsActivity", "btnInsert onClick");
//                kidsAdapter.insertData();
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("KidsActivity", "imgBack onClick");
                finish();
            }
        });
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        kidsAdapter = new KidsAdapter(getApplicationContext(),childs);
        kidsAdapter.setOnItemClickListener(new KidsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position, Child data) {
                Log.e("ContactorActivity", "item onClick position:" + position);
                Intent request = new Intent(getApplicationContext(), SearchActivity.class);
                startActivityForResult(request, 1);
            }
        });
        recyclerView.setAdapter(kidsAdapter);
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
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    //设置http请求方式，get、post、put、...(默认get请求)
                    connection.setRequestMethod("POST");//设置请求方式
//                    Child child = new User(id,etName.getText().toString(),etPwd.getText().toString());
//                    String jsonStr = objectToJSON(user);
//                    Log.e("json",jsonStr);
//                    //获取输出流对象
//                    OutputStream os = connection.getOutputStream();
//                    os.write(jsonStr.getBytes());

                    //从服务器段获取响应
                    InputStream is = connection.getInputStream();
                    byte[] bytes = new byte[256];
                    int len = is.read(bytes);//将数据保存在bytes中，长度保存在len中
                    String resp = new String(bytes,0,len);
                    Log.e("登录的结果",resp);

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

}