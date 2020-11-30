package com.example.androidzonghe1.adapter.lsbWork;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidzonghe1.R;

import java.util.ArrayList;
import java.util.List;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.ViewHolder> {

    Context context;
    List<String> data;

    public TicketAdapter(Context context){
        this.context = context;
        data = new ArrayList<String>();
        data.add("one");
    }

    public TicketAdapter(List<String> data, Context context){
        this.data = data;
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.ticket_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgTicket;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgTicket = itemView.findViewById(R.id.img_ticket);
        }
    }
}
