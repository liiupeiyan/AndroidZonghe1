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

import com.example.androidzonghe1.R;
import com.example.androidzonghe1.adapter.lsbWork.KidsAdapter;

public class KidsActivity extends AppCompatActivity {

    ImageView imgBack;
    RecyclerView recyclerView;
    Button btnInsert;
    KidsAdapter kidsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kids);

        recyclerView = findViewById(R.id.list_view);
        imgBack = findViewById(R.id.img_back);
        btnInsert = findViewById(R.id.btn_insert);

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
        kidsAdapter = new KidsAdapter(getApplicationContext());
        kidsAdapter.setOnItemClickListener(new KidsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position, String data) {
                Log.e("ContactorActivity", "item onClick position:" + position);
                Intent request = new Intent();
                startActivityForResult(request, 1);
            }
        });
        recyclerView.setAdapter(kidsAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}