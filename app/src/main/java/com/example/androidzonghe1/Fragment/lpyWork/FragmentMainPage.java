package com.example.androidzonghe1.Fragment.lpyWork;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.androidzonghe1.R;

public class FragmentMainPage extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";

    private int mPage;

    public static  FragmentMainPage newInstance(int page){
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        FragmentMainPage fragmentMainPage = new FragmentMainPage();
        fragmentMainPage.setArguments(args);
        return fragmentMainPage;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nodata_driver,container,false);
        return view;
    }
}
