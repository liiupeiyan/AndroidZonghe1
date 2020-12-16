package com.example.androidzonghe1.adapter.lpyWork;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.androidzonghe1.ConfigUtil;
import com.example.androidzonghe1.R;
import com.example.androidzonghe1.activity.yyWork.OrderDetailsActivity;
import com.example.androidzonghe1.entity.lpyWork.Driver;
import com.example.androidzonghe1.entity.lpyWork.RecycleviewTitle;

import java.util.List;

public class RecycleAdapterDriver extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<List> data;
    private List<Driver> drivers;
    private static final int TITLE = 0;
    private static final int CONTENT = 1;

    private View view;
    private Context mContext;
    private LayoutInflater layoutInflater;
    public RecycleAdapterDriver(List<List> data) {
        this.data = data;
        this.drivers = data.get(1);
    }

    @Override
    public int getItemViewType(int position) {
        Object o;
        if (position >= 2){
            o = data.get(1).get(position-1);
        } else {
            o = data.get(position).get(0);
        }

        if(o instanceof RecycleviewTitle){
            return TITLE;
        }
        if(o instanceof Driver){
            return CONTENT;
        }
        return super.getItemViewType(position);
    }

    //这里返回一个ViewHolder
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null){
            mContext = parent.getContext();
        }
        if (layoutInflater == null){
            layoutInflater = LayoutInflater.from(mContext);
        }
        switch (viewType){
            case TITLE:
                view = layoutInflater.inflate(R.layout.item_recycleview_title,parent,false);
                RecycleAdapterDriver.TitleViewHolder titleViewHolder = new RecycleAdapterDriver.TitleViewHolder(view);
                return titleViewHolder;
            case CONTENT:
                view = layoutInflater.inflate(R.layout.item_recycleview_driver,parent,false);
                RecycleAdapterDriver.Myholder myholder = new RecycleAdapterDriver.Myholder(view);
                return myholder;
        }
        return null;

    }
    //为ViewHolder中的布局绑定数据
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof TitleViewHolder){
            List<RecycleviewTitle> titles = data.get(position);
            ((TitleViewHolder) holder).title.setText(titles.get(position).getTitle());
        }
        if(holder instanceof Myholder){
            RequestOptions options = new RequestOptions()
                    .placeholder(R.drawable.loading);
            Glide.with(mContext)
//                    .load(drivers.get(position-1).getImg())
                    .load(ConfigUtil.xt+drivers.get(position-1).getImg())
                    .apply(options)
                    .into(((Myholder)holder).img);
            ((Myholder) holder).name.setText(drivers.get(position-1).getName());
            ((Myholder) holder).age.setText(drivers.get(position-1).getAge()+"岁");
            if (drivers.get(position-1).getStatus().equals("0")){
                ((Myholder) holder).state.setText("空闲");
            }else if(drivers.get(position-1).getStatus().equals("1")){
                ((Myholder) holder).state.setText("忙碌");
            }else {
                ((Myholder) holder).state.setText(drivers.get(position-1).getStatus());
            }
            ((Myholder) holder).car.setText(drivers.get(position-1).getCar());
            ((Myholder) holder).style.setText(drivers.get(position-1).getStyle());
            ((Myholder) holder).experience.setText("驾龄："+drivers.get(position-1).getExperience());
            ((Myholder) holder).phone.setText("电话："+drivers.get(position-1).getPhone());
            //查看订单详情的点击事件
            ((Myholder) holder).driverCall.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+drivers.get(position-1).getPhone()));
                    mContext.startActivity(callIntent);
                }
            });
            holder.itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    Log.e("这里是点击每一行item的响应事件",""+position);
                    ((Myholder) holder).state.setText("忙碌");
                    Intent intent = new Intent(mContext, OrderDetailsActivity.class);
                    mContext.startActivity(intent);
                }
            });
        }

    }
    @Override
    public int getItemCount() {
        int num = 0;
        for (int i = 0; i < data.size() ;i++){
            num += data.get(i).size();
        }
//        Log.e("driver.size",num+"");
        return num;
    }
    public class Myholder extends RecyclerView.ViewHolder {
        private ImageView img;
        private TextView name;
        private TextView age;
        private TextView state;
        private TextView car;
        private TextView style;
        private TextView experience;
        private TextView phone;
        private Button driverCall;
        public Myholder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.iv_driver_img);
            name = itemView.findViewById(R.id.tv_driver_name);
            age = itemView.findViewById(R.id.tv_driver_age);
            state = itemView.findViewById(R.id.tv_driver_state);
            car = itemView.findViewById(R.id.tv_driver_car);
            style = itemView.findViewById(R.id.tv_driver_car_style);
            experience = itemView.findViewById(R.id.tv_driver_experience);
            phone = itemView.findViewById(R.id.tv_driver_phone);
            driverCall = itemView.findViewById(R.id.btn_call_driver);
        }
    }

    public class TitleViewHolder extends RecyclerView.ViewHolder{
        public TextView title;
        public TitleViewHolder(@NonNull View itemView){
            super(itemView);
            title = itemView.findViewById(R.id.item_recycleview_title);
        }
    }
}
