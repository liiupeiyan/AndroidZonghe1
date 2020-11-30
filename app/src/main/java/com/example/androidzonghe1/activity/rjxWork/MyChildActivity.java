package com.example.androidzonghe1.activity.rjxWork;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidzonghe1.R;
import com.example.androidzonghe1.adapter.rjxWork.MyChildAdapter;

public class MyChildActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mychild);

        ListView listView = findViewById(R.id.my_child);
        MyChildAdapter adapter = new MyChildAdapter(R.layout.child_list_item,this);
        listView.setAdapter(adapter);
    }
}