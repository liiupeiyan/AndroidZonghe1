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
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.baidu.mapapi.map.MapView;
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
    EditText etEnd;
    EditText etStart;

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

        //获取控件引用
        rgMethod = view.findViewById(R.id.rg_method);
        rbMultiple = view.findViewById(R.id.rb_multiple);
        rbSingle = view.findViewById(R.id.rb_single);
        etEnd = view.findViewById(R.id.et_end);
        etStart = view.findViewById(R.id.et_start);

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

        etEnd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
//                Intent intentEnd = new Intent(getContext(), SearchActivity.class);
//                startActivityForResult(intentEnd, END_CODE);
            }
        });

        etStart.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
//                Intent intentEnd = new Intent(getContext(), SearchActivity.class);
//                startActivityForResult(intentEnd, END_CODE);
            }
        });



//        //    第一步，创建地理编码检索实例；
//        mSearch = GeoCoder.newInstance();
//        //    第二步，创建地理编码检索监听者；
//        OnGetGeoCoderResultListener listener = new OnGetGeoCoderResultListener() {
//            @Override
//            public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {
//                if (geoCodeResult == null || geoCodeResult.error != SearchResult.ERRORNO.NO_ERROR) {
//                    //没有检索到结果
//                    Log.e("结果","nulllllll");
//                }else {
//                    //获取地理编码结果
//                    Log.e("经度",geoCodeResult.getLocation().latitude+"");
//                    Log.e("纬度",geoCodeResult.getLocation().longitude+"");
//                }
//            }
//
//            @Override
//            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
//                if (reverseGeoCodeResult == null || reverseGeoCodeResult.error != SearchResult.ERRORNO.NO_ERROR) {
//                    //没有找到检索结果
//                }
//                //获取反向地理编码结果
//            }
//        };
//        //    第三步，设置地理编码检索监听者；
//        mSearch.setOnGetGeoCodeResultListener(listener);
//        //    第四步，发起地理编码检索；
//        mSearch.geocode(new GeoCodeOption().city("北京市").address("海淀区上地十街10号"));
//        //    第五步，释放地理编码检索实例；
//        mSearch.destroy();
//        Map<String,Double> map=LngAndLatUtil.getLngAndLat("北京故宫");
//        Log.e("经纬度：","(经度："+map.get("lng")+",纬度："+map.get("lat")+")");
//        GeoPoint point = new GeoPoint(getGeoPointBystr("北京天安门").getLatitudeE6(),getGeoPointBystr("北京天安门").getLongitudeE6());
//        Log.e("经纬度","(经度："+point.getLatitudeE6()+",纬度："+point.getLongitudeE6()+")");

//        getGeoPointBystr("上海市杨浦区四平路1239号");
        return view;
    }
//    public GeoPoint getGeoPointBystr(String str) {
//        GeoPoint gpGeoPoint = null;
//        if (str!=null) {
//            Geocoder gc = new Geocoder(getContext(), Locale.CHINA);
//            List<Address> addressList = null;
//            try {
//                addressList = gc.getFromLocationName(str, 5);
//                if (addressList.size()>0) {
////                    Address address_temp = addressList.get(0);
//                    //计算经纬度
//                    double latitude=addressList.get(0).getLatitude()*1E6;
//                    double longitude=addressList.get(0).getLongitude()*1E6;
//                    Log.e("a","经度："+latitude);
//                    Log.e("b","纬度："+longitude);
//                    //生产GeoPoint
//                    gpGeoPoint = new GeoPoint((int)latitude, (int)longitude);
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        return gpGeoPoint;
//    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case END_CODE:
                if (resultCode == 0){
                    SuggestionResult.SuggestionInfo suggestionInfo = data.getExtras().getParcelable("suggestionInfo");
                    Log.e("FragmentLaunchRoute", "suggestionInfo" + suggestionInfo.toString());
                }
                break;
            case START_CODE:
                if (resultCode == 0){
                    SuggestionResult.SuggestionInfo suggestionInfo = data.getExtras().getParcelable("suggestionInfo");
                    Log.e("FragmentLaunchRoute", "suggestionInfo" + suggestionInfo.toString());
                }
                break;
        }
    }

//    public void addOver

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
