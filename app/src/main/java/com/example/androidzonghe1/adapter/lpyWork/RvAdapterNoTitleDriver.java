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
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.androidzonghe1.R;
import com.example.androidzonghe1.activity.yyWork.OrderDetailsActivity;
import com.example.androidzonghe1.entity.lpyWork.Driver;

import java.util.List;

public class RvAdapterNoTitleDriver extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<Driver> drivers;

    private View view;
    private Context mContext;

    private int myPosition;
    private LayoutInflater layoutInflater;
    public RvAdapterNoTitleDriver(List<Driver> data) {
        this.drivers = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mContext == null){
            mContext = parent.getContext();
        }
        if (layoutInflater == null){
            layoutInflater = LayoutInflater.from(mContext);
        }
        view = layoutInflater.inflate(R.layout.item_recycleview_driver,null);
        return new RvAdapterNoTitleDriver.Myholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Glide.with(mContext)
                    .load(drivers.get(position).getImg())
                .load(R.drawable.driver_img)
                .into(((Myholder)holder).img);
        ((Myholder) holder).name.setText(drivers.get(position).getName());
        ((Myholder) holder).age.setText(drivers.get(position).getAge()+"");
        ((Myholder) holder).state.setText(drivers.get(position).getStatus());
        ((Myholder) holder).car.setText(drivers.get(position).getCar());
        ((Myholder) holder).style.setText(drivers.get(position).getStyle());
        ((Myholder) holder).experience.setText(drivers.get(position).getExperience());
        ((Myholder) holder).phone.setText(drivers.get(position).getPhone());
        //查看订单详情的点击事件
        ((Myholder) holder).driverCall.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+drivers.get(position).getPhone()));
                mContext.startActivity(callIntent);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Log.e("这里是点击每一行item的响应事件",""+position);
                ((Myholder) holder).state.setText("忙碌");
//                Intent intent = new Intent(mContext, OrderDetailsActivity.class);
//                mContext.startActivity(intent);
                myPosition = position;
                ((Myholder) holder).itemD.setBackgroundResource(R.drawable.shape_red_frame);
            }
        });
        holder.itemView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b){
                    Log.e("没有焦点","123");
                    ((Myholder) holder).itemD.setBackgroundResource(R.color.white);
                }
            }
        });


    }

    public int getMyPosition() {
        return myPosition;
    }

    @Override
    public int getItemCount() {
        return drivers.size();
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
        private RelativeLayout itemD;
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
            itemD = itemView.findViewById(R.id.rl_driver_item);
        }
    }

}
