package com.example.androidzonghe1.Fragment.lpyWork;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager.widget.ViewPager;

import com.example.androidzonghe1.ConfigUtil;
import com.example.androidzonghe1.R;
import com.example.androidzonghe1.activity.lpyWork.ActivityMyMessage;
import com.example.androidzonghe1.activity.lsbWork.SearchActivity;
import com.example.androidzonghe1.adapter.lpyWork.ImageAdapter;
import com.example.androidzonghe1.adapter.lpyWork.MyViewPagerAdapter;
import com.example.androidzonghe1.entity.lpyWork.DataBean;
import com.google.android.material.tabs.TabLayout;
import com.youth.banner.Banner;
import com.youth.banner.indicator.CircleIndicator;

public class FragmentHomePage extends Fragment {
    private CoordinatorLayout coordinatorLayout;
    private ViewPager myViewPager;
    private TabLayout tab_layout;
    private Banner banner;
    private ImageView ivMessage;
    private Button et_search;
    private final int REQUEST_SEARCH_CODE = 100;
    private View view;

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
                Intent intent = new Intent(getActivity(), ActivityMyMessage.class);
                startActivity(intent);
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

    private void findViews(){
        coordinatorLayout = view.findViewById(R.id.fragment_home_page_main);
        myViewPager = view.findViewById(R.id.view_page_home_page);
        tab_layout = view.findViewById(R.id.tab_layout_home_page);
        banner = view.findViewById(R.id.banner);
        ivMessage = view.findViewById(R.id.mian_header_message);
        et_search = view.findViewById(R.id.main_header_search);
    }



    private void InitUiAndDatas(){
        //初始化viewPager的adapter代码
        MyViewPagerAdapter adapter = new MyViewPagerAdapter(getFragmentManager());
        //为Adapter添加Aapter和标题
//        if (ConfigUtil.trips.size() == 0){
//            adapter.addFragment(new FragmentNoDataDayTrip(),"今日行程");
//        } else {
//            adapter.addFragment(new FragmentDayTrip(),"今日行程");
//        }m
        adapter.addFragment(new FragmentDayTrip(),"今日行程");
        adapter.addFragment(new FragmentSameSchoolRoute(),"同校路线");
        adapter.addFragment(new FragmentSameSchoolParents(),"同校家长");
        if (ConfigUtil.drivers.size() == 0){
            adapter.addFragment(new FragmentNoDataDriver(),"接送员");
        } else {
            adapter.addFragment(new FragmentDriver(),"接送员");
        }
//        adapter.addFragment(new FragmentDriver(),"接送员");

        //为ViewPager绑定Adapter
        myViewPager.setAdapter(adapter);
        //为TabLayout添加标签，注意这里我们传入了标签名称，但demo运行时显示的标签名称并不是我们添加的，那么为什么呢？卖个官子...
        tab_layout.addTab(tab_layout.newTab().setText("今日行程").setIcon(R.drawable.trip4));
        tab_layout.addTab(tab_layout.newTab().setText("同校路线").setIcon(R.drawable.route2));
        tab_layout.addTab(tab_layout.newTab().setText("同校家长").setIcon(R.drawable.same_school));
        tab_layout.addTab(tab_layout.newTab().setText("接送员").setIcon(R.drawable.driver));
        //给tabLayout设置ViewPage，如果设置关联了Viewpage，那么ViewpagAdapter中getPageTitle返回的就是Tab上标题(上面疑问的回答)
        //为ViewPager 和Tablelayout进行绑定，从而实现滑动标签切换Fragment的目的
//        tab_layout.setupWithViewPager(myViewPager);
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
    }
}
