package com.example.androidzonghe1.adapter.yyWork;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.androidzonghe1.ConfigUtil;
import com.example.androidzonghe1.MainActivity;
import com.example.androidzonghe1.R;
import com.example.androidzonghe1.activity.yyWork.MyCommitActivity;
import com.example.androidzonghe1.entity.yyWork.Commit;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MyCommitAdapter extends BaseAdapter {
    private ViewHolder viewHolder;
    private Context context;
    private int resId;
    private List<Commit> commitList = new ArrayList<>();

    public MyCommitAdapter(Context context, int resId, List<Commit> commitList) {
        this.context = context;
        this.resId = resId;
        this.commitList = commitList;
    }

    @Override
    public int getCount() {
        if(commitList!=null){
            return commitList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if(commitList!=null){
            return commitList.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        viewHolder = null;
        if(convertView==null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(resId, null);
            viewHolder = new ViewHolder();
            convertView.setTag(viewHolder);
            viewHolder.iv1 = convertView.findViewById(R.id.iv_tx);
            viewHolder.iv2 = convertView.findViewById(R.id.iv_ci);
            viewHolder.commit=convertView.findViewById(R.id.edt_ct);
            viewHolder.driver=convertView.findViewById(R.id.tv_de);
            viewHolder.name=convertView.findViewById(R.id.tv_un);
            viewHolder.order=convertView.findViewById(R.id.tv_or);
            viewHolder.time = convertView.findViewById(R.id.tv_tm);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.commit.setText(commitList.get(position).getContent());
        viewHolder.driver.setText(commitList.get(position).getDriver());
        viewHolder.name.setText(commitList.get(position).getUserName());
        viewHolder.order.setText(commitList.get(position).getOrderName());
        viewHolder.time.setText(commitList.get(position).getTime());
        RequestOptions options1 = new RequestOptions().centerCrop();
        Glide.with(context)
                .load(ConfigUtil.xt+"ShowDisscussImageServlet?id="+commitList.get(position).getId())
                .into(viewHolder.iv2);
        RequestOptions options = new RequestOptions().circleCrop();
        Glide.with(context)
                .load(ConfigUtil.xt+"ShowParentImageServlet?id="+ConfigUtil.parent.getId())
                .apply(options)
                .into(viewHolder.iv1);
        return convertView;
    }


    private class ViewHolder{
        ImageView iv1;//头像
        TextView name;
        TextView time;
        TextView driver;
        TextView order;
        EditText commit;
        ImageView iv2;
    }
}
