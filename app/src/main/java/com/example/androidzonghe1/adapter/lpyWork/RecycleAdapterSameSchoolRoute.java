package com.example.androidzonghe1.adapter.lpyWork;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidzonghe1.R;
import com.example.androidzonghe1.entity.lpyWork.RecycleviewTitle;
import com.example.androidzonghe1.entity.lpyWork.SameSchoolRoute;

import java.util.List;

public class RecycleAdapterSameSchoolRoute extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<List> data;
    private List<SameSchoolRoute> routes;
    private static final int TITLE = 0;
    private static final int CONTENT = 1;

    private View view;
    private Context mContext;
    private LayoutInflater layoutInflater;
    public RecycleAdapterSameSchoolRoute(List<List> data) {
        this.data = data;
        this.routes = data.get(1);
    }

    @Override
    public int getItemViewType(int position) {
        Object o ;
        if (position >= 2){
            o = data.get(1).get(position-1);
        } else {
            o = data.get(position).get(0);
        }

        if(o instanceof RecycleviewTitle){
            return TITLE;
        }
        if(o instanceof SameSchoolRoute){
            return CONTENT;
        }
        return super.getItemViewType(position);
    }

    //这里返回一个ViewHolder
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null){
            mContext = parent.getContext();
        }
        if (layoutInflater == null){
            layoutInflater = LayoutInflater.from(mContext);
        }
        switch (viewType){
            case TITLE:
                view = layoutInflater.inflate(R.layout.item_recycleview_title,parent,false);
                RecycleAdapterSameSchoolRoute.TitleViewHolder titleViewHolder = new RecycleAdapterSameSchoolRoute.TitleViewHolder(view);
                return titleViewHolder;
            case CONTENT:
                view = layoutInflater.inflate(R.layout.item_recycleview_same_school_route,parent,false);
                RecycleAdapterSameSchoolRoute.Myholder myholder = new RecycleAdapterSameSchoolRoute.Myholder(view);
                return myholder;
        }
        return null;
    }
    //为ViewHolder中的布局绑定数据
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof TitleViewHolder){
            List<RecycleviewTitle> titles = data.get(position);
            ((TitleViewHolder) holder).title.setText(titles.get(position).getTitle());
        }
        if(holder instanceof Myholder) {
            ((Myholder) holder).schoolName.setText(routes.get(position-1).getSchool());
            ((Myholder) holder).peopleNow.setText(routes.get(position-1).getPeopleNow()+"");
            ((Myholder) holder).peopleTotal.setText(routes.get(position-1).getPeopleTotal()+"");
            ((Myholder) holder).routeState.setText(routes.get(position-1).getRouteState());
            ((Myholder) holder).communityFirst.setText(routes.get(position-1).getCommunityFirst());
            ((Myholder) holder).community1Distance.setText(routes.get(position-1).getDistanceCommunityFirst()+"");
            ((Myholder) holder).communitySecond.setText(routes.get(position-1).getCommunitySecond());
            ((Myholder) holder).community2Distance.setText(routes.get(position-1).getDistanceCommunitySecond()+"");

            //查看订单详情的点击事件
            ((Myholder) holder).routeInfo.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    Log.e("RecycleAdapter","查看订单详情");
                }
            });
            holder.itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    Log.e("这里是点击每一行item的响应事件",""+position);
                }
            });
        }
    }
    @Override
    public int getItemCount() {
        int num = 0;
        for (int i = 0; i < data.size() ;i++){
            num += data.get(i).size();
        }
//        Log.e("driver.size",num+"");
        return num;
    }
    public class Myholder extends RecyclerView.ViewHolder {
        private TextView schoolName;
        private TextView peopleNow;
        private TextView peopleTotal;
        private TextView routeState;
        private TextView communityFirst;
        private TextView community1Distance;
        private TextView communitySecond;
        private TextView community2Distance;
        private Button routeInfo;
        public Myholder(View itemView) {
            super(itemView);
            schoolName = itemView.findViewById(R.id.ss_school_name);
            peopleNow = itemView.findViewById(R.id.ss_route_people_now);
            peopleTotal = itemView.findViewById(R.id.ss_route_people_total);
            routeState = itemView.findViewById(R.id.ss_route_state);
            communityFirst = itemView.findViewById(R.id.ss_school_community_first);
            community1Distance = itemView.findViewById(R.id.ss_route_distance1);
            communitySecond = itemView.findViewById(R.id.ss_school_community_second);
            community2Distance = itemView.findViewById(R.id.ss_route_distance2);
            routeInfo = itemView.findViewById(R.id.item_ss_school_btn_route_info);
        }
    }

    public class TitleViewHolder extends RecyclerView.ViewHolder{
        public TextView title;
        public TitleViewHolder(@NonNull View itemView){
            super(itemView);
            title = itemView.findViewById(R.id.item_recycleview_title);
        }
    }
}
