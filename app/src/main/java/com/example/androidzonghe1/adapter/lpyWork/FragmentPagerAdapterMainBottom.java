package com.example.androidzonghe1.adapter.lpyWork;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.androidzonghe1.Fragment.lpyWork.FragmentMainPage;
import com.example.androidzonghe1.R;

public class FragmentPagerAdapterMainBottom extends FragmentPagerAdapter {
    final int PAGE_COUNT = 3;
    private String tabTitles[] = new String[]{"TAB1","TAB2","TAB3"};
    private Context context;
    private Button btn;


    public View getTabView(int position){
        View view = LayoutInflater.from(context).inflate(R.layout.item_tablayout_bottom,null);

        TextView tv = (TextView) view.findViewById(R.id.tv_item_tablayout_bottom);
        tv.setText(tabTitles[position]);
        ImageView img = (ImageView) view.findViewById(R.id.iv_item_tablayout_bottom);
        //img.setImageResource(imageResId[position]);
        return view;
    }

    public FragmentPagerAdapterMainBottom(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }




    @NonNull
    @Override
    public Fragment getItem(int position) {
        return FragmentMainPage.newInstance(position+1);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }


}
