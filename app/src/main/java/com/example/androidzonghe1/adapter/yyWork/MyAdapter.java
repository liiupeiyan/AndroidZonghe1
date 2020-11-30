package com.example.androidzonghe1.adapter.yyWork;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.androidzonghe1.R;
import com.example.androidzonghe1.entity.yyWork.Read;

import java.util.List;

public class MyAdapter extends BaseAdapter {
    private Context mContext;
    private int resId;
    private List<Read> readList;

    public MyAdapter(Context mContext, int resId, List<Read> readList) {
        this.mContext = mContext;
        this.resId = resId;
        this.readList = readList;
    }

    @Override
    public int getCount() {
        if(readList!=null){
            return readList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if(readList!=null){
            return readList.get(position);
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
        if(convertView==null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(resId,null);
            viewHolder = new ViewHolder();
            viewHolder.imageView = convertView.findViewById(R.id.img);
            viewHolder.time = convertView.findViewById(R.id.tv_time);
            viewHolder.title = convertView.findViewById(R.id.tv_title);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.imageView.setImageResource(readList.get(position).getResId());
        viewHolder.title.setText(readList.get(position).getTitle());
        viewHolder.time.setText(readList.get(position).getTime());
        return convertView;
    }
    private class ViewHolder{
        ImageView imageView;
        TextView title;
        TextView time;
    }
}
