package com.example.androidzonghe1.adapter.rjxWork;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


public class MyChildAdapter extends BaseAdapter {
    private int itemLayoutRes;
    private Context context;

    public MyChildAdapter(int itemLayoutRes, Context context) {
        this.itemLayoutRes = itemLayoutRes;
        this.context = context;
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //加载item的布局文件,借助布局填充器
        if(convertView==null){
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(itemLayoutRes,null);//将布局文件加载到内存（root要传Adapter类型）
        }




        return convertView;
    }


}
