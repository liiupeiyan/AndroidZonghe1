package com.example.androidzonghe1.activity.xtWork;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.androidzonghe1.R;

public class AcitivySetting extends AppCompatActivity implements View.OnClickListener {

    ImageView imgBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_setting);

        imgBack = findViewById(R.id.img_back);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_back:
                Log.e("ActivitySetting", "imgBack onClicked");
                finish();
                break;
        }
    }
}
