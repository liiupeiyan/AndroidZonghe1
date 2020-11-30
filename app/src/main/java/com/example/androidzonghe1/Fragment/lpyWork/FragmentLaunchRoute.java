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

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.example.androidzonghe1.R;
import com.example.androidzonghe1.activity.lsbWork.SearchActivity;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class FragmentLaunchRoute extends Fragment {

    private MapView mapView;
    RadioGroup rgMethod;
    RadioButton rbMultiple;
    RadioButton rbSingle;
    Button btnEnd;
    Button btnStart;
    BaiduMap baiduMap;
    final int END_CODE = 1;
    final int START_CODE = 2;

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

        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("FragmentLaunchRoute", "requestCode:" + requestCode + "\tresultCode:" + resultCode);
        switch (requestCode){
            case END_CODE:
                if (resultCode == 1){
                    SuggestionResult.SuggestionInfo suggestionInfo = data.getExtras().getParcelable("suggestionInfo");
                    Log.e("FragmentLaunchRoute", "suggestionInfo" + suggestionInfo.toString());
                    addMarkerOverlay(END_CODE, suggestionInfo);
                }
                break;
            case START_CODE:
                if (resultCode == 1){
                    SuggestionResult.SuggestionInfo suggestionInfo = data.getExtras().getParcelable("suggestionInfo");
                    Log.e("FragmentLaunchRoute", "suggestionInfo" + suggestionInfo.toString());
                    addMarkerOverlay(START_CODE, suggestionInfo);
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
}
