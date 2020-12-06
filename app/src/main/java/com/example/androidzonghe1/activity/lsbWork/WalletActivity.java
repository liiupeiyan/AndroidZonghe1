package com.example.androidzonghe1.activity.lsbWork;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidzonghe1.ConfigUtil;
import com.example.androidzonghe1.R;
import com.example.androidzonghe1.activity.yyWork.MoneyDialog;
import com.example.androidzonghe1.activity.yyWork.PaySucc;
import com.example.androidzonghe1.activity.yyWork.UsageRulesActivity;
import com.example.androidzonghe1.activity.yyWork.WalletDetailsActivity;
import com.example.androidzonghe1.adapter.lsbWork.TicketAdapter;
import com.example.androidzonghe1.entity.yyWork.DataMmoney;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


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
    String uMoney;
    String nMoney;
    String str;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if(msg.what==1){
                if(!uMoney.equals("false")){
                    tvMoney.setText(uMoney);
                }
            }else if(msg.what==2){
                if(!str.equals("false")){
                    tvMoney.setText(nMoney);
                }
            }
        }
    };

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
        if(ConfigUtil.isLogin){
            getUserMoney();
        }
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
                //将钱传到数据库
                break;
        }
    }
    //从数据库查询该用户的余额，显示在tvMoney上
    public void getUserMoney(){
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    URL url = new URL(ConfigUtil.xt+"QueryMoneyServlet");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    OutputStream os = connection.getOutputStream();
                    os.write(ConfigUtil.phone.getBytes());
                    InputStream is =connection.getInputStream();
                    byte[] buffer = new byte[512];
                    int len = 0;
                    if((len = is.read(buffer))!=-1){
                        uMoney = new String(buffer,0,len,"UTF-8");
                    }
                    Message message = new Message();
                    message.what=1;
                    handler.sendMessage(message);
                    os.close();
                    is.close();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
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
        nMoney = Double.parseDouble(tvMoney.getText()+"")+mon+"";
        //修改数据库  根据手机号修改余额
        upMoney();
//        tvMoney.setText(nMoney);
    }

    private void upMoney() {
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    //
                    URL url = new URL(ConfigUtil.xt+"UpdateMoneyServlet?money="+nMoney+"&phone="+ConfigUtil.phone);
                    InputStream is =url.openStream();
                    byte[] buffer = new byte[512];
                    int len = 0;
                    if((is.read(buffer))!=-1){
                        str = new String(buffer,0,len,"UTF-8");
                    }
                    Message message = new Message();
                    message.what=2;
                    handler.sendMessage(message);
                    is.close();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}