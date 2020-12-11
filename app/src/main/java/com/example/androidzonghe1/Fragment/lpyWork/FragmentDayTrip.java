package com.example.androidzonghe1.Fragment.lpyWork;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidzonghe1.ConfigUtil;
import com.example.androidzonghe1.R;
import com.example.androidzonghe1.adapter.lpyWork.RecycleAdapterDayTrip;
import com.example.androidzonghe1.entity.lpyWork.Order;
import com.example.androidzonghe1.entity.lpyWork.RecycleviewTitle;
import com.example.androidzonghe1.entity.yyWork.DriverOrder;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FragmentDayTrip extends Fragment {
    private RecyclerView recyclerView;
    private SmartRefreshLayout refreshLayout;
    private View view;
    private RecycleAdapterDayTrip adapter;
    private final int REFRESH = 0;
    private final int LOADMORE = 1;
    private List<List> data= new ArrayList<>();
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
                case 10:
                    if(msg.obj != null){
                        String json = (String) msg.obj;
                        try {
                            JSONArray jsonArray = new JSONArray(json);
                            for (int i = 0;i<jsonArray.length();i++){
                                JSONObject object = jsonArray.getJSONObject(i);
                                DriverOrder order = new DriverOrder();
                                order.setId(object.getInt("order_id"));
                                order.setAddress(object.getString("address"));
                                order.setFrom(object.getString("from"));
                                order.setTo(object.getString("to"));
                                order.setDate(object.getString("date"));
                                order.setTime(object.getString("time"));
                                order.setEndTime(object.getString("timeend"));
                                order.setPrice(object.getDouble("price"));

                                ConfigUtil.trip.add(order);
                            }


                            if (ConfigUtil.trip.size() == 0){
                                ConfigUtil.initTrips();
                            }
                            //给recycleview设置内容
                            data.add(ConfigUtil.trips);
                            adapter = new RecycleAdapterDayTrip(data);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                            recyclerView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }else {
                        ConfigUtil.initTrip();
                    }

                    break;
            }
        }
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_day_trip, container, false);
        //请求服务端获取数据
        getAllTravel(ConfigUtil.xt+"ShowWalletServlet?id="+ConfigUtil.parent.getId());
        findViews();
        //设置刷新头和加载更多的样式
        refreshLayout.setRefreshHeader(new ClassicsHeader(getActivity()));
        refreshLayout.setRefreshFooter(new ClassicsFooter(getActivity()));
        //上拉刷新下拉加载更多的事件处理
        setListener();
        return view;
    }

    private void findViews(){
        recyclerView = view.findViewById(R.id.rv_first);
        refreshLayout = view.findViewById(R.id.first_refreshLayout);
        //给recycleview设置标题
        List<RecycleviewTitle> title = new ArrayList<>();
        title.add(new RecycleviewTitle("今日行程"));
        data.add(title);
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

    private void getAllTravel(String s){
        new Thread(){
            @Override
            public void run() {
                try {
                    URL url = new URL(s);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    //设置http请求方式，get、post、put、...(默认get请求)
                    connection.setRequestMethod("POST");//设置请求方式

                    //从服务器段获取响应
                    InputStream is = connection.getInputStream();
                    byte[] bytes = new byte[512];
                    int len = is.read(bytes);//将数据保存在bytes中，长度保存在len中
                    String resp = new String(bytes,0,len);
                    Log.e("所有行程",resp);

                    //借助Message传递数据
                    Message message = new Message();
                    //设置Message对象的参数
                    message.what = 10;
                    message.obj = resp;
                    //发送Message
                    handler.sendMessage(message);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
