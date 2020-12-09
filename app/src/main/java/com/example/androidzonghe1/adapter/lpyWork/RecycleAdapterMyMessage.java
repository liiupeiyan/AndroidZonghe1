package com.example.androidzonghe1.adapter.lpyWork;

import android.content.Context;
import android.content.Intent;
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
import com.example.androidzonghe1.ConfigUtil;
import com.example.androidzonghe1.R;
import com.example.androidzonghe1.entity.lpyWork.Messages;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class RecycleAdapterMyMessage extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<Messages> messages;

    private View view;
    private Context mContext;

    private LayoutInflater layoutInflater;
    public RecycleAdapterMyMessage(List<Messages> messages) {
        this.messages = messages;
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
        view = layoutInflater.inflate(R.layout.item_recycleview_message,null);
        return new Myholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((Myholder)holder).title.setText(messages.get(position).getTitle());
        ((Myholder)holder).type.setText(messages.get(position).getType());
        ((Myholder)holder).date.setText(messages.get(position).getDate());
        Glide.with(mContext)
                .load(R.drawable.img1)
                .into(((Myholder)holder).img);
        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Log.e("这里是点击每一行item的响应事件",""+position);
            }
//                Intent intent = new Intent(mContext, .class);
        });
        //点击删除
        ((Myholder) holder).delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                deleteMessage(ConfigUtil.Url + "DeleteMessageServlet?userId=" + ConfigUtil.parent.getId());
                deleteMessage(ConfigUtil.Url + "DeleteMessageServlet?userId=1");
                messages.remove(position);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class Myholder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView type;
        private TextView date;
        private ImageView img;
        private Button delete;
        public Myholder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.item_msg_title);
            type = itemView.findViewById(R.id.item_msg_type);
            date = itemView.findViewById(R.id.item_msg_date);
            img = itemView.findViewById(R.id.item_msg_img);
            delete = itemView.findViewById(R.id.btn_delete);
        }
    }

    //删除消息
    public void deleteMessage(String s){
        Thread thread = new Thread(){
            @Override
            public void run() {
                try {
                    URL url = new URL(s);
                    url.openStream();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
