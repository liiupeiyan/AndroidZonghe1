package com.example.androidzonghe1.adapter.lpyWork;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MyViewPagerAdapter extends FragmentPagerAdapter {
    private final List<Fragment> mFragments = new ArrayList<>();
    private final List<String> fragmentTitles = new ArrayList<>();
    public MyViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }
    public void addFragment(Fragment fragment,String title){
        mFragments.add(fragment);
        fragmentTitles.add(title);
    }
    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }
    @Override
    public int getCount() {
        return mFragments.size();
    }
    @Override
    public CharSequence getPageTitle(int position) {
        //这里返回的标题就是TabLayout的标题
        return fragmentTitles.get(position);
    }
}
