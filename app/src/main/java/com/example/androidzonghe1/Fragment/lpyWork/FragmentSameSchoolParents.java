package com.example.androidzonghe1.Fragment.lpyWork;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
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
import android.widget.TextView;
import android.widget.ZoomControls;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.LogoPosition;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.example.androidzonghe1.ConfigUtil;
import com.example.androidzonghe1.R;
import com.example.androidzonghe1.activity.lpyWork.MyTheActivity;
import com.example.androidzonghe1.entity.rjxWork.History;
import com.example.androidzonghe1.entity.rjxWork.Locate;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lanren.easydialog.AnimatorHelper;
import com.lanren.easydialog.DialogViewHolder;
import com.lanren.easydialog.EasyDialog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FragmentSameSchoolParents extends Fragment {
    private View view;
    private TextureMapView mapView;
    private BaiduMap baiduMap;
    private LocationClient locationClient;
    private List<Locate> locates;
    private List<OverlayOptions> options = new ArrayList<>();
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    String jsonList = msg.obj.toString();
                    Gson gson = new Gson();
                    Type collectionType = new TypeToken<List<Locate>>() {}.getType();
                    locates = gson.fromJson(jsonList, collectionType);
                    for (Locate locate: locates) {
                        switch (locate.getRelationship()){
                            case "爷爷":
                                locate.setImg(R.drawable.overlay_g_f);
                                break;
                            case "奶奶":
                                locate.setImg(R.drawable.overlay_g_m);
                                break;
                            case "爸爸":
                                locate.setImg(R.drawable.overlay_f);
                                break;
                            case "妈妈":
                                locate.setImg(R.drawable.overlay_m);
                                break;
                            case "哥哥":
                                locate.setImg(R.drawable.overlay_f);
                                break;
                            case "姐姐":
                                locate.setImg(R.drawable.overlay_m);
                                break;
                        }
                    };
                    break;
            }
        }
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_same_school_parents, container, false);
        getHomeAddresses(ConfigUtil.Url+"GetLocateServlet");
        //获取引用
        findViews();
        //自定义百度地图
        mapCustom();
        //添加覆盖物
        addMarkerOverLay();
        //实现定位功能
        //1.创建定位客户端对象,参数要求是应用程序级别的上下文对象
        locationClient = new LocationClient(getContext().getApplicationContext());
        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            //2.动态申请权限
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    100);
        } else {
            if (ConfigUtil.school.equals("")){
                setPosition();
            } else {
                setPointPosition(ConfigUtil.latitude,ConfigUtil.longitude);
            }
        }

        return view;
    }

    private void findViews(){
//        ivSearch.setOnClickListener(this);
        mapView = view.findViewById(R.id.ss_school_map_view);
        //百度地图的控制器
        baiduMap = mapView.getMap();
    }

    private void mapCustom(){
        //比例尺
        Log.e("默认的比例尺大小",mapView.getMapLevel()+"");
        //修改比例尺
        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(16.0f);
        baiduMap.setMapStatus(msu);
        baiduMap.setMaxAndMinZoomLevel(19,13);
        //隐藏百度图标
        View logo = mapView.getChildAt(1);
        if (logo != null && (logo instanceof ImageView || logo instanceof ZoomControls)){
            logo.setVisibility(View.INVISIBLE);
        }
        //设置指南针不显示
        baiduMap.setCompassEnable(false);
        //设置不显示比例尺
        mapView.showScaleControl(false);
        //设置放大缩小不显示
        mapView.showZoomControls(false);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //grantResult数组当中存储的是用户选择的结果，0表示允许，-1表示拒绝
        if (requestCode == 100 && grantResults[0] == 0){
            setPointPosition(ConfigUtil.latitude,ConfigUtil.longitude);
        }
    }

    //定位自身位置并添加覆盖物
    public void setPosition(){
        //3.创建LocationClient
        LocationClientOption option = new LocationClientOption();
        //配置定位参数
        //设置打开GPS
        option.setOpenGps(true);
        //设置坐标系类型
        option.setCoorType("bd09ll");
        //设置定位模式，使用低功耗定位模式
        option.setLocationMode(LocationClientOption.LocationMode.Battery_Saving);
        //4.将定位参数应用到定位客户端
        locationClient.setLocOption(option);
        //5.设置定位成功的监听器（实现异步定位操作，定位成功后会自动回调抽象方法）
        locationClient.registerLocationListener(new BDAbstractLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                //定位成功后自动执行，定位成功后位置信息保存在BDLocation对象中
                double latitude = bdLocation.getLatitude();//纬度
                double longitude = bdLocation.getLongitude();//经度
                Log.e("定位错误码：",bdLocation.getLocType()+"");
                Log.e("定位成功","纬度："+latitude +
                        "经度："+longitude);

                //移动地图界面显示到当前位置
                LatLng point = new LatLng(latitude,longitude);

                MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(point);
                //移动地图界面
                baiduMap.animateMapStatus(update);
                //添加定位图层
                //1.配置定位图层
                MyLocationConfiguration configuration = new MyLocationConfiguration(
                        MyLocationConfiguration.LocationMode.COMPASS,//定位图层
                        true,
                        BitmapDescriptorFactory.fromResource(R.drawable.school_overlay));//默认小图标
                //在地图显示定位图层
                baiduMap.setMyLocationConfiguration(configuration);
                baiduMap.setMyLocationEnabled(true);
                //2.配置定位数
                MyLocationData data = new MyLocationData.Builder()
                        .latitude(latitude)
                        .longitude(longitude)
                        .build();
                //将定位数据设置到地图
                baiduMap.setMyLocationData(data);
            }
        });
        //6.启动定位
        locationClient.start();
    }

    //定位搜索的指定学校位置并添加覆盖物
    public void setPointPosition(double latitude,double longitude){
        locationClient.stop();//停止定位
        //关闭定位图层
        baiduMap.setMyLocationEnabled(false);
        //开启图层定位
        baiduMap.setMyLocationEnabled(true);
        //判断若果定位服务被关闭，启动定位
        if(!locationClient.isStarted()){
            locationClient.start();
        }
        //3.创建LocationClient
        LocationClientOption option = new LocationClientOption();
        //配置定位参数
        //设置打开GPS
        option.setOpenGps(true);
        //设置坐标系类型
        option.setCoorType("bd09ll");
        //设置定位模式，使用低功耗定位模式
        option.setLocationMode(LocationClientOption.LocationMode.Battery_Saving);
        //4.将定位参数应用到定位客户端
        locationClient.setLocOption(option);
        //5.设置定位成功的监听器（实现异步定位操作，定位成功后会自动回调抽象方法）
        locationClient.registerLocationListener(new BDAbstractLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
    //                //定位成功后自动执行，定位成功后位置信息保存在BDLocation对象中
                double latitude1 = latitude;//纬度
                double longitude1 = longitude;//经度
    //                Log.e("定位错误码：",bdLocation.getLocType()+"");
    //                Log.e("定位成功","纬度："+latitude +
    //                        "经度："+longitude);


                //移动地图界面显示到当前位置
                LatLng point = new LatLng(latitude,longitude);

                MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(point);
                //移动地图界面
                baiduMap.animateMapStatus(update);
                //添加定位图层
                //1.配置定位图层
                MyLocationConfiguration configuration = new MyLocationConfiguration(
                        MyLocationConfiguration.LocationMode.COMPASS,//定位图层
                        true,
                        BitmapDescriptorFactory.fromResource(R.drawable.position_lpy));//默认小图标
                //在地图显示定位图层
                baiduMap.setMyLocationConfiguration(configuration);
                baiduMap.setMyLocationEnabled(true);
                //2.配置定位数
                MyLocationData data = new MyLocationData.Builder()
                        .latitude(latitude)
                        .longitude(longitude)
                        .build();
                //将定位数据设置到地图
                baiduMap.setMyLocationData(data);
            }
        });
        //6.启动定位
        locationClient.start();
    }


    //添加标注覆盖物（在地图界面某个坐标点显示小图标）
    public void addMarkerOverLay(){
        for (Locate locate: locates) {
            BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(locate.getImg());
            LatLng point = new LatLng(locate.getLatitude(),locate.getLongitude());
            OverlayOptions option = new MarkerOptions()
                    .position(point)
                    .icon(icon);
            options.add(option);
            }
        baiduMap.addOverlays(options);
        //4.添加覆盖物的事件监听器
        baiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                //弹出邀请页
                new EasyDialog(getContext(), R.layout.view_invite_card) {
                    @Override
                    public void onBindViewHolder(DialogViewHolder holder) {
                        ImageView close = holder.getView(R.id.img_close);
                        ImageView photo = holder.getView(R.id.iv_photo);
                        photo.setImageBitmap(marker.getIcon().getBitmap());
                        TextView textView = holder.getView(R.id.word1);
                        textView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                        close.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dismiss();
                            }
                        });
                        Button button = holder.getView(R.id.btn_invite);
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //邀请
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

                return false;
            }
        });
        };

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onStop() {
        super.onStop();
        locationClient.stop();//停止定位
        //关闭定位图层
        baiduMap.setMyLocationEnabled(false);
    }

    @Override
    public void onStart() {
        super.onStart();
        //开启图层定位
        baiduMap.setMyLocationEnabled(true);
        //判断若果定位服务被关闭，启动定位
        if(!locationClient.isStarted()){
            locationClient.start();
        }

    }

    //获取所有用户的homeAddress信息
    private void getHomeAddresses(final String str) {
        new Thread() {
            @Override
            public void run() {
                super.run();

                try {
                    URL url = new URL(str+"?name="+ConfigUtil.school+"");
                    //获取网络连接对象URLConnection
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    //获取网络输入流
                    InputStream is = connection.getInputStream();
                    connection.setRequestMethod("POST");
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                    String homeAddressList = reader.readLine();
                    System.out.println(homeAddressList);
                    Message message = new Message();
                    message.what = 1;
                    message.obj = homeAddressList;
                    handler.sendMessage(message);
                    Log.e("homeAddresslist",homeAddressList);
                    is.close();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
