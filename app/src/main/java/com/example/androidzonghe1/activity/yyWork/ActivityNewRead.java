package com.example.androidzonghe1.activity.yyWork;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.androidzonghe1.R;
import com.example.androidzonghe1.adapter.yyWork.MyAdapter;
import com.example.androidzonghe1.entity.yyWork.Read;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ActivityNewRead extends AppCompatActivity {
    private List<Read> readList = new ArrayList<>();
    private MyAdapter adapter;
    private ListView listView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.third_fragment);
        listView = findViewById(R.id.list);
        Read r1 = new Read(R.drawable.pp1,"叮咚接送V4.0功能版本介绍",new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis())));
        Read r2 = new Read(R.drawable.p3,"拼车小技巧",new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis())));
        Read r3 = new Read(R.drawable.pp2,"接送员审核",new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis())));
        Read r4 = new Read(R.drawable.p4,"众志成城 同心战疫",new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis())));
        readList.add(r1);
        readList.add(r2);
        readList.add(r3);
        readList.add(r4);
        adapter = new MyAdapter(this,R.layout.list_item,readList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==2){
                    //跳转到详细界面
                    Intent intent = new Intent(ActivityNewRead.this, SendActivity.class);
                    startActivity(intent);
                }else if(position==1){
                    Intent intent1 = new Intent(ActivityNewRead.this, FuncActivity.class);
                    startActivity(intent1);
                }else if(position==0){
                    Intent intent2 = new Intent(ActivityNewRead.this, CarPoolActivity.class);
                    startActivity(intent2);
                }else if(position==3){
                    Intent intent3 = new Intent(ActivityNewRead.this, PreventActivity.class);
                    startActivity(intent3);
                }
            }
        });
    }
}
