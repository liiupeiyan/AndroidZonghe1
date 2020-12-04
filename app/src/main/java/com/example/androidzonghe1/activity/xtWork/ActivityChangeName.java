package com.example.androidzonghe1.activity.xtWork;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.androidzonghe1.R;
import com.example.androidzonghe1.others.lsbWork.DelEditText;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.zyyoona7.wheel.WheelView;

import java.util.ArrayList;
import java.util.List;

public class ActivityChangeName extends AppCompatActivity implements View.OnClickListener {

    Toolbar toolbar;
    ImageView imgBack;
    Button btnRelation;
    Button btnSave;
    DelEditText etName;

    final int RESPONSE_CODE = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_name);

        if (getActionBar() != null){
            getActionBar().hide();
        }
        toolbar = findViewById(R.id.toolBar);
        btnRelation = findViewById(R.id.btn_relation);
        btnSave = findViewById(R.id.btn_save);
        etName = findViewById(R.id.et_name);
        imgBack = findViewById(R.id.img_back);

        setSupportActionBar(toolbar);

        imgBack.setOnClickListener(this::onClick);
        btnSave.setOnClickListener(this::onClick);
        btnRelation.setOnClickListener(this::onClick);

        etName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etName.getText().toString().toString().trim().length() < 0){
                    btnSave.setBackground(getResources().getDrawable(R.drawable.anniu_white));
                } else {
                    btnSave.setBackground(getResources().getDrawable(R.drawable.anniu));
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_back:
                Log.e("ActivityChangeName", "img_back onClicked");
                finish();
                break;
            case R.id.btn_relation:
                Log.e("ActivityChangeName", "btn_relation onClicked");
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(ActivityChangeName.this);
                View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.relation_dialog, null);
                Button btnCancel = view.findViewById(R.id.btn_cancel);
                Button btnConfirm = view.findViewById(R.id.btn_confirm);
                WheelView<String> wheelView = view.findViewById(R.id.wheel_view);
                List<String> relations = new ArrayList<String>();
                relations.add("爸爸");
                relations.add("妈妈");
                relations.add("爷爷");
                relations.add("奶奶");
                relations.add("外公");
                relations.add("外婆");
                relations.add("叔叔");
                relations.add("阿姨");
                wheelView.setData(relations);
                wheelView.setTextSize(100);
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bottomSheetDialog.dismiss();
                    }
                });
                btnConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        btnRelation.setText(wheelView.getSelectedItemData());
                        bottomSheetDialog.dismiss();
                    }
                });
                bottomSheetDialog.setContentView(view);
                bottomSheetDialog.setCancelable(true);
                bottomSheetDialog.setCanceledOnTouchOutside(true);
                bottomSheetDialog.show();
                break;
            case R.id.btn_save://保存数据
                Log.e("ActivityChangeName", "btn_save onClicked");
                if (etName.getText().toString().trim().length() > 0){
                    Intent response = new Intent();
                    response.putExtra("name", etName.getText().toString().trim());
                    response.putExtra("relation", btnRelation.getText().toString().trim());
                    setResult(RESPONSE_CODE, response);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "请输入孩子的昵称", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
