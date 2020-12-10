package com.example.androidzonghe1.adapter.xtWork;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import com.example.androidzonghe1.R;
import com.example.androidzonghe1.activity.lsbWork.WalletActivity;
import com.example.androidzonghe1.activity.xtWork.AcitivySetting;
import com.example.androidzonghe1.activity.xtWork.ActivityQuestion;
import com.example.androidzonghe1.activity.yjWork.LoginActivity;
import com.example.androidzonghe1.activity.yjWork.MyOrderActivity;
import com.example.androidzonghe1.activity.yjWork.MyTripActivity;
import com.example.androidzonghe1.activity.yyWork.MyCommitActivity;
import com.example.androidzonghe1.entity.xtWork.RvFragmentMy;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.lanren.easydialog.AnimatorHelper;
import com.lanren.easydialog.DialogViewHolder;
import com.lanren.easydialog.EasyDialog;

import java.util.List;

public class RecycleAdapterFragmentMy extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<RvFragmentMy> mys;
    private View view;
    private Context mContext;
    private LayoutInflater layoutInflater;
    public RecycleAdapterFragmentMy(List<RvFragmentMy> mys) {
        this.mys= mys;
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
        view = layoutInflater.inflate(R.layout.item_fragment_my_rv,null);
        return new RecycleAdapterFragmentMy.Myholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((Myholder)holder).name.setText(mys.get(position).getItemName());
        Glide.with(mContext)
                .load(mys.get(position).getIconRes())
                .into(((Myholder)holder).imgIcon);
        Glide.with(mContext)
                .load(mys.get(position).getImgRes())
                .into(((Myholder)holder).imgRes);
        switch(position){//每一个item的点击事件用intent跳转
            case 0:
                holder.itemView.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        Log.e("RecyclerAdapterFragment",""+position);
                        Intent intent = new Intent(mContext, MyTripActivity.class);
                        mContext.startActivity(intent);
                    }
                });
                break;
            case 1:
                holder.itemView.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        Log.e("RecyclerAdapterFragment",""+position);
                        Intent intent = new Intent(mContext, MyOrderActivity.class);
                        mContext.startActivity(intent);
                    }
                });
                break;
            case 2:
                holder.itemView.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        Log.e("RecyclerAdapterFragment",""+position);
                        Intent intent = new Intent(mContext, WalletActivity.class);
                        mContext.startActivity(intent);
                    }
                });
                break;
            case 3:
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //查看我的评论
                        Intent intent = new Intent(mContext, MyCommitActivity.class);
                        mContext.startActivity(intent);
                    }
                });
                break;
            case 4:
                holder.itemView.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        Log.e("RecyclerAdapterFragment","position" + mys.get(position).getItemName());
                        new EasyDialog(mContext, R.layout.lianxikefu) {
                            @Override
                            public void onBindViewHolder(DialogViewHolder holder) {
                                Button btnCancel = holder.getView(R.id.btn_cancel);
                                Button btnRingUp = holder.getView(R.id.btn_ring_up);
                                btnCancel.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dismiss();
                                    }
                                });
                                btnRingUp.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:198998931058"));
                                        mContext.startActivity(callIntent);
                                        dismiss();
                                    }
                                });
                            }
                        }.backgroundLight(0.2)
                                .setCanceledOnTouchOutside(false)
                                .setCancelAble(true)
                                .fromTopToMiddle()
                                .setCustomAnimations(AnimatorHelper.TOP_IN_ANIM, AnimatorHelper.TOP_OUT_ANIM)
                                .showDialog(true);
                    }
                });
                break;
            case 5:
                holder.itemView.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        Log.e("RecyclerAdapterFragment",""+position + "\t" + mys.get(position).getItemName());
                        Intent intent = new Intent(mContext, AcitivySetting.class);
                        mContext.startActivity(intent);
                    }
                });
                break;
            case 6:
                holder.itemView.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        Log.e("RecyclerAdapterFragment",""+position + "\t" + mys.get(position).getItemName());
                        Intent intent = new Intent(mContext, ActivityQuestion.class);
                        mContext.startActivity(intent);
                    }
                });
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mys.size();
    }

    public class Myholder extends RecyclerView.ViewHolder {
        private ImageView imgRes;
        private TextView name;
        private ImageView imgIcon;
        public Myholder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_fragment_my_title);
            imgRes = itemView.findViewById(R.id.iv_fragment_my_img_res);
            imgIcon = itemView.findViewById(R.id.iv_fragment_my_img_icon);
        }
    }


}
