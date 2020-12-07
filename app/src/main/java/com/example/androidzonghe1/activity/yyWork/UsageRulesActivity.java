package com.example.androidzonghe1.activity.yyWork;

import androidx.appcompat.widget.Toolbar;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.androidzonghe1.R;
import com.example.androidzonghe1.activity.lsbWork.BaseSlideFinishActivity;
import com.example.androidzonghe1.others.lsbWork.SlideFinishRelativeLayout;

public class UsageRulesActivity extends BaseSlideFinishActivity {
    private Toolbar toolbar;

    MyGestureListener myGestureListener;
    GestureDetector gestureDetector;
    final static String TAG = "MyGesture";

    int width;
    int height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usage_rules);
        setSlideMode(SlideFinishRelativeLayout.SlideMode.EDGD);
        enableSlideFinish(true);

        toolbar = findViewById(R.id.toolBar);

        if (getActionBar() != null){
            getActionBar().hide();
        }
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("UsageRulesActivity", "toolbar back onClicked");
                finish();
            }
        });

        WindowManager windowManager = getWindowManager();
        width = windowManager.getDefaultDisplay().getWidth();
        height = windowManager.getDefaultDisplay().getHeight();

        myGestureListener = new MyGestureListener();
        gestureDetector = new GestureDetector(this, myGestureListener);
        Log.e("UsageRulesActivity", "width:" + width + ", height:" + height);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    public class  MyGestureListener implements GestureDetector.OnGestureListener{

        @Override
        public boolean onDown(MotionEvent e) {
            Log.e("UsageRulesActivity", "onDown");
            Log.e("UsageRulesActivity", "X:" + e.getX());
            Log.e("UsageRulesActivity", "Y:" + e.getY());
            return false;
        }

        @Override
        public void onShowPress(MotionEvent e) {
            Log.e("UsageRulesActivity", "onShowPress");
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            Log.e("UsageRulesActivity", "onSingleTapUp");
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            Log.e("UsageRulesActivity", "onScroll");

            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            Log.e("UsageRulesActivity", "onLongPress");
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            Log.e("UsageRulesActivity", "onFling");
            if (e2.getX() - e1.getX() > 200){
                finish();
            }
            return false;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}