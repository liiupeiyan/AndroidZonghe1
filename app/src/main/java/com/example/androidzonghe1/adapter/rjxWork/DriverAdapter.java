package com.example.androidzonghe1.adapter.rjxWork;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.androidzonghe1.R;
import com.example.androidzonghe1.entity.lpyWork.Driver;

import java.util.List;

public class DriverAdapter extends BaseAdapter{
    public List<Driver> drivers;
    public int layousRes;
    public Context context;

    public DriverAdapter(List<Driver> drivers, int layousRes, Context context) {
        this.drivers = drivers;
        this.layousRes = layousRes;
        this.context = context;
    }

    @Override
    public int getCount() {
        if (null != drivers){
            return drivers.size();
        }
        return 8;
    }

    @Override
    public Object getItem(int position) {
        if (null != drivers){
            return drivers.get(position);
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
        //加载item的布局文件
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(layousRes, null);

            //创建ViewHolder对象
            viewHolder = new ViewHolder();
            //获取到item中每个视图对象的引用
            viewHolder.img = convertView.findViewById(R.id.iv_driver_img);
            viewHolder.name = convertView.findViewById(R.id.tv_driver_name);
            viewHolder.age = convertView.findViewById(R.id.tv_driver_age);
            viewHolder.state = convertView.findViewById(R.id.tv_driver_state);
            viewHolder.car = convertView.findViewById(R.id.tv_driver_car);
            viewHolder.style = convertView.findViewById(R.id.tv_driver_car_style);
            viewHolder.experience = convertView.findViewById(R.id.tv_driver_experience);
            viewHolder.phone = convertView.findViewById(R.id.tv_driver_phone);
            viewHolder.driverCall = convertView.findViewById(R.id.btn_call_driver);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }
    private class ViewHolder {
        private ImageView img;
        private TextView name;
        private TextView age;
        private TextView state;
        private TextView car;
        private TextView style;
        private TextView experience;
        private TextView phone;
        private Button driverCall;
        
    }
}
