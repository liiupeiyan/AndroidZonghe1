package com.example.androidzonghe1.Fragment.lpyWork;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.androidzonghe1.R;

public class FragmentNoDataDayTrip extends Fragment {
//    private SmartRefreshLayout refreshLayout;
    private View view;
//    private final int REFRESH = 0;
//    private final int LOADMORE = 1;
//    private Handler handler = new Handler(){
//        @Override
//        public void handleMessage(@NonNull Message msg) {
//            super.handleMessage(msg);
//            switch (msg.what){
//                case REFRESH: //下拉刷新
//                    refreshLayout.finishRefresh();
//                    break;
//                case LOADMORE: //上滑加载更多
//                    refreshLayout.finishLoadMore();
//                    break;
//            }
//        }
//    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_nodata_day_trip, container, false);
        findViews();
//        //设置刷新头和加载更多的样式
//        refreshLayout.setRefreshHeader(new ClassicsHeader(getActivity()));
//        refreshLayout.setRefreshFooter(new ClassicsFooter(getActivity()));
        //上拉刷新下拉加载更多的事件处理
        setListener();
        return view;
    }

    private void findViews(){
//        refreshLayout = view.findViewById(R.id.first_refreshLayout);
    }

    private void setListener(){
//        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
//            @Override
//            public void onRefresh(@NonNull RefreshLayout refreshLayout) {v b
//                //下拉刷新时调用
//                new Thread(){
//                    @Override
//                    public void run() {
//                        //通过网络访问服务器端
//                        try {
//                            Thread.sleep(1000);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                        //重新从客户端请求数
//                        Message message = new Message();
//                        message.what = REFRESH;
//                        handler.sendMessage(message);
//                    }
//                }.start();
//            }
//        });
//        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
//            @Override
//            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
//                //上拉加载更多时调用
//                new Thread(){
//                    @Override
//                    public void run() {
//                        super.run();
//                        //通过网络访问服务器端
//                        try {
//                            Thread.sleep(1000);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                        //重新从客户端请求数据
//
//                        Message message = new Message();
//                        message.what = LOADMORE;
//                        handler.sendMessage(message);
//                    }
//                }.start();
//            }
//        });
    }
}
