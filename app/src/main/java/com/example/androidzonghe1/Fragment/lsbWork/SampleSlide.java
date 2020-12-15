package com.example.androidzonghe1.Fragment.lsbWork;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.androidzonghe1.R;

public class SampleSlide extends Fragment {

    private int layoutResId;
    private int drawableId;

    public static SampleSlide newInstantce(int layoutResId, int drawableId){
        SampleSlide sampleSlide = new SampleSlide();
        Bundle args = new Bundle();
        args.putInt("layoutResId", layoutResId);
        args.putInt("drawableId", drawableId);
        sampleSlide.setArguments(args);
        return sampleSlide;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null){
            layoutResId = getArguments().getInt("layoutResId");
            drawableId = getArguments().getInt("drawableId");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(layoutResId, container, false);
        ImageView img = view.findViewById(R.id.img);
        img.setImageResource(drawableId);
        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
