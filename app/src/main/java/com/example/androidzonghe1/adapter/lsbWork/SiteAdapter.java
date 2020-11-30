package com.example.androidzonghe1.adapter.lsbWork;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.baidu.mapapi.search.sug.SuggestionResult;
import com.example.androidzonghe1.R;

import java.util.List;

public class SiteAdapter extends RecyclerView.Adapter<SiteAdapter.ViewHolder> implements View.OnClickListener {
    List<SuggestionResult.SuggestionInfo> suggestionInfos;
    RecyclerView recyclerView;
    OnItemClickListener onItemClickListener;

    public SiteAdapter(List<SuggestionResult.SuggestionInfo> suggestionInfos) {
        this.suggestionInfos = suggestionInfos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.site_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvKey.setText(suggestionInfos.get(position).getKey());
        holder.tvAddress.setText(suggestionInfos.get(position).getAddress());
        holder.itemView.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return suggestionInfos == null ? 0 : suggestionInfos.size();
    }

    @Override
    public void onClick(View v) {
        int position = recyclerView.getChildAdapterPosition(v);
        if (onItemClickListener != null)
            onItemClickListener.onItemClick(recyclerView, v, position, suggestionInfos.get(position));
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvKey;
        TextView tvAddress;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAddress = itemView.findViewById(R.id.tv_address);
            tvKey = itemView.findViewById(R.id.tv_key);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        void onItemClick(RecyclerView parent, View view, int position, SuggestionResult.SuggestionInfo data);
    }

    /**
     * 当开始观察此适配器时由RecyclerView调用
     */
    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        Log.e("SiteAdapter", "onAttachedToRecyclerView");
        this.recyclerView = recyclerView;
    }

    /**
     * 当停止观察此适配器时由RecyclerView调用
     */
    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        Log.e("SiteAdapter", "onDetachedFromRecyclerView");
        this.recyclerView = null;
    }
}
/**
 * 即使在他们的签名中也存在显着差异：
 * onDetachedFromRecyclerView(RecyclerView recyclerView) – 当它停止观察此适配器时由RecyclerView调用.
 * 您可能没有注意到的是,在此之前总会调用匹配方法：
 * onAttachedToRecyclerView(RecyclerView recyclerView) – 当它开始观察此适配器时由RecyclerView调用.
 * 当您调用recyclerView.setAdapter(适配器)时,适配器将接收对onAttachedToRecyclerView(recyclerView)的调用.然后调用recyclelerView.setAdapter()后会触发适配器onDetachedFromRecyclerView(recyclerView).
 * 除了某些特殊情况(例如保持观察到的RecyclerViews数等)之外,您通常不需要覆盖此方法.
 * onViewRecycled(VH holder)更简单,在将viewHolder发送到recycleViewPool之前调用它.
 * 您可以将其视为onBindViewHolder(VH holder,int position)的“清理”方法.
 * onViewDetachedFromWindow(VH持有者)始终跟随匹配的onViewAttachedToWindow(VH持有者).当视图持有者变得可见或不可见(附加/分离调用)时,就会调用它.
 * 如果viewHolder已分离但尚未回收,则可以再次接收onViewAttachedToWindow(ViewHolder)调用,而无需使用onBindViewHolder重新绑定数据.
 */