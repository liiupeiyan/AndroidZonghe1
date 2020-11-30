package com.example.androidzonghe1.Fragment.lsbWork;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.androidzonghe1.R;
import com.panxw.android.imageindicator.AutoPlayManager;
import com.panxw.android.imageindicator.ImageIndicatorView;

public class SampleSlide extends Fragment {
    private static final String ARG_LAYOUT_RES_ID = "layoutResId";
    private int layoutResId;
    public static SampleSlide newInstantce(int layoutResId){
        SampleSlide sampleSlide = new SampleSlide();
        Bundle args = new Bundle();
        args.putInt(ARG_LAYOUT_RES_ID, layoutResId);
        //fragment保存参数，传入一个Bundle对象
        sampleSlide.setArguments(args);
        return sampleSlide;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null){
            //取出保存的值
            layoutResId = getArguments().getInt(ARG_LAYOUT_RES_ID);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(layoutResId, container, false);
        ImageIndicatorView imageIndicatorView = view.findViewById(R.id.indicate_view);
        Integer[] imgs = new Integer[5];
        for (int i = 1; i <= 5; i++) {
            int id = getResources().getIdentifier("create" + i, "drawable", getActivity().getPackageName());
            imgs[i - 1] = id;
        }
        imageIndicatorView.setupLayoutByDrawable(imgs);

        imageIndicatorView.setIndicateStyle(ImageIndicatorView.INDICATE_USERGUIDE_STYLE);

        imageIndicatorView.show();

        final AutoPlayManager autoPlayManager = new AutoPlayManager(imageIndicatorView);

        autoPlayManager.setBroadcastEnable(true);

        autoPlayManager.setBroadcastTimeIntevel(3000, 3000);

        autoPlayManager.loop();

        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
//        Log.e("SampleSlide", "=========savedInstanceState ");
    }
}
