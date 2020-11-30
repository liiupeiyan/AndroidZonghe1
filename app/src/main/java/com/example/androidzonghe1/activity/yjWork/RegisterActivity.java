package com.example.androidzonghe1.activity.yjWork;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidzonghe1.R;

public class RegisterActivity extends AppCompatActivity {
    private ImageView back;
    private EditText text_name,text_school,text_ship;
    private Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        back = findViewById(R.id.back_register);
        text_name = findViewById(R.id.register_child_name);
        text_ship = findViewById(R.id.register_child_ship);
        text_school = findViewById(R.id.register_child_school);
        btn = findViewById(R.id.btn_register);
        //注册返回按钮
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //注册提交按钮
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
