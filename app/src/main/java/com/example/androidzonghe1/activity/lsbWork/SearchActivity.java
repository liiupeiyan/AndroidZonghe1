package com.example.androidzonghe1.activity.lsbWork;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;
import com.example.androidzonghe1.Fragment.lsbWork.CityFragment;
import com.example.androidzonghe1.Fragment.lsbWork.SiteFragment;
import com.example.androidzonghe1.MyApplication;
import com.example.androidzonghe1.R;

public class SearchActivity extends AppCompatActivity {

    EditText etCity;
    EditText etSite;
    Button btnCancel;
    FrameLayout fragment;

    static String site = "";//当前地点
    static String city = "";//当前城市
    static String location = "";//当前定位
    static String keyword = "";//site 搜索框内容
    static String cityText = "";//city搜索框内容

    SiteFragment siteFragment;
    CityFragment cityFragment;
    Bundle siteBundle;
    Bundle cityBundle;

    SuggestionSearch suggestionSearch;

    LocationClient locationClient;//定位

    public Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 1:
                    city = (String) msg.obj;
                    cityText = (String) msg.obj;
                    etCity.setText(city);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        suggestionSearch = SuggestionSearch.newInstance();
        setContentView(R.layout.activity_search);

        MyApplication myApplication = (MyApplication) getApplication();
        myApplication.setHandler(handler);

        locationClient = new LocationClient(getApplicationContext());
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(SearchActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            location();
        }

        etCity = findViewById(R.id.et_city);
        etSite = findViewById(R.id.et_site);
        btnCancel = findViewById(R.id.btn_cancel);
        fragment = findViewById(R.id.fragment);

        siteFragment = new SiteFragment();
        cityFragment = new CityFragment();
        siteBundle = new Bundle();
        cityBundle = new Bundle();

        etCity.setText(location);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment, siteFragment);
        fragmentTransaction.replace(R.id.fragment, cityFragment);
        fragmentTransaction.commit();


        btnCancel.setOnClickListener(v -> {
            cityFragment.onDestroy();
            finish();
        });

        etCity.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                etCity.setText("");
                etCity.setHint("城市名称");
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, cityFragment);
                fragmentTransaction.commit();
            }
        });
        etCity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!etCity.getText().toString().trim().equals(""))
                    cityText = etCity.getText().toString().trim();
                cityFragment.setSearchCityList(etCity.getText().toString().trim());
            }
        });
        etSite.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (cityText.equals("")){
                    etCity.setText(city);
                } else {
                    etCity.setText(cityText);
                }
                etCity.setHint("");
                Log.e("SearchActivity", "keyword:" + keyword + "\tcity:" + city);
                if (keyword == null || keyword.equals("")){
                    suggestionSearch.requestSuggestion(new SuggestionSearchOption().city(city).keyword(city));
                } else {
                    suggestionSearch.requestSuggestion(new SuggestionSearchOption().city(city).keyword(keyword));
                }
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, siteFragment);
                fragmentTransaction.commit();
            }
        });

        etSite.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                keyword = etSite.getText().toString().trim();
                Log.e("SearchActivity", "keyword:" + keyword + "\tcity:" + city);
                if (keyword == null || keyword.equals("")){
                    suggestionSearch.requestSuggestion(new SuggestionSearchOption().city(city).keyword(city));
                } else {
                    suggestionSearch.requestSuggestion(new SuggestionSearchOption().city(city).keyword(keyword));
                }
            }
        });

        suggestionSearch.setOnGetSuggestionResultListener(suggestionResult -> {
            siteBundle.putParcelable("suggestionResult", suggestionResult);
            siteFragment.setArguments(siteBundle);
            siteFragment.initListView();
        });

    }

    public void location(){
        LocationClientOption locationClientOption = new LocationClientOption();
        locationClientOption.setOpenGps(true);
        locationClientOption.setIsNeedAddress(true);//设置是否需要获取地址
        locationClientOption.setCoorType("bd0911");
        locationClientOption.setLocationMode(LocationClientOption.LocationMode.Battery_Saving);
        locationClient.setLocOption(locationClientOption);
        locationClient.registerLocationListener(new BDAbstractLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                location = bdLocation.getCity();
                cityFragment.setTvLocation(location);
            }
        });
        locationClient.start();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1 && grantResults[0] == 0){
            location();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
//        siteFragment.onStart();
//        cityFragment.onStart();
        if (!locationClient.isStarted()){
            locationClient.start();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        locationClient.stop();//停止定位
    }

    @Override
    protected void onResume() {
        super.onResume();
//        cityFragment.onResume();
//        siteFragment.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
//        cityFragment.onPause();
//        siteFragment.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        cityFragment.onDestroy();
//        siteFragment.onDestroy();
    }

}