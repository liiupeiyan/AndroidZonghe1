package com.example.androidzonghe1.adapter.lpyWork;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.androidzonghe1.R;
import com.example.androidzonghe1.entity.lpyWork.Message;

import java.util.List;

public class RecycleAdapterMyMessage extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<Message> messages;

    private View view;
    private Context mContext;

    private LayoutInflater layoutInflater;
    public RecycleAdapterMyMessage(List<Message> messages) {
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
        public Myholder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.item_msg_title);
            type = itemView.findViewById(R.id.item_msg_type);
            date = itemView.findViewById(R.id.item_msg_date);
            img = itemView.findViewById(R.id.item_msg_img);
        }
    }


}
