package com.example.androidzonghe1.others.lsbWork;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.androidzonghe1.Fragment.lsbWork.SampleSlide;
import com.example.androidzonghe1.MainActivity;
import com.example.androidzonghe1.R;
import com.github.paolorotolo.appintro.AppIntro2;
import com.github.paolorotolo.appintro.AppIntro2Fragment;

public class MyAppIntro2 extends AppIntro2 {
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //不能setContentView()

        addSlide(SampleSlide.newInstantce(R.layout.sample_slide));

        addSlide(AppIntro2Fragment.newInstance("title", "description", R.drawable.create1, getColor(R.color.white)));
        addSlide(AppIntro2Fragment.newInstance("title", "description", R.drawable.create2, getColor(R.color.black)));
        addSlide(AppIntro2Fragment.newInstance("title", "description", R.drawable.create3, getColor(R.color.purple_200)));
        addSlide(AppIntro2Fragment.newInstance("title", "description", R.drawable.create4, getColor(R.color.purple_500)));
        addSlide(AppIntro2Fragment.newInstance("title", "description", R.drawable.create5, getColor(R.color.purple_700)));

        //显示或隐藏状态栏
        showStatusBar(true);
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
    }
}
