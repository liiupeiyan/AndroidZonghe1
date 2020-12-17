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
import com.bumptech.glide.request.RequestOptions;
import com.example.androidzonghe1.ConfigUtil;
import com.example.androidzonghe1.R;
import com.example.androidzonghe1.activity.yyWork.OrderDetailsActivity;
import com.example.androidzonghe1.adapter.lsbWork.KidsAdapter;
import com.example.androidzonghe1.entity.lpyWork.Driver;
import com.example.androidzonghe1.entity.xtWork.Child;

import java.util.List;

public class RvAdapterNoTitleDriver extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<Driver> drivers;

    private View view;
    private Context mContext;
    private OnItemClickListener onItemClickListener;

    public static int myPosition;
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
        Log.e("BindView","true");
        Log.e("flag",drivers.get(position).getFlag()+"");
        ((Myholder) holder).itemD.setBackgroundResource(drivers.get(position).getFlag()>0 ? R.drawable.shape_red_frame : R.drawable.radius_lpy);
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.loading);
        Glide.with(mContext)
                .load(ConfigUtil.xt+drivers.get(position).getImg())
                .apply(options)
                .into(((Myholder)holder).img);
        ((Myholder) holder).name.setText(drivers.get(position).getName());
        ((Myholder) holder).age.setText(drivers.get(position).getAge()+"岁");
        if (drivers.get(position).getStatus().equals("0")){
            ((Myholder) holder).state.setText("空闲");
        }else if(drivers.get(position).getStatus().equals("1")){
            //忙碌的司机不显示
        }
        ((Myholder) holder).car.setText(drivers.get(position).getCar());
        ((Myholder) holder).style.setText(drivers.get(position).getStyle());
        ((Myholder) holder).experience.setText("驾龄："+drivers.get(position).getExperience());
        ((Myholder) holder).phone.setText("电话："+drivers.get(position).getPhone());
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
                Log.e("myPostion--DriverAter",myPosition+"");
//                ((Myholder) holder).itemD.setBackgroundResource(R.drawable.shape_red_frame);
                for (int i = 0; i < drivers.size() ;i++){
                    drivers.get(i).setFlag(0);
                }
                drivers.get(position).setFlag(1);
                notifyDataSetChanged();
            }
        });

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



    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        void onItemClick(RecyclerView parent, View view, int position, Child data);
    }

}
