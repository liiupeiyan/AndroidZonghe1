package com.example.androidzonghe1.activity.lsbWork;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidzonghe1.R;
import com.example.androidzonghe1.adapter.lsbWork.ContactorAdapter;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.zyyoona7.wheel.WheelView;

import java.util.ArrayList;
import java.util.List;

public class ContactorActivity extends AppCompatActivity {

    ImageView imgBack;
    RecyclerView recyclerView;
    Button btnInsert;
    ContactorAdapter contactorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactor);

        recyclerView = findViewById(R.id.lv_contactor);
        imgBack = findViewById(R.id.img_back);
        btnInsert = findViewById(R.id.btn_insert);

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("ContactorActivity", "btnInsert onClick");
                contactorAdapter.insertData();
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("COntactorActivity", "imgBack onClick");
            }
        });

        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);

        contactorAdapter = new ContactorAdapter(getApplicationContext());
        contactorAdapter.setOnItemClickListener(new ContactorAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position, String data) {
                Log.e("ContactorActivity", "item onClick position:" + position);
                Button btnRelation = (Button) view;
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(ContactorActivity.this);
                View v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.relation_dialog, null);
                Button btnCancel = v.findViewById(R.id.btn_cancel);
                Button btnConfirm = v.findViewById(R.id.btn_confirm);
                WheelView<String> wheelView = v.findViewById(R.id.wheel_view);
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
//                        ((Button) view).setText(wheelView.getSelectedItemData());
                        btnRelation.setText(wheelView.getSelectedItemData());
                        bottomSheetDialog.dismiss();
                    }
                });
                bottomSheetDialog.setContentView(v);
                bottomSheetDialog.setCancelable(true);
                bottomSheetDialog.setCanceledOnTouchOutside(true);
                bottomSheetDialog.show();
            }
        });
        recyclerView.setAdapter(contactorAdapter);
    }
}