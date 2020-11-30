package com.example.androidzonghe1.adapter.rjxWork;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.example.androidzonghe1.R;

public class ContactorAdapter extends BaseAdapter {
    private int itemLayoutRes;
    private Context context;

    public ContactorAdapter(int itemLayoutRes, Context context) {
        this.itemLayoutRes = itemLayoutRes;
        this.context = context;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Object getItem(int position) {
        return null;
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

        //删除按钮
        Button delete = convertView.findViewById(R.id.btn_delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return convertView;
    }
}
