package com.example.androidzonghe1.Fragment.lpyWork;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager.widget.ViewPager;

import com.baidu.mapapi.search.sug.SuggestionResult;
import com.example.androidzonghe1.ConfigUtil;
import com.example.androidzonghe1.R;
import com.example.androidzonghe1.activity.lpyWork.ActivityMyMessage;
import com.example.androidzonghe1.activity.lsbWork.SearchActivity;
import com.example.androidzonghe1.adapter.lpyWork.ImageAdapter;
import com.example.androidzonghe1.adapter.lpyWork.MyViewPagerAdapter;
import com.example.androidzonghe1.adapter.lpyWork.RecycleAdapterDayTrip;
import com.example.androidzonghe1.adapter.lpyWork.RecycleAdapterSameSchoolRoute;
import com.example.androidzonghe1.entity.lpyWork.DataBean;
import com.example.androidzonghe1.entity.lpyWork.Driver;
import com.example.androidzonghe1.entity.lpyWork.SameSchoolRoute;
import com.example.androidzonghe1.entity.yyWork.DriverOrder;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.youth.banner.Banner;
import com.youth.banner.indicator.CircleIndicator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FragmentHomePage extends Fragment {
    private CoordinatorLayout coordinatorLayout;
    private ViewPager myViewPager;
    private TabLayout tab_layout;
    private Banner banner;
    private ImageView ivMessage;
    private LinearLayout et_search;
    private TextView tvSchoolName;
    private final int REQUEST_SEARCH_CODE = 100;
    private View view;
    private MyViewPagerAdapter adapter;
    private Handler handler = new Handler(){
        @SuppressLint("HandlerLeak")
        @Override
        public void handleMessage(@NonNull Message msg) {
            Log.e("handlMessage","true");
            switch (msg.what){
                case 10://接收到同校路线数据
                    String resp = (String) msg.obj;
                    Gson gson = new Gson();
                    Type collectionType = new TypeToken<ArrayList<SameSchoolRoute>>() {}.getType();
                    ConfigUtil.routes = gson.fromJson(resp, collectionType);


//                    adapter.removeAllFragment();
//                    adapter.addFragment(new FragmentDayTrip(),"今日行程");
//                    Log.e("ConfigUtil.routes.size",ConfigUtil.routes.size()+"");
//                    if(ConfigUtil.routes.size() == 0){
//                        adapter.addFragment(new FragmentNoDataSchoolRoute(),"同校路线");
//                    }else {
//                        adapter.addFragment(new FragmentSameSchoolRoute(),"同校路线");
//                    }
//                    adapter.addFragment(new FragmentSameSchoolParents(),"同校家长");
//                    adapter.addFragment(new FragmentDriver(),"接送员");
//                    adapter.changeId(1);
//                    adapter.notifyDataSetChanged();
//                    //为ViewPager绑定Adapter
//                    myViewPager.setAdapter(adapter);
                    break;
                case 12://接收到今日行程数据
                    Log.e("tripHandleMessage","true"+msg.obj.toString());
                    String json = msg.obj.toString();
                    try {
                        ConfigUtil.trip=new ArrayList<>();
                        JSONArray jsonArray = new JSONArray(json);
                        for (int i = 0;i<jsonArray.length();i++){
                            JSONObject object = jsonArray.getJSONObject(i);
                            Log.e("jsonObject",object.toString());
                            DriverOrder order = new DriverOrder();
                            order.setId(object.getInt("order_id"));
                            order.setAddress(object.getString("address"));
                            order.setFrom(object.getString("from"));
                            order.setTo(object.getString("to"));
                            order.setDate(object.getString("date"));
                            order.setTime(object.getString("time"));
                            order.setState(object.getString("status"));
                            if(order.getState().equals("已完成")){
                                order.setEndTime(object.getString("timeend"));
                            }
                            order.setPrice(object.getDouble("price"));
                            Log.e("jsonObjec...",order.toString());
                            ConfigUtil.trip.add(order);
                        }
                        Log.e("ConfigUtil.trip-----",ConfigUtil.trip.toString());
                        //给recycleview设置内容
//                            FragmentDayTrip.data.add(ConfigUtil.trip);
//                            adapter.changeId(2);
//                            adapter.notifyDataSetChanged();
//                            //为ViewPager绑定Adapter
//                            myViewPager.setAdapter(adapter);
                    } catch (JSONException e) {
                        Log.e("catch Exception","true");
                        e.printStackTrace();
                    }
                    break;
                case 1://司机信息
                    String strDriver =  msg.obj.toString();
                    Gson gsonDriver = new Gson();
                    Type collection = new TypeToken<List<Driver>>() {}.getType();
                    ConfigUtil.drivers.clear();
                    ConfigUtil.drivers = gsonDriver.fromJson(strDriver,collection);
//                    adapter.notifyDataSetChanged();
//                    //为ViewPager绑定Adapter
//                    myViewPager.setAdapter(adapter);
                    break;
            }
//            adapter.changeId(1);
            adapter.notifyDataSetChanged();
            //为ViewPager绑定Adapter
            myViewPager.setAdapter(adapter);
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home_page ,container, false);


        findViews();
        InitUiAndDatas();
        useBanner();
        //动态申请权限
//        String[] permissions = new String[]{
//                android.Manifest.permission.ACCESS_FINE_LOCATION,
//        };
        if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
//            2.动态申请权限
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    100);
        }
        ivMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (ConfigUtil.isLogin){
//
                    Intent intent = new Intent(getActivity(), ActivityMyMessage.class);
                    startActivity(intent);
//                }else {
                    //先登录
//                }

            }
        });
        et_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivityForResult(intent,REQUEST_SEARCH_CODE);
            }
        });
        return view;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //grantResult数组当中存储的是用户选择的结果，0表示允许，-1表示拒绝
        if (requestCode == 100 && grantResults[0] == 0){
            Log.e("定权限已开启","MainActivity");
        }
    }

    public void getDatas(){
        //请求服务端获取数据
        //今日行程
        getAllTravel(ConfigUtil.xt+"ShowWalletServlet?id="+ConfigUtil.parent.getId());
        //司机信息
        getAllDrivers(ConfigUtil.xt+"GetDriverServlet");
        //
    }

    private void findViews(){
        coordinatorLayout = view.findViewById(R.id.fragment_home_page_main);
        myViewPager = view.findViewById(R.id.view_page_home_page);
        tab_layout = view.findViewById(R.id.tab_layout_home_page);
        banner = view.findViewById(R.id.banner);
        ivMessage = view.findViewById(R.id.mian_header_message);
        et_search = view.findViewById(R.id.main_header_search);
        tvSchoolName = view.findViewById(R.id.home_page_school_name);
    }

    private void InitUiAndDatas(){
        //初始化viewPager的adapter代码
        adapter = new MyViewPagerAdapter(getFragmentManager());
        //为Adapter添加Aapter和标题
//        if (ConfigUtil.trips.size() == 0){
//            adapter.addFragment(new FragmentNoDataDayTrip(),"今日行程");
//        } else {
//            adapter.addFragment(new FragmentDayTrip(),"今日行程");
//        }
        adapter.addFragment(new FragmentDayTrip(),"今日行程");
        Log.e("ConfigUtil.routes.size",ConfigUtil.routes.size()+"");
//        if(ConfigUtil.routes.size() == 0){
//            adapter.addFragment(new FragmentNoDataSchoolRoute(),"同校路线");
//        }else {
            adapter.addFragment(new FragmentSameSchoolRoute(),"同校路线");
//            FragmentSameSchoolRoute.adapter.notifyDataSetChanged();
//        }
        adapter.addFragment(new FragmentSameSchoolParents(),"同校家长");
//        if (ConfigUtil.drivers.size() == 0){
//            adapter.addFragment(new FragmentNoDataDriver(),"接送员");
//        } else {
            adapter.addFragment(new FragmentDriver(),"接送员");
//        }
//        adapter.addFragment(new FragmentDriver(),"接送员");

        //为ViewPager绑定Adapter
        myViewPager.setAdapter(adapter);
//        tab_layout.setTabMode(TableLayout.MOOE_FIX);
        //为TabLayout添加标签，注意这里我们传入了标签名称，但demo运行时显示的标签名称并不是我们添加的，那么为什么呢？卖个官子...
        tab_layout.addTab(tab_layout.newTab().setText("今日行程").setIcon(R.drawable.trip4));
        tab_layout.addTab(tab_layout.newTab().setText("同校路线").setIcon(R.drawable.route2));
        tab_layout.addTab(tab_layout.newTab().setText("同校家长").setIcon(R.drawable.same_school));
        tab_layout.addTab(tab_layout.newTab().setText("接送员").setIcon(R.drawable.driver));
        //给tabLayout设置ViewPage，如果设置关联了Viewpage，那么ViewpagAdapter中getPageTitle返回的就是Tab上标题(上面疑问的回答)
        //为ViewPager 和Tablelayout进行绑定，从而实现滑动标签切换Fragment的目的
//        tab_layout.getTabAt(3);
        tab_layout.setupWithViewPager(myViewPager);
//        if (ConfigUtil.flagChooseDriver){
//            myViewPager.setCurrentItem(3);
//            ConfigUtil.flagChooseDriver = false;
//        }
    }

    //轮播图的使用
    @SuppressLint("WrongConstant")
    public void useBanner() {
        //--------------------------简单使用-------------------------------
        banner.addBannerLifecycleObserver(this)//添加生命周期观察者
                .setAdapter(new ImageAdapter(getContext(), DataBean.getTestData()))
                .setIndicator(new CircleIndicator(getContext()));
        //设置圆角
        banner.setBannerRound(25);
//        轮播方向
        banner.setOrientation(LinearLayoutManager.VERTICAL);
        //效果，画廊效果，魅族效果
//        banner.setBannerGalleryEffect(50,50,0);
//        banner.setBannerGalleryMZ(0,0);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == -1){//未选中任何地点
            Toast.makeText(getContext(),"未选中任何地点",Toast.LENGTH_SHORT).show();
            tvSchoolName.setText("选择学校");
            tvSchoolName.setTextSize(16);
            FragmentSameSchoolRoute.top.setVisibility(View.VISIBLE);
        } else if (resultCode == 0){
            if(requestCode == REQUEST_SEARCH_CODE){
                SuggestionResult.SuggestionInfo info = data.getExtras().getParcelable("suggestionInfo");
                Log.e("suggestionInfo",info.toString());
                String schoolName =  info.key;
                ConfigUtil.school = schoolName;
                ConfigUtil.latitude = info.getPt().latitude;
                ConfigUtil.longitude = info.getPt().longitude;
                //获取同校路线
                getSameSchoolRoute(ConfigUtil.xt+"GetSameRouteServlet?school="+schoolName);
                FragmentSameSchoolParents fragmentSameSchoolParents = new FragmentSameSchoolParents();
//                adapter.changeId(1);
//                adapter.notifyDataSetChanged();
//                //为ViewPager绑定Adapter
//                myViewPager.setAdapter(adapter);
                tvSchoolName.setText(schoolName);
                tvSchoolName.setTextSize(18);
            }
        } else{

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getDatas();
        Log.e("ConfigUtil.phone",ConfigUtil.phone);
    }

    //网络请求 获取今日行程
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
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                    String resp = reader.readLine();
                    Log.e("所有行程1",resp);
                    is.close();
                    //借助Message传递数据
                    Message message = new Message();
                    //设置Message对象的参数
                    message.what = 12;
                    message.obj = resp;
                    //发送Message
                    handler.sendMessage(message);
                    Log.e("所有行程2",resp);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    //网络操作 获取同校路线
    public void getSameSchoolRoute(String s){
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    URL url = new URL(s);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");//设置请求方式

                    //从服务器段获取响应
                    InputStream is = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                    String resp = reader.readLine();
//                    byte[] bytes = new byte[1024];
//                    int len = is.read(bytes);//将数据保存在bytes中，长度保存在len中
//                    String resp = new String(bytes,0,len);
                    Log.e("搜索结果",resp);

                    is.close();

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


    public void getAllDrivers(String s){
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
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                    String resp = reader.readLine();
                    //                    byte[] bytes = new byte[512];
//                    int len = is.read(bytes);//将数据保存在bytes中，长度保存在len中
//                    String resp = new String(bytes,0,len);
                    Log.e("所有司机",resp);
                    //                    借助Message传递数据
                    Message message = new Message();
//                    设置Message对象的参数
                    message.what = 1;
                    message.obj = resp;
//                    发送Message
                    handler.sendMessage(message);

                    is.close();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
