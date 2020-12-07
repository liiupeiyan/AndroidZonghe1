package com.example.androidzonghe1.activity.xtWork;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.baidu.mapapi.search.sug.SuggestionResult;
import com.example.androidzonghe1.R;
import com.example.androidzonghe1.activity.lsbWork.SearchActivity;
import com.makeramen.roundedimageview.RoundedImageView;

import de.hdodenhof.circleimageview.CircleImageView;

public class ActivityPersonInfo extends AppCompatActivity {

    private LinearLayout llName;
    private RoundedImageView imgRelation;
    private TextView tvTel;
    private TextView tvName;
    private Button btnAddressUsual;
    private Button btnAddressChild;
    private ImageView imgBack;
    private final int NAME_REQUEST_CODD = 1;
    private final int USUAL_ADDRESS_REQUESTCODD = 2;
    private final int CHILD_ADDRESS_REQUEST_CODD = 3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.person_info_layout);

        llName = findViewById(R.id.ll_personInfo_name);
        imgBack =findViewById(R.id.img_back);
        tvName =findViewById(R.id.tv_name);
        btnAddressChild = findViewById(R.id.et_gerenxinxi_child_adress);
        btnAddressUsual = findViewById(R.id.et_gerenxinxi_address_usually);
        imgRelation = findViewById(R.id.img_relation);
        tvTel = findViewById(R.id.tv_tel);

        llName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityPersonInfo.this, ActivityChangeName.class);
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
        imgBack.setOnClickListener(new View.OnClickListener() { //返回
            @Override
            public void onClick(View v) {
                Log.e("ActivityPersonInfo", "imgBack onClicked");
                Intent response = new Intent();
                response.putExtra("name", tvName.getText().toString().trim());
                response.putExtra("addressUsual", btnAddressUsual.getText().toString().trim());
                response.putExtra("addressChild", btnAddressChild.getText().toString().trim());
                setResult(1, response);
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode){
            case NAME_REQUEST_CODD:  //ActivityChangeName
                if (resultCode == 1){
                    String name = data.getStringExtra("name");
                    String relation = data.getStringExtra("relation");
                    //修改头像

                    tvName.setText(Html.fromHtml("<u>" + name + relation + "</u>"));
                } else { //没有做出修改

                }
                break;
            case USUAL_ADDRESS_REQUESTCODD:
                if (resultCode == 0){
                    SuggestionResult.SuggestionInfo info = data.getExtras().getParcelable("suggestionInfo");
                    Log.e("suggestionInfo",info.toString());
                    String schoolName =  info.key;
                    btnAddressUsual.setText(Html.fromHtml("<u>"+ schoolName + "</u>"));
                }
                break;
            case CHILD_ADDRESS_REQUEST_CODD:
                if (resultCode == 0){
                    SuggestionResult.SuggestionInfo info = data.getExtras().getParcelable("suggestionInfo");
                    Log.e("suggestionInfo",info.toString());
                    String schoolName =  info.key;
                    btnAddressChild.setText(Html.fromHtml("<u>"+ schoolName + "</u>"));
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
