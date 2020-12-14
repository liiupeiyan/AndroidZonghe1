package com.example.androidzonghe1.adapter.yyWork;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextClock;
import android.widget.TextView;

import com.example.androidzonghe1.R;
import com.example.androidzonghe1.activity.yyWork.CommitActivity;
import com.example.androidzonghe1.entity.yyWork.Order;

import java.util.ArrayList;
import java.util.List;

public class MyOrderAdapter extends BaseAdapter {
    private String driver;
    private Context context;
    private int resId;
    private List<Order> orders = new ArrayList<>();

    public MyOrderAdapter(Context context, int resId, List<Order> orders) {
        this.context = context;
        this.resId = resId;
        this.orders = orders;
    }

    @Override
    public int getCount() {
        if(orders!=null){
            return orders.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if(orders!=null){
            return orders.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView==null){
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(resId,null);
            viewHolder = new ViewHolder();
            viewHolder.name = convertView.findViewById(R.id.tv_order_name);
            viewHolder.price = convertView.findViewById(R.id.tv_order_price);
            viewHolder.time = convertView.findViewById(R.id.tv_order_time);
            viewHolder.commit = convertView.findViewById(R.id.btn_commit);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.name.setText(orders.get(position).getOrderName());
        viewHolder.time.setText(orders.get(position).getTime());
        viewHolder.price.setText(orders.get(position).getSpend());
        driver = orders.get(position).getdName();
        viewHolder.commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CommitActivity.class);
                intent.putExtra("driver",orders.get(position).getdName());
                intent.putExtra("id",orders.get(position).getId());
                context.startActivity(intent);
            }
        });
        return convertView;
    }
    private class ViewHolder{
        TextView name;
        TextView time;
        TextView price;
        Button commit;
    }
}
