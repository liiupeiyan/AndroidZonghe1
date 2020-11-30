package com.example.androidzonghe1.adapter.lpyWork;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.androidzonghe1.entity.lpyWork.DataBean;
import com.youth.banner.adapter.BannerAdapter;

import java.util.List;

public class ImageAdapter extends BannerAdapter<DataBean, ImageAdapter.BannerViewHolder> {
    private Context context;
    public ImageAdapter (Context context, List<DataBean> mDatas) {
        //设置数据，也可以调用banner提供的方法,或者自己在adapter中实现
        super(mDatas);
        this.context = context;
    }

    //创建ViewHolder，可以用viewType这个字段来区分不同的ViewHolder
    @Override
    public BannerViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        ImageView imageView = new ImageView(parent.getContext());
        //注意，必须设置为match_parent，这个是viewpager2强制要求的
        imageView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//        VideoView videoView = new VideoView(parent.getContext());
//        videoView.setLayoutParams(new ViewGroup.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.MATCH_PARENT));

        return new BannerViewHolder(imageView);
    }

    @Override
    public void onBindView(BannerViewHolder holder, DataBean data, int position, int size) {
        if(data.imageRes == null){
//            MediaController controller = new MediaController(context);
//            holder.videoView.setMediaController(controller);
//            controller.setMediaPlayer(holder.videoView);
//            holder.videoView.setVideoPath(data.imageUrl);
        }else {
            holder.imageView.setImageResource(data.imageRes);
        }

    }

    class BannerViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        VideoView videoView;
        public BannerViewHolder(@NonNull ImageView imageView1) {
            super(imageView1);
            this.imageView = imageView1;
        }
    }
}
