package com.example.androidzonghe1.adapter.lpyWork;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidzonghe1.R;
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
            ((Myholder) holder).name.setText(drivers.get(position-1).getName());
            ((Myholder) holder).phone.setText(drivers.get(position-1).getPhone()+"");
            //查看订单详情的点击事件
            ((Myholder) holder).driverInfo.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    Log.e("RecycleAdapter","查看订单详情");
                }
            });
            holder.itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    Log.e("这里是点击每一行item的响应事件",""+position);
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
        private TextView name;
        private TextView phone;
        private Button driverInfo;
        public Myholder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.item_driver_tv_name);
            phone = itemView.findViewById(R.id.item_driver_tv_phone);
            driverInfo = itemView.findViewById(R.id.item_driver_btn_driver_info);
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
