package com.example.androidzonghe1.activity.rjxWork;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.androidzonghe1.R;
import com.example.androidzonghe1.adapter.rjxWork.ContactorAdapter;

public class ContactorActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获取ActionBar
        ActionBar actionBar = getSupportActionBar();
        //隐藏ActionBar
        actionBar.hide();
        setContentView(R.layout.activity_contactor);

        ListView listView = findViewById(R.id.lv_contactor);
        ContactorAdapter adapter = new ContactorAdapter(R.layout.contactor_list_item,this);
        listView.setAdapter(adapter);

    }
}