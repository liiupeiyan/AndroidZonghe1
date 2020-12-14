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

public class MyAppIntro2 extends AppIntro2 {
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addSlide(SampleSlide.newInstantce(R.layout.sample_slide, R.drawable.create1));
        addSlide(SampleSlide.newInstantce(R.layout.sample_slide, R.drawable.create2));
        addSlide(SampleSlide.newInstantce(R.layout.sample_slide, R.drawable.create3));
        addSlide(SampleSlide.newInstantce(R.layout.sample_slide, R.drawable.create4));
        addSlide(SampleSlide.newInstantce(R.layout.sample_slide, R.drawable.create5));

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
