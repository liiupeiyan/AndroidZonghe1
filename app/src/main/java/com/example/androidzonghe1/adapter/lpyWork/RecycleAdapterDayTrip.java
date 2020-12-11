package com.example.androidzonghe1.adapter.lpyWork;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidzonghe1.R;
import com.example.androidzonghe1.activity.rjxWork.TravelDetailActivity;
import com.example.androidzonghe1.entity.lpyWork.DayTrip;
import com.example.androidzonghe1.entity.lpyWork.RecycleviewTitle;
import com.example.androidzonghe1.entity.yyWork.DriverOrder;

import java.util.List;

public class RecycleAdapterDayTrip extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<List> data;
    private List<DriverOrder> trips;
    private static final int TITLE = 0;
    private static final int CONTENT = 1;

    private View view;
    private Context mContext;
    private LayoutInflater layoutInflater;
    public RecycleAdapterDayTrip(List<List> data) {
        this.data = data;
        this.trips = data.get(1);
    }

    @Override
    public int getItemViewType(int position) {
        Object o ;
        if (position >= 2){
            o = data.get(1).get(position-1);
        } else {
            o = data.get(position).get(0);
        }

        if(o instanceof RecycleviewTitle){
            return TITLE;
        }
        if(o instanceof DayTrip){
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
                RecycleAdapterDayTrip.TitleViewHolder titleViewHolder = new RecycleAdapterDayTrip.TitleViewHolder(view);
                return titleViewHolder;
            case CONTENT:
                if (trips.size() == 0){
                    view = layoutInflater.inflate(R.layout.fragment_nodata_day_trip,parent,false);
                } else {
                    view = layoutInflater.inflate(R.layout.item_recycleview_day_trip,parent,false);
                    RecycleAdapterDayTrip.Myholder myholder = new RecycleAdapterDayTrip.Myholder(view);
                    return myholder;
                }

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
        if(holder instanceof Myholder) {
            String goOrCome = trips.get(position-1).getAddress();
            String date = trips.get(position-1).getDate();
            String time = trips.get(position-1).getTime();
            String state = trips.get(position-1).getState();
            String endTime = trips.get(position-1).getEndTime();
            String from = trips.get(position-1).getFrom();
            String to = trips.get(position-1).getTo();
            ((Myholder) holder).goOrCome.setText(goOrCome);
            ((Myholder) holder).date.setText(date);
            ((Myholder) holder).timeBegin.setText(time);
            ((Myholder) holder).tripState.setText(state);
            ((Myholder) holder).timeBeginXia.setText(time);
            ((Myholder) holder).timeEndXia.setText(endTime);
            ((Myholder) holder).placeBegin.setText(from);
            ((Myholder) holder).placeEnd.setText(to);
                //查看订单详情的点击事件
                ((Myholder) holder).tripInfo.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        Log.e("RecycleAdapter","查看订单详情");
                        //跳转界面
                        Intent intent = new Intent(mContext, TravelDetailActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("goOrCome",goOrCome);
                        bundle.putString("date",date);
                        bundle.putString("time",time);
                        bundle.putString("state",state);
                        bundle.putString("endTime",endTime);
                        bundle.putString("from",from);
                        bundle.putString("to",to);
                        bundle.putString("id",trips.get(position-1).getId()+"");
                        intent.putExtra("bundle",bundle);
                        mContext.startActivity(intent);
                    }
                });
                ((Myholder) holder).itemView.setOnClickListener(new View.OnClickListener(){
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
        private TextView goOrCome;
        private TextView date;
        private TextView timeBegin;
        private TextView timeEndXia;
        private TextView timeBeginXia;
        private TextView placeBegin;
        private TextView placeEnd;
        private TextView tripState;
        private Button tripInfo;
        public Myholder(View itemView) {
            super(itemView);
            goOrCome = itemView.findViewById(R.id.day_trip_go_or_come);
            date = itemView.findViewById(R.id.day_trip_date);
            timeBegin = itemView.findViewById(R.id.day_trip_time_begin);
            timeEndXia = itemView.findViewById(R.id.day_trip_time_end_xia);
            timeBeginXia = itemView.findViewById(R.id.day_trip_time_begin_xia);
            placeBegin = itemView.findViewById(R.id.day_trip_place_begin);
            placeEnd = itemView.findViewById(R.id.day_trip_place_end);
            tripState = itemView.findViewById(R.id.day_trip_state);
            tripInfo = itemView.findViewById(R.id.item_day_trip_btn_info);

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
