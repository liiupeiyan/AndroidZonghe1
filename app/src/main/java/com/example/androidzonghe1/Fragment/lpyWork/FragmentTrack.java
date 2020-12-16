package com.example.androidzonghe1.Fragment.lpyWork;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ZoomControls;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.model.LatLng;
import com.example.androidzonghe1.R;
import com.example.androidzonghe1.activity.Track.TrackQueryActivity;
import com.example.androidzonghe1.activity.lpyWork.TracingActivity;

import java.lang.reflect.Method;

public class FragmentTrack extends Fragment {
    private View view;
    private TextureMapView mapView;
    private BaiduMap baiduMap;
    private LocationClient locationClient;
    private Toolbar toolbar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view  = inflater.inflate(R.layout.fragment_track,container,false);

        //加载选项菜单
        toolbar = view.findViewById(R.id.toolbar_track);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);

        mapView = view.findViewById(R.id.mapview_track);
        //百度地图的控制器
        baiduMap = mapView.getMap();
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
            setPosition();
        }


        return view;

    }

    //加载选项菜单


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.track_option_menu,menu);
        if (menu != null) {
            if (menu.getClass() == MenuBuilder.class) {
                try {
                    Method m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                } catch (Exception e) {
                }
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //处理选项菜单 的点击事件
        switch (item.getItemId()){
            case R.id.track_actual:
                Intent intent = new Intent(getContext(), TracingActivity.class);
                startActivity(intent);
                break;
            case R.id.track_history:
                Intent intent1 = new Intent(getContext(), TrackQueryActivity.class);
                startActivity(intent1);
                break;
        }
        return super.onOptionsItemSelected(item);
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
}
