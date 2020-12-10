package com.example.androidzonghe1.activity.yjWork;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.androidzonghe1.ConfigUtil;
import com.example.androidzonghe1.R;
import com.example.androidzonghe1.adapter.yjWork.MyTripAdapter;

public class MyTripActivity extends AppCompatActivity {
    private ImageView back,calendar;
    private View none;
    private ListView listView;
    private MyTripAdapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_trip);
        back = findViewById(R.id.trip_back);
        calendar = findViewById(R.id.calendar);
        none = findViewById(R.id.trip_none);
        listView = findViewById(R.id.my_trip_view);
        if (ConfigUtil.trips!=null&&ConfigUtil.trips.size()!=0){
            none.setVisibility(View.GONE);
        }else {
            none.setVisibility(View.VISIBLE);
        }
        adapter = new MyTripAdapter(this,ConfigUtil.trips,R.layout.my_trip_item);
        listView.setAdapter(adapter);
        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(getApplicationContext(),CalendarActivity.class);
                startActivity(intent);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
