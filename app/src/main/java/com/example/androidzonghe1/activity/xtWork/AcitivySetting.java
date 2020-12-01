package com.example.androidzonghe1.activity.xtWork;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.androidzonghe1.R;
import com.example.androidzonghe1.activity.yjWork.ServiceAgreementActivity;

public class AcitivySetting extends AppCompatActivity implements View.OnClickListener {

    Toolbar toolbar;
    LinearLayout lGoodReputation;
    LinearLayout lUserAgreement;
    LinearLayout lServiceAgreement;
    LinearLayout lPersonMsg;
    LinearLayout lAboutDingDong;
    Button btnLogOff;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_setting);

        toolbar = findViewById(R.id.toolBar);
        btnLogOff = findViewById(R.id.btn_log_off);
        lGoodReputation = findViewById(R.id.ll_good_reputation);
        lUserAgreement = findViewById(R.id.ll_user_agreement);
        lServiceAgreement = findViewById(R.id.ll_service_agreement);
        lPersonMsg = findViewById(R.id.ll_person_msg);
        lAboutDingDong = findViewById(R.id.ll_about_dingdong);

        btnLogOff.setOnClickListener(this::onClick);
        lGoodReputation.setOnClickListener(this::onClick);
        lUserAgreement.setOnClickListener(this::onClick);
        lServiceAgreement.setOnClickListener(this::onClick);
        lPersonMsg.setOnClickListener(this::onClick);
        lAboutDingDong.setOnClickListener(this::onClick);

        if (getActionBar() != null){
            getActionBar().hide();
        }
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("ActivityQuestion", "toolbar back onClicked");
                finish();
            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_log_off:
//                Intent logOffIntent = new Intent(getApplicationContext(), )
                Log.e("ActivitySetting", "btnLogOff onClicked");
                break;
            case R.id.ll_good_reputation:
                Log.e("ActivitySetting", "lGoodReputation onClicked");
                break;
            case R.id.ll_user_agreement:
                Log.e("ActivitySetting", "lUserAgreement onClicked");
                break;
            case R.id.ll_service_agreement:
                Log.e("ActivitySetting", "lServiceAgreement onClicked");
                Intent serviceAgreementIntent = new Intent(getApplicationContext(), ServiceAgreementActivity.class);
                startActivity(serviceAgreementIntent);
                break;
            case R.id.ll_person_msg:
                Log.e("ActivitySetting", "lPersonMsg onClicked");
                break;
            case R.id.ll_about_dingdong:
                Log.e("ActivitySetting", "lAboutDingDong onClicked");
                break;
        }
    }
}
