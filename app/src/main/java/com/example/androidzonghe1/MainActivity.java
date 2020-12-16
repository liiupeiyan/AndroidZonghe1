package com.example.androidzonghe1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;

import androidx.appcompat.app.AppCompatActivity;


import com.example.androidzonghe1.activity.Track.TrackApplication;
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
        Boolean isLogin = sharedPreferences.getBoolean("isLogin", false);
        if (isLogin) {
            String phone = sharedPreferences.getString("phone", "");
            String userName = sharedPreferences.getString("userName", "");
            String parentName = sharedPreferences.getString("parentName", "");
            int parentId = sharedPreferences.getInt("parentId", -1);
            String pwd = sharedPreferences.getString("pwd", "");
            String parentRelat = sharedPreferences.getString("parentRelat", "");
            ConfigUtil.isLogin = isLogin;
            ConfigUtil.phone = phone;
            ConfigUtil.userName = userName;
            ConfigUtil.parent.setName(parentName);
            ConfigUtil.parent.setId(parentId);
            ConfigUtil.pwd = pwd;
            ConfigUtil.parent.setRelat(parentRelat);
            TrackApplication.entityName = ConfigUtil.phone;
        }

        Boolean flag = sharedPreferences.getBoolean("flag", false);
        if (!flag){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("flag", true);
            editor.apply();
            startActivity(new Intent(getApplicationContext(), MyAppIntro2.class));
            finish();
        } else {
            handler.postDelayed(() -> {
                Intent intent = new Intent(getApplicationContext(), MyTheActivity.class);
                startActivity(intent);
                finish();
            }, 2000);
        }

    }

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
            handler.removeCallbacksAndMessages(null);
        }
    }

}