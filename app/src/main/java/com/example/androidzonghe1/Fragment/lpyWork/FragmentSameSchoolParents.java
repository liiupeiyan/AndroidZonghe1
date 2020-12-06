package com.example.androidzonghe1.Fragment.lpyWork;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;
import com.example.androidzonghe1.R;
import com.example.androidzonghe1.activity.lpyWork.MyTheActivity;
import com.lanren.easydialog.AnimatorHelper;
import com.lanren.easydialog.DialogViewHolder;
import com.lanren.easydialog.EasyDialog;

public class FragmentSameSchoolParents extends Fragment {
    private View view;
    private MapView mapView;
    private BaiduMap baiduMap;
    private LocationClient locationClient;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_same_school_parents, container, false);
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
            setPosition();
        }

        return view;
    }

    private void findViews(){
//        ivSearch.setOnClickListener(this);
        mapView = view.findViewById(R.id.ss_school_map_view);
        //百度地图的控制器
        baiduMap = mapView.getMap();
        //修改指南针位置
        UiSettings uiSettings = baiduMap.getUiSettings();
        uiSettings.setCompassEnabled(false);//先设置UiSettings为false
        baiduMap.setCompassEnable(true);//baidumap为true
        baiduMap.setCompassPosition(new Point(900,100));
    }

    private void mapCustom(){
        //修改logo位置
        mapView.setLogoPosition(LogoPosition.logoPostionCenterBottom);
        //隐藏logo
        //获取地图的子视图对象，判断对象不为null并且该对象是ImageView对象，删除
//        View child = mapView.getChildAt(1);
//        if(child != null && (child instanceof ImageView)){
//            //隐藏子视图对象
//            child.setVisibility(View.INVISIBLE);//切换可见不可见
////            child.setVisibility(View.GONE);
//        }
        //修改指南针位置
        UiSettings uiSettings = baiduMap.getUiSettings();
        uiSettings.setCompassEnabled(false);//先设置UiSettings为false
        baiduMap.setCompassEnable(true);//baidumap为true
        baiduMap.setCompassPosition(new Point(900,100));
        //修改图标
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.compass);
//        baiduMap.setCompassIcon(bitmap);
        //比例尺
        Log.e("默认的比例尺大小",mapView.getMapLevel()+"");
        //修改比例尺
        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(16.0f);
        baiduMap.setMapStatus(msu);
        baiduMap.setMaxAndMinZoomLevel(19,13);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //grantResult数组当中存储的是用户选择的结果，0表示允许，-1表示拒绝
        if (requestCode == 100 && grantResults[0] == 0){
            setPosition();
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



    //添加标注覆盖物（在地图界面某个坐标点显示小图标）
    public void addMarkerOverLay(){
        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.boy);
        BitmapDescriptor icon2 = BitmapDescriptorFactory.fromResource(R.drawable.boy2);
        BitmapDescriptor icon3 = BitmapDescriptorFactory.fromResource(R.drawable.boy3);
        //1.定义坐标点
        //114.524356,38.002234  师活超市
        //   38.001082;//纬度114.53209;//经度  国培超市
        //114.524332,38.003104 //图书馆
        LatLng point1 = new LatLng(38.002234, 114.524356);
        LatLng point2 = new LatLng(38.001082, 114.53209);
        LatLng point3 = new LatLng(38.003104,114.524332);
        //2.创建OverlayOption子类的对象
        MarkerOptions options = new MarkerOptions()
                .position(point1)//位置
                .icon(icon);//指定图标
        MarkerOptions options2 = new MarkerOptions()
                .position(point2)//位置
                .icon(icon2);//指定图标
        MarkerOptions options3 = new MarkerOptions()
                .position(point3)
                .icon(icon3);
        //3.将覆盖物显示到地图界面
        Marker marker = (Marker) baiduMap.addOverlay(options);
        Marker marker2 = (Marker) baiduMap.addOverlay(options2);
        Marker marker3 = (Marker) baiduMap.addOverlay(options3);
//        marker.setTitle("天安门");
        Bundle bundle = new Bundle();
        bundle.putString("title", "师活超市");
        marker.setExtraInfo(bundle);
        Bundle bundle2 = new Bundle();
        bundle2.putString("title", "国培超市");
        marker2.setExtraInfo(bundle2);
        Bundle bundle3 = new Bundle();
        bundle3.putString("title", "图书馆");
        marker3.setExtraInfo(bundle3);

        //4.添加覆盖物的事件监听器
        baiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                //弹出邀请页
                new EasyDialog(getContext(), R.layout.view_invite_card) {
                    @Override
                    public void onBindViewHolder(DialogViewHolder holder) {
                        ImageView close = holder.getView(R.id.img_close);
                        close.setImageBitmap(marker.getIcon().getBitmap());
                        close.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dismiss();
                            }
                        });
                        Button button = holder.getView(R.id.btn_get);
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

//        baiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
//            @Override
//            public boolean onMarkerClick(Marker marker) {
//                //处理标注覆盖物的点击事件
//                Bundle extra = marker.getExtraInfo();
//                String title = extra.getString("title");
//                Log.e("点击的标注覆盖物标题为：", title);
//                //显示弹出窗覆盖物
//                TextView contentTV = new TextView(MyTheActivity.this);
//                contentTV.setText(title);
//                contentTV.setTextColor(Color.WHITE);
//                contentTV.setTextSize(30.0f);
//                contentTV.setBackgroundResource(R.drawable.popup);
//                BitmapDescriptor descriptor = BitmapDescriptorFactory.fromView(contentTV);
//                final InfoWindow window = new InfoWindow(descriptor, marker.getPosition(),
//                        -120,
//                        new InfoWindow.OnInfoWindowClickListener() {
//                            @Override
//                            public void onInfoWindowClick() {
//                                //当点击弹出窗口时执行
//                                //隐藏
//                                baiduMap.hideInfoWindow();
//                            }
//                        });
//                //显示弹出窗口覆盖物
//                baiduMap.showInfoWindow(window);
//                return false;
//            }
//        });
//        //处理标注覆盖物的拖拽事件
//        baiduMap.setOnMarkerDragListener(new BaiduMap.OnMarkerDragListener() {
//            @Override
//            public void onMarkerDrag(Marker marker) {
//                Log.e("标注覆盖物", "正在拖拽");
//            }
//
//            @Override
//            public void onMarkerDragEnd(Marker marker) {
//                Log.e("标注覆盖物", "结束拖拽"+"纬度："+
//                        marker.getPosition().latitude+"经度："+
//                        marker.getPosition().longitude);
//            }


//            @Override
//            public void onMarkerDragStart(Marker marker) {
//                Log.e("标注覆盖物", "拖拽开始");
//            }
//        });
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
