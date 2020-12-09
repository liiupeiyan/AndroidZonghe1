package com.example.androidzonghe1.adapter.yyWork;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.androidzonghe1.R;
import com.example.androidzonghe1.entity.yyWork.Order;

import java.util.List;

public class WalletDetailsAdapter extends BaseAdapter {
    private int resId;
    private Context mContext;
    private List<Order> orders;
    public WalletDetailsAdapter(int resId, Context mContext, List<Order> orders) {
        this.resId = resId;
        this.mContext = mContext;
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
        ViewHolder holder = null;
        if(convertView==null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(resId,null);
            holder = new ViewHolder();
            holder.balance = convertView.findViewById(R.id.balance);
            holder.order = convertView.findViewById(R.id.name);
            holder.spend = convertView.findViewById(R.id.spend);
            holder.time = convertView.findViewById(R.id.time);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.order.setText(orders.get(position).getOrderName());
        holder.spend.setText(orders.get(position).getSpend()+"");
        holder.time.setText(orders.get(position).getTime());
        holder.balance.setText(orders.get(position).getBalance()+"");
        return convertView;
    }
    private class ViewHolder{
        TextView order;
        TextView spend;
        TextView time;
        TextView balance;
    }
}
