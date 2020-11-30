package com.example.androidzonghe1.activity.lsbWork;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidzonghe1.R;
import com.example.androidzonghe1.adapter.lsbWork.TicketAdapter;

public class WalletActivity extends AppCompatActivity implements View.OnClickListener {

    Toolbar toolbar;
    ImageView imgBack;
    Button btnDetail;
    Button btnMoney;
    Button btnUseRole;
    RecyclerView recyclerView;
    TicketAdapter ticketAdapter;
    TextView tvMoney;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
        if (getActionBar() != null){
            getActionBar().hide();
        }
        toolbar = findViewById(R.id.toolbar);
        imgBack = findViewById(R.id.img_back);
        btnDetail = findViewById(R.id.btn_detail);
        btnMoney = findViewById(R.id.btn_withdraw_money);
        btnUseRole = findViewById(R.id.btn_use_role);
        recyclerView = findViewById(R.id.list_view);
        tvMoney = findViewById(R.id.tv_money);

        imgBack.setOnClickListener(this::onClick);
        btnDetail.setOnClickListener(this::onClick);
        btnUseRole.setOnClickListener(this::onClick);
        btnMoney.setOnClickListener(this::onClick);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        ticketAdapter = new TicketAdapter(getApplicationContext());

        recyclerView.setAdapter(ticketAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_back:
                Log.e("WalletActivity", "imgBack onClick");
                finish();
                break;
            case R.id.btn_detail:
                Log.e("WalletActivity", "btnDetail onClick");
                break;
            case R.id.btn_use_role:
                Log.e("WalletActivity", "btnUseRole onClick");

                break;
            case R.id.btn_withdraw_money:
                Log.e("WalletActivity", "btnWithdrawMoney onClick");
                break;
        }
    }
}