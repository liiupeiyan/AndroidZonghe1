package com.example.androidzonghe1.activity.yyWork;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.androidzonghe1.R;


public class PreventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prevent);
    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.pic6:
                Intent intent = new Intent(this,DisActivity.class);
                startActivity(intent);
                break;
            case R.id.pic5:
                Intent intent1 = new Intent(this,DisinfectActivity.class);
                startActivity(intent1);
                break;
        }
    }
}
