package com.example.androidzonghe1;

import android.app.Application;
import android.os.Handler;

import androidx.multidex.MultiDex;

import com.baidu.mapapi.SDKInitializer;
import com.example.androidzonghe1.Utils.lsbWork.ScreenUtils;
import com.example.androidzonghe1.Utils.lsbWork.ToastUtils;

public class MyApplication extends Application {
    Handler handler;
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化百度地图
//        SDKInitializer.initialize(this);
        init();
        MultiDex.install(this);
    }

    private void init() {
        ToastUtils.init(this);
        ScreenUtils.init(this);
    }

    public void setHandler(Handler handler){
        this.handler = handler;
    }
    public Handler getHandler(){
        return handler;
    }
}
