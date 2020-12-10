package com.example.androidzonghe1.activity.yyWork;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.androidzonghe1.R;

public class SuccessCommitActivity extends AppCompatActivity {
    private Button suc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_commit);
        suc = findViewById(R.id.btn_suc);
        suc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                setResult(20,intent);
                finish();
            }
        });
    }
}