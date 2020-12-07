package com.example.androidzonghe1.Fragment.lpyWork;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
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
import com.baidu.mapapi.search.core.RouteLine;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRouteLine;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.IndoorRouteResult;
import com.baidu.mapapi.search.route.MassTransitRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.example.androidzonghe1.ConfigUtil;
import com.example.androidzonghe1.JavaClass.lpyWork.DrivingRouteOverlay;
import com.example.androidzonghe1.JavaClass.lpyWork.OverlayManager;
import com.example.androidzonghe1.R;
import com.example.androidzonghe1.activity.lpyWork.RoutePlanDemo;
import com.example.androidzonghe1.activity.lsbWork.SearchActivity;
import com.example.androidzonghe1.activity.yyWork.CaculateDistance;
import com.example.androidzonghe1.activity.yyWork.OrderDetailsActivity;
import com.example.androidzonghe1.JavaClass.lpyWork.WalkingRouteOverlay;
import com.example.androidzonghe1.adapter.lpyWork.RouteLineAdapter;

import java.util.List;

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
    RoutePlanSearch mSearch = null;
    private PlanNode stNode;//路线规划起点
    private PlanNode edNode;//路线规划终点
    private SuggestionResult.SuggestionInfo stSuggestionInfo;//起点位置信息
    private SuggestionResult.SuggestionInfo enSuggestionInfo;//终点位置信息
    private int nodeIndex = -1; // 节点索引,供浏览节点时使用
    DrivingRouteResult nowResultdrive = null;
    boolean hasShownDialogue = false;
    RouteLine route = null;
    OverlayManager routeOverlay = null;


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

        //路线规划
        mSearch = RoutePlanSearch.newInstance();
        mSearch.setOnGetRoutePlanResultListener(listener);
        return view;
    }

    OnGetRoutePlanResultListener listener  = new OnGetRoutePlanResultListener() {
        @Override
        public void onGetWalkingRouteResult(WalkingRouteResult walkingRouteResult) {
            //创建WalkingRouteOverlay实例
            WalkingRouteOverlay overlay = new WalkingRouteOverlay(baiduMap);
            if (walkingRouteResult.getRouteLines().size() > 0) {
                //获取路径规划数据,(以返回的第一条数据为例)
                //为WalkingRouteOverlay实例设置路径数据
                overlay.setData(walkingRouteResult.getRouteLines().get(0));
                //在地图上绘制WalkingRouteOverlay
                overlay.addToMap();

            }
        }

        @Override
        public void onGetTransitRouteResult(TransitRouteResult transitRouteResult) {

        }

        @Override
        public void onGetMassTransitRouteResult(MassTransitRouteResult massTransitRouteResult) {

        }

//        @Override
//        public void onGetDrivingRouteResult(DrivingRouteResult drivingRouteResult) {
//            if (drivingRouteResult == null || drivingRouteResult.error !=   SearchResult.ERRORNO.NO_ERROR) {
//                Toast.makeText(getContext(), "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
//            }
//            if (drivingRouteResult.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
//                // 起终点或途经点地址有岐义，通过以下接口获取建议查询信息
//                drivingRouteResult.getSuggestAddrInfo();
//                return;
//            }
//            if (drivingRouteResult.error == SearchResult.ERRORNO.NO_ERROR) {
//                if (drivingRouteResult.getRouteLines().size() >= 1) {
//                        DrivingRouteLine route = drivingRouteResult.getRouteLines().get(0);
//                    DrivingRouteOverlay overlay = new DrivingRouteOverlay(baiduMap);
////                    routeOverlay = overlay;
//                    baiduMap.setOnMarkerClickListener(overlay);
//                    overlay.setData(drivingRouteResult.getRouteLines().get(0));
//                    overlay.addToMap();
//                    overlay.zoomToSpan();
//                } else {
//                    Log.d("route result", "结果数<0");
//                    return;
//                }
//            }
//        }

        @Override
        public void onGetDrivingRouteResult(DrivingRouteResult result) {
            if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                Toast.makeText(getContext(), "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
            }
            if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
                // 起终点或途经点地址有岐义，通过以下接口获取建议查询信息
                // result.getSuggestAddrInfo()
                return;
            }
            if (result.error == SearchResult.ERRORNO.NO_ERROR) {
                nodeIndex = -1;

                Log.e("routeLine",result.getRouteLines().size()+"");
                if (result.getRouteLines().size() > 1) {
                    nowResultdrive = result;
                    if (!hasShownDialogue) {
                        MyTransitDlg myTransitDlg = new MyTransitDlg(getContext(),
                                result.getRouteLines(),
                                RouteLineAdapter.Type.DRIVING_ROUTE);
                        myTransitDlg.setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                hasShownDialogue = false;
                            }
                        });
                        myTransitDlg.setOnItemInDlgClickLinster(new OnItemInDlgClickListener() {
                            public void onItemClick(int position) {
                                route = nowResultdrive.getRouteLines().get(position);
                                DrivingRouteOverlay overlay = new DrivingRouteOverlay(baiduMap);
                                baiduMap.setOnMarkerClickListener(overlay);
                                routeOverlay = overlay;
                                overlay.setData(nowResultdrive.getRouteLines().get(position));
                                overlay.addToMap();
                                overlay.zoomToSpan();
                                overlay.onRouteNodeClick(position);
                            }

                        });
                        myTransitDlg.show();
                        hasShownDialogue = true;
                    }
                } else if (result.getRouteLines().size() == 1) {
                    route = result.getRouteLines().get(0);
                    DrivingRouteOverlay overlay = new DrivingRouteOverlay(baiduMap);
                    routeOverlay = overlay;
                    baiduMap.setOnMarkerClickListener(overlay);
                    overlay.setData(result.getRouteLines().get(0));
                    overlay.addToMap();
                    overlay.zoomToSpan();
//                    mBtnPre.setVisibility(View.VISIBLE);
//                    mBtnNext.setVisibility(View.VISIBLE);
                } else {
                    Log.d("route result", "结果数<0");
                    return;
                }

            }
        }



        @Override
        public void onGetIndoorRouteResult(IndoorRouteResult indoorRouteResult) {

        }

        @Override
        public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult) {

        }
    };


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("FragmentLaunchRoute", "requestCode:" + requestCode + "\tresultCode:" + resultCode);
        switch (requestCode){
            case END_CODE:
                if (resultCode == 0){
                    enSuggestionInfo = data.getExtras().getParcelable("suggestionInfo");
                    Log.e("FragmentLaunchRoute", "suggestionInfo" + enSuggestionInfo.toString());
                    btnEnd.setText(enSuggestionInfo.key);
                    addMarkerOverlay(END_CODE, enSuggestionInfo);
                    String pt = enSuggestionInfo.pt.toString();
                    setPosition(enSuggestionInfo.getPt().latitude,enSuggestionInfo.getPt().longitude);
                    //修改比例尺
//                    MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(15.0f);
//                    baiduMap.setMapStatus(msu);
                    addMarkerOverLay(enSuggestionInfo.getPt().latitude,enSuggestionInfo.getPt().longitude,1);
                    edNode = PlanNode.withCityNameAndPlaceName(enSuggestionInfo.city,enSuggestionInfo.key);

//                    MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(16.0f);
//                    baiduMap.setMapStatus(msu);
//                    baiduMap.setMaxAndMinZoomLevel(19,13);
                    //显示发起新路线按钮
                    if(!btnStart.getText().toString().equals("请输入孩子上车地点") && !btnEnd.getText().toString().equals("请输入终点")){
                        order.setVisibility(View.VISIBLE); //设置按钮为可见
                        if (stNode.getName().equals(edNode.getName())){
                            Toast.makeText(getContext(), "起点和终点不能一致", Toast.LENGTH_SHORT).show();
                            order.setVisibility(View.GONE);
                        } else {
                            // 驾车路线
                            mSearch.drivingSearch((new DrivingRoutePlanOption())
                                    .from(stNode)
                                    .to(edNode));
                            //步行路线
//                        mSearch.walkingSearch((new WalkingRoutePlanOption())
//                                .from(stNode)
//                                .to(edNode));
                        }
                        order.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //跳转到OrderDetailsActivity
                                Intent intent = new Intent(getContext(), OrderDetailsActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("stName",stNode.getName());
                                bundle.putString("enName",edNode.getName());
                                //算出两点经纬度计算距离
                                double distance =  CaculateDistance.GetDistance(stSuggestionInfo.getPt().latitude,stSuggestionInfo.getPt().longitude,enSuggestionInfo.getPt().latitude,enSuggestionInfo.getPt().longitude);
                                bundle.putDouble("distance",distance);
                                intent.putExtra("lrInfo",bundle);
                                ConfigUtil.flagOrder = true;
                                startActivity(intent);
                            }
                        });
                    }
                } else if (requestCode == -1){//未选中任何地点

                }
                break;
            case START_CODE:
                if (resultCode == 0){
                    stSuggestionInfo = data.getExtras().getParcelable("suggestionInfo");
                    Log.e("FragmentLaunchRoute", "suggestionInfo" +  stSuggestionInfo.toString());

                    btnStart.setText( stSuggestionInfo.key);
                    addMarkerOverlay(START_CODE,  stSuggestionInfo);
                    String pt =  stSuggestionInfo.pt.toString();
                    setPosition( stSuggestionInfo.getPt().latitude, stSuggestionInfo.getPt().longitude);
                    //修改比例尺
//                    MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(16.0f);
//                    baiduMap.setMapStatus(msu);
//                    baiduMap.setMaxAndMinZoomLevel(19,13);
                    addMarkerOverLay( stSuggestionInfo.getPt().latitude, stSuggestionInfo.getPt().longitude,0);
                    stNode = PlanNode.withCityNameAndPlaceName( stSuggestionInfo.city, stSuggestionInfo.key);
                    //显示发起新路线按钮
                    if(!btnStart.getText().toString().equals("请输入孩子上车地点")&&!btnEnd.getText().toString().equals("请输入终点")){
                        order.setVisibility(View.VISIBLE); //设置按钮为可见的
                        if (stNode.getName().equals(edNode.getName())){
                            Toast.makeText(getContext(), "起点和终点不能一致", Toast.LENGTH_SHORT).show();
                        } else {
                            //驾车路线
                            mSearch.drivingSearch((new DrivingRoutePlanOption())
                                    .from(stNode)
                                    .to(edNode));
//                        //步行路线
//                        mSearch.walkingSearch((new WalkingRoutePlanOption())
//                                .from(stNode)
//                                .to(edNode));
                        }
                        order.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //跳转到OrderDetailsActivity
                                Intent intent = new Intent(getContext(), OrderDetailsActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("stName",stNode.getName());
                                bundle.putString("enName",edNode.getName());
                                //算出两点经纬度计算距离
                                double distance =  CaculateDistance.GetDistance(stSuggestionInfo.getPt().latitude,stSuggestionInfo.getPt().longitude,enSuggestionInfo.getPt().latitude,enSuggestionInfo.getPt().longitude);
                                bundle.putDouble("distance",distance);
                                intent.putExtra("lrInfo",bundle);
                                ConfigUtil.flagOrder = true;
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
    public void addMarkerOverLay(double latitude,double longitude,int flag){
        BitmapDescriptor icon = null;
        if (flag == 0){
            icon = BitmapDescriptorFactory.fromResource(R.drawable.icon_st_map);
        } else if(flag == 1){
            icon = BitmapDescriptorFactory.fromResource(R.drawable.icon_en_map);
        }

//        BitmapDescriptor icon2 = BitmapDescriptorFactory.fromResource(R.drawable.boy2);
//        BitmapDescriptor icon3 = BitmapDescriptorFactory.fromResource(R.drawable.boy3);
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mSearch.destroy();
    }

    // 响应DLg中的List item 点击
    interface OnItemInDlgClickListener {
        public void onItemClick(int position);
    }

    // 供路线选择的Dialog
    class MyTransitDlg extends Dialog {

        private List<? extends RouteLine> mtransitRouteLines;
        private ListView transitRouteList;
        private RouteLineAdapter mTransitAdapter;

        OnItemInDlgClickListener onItemInDlgClickListener;

        public MyTransitDlg(Context context, int theme) {
        super(context, theme);
    }

        public MyTransitDlg(Context context, List<? extends RouteLine> transitRouteLines, RouteLineAdapter.Type
        type) {
        this(context, 0);
        mtransitRouteLines = transitRouteLines;
        mTransitAdapter = new RouteLineAdapter(context, mtransitRouteLines, type);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

        @Override
        public void setOnDismissListener(DialogInterface.OnDismissListener listener) {
        super.setOnDismissListener(listener);
    }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transit_dialog);

        transitRouteList = (ListView) findViewById(R.id.transitList);
        transitRouteList.setAdapter(mTransitAdapter);

        transitRouteList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onItemInDlgClickListener.onItemClick(position);
//                mBtnPre.setVisibility(View.VISIBLE);
//                mBtnNext.setVisibility(View.VISIBLE);
                dismiss();
                hasShownDialogue = false;
            }
        });
    }

        public void setOnItemInDlgClickLinster(OnItemInDlgClickListener itemListener) {
        onItemInDlgClickListener = itemListener;
    }

    }
}
