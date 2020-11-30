package com.example.androidzonghe1.adapter.yyWork;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidzonghe1.R;

public class RecylerAdapter extends RecyclerView.Adapter<RecylerAdapter.MyHolder> {
    private Context mContext;
    private String[] strs = new String[100];
    public RecylerAdapter(Context context) {
        this.mContext = context;
        //为测试给Recycler添加数据
        for (int i = 0; i < 10; i++) {
            strs[i] = i + "";
        }
    }
    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recycler_item, null);
        MyHolder myholder = new MyHolder(view);
        return myholder;
    }


    @Override
    public void onBindViewHolder(@NonNull RecylerAdapter.MyHolder holder, final int position) {
        holder.textView.setText(strs[position]);
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("这里是点击每一行item的响应事件",position+"");
            }
        });
    }

    @Override
    public int getItemCount() {
        return strs.length;
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        public MyHolder(View itemView) {
            super(itemView);
            //ButterKnife也可以用于ViewHoder中
            textView = itemView.findViewById(R.id.tv_text);
        }
    }
}
