package com.example.androidzonghe1.activity.yjWork;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.androidzonghe1.R;

public class MyOrderActivity extends AppCompatActivity {
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        toolbar = findViewById(R.id.toolBar);

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
    }
}