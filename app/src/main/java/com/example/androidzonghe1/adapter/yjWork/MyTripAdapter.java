package com.example.androidzonghe1.adapter.yjWork;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.androidzonghe1.R;
import com.example.androidzonghe1.activity.yjWork.MyTripActivity;
import com.example.androidzonghe1.entity.lpyWork.DayTrip;

import java.util.ArrayList;
import java.util.List;

public class MyTripAdapter extends BaseAdapter {
    private Context mContext;
    private List<DayTrip> trips = new ArrayList<>();
    private int itemLayoutRes;

    public MyTripAdapter(Context mContext, List<DayTrip> trips, int itemLayoutRes) {
        this.mContext = mContext;
        this.trips = trips;
        this.itemLayoutRes = itemLayoutRes;
    }

    @Override
    public int getCount() {
        if (null!=trips){
            return trips.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int i) {
        if (null!=trips){
            return trips.get(i);
        }
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(itemLayoutRes, null);
        final TextView tv_date = view.findViewById(R.id.my_trip_date);
        final TextView tv_state = view.findViewById(R.id.my_trip_state);
        TextView tv_begin = view.findViewById(R.id.my_trip_place_begin);
        TextView tv_end = view.findViewById(R.id.my_trip_place_end);
        Button btn = view.findViewById(R.id.item_my_trip_btn_info);
        tv_date.setText(trips.get(i).getDate());
        tv_state.setText(trips.get(i).getTripState());
        tv_begin.setText(trips.get(i).getPlaceBegin());
        tv_end.setText(trips.get(i).getPlaceEnd());
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return view;
    }
}
