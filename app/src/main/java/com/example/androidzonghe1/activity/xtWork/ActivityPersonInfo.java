package com.example.androidzonghe1.activity.xtWork;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.androidzonghe1.ConfigUtil;
import com.example.androidzonghe1.R;
import com.example.androidzonghe1.activity.lsbWork.SearchActivity;

public class ActivityPersonInfo extends AppCompatActivity {

    private LinearLayout llName;
    private Button btnAddressUsual;
    private Button btnAddressChild;
    private TextView tv_name,tv_phone;
    private final int NAME_REQUEST_CODD = 1;
    private final int USUAL_ADDRESS_REQUESTCODD = 2;
    private final int CHILD_ADDRESS_REQUEST_CODD = 3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gerenxinxi_layout);
        StringBuilder sb = new StringBuilder(ConfigUtil.phone);
        sb.replace(3,8,"****");
        llName = findViewById(R.id.ll_personInfo_name);
        btnAddressChild = findViewById(R.id.et_gerenxinxi_child_adress);
        btnAddressUsual = findViewById(R.id.et_gerenxinxi_address_usually);
        tv_name = findViewById(R.id.yj_person_name);
        tv_phone = findViewById(R.id.tv_gerenxinxi_phone);
        tv_name.setText(ConfigUtil.parent.getName());
        tv_phone.setText(sb);

        llName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityPersonInfo.this, AcitivityChangeName.class);
                startActivityForResult(intent,NAME_REQUEST_CODD);
            }
        });
        btnAddressUsual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityPersonInfo.this, SearchActivity.class);
                startActivityForResult(intent,USUAL_ADDRESS_REQUESTCODD);
            }
        });
        btnAddressChild.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityPersonInfo.this, SearchActivity.class);
                startActivityForResult(intent,CHILD_ADDRESS_REQUEST_CODD);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode){
            case NAME_REQUEST_CODD:
                break;
            case USUAL_ADDRESS_REQUESTCODD:
                break;
            case CHILD_ADDRESS_REQUEST_CODD:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
