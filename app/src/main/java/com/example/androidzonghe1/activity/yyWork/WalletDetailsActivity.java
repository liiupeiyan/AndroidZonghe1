package com.example.androidzonghe1.activity.yyWork;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.androidzonghe1.R;
import com.example.androidzonghe1.adapter.yyWork.WalletDetailsAdapter;
import com.example.androidzonghe1.entity.yyWork.Order;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WalletDetailsActivity extends AppCompatActivity {
    private ListView listView;
    private List<Order> wallets = new ArrayList<>();
    private WalletDetailsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_details);
        listView = findViewById(R.id.wallet_list);
        Order order = new Order("河北天客隆",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis())),299.08, 10.40);
        Order order1 = new Order("手机话费",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis())),269.08, 10.40);
        Order order2 = new Order("美团订单",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis())),239.08, 10.40);
        Order order3 = new Order("扫描二维码",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis())),199.08, 10.40);
        wallets.add(order);
        wallets.add(order1);
        wallets.add(order2);
        wallets.add(order3);
        adapter = new WalletDetailsAdapter(R.layout.wallet_item_list,this,wallets);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(WalletDetailsActivity.this,WalletActivity.class);
                intent.putExtra("name",wallets.get(position).getOrderName());
                intent.putExtra("spend",wallets.get(position).getSpend());
                intent.putExtra("time",wallets.get(position).getTime());
                intent.putExtra("balance",wallets.get(position).getBalance());
                startActivity(intent);
            }
        });
    }
}