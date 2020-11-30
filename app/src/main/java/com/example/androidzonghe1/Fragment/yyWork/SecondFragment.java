package com.example.androidzonghe1.Fragment.yyWork;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.example.androidzonghe1.R;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class SecondFragment extends Fragment {
    private GeoCoder mSearch;
    private MapView mapView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.map_layout,
                container,
                false);
        mapView = view.findViewById(R.id.map_view);
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
    public GeoPoint getGeoPointBystr(String str) {
        GeoPoint gpGeoPoint = null;
        if (str!=null) {
            Geocoder gc = new Geocoder(getContext(), Locale.CHINA);
            List<Address> addressList = null;
            try {
                addressList = gc.getFromLocationName(str, 5);
                if (addressList.size()>0) {
//                    Address address_temp = addressList.get(0);
                    //计算经纬度
                    double latitude=addressList.get(0).getLatitude()*1E6;
                    double longitude=addressList.get(0).getLongitude()*1E6;
                    Log.e("a","经度："+latitude);
                    Log.e("b","纬度："+longitude);
                    //生产GeoPoint
                    gpGeoPoint = new GeoPoint((int)latitude, (int)longitude);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return gpGeoPoint;
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
