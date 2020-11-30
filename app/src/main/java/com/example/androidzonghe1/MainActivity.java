package com.example.androidzonghe1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;

import androidx.appcompat.app.AppCompatActivity;


import com.example.androidzonghe1.activity.lpyWork.MyTheActivity;
import com.example.androidzonghe1.others.lsbWork.MyAppIntro2;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Boolean flag = sharedPreferences.getBoolean("flag", false);
        Log.e("StartActivity", "flag:" + flag);
        if (flag == false){
            flag = true;
            Log.e("StartActivity", "flag:" + flag);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("flag", flag);
            editor.apply();
            startActivity(new Intent(getApplicationContext(), MyAppIntro2.class));
            finish();
        }

        Log.e("StartActivity", "time1:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()).toString().trim());


    }

    /**
     * 屏蔽物理返回键
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK)
            return true;
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null){
            //删除handler所有消息和回调函数
            //当参数为null时,删除所有回调函数和message
            //这样做的好处是在Acticity退出的时候，可以避免内存泄露
            handler.removeCallbacksAndMessages(null);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.e("MainActivity", "time2:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()).toString().trim());
                Intent intent = new Intent(getApplicationContext(), MyTheActivity.class);
                startActivity(intent);
                finish();
            }
        }, 2000);
    }

}