package com.example.androidzonghe1.Fragment.yyWork;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.androidzonghe1.R;
import com.example.androidzonghe1.adapter.yyWork.MyViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

//第一个页面内容对应的Fragment类
public class FirstFragment extends Fragment {
    private CoordinatorLayout cl;
    //    private Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    //Bundle两个不同页面之间传递数据用
    //LayoutInflater加载布局文件
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_layout,
                container,
                false);
        cl = view.findViewById(R.id.android_main);
//        toolbar = findViewById(R.id.tool_bar);
        viewPager = view.findViewById(R.id.view_page);
        tabLayout = view.findViewById(R.id.tab_layout);
        initUiAndData();
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initUiAndData() {
//        toolbar.setTitleTextColor(Color.WHITE);
//        setSupportActionBar(toolbar);
        MyViewPagerAdapter adapter = new MyViewPagerAdapter(getFragmentManager());
        adapter.addFragment(new MyFragment(),"杨颖");
        adapter.addFragment(new MyFragment(),"周九良");
        adapter.addFragment(new MyFragment(),"尚九熙");
        viewPager.setAdapter(adapter);
        tabLayout.addTab(tabLayout.newTab().setText("one_"));
        tabLayout.addTab(tabLayout.newTab().setText("two"));
        tabLayout.addTab(tabLayout.newTab().setText("three"));
        tabLayout.setupWithViewPager(viewPager);
    }

}
