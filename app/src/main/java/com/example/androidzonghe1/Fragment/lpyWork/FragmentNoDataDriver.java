package com.example.androidzonghe1.Fragment.lpyWork;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.androidzonghe1.R;

public class FragmentNoDataDriver extends Fragment {
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_nodata_driver, container, false);
        findViews();
        //上拉刷新下拉加载更多的事件处理
        setListener();
        return view;
    }

    private void findViews(){
    }

    private void setListener(){

    }
}
