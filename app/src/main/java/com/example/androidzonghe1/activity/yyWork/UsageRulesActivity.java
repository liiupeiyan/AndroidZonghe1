package com.example.androidzonghe1.activity.yyWork;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import com.example.androidzonghe1.R;

public class UsageRulesActivity extends AppCompatActivity {
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usage_rules);
        toolbar = findViewById(R.id.toolBar);
    }
}