    package com.example.androidzonghe1.Fragment.lpyWork;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidzonghe1.ConfigUtil;
import com.example.androidzonghe1.R;
import com.example.androidzonghe1.adapter.lpyWork.RecycleAdapterSameSchoolRoute;
import com.example.androidzonghe1.entity.lpyWork.Driver;
import com.example.androidzonghe1.entity.lpyWork.RecycleviewTitle;
import com.example.androidzonghe1.entity.lpyWork.SameSchoolRoute;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mob.tools.network.HttpConnection;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FragmentSameSchoolRoute extends Fragment {
    private RecyclerView recyclerView;
    public static LinearLayout top;
    private SmartRefreshLayout refreshLayout;
    private View view;
    public static RecycleAdapterSameSchoolRoute adapter;
    private final int REFRESH = 0;
    private final int LOADMORE = 1;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case REFRESH: //下拉刷新
                    //重新从客户端请求数据
                    adapter.notifyDataSetChanged();
                    refreshLayout.finishRefresh();
                    break;
                case LOADMORE: //上滑加载更多
                    //重新从客户端请求数据
                    adapter.notifyDataSetChanged();
                    refreshLayout.finishLoadMore();
                    break;

            }
        }
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_same_school_route, container, false);



        findViews();
        //设置刷新头和加载更多的样式
        refreshLayout.setRefreshHeader(new ClassicsHeader(getActivity()));
        refreshLayout.setRefreshFooter(new ClassicsFooter(getActivity()));
        //上拉刷新下拉加载更多的事件处理
        setListener();
        return view;
    }

    private void findViews(){
        top = view.findViewById(R.id.rjx);
        recyclerView = view.findViewById(R.id.rv_first);
        refreshLayout = view.findViewById(R.id.first_refreshLayout);
        if (ConfigUtil.routes.size() != 0){
//            ConfigUtil.initRoutes();
            top.setVisibility(View.GONE);
        }
        //给recycleview设置标题
        List<RecycleviewTitle> title = new ArrayList<>();
        title.add(new RecycleviewTitle("同校路线"));
        List<List> data = new ArrayList<>();
        data.add(title);
        data.add(ConfigUtil.routes);
        adapter = new RecycleAdapterSameSchoolRoute(data);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
    }

    private void setListener(){
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                //下拉刷新时调用
                new Thread(){
                    @Override
                    public void run() {
                        //通过网络访问服务器端
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        //重新从客户端请求数
                        Message message = new Message();
                        message.what = REFRESH;
                        handler.sendMessage(message);
                    }
                }.start();

            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                //上拉加载更多时调用
                new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        //通过网络访问服务器端
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        //重新从客户端请求数据
                        Message message = new Message();
                        message.what = LOADMORE;
                        handler.sendMessage(message);
                    }
                }.start();
            }
        });
    }


}
