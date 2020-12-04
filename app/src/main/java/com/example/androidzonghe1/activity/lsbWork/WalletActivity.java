package com.example.androidzonghe1.activity.lsbWork;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidzonghe1.R;
import com.example.androidzonghe1.activity.yyWork.MoneyDialog;
import com.example.androidzonghe1.activity.yyWork.PaySucc;
import com.example.androidzonghe1.activity.yyWork.UsageRulesActivity;
import com.example.androidzonghe1.activity.yyWork.WalletDetailsActivity;
import com.example.androidzonghe1.adapter.lsbWork.TicketAdapter;
import com.example.androidzonghe1.entity.yyWork.DataMmoney;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class WalletActivity extends AppCompatActivity implements View.OnClickListener {
    Toolbar toolbar;
    ImageView imgBack;
    Button btnDetail;
    Button btnMoney;
    Button btnUseRole;
    RecyclerView recyclerView;
    TicketAdapter ticketAdapter;
    TextView tvMoney;
    MoneyDialog dialog;
    double mon;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
        EventBus.getDefault().register(this);
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

    @SuppressLint("ResourceType")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_back:
                Log.e("WalletActivity", "imgBack onClick");
                finish();
                break;
            case R.id.btn_detail:
                Log.e("WalletActivity", "btnDetail onClick");
                Intent detailIntent = new Intent(getApplicationContext(), WalletDetailsActivity.class);
                startActivity(detailIntent);
                break;
            case R.id.btn_use_role:
                Log.e("WalletActivity", "btnUseRole onClick");
                Intent useRoleIntent = new Intent(getApplicationContext(), UsageRulesActivity.class);
                startActivity(useRoleIntent);
                break;
            case R.id.btn_withdraw_money:
                Log.e("WalletActivity", "btnWithdrawMoney onClick");
                //充值钱
                showMoneyDialog();
//                //获取充值钱数
//                Log.e("qiannnn", DataMmoney.getMoney());
//                mon = Double.parseDouble(DataMmoney.getMoney());
////                mon = Double.parseDouble(dialog.getText(R.id.edt_moy)+"");
//                tvMoney.setText(Double.parseDouble(tvMoney.getText()+"")+mon+"");
                //将钱传到数据库
                break;
        }
    }

    private void showMoneyDialog() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        dialog = new MoneyDialog();
        if(!dialog.isAdded()){
            transaction.add(dialog,"dialog_tag");
        }
//        Log.e("qiannnn",dialog.getMyMoney()+"");
        transaction.show(dialog);
        transaction.commit();
    }
    @Subscribe
    public void updateMoney(String money){
        mon = Double.parseDouble(money);
//                mon = Double.parseDouble(dialog.getText(R.id.edt_moy)+"");
        tvMoney.setText(Double.parseDouble(tvMoney.getText()+"")+mon+"");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}