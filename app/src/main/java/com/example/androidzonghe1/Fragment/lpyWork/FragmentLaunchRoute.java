package com.example.androidzonghe1.Fragment.lpyWork;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.ZoomControls;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.example.androidzonghe1.R;
import com.example.androidzonghe1.activity.lsbWork.SearchActivity;
import com.example.androidzonghe1.activity.yyWork.OrderDetailsActivity;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class FragmentLaunchRoute extends Fragment {
    private Button order;
    private MapView mapView;
    RadioGroup rgMethod;
    RadioButton rbMultiple;
    RadioButton rbSingle;
    Button btnEnd;
    Button btnStart;
    BaiduMap baiduMap;
    final int END_CODE = 1;
    final int START_CODE = 2;
    private LocationClient locationClient;

    String method = "multiple";

    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case END_CODE:

                    break;
                case START_CODE:

                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.map_layout, container, false);
        mapView = view.findViewById(R.id.map_view);
        baiduMap = mapView.getMap();

        //获取控件引用
        order = view.findViewById(R.id.btn_order);
        rgMethod = view.findViewById(R.id.rg_method);
        rbMultiple = view.findViewById(R.id.rb_multiple);
        rbSingle = view.findViewById(R.id.rb_single);
        btnEnd = view.findViewById(R.id.btn_end);
        btnStart = view.findViewById(R.id.btn_start);


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

        rgMethod.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_multiple:
                        rbMultiple.setText("拼车接送");
                        rbMultiple.setBackgroundResource(R.drawable.map_layout_btn_bg);
                        rbSingle.setText(Html.fromHtml("<u>"+"单人接送"+"</u>"));
                        rbSingle.setBackgroundColor(getResources().getColor(R.color.white));
                        method = "multiple";
                        break;
                    case R.id.rb_single:
                        rbMultiple.setText(Html.fromHtml("<u>"+"拼车接送"+"</u>"));
                        rbMultiple.setBackgroundColor(getResources().getColor(R.color.white));
                        rbSingle.setText("单人专享");
                        rbSingle.setBackgroundResource(R.drawable.map_layout_btn_bg);
                        method = "single";
                        break;
                }
            }
        });

        btnEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("FragmentLaunchRoute", "btnEnd onClicked");
                Intent intentEnd = new Intent(getContext(), SearchActivity.class);
                startActivityForResult(intentEnd, END_CODE);
            }
        });

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("FragmentLaunchRoute", "btnStart onClicked");
                Intent intentEnd = new Intent(getContext(), SearchActivity.class);
                startActivityForResult(intentEnd, START_CODE);
            }
        });

        //定位功能
        locationClient = new LocationClient(getContext().getApplicationContext());
        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("FragmentLaunchRoute", "requestCode:" + requestCode + "\tresultCode:" + resultCode);
        switch (requestCode){
            case END_CODE:
                if (resultCode == 0){
                    SuggestionResult.SuggestionInfo suggestionInfo = data.getExtras().getParcelable("suggestionInfo");
                    Log.e("FragmentLaunchRoute", "suggestionInfo" + suggestionInfo.toString());
                    btnEnd.setText(suggestionInfo.key);
                    addMarkerOverlay(END_CODE, suggestionInfo);
                    String pt = suggestionInfo.pt.toString();
                    setPosition(suggestionInfo.getPt().latitude,suggestionInfo.getPt().longitude);
                    addMarkerOverLay(suggestionInfo.getPt().latitude,suggestionInfo.getPt().longitude);
                    MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(16.0f);
                    baiduMap.setMapStatus(msu);
                    baiduMap.setMaxAndMinZoomLevel(19,13);
                    //显示发起新路线按钮
                    if(!btnStart.getText().equals("请输入孩子上车地点")&&!btnEnd.getText().equals("请输入终点")){
                        order.setVisibility(View.VISIBLE); //设置按钮为可见的
                        order.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //跳转到OrderDetailsActivity
                                Intent intent = new Intent(getContext(), OrderDetailsActivity.class);
                                startActivity(intent);
                            }
                        });
                    }
                } else if (requestCode == -1){//未选中任何地点

                }
                break;
            case START_CODE:
                if (resultCode == 0){
                    SuggestionResult.SuggestionInfo suggestionInfo = data.getExtras().getParcelable("suggestionInfo");
                    Log.e("FragmentLaunchRoute", "suggestionInfo" + suggestionInfo.toString());
                    btnStart.setText(suggestionInfo.key);
                    addMarkerOverlay(START_CODE, suggestionInfo);
                    String pt = suggestionInfo.pt.toString();
                    setPosition(suggestionInfo.getPt().latitude,suggestionInfo.getPt().longitude);
                    addMarkerOverLay(suggestionInfo.getPt().latitude,suggestionInfo.getPt().longitude);
                    //显示发起新路线按钮
                    if(!btnStart.getText().equals("请输入孩子上车地点")&&!btnEnd.getText().equals("请输入终点")){
                        order.setVisibility(View.VISIBLE); //设置按钮为可见的
                        order.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //跳转到OrderDetailsActivity
                                Intent intent = new Intent(getContext(), OrderDetailsActivity.class);
                                startActivity(intent);
                            }
                        });
                    }
                } else if(requestCode == -1){//未选中任何地点

                }
                break;
        }
    }

    //添加覆盖物
    public void addMarkerOverlay(int code, SuggestionResult.SuggestionInfo info){
        BitmapDescriptor icon = null;
        switch (code){
            case END_CODE:
                icon = BitmapDescriptorFactory.fromResource(R.drawable.p1);
                break;
            case START_CODE:
                icon = BitmapDescriptorFactory.fromResource(R.drawable.p2);
                break;
        }
        LatLng point = new LatLng(info.getPt().latitude, info.getPt().longitude);
        MarkerOptions options = new MarkerOptions()
                .position(point)
                .icon(icon);
        Marker marker = (Marker) baiduMap.addOverlay(options);
        marker.setTitle(info.getKey());
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
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

    //定位自身位置并添加覆盖物
    public void setPosition(double latitude,double longitude){
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
    public void addMarkerOverLay(double latitude,double longitude){
        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.boy);
        BitmapDescriptor icon2 = BitmapDescriptorFactory.fromResource(R.drawable.boy2);
        BitmapDescriptor icon3 = BitmapDescriptorFactory.fromResource(R.drawable.boy3);
        //1.定义坐标点
        //114.524356,38.002234  师活超市
        //   38.001082;//纬度114.53209;//经度  国培超市
        //114.524332,38.003104 //图书馆
        LatLng point1 = new LatLng(latitude, longitude);
//        LatLng point2 = new LatLng(38.001082, 114.53209);
//        LatLng point3 = new LatLng(38.003104,114.524332);
        //2.创建OverlayOption子类的对象
        MarkerOptions options = new MarkerOptions()
                .position(point1)//位置
                .icon(icon);//指定图标
//        MarkerOptions options2 = new MarkerOptions()
//                .position(point2)//位置
//                .icon(icon2);//指定图标
//        MarkerOptions options3 = new MarkerOptions()
//                .position(point3)
//                .icon(icon3);
        //3.将覆盖物显示到地图界面
        Marker marker = (Marker) baiduMap.addOverlay(options);
//        Marker marker2 = (Marker) baiduMap.addOverlay(options2);
//        Marker marker3 = (Marker) baiduMap.addOverlay(options3);
//        marker.setTitle("天安门");
        Bundle bundle = new Bundle();
        bundle.putString("title", "师活超市");
        marker.setExtraInfo(bundle);
//        Bundle bundle2 = new Bundle();
//        bundle2.putString("title", "国培超市");
//        marker2.setExtraInfo(bundle2);
//        Bundle bundle3 = new Bundle();
//        bundle3.putString("title", "图书馆");
//        marker3.setExtraInfo(bundle3);
    }

}
