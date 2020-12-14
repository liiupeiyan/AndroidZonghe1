package com.example.androidzonghe1.activity.lpyWork;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.androidzonghe1.ConfigUtil;
import com.example.androidzonghe1.Fragment.lpyWork.FragmentHomePage;
import com.example.androidzonghe1.Fragment.lpyWork.FragmentLaunchRoute;
import com.example.androidzonghe1.Fragment.lpyWork.FragmentMy;
import com.example.androidzonghe1.Fragment.lpyWork.FragmentTrack;
import com.example.androidzonghe1.R;
import com.example.androidzonghe1.activity.yjWork.ActivityLoginPage;
import com.example.androidzonghe1.activity.yjWork.RegisterActivity;
import com.example.androidzonghe1.entity.lpyWork.Driver;
import com.example.androidzonghe1.entity.rjxWork.History;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lanren.easydialog.AnimatorHelper;
import com.lanren.easydialog.DialogViewHolder;
import com.lanren.easydialog.EasyDialog;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;

public class MyTheActivity extends AppCompatActivity {

    private Fragment currentFragment = new Fragment();
    private FragmentHomePage fragmentHomePage;
    private FragmentLaunchRoute fragmentLaunchRoute;
    private FragmentTrack fragmentTrack;
    private FragmentMy fragmentMy;
    private Button btnHomePage;
    private Button btnLaunchRoute;
    private Button btnMy;
    private Button btnTrack;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 1:
                    String str = (String) msg.obj;
                    Gson gson = new Gson();
                    Type collection = new TypeToken<List<Driver>>() {}.getType();
                    ConfigUtil.drivers = gson.fromJson(str,collection);
                    break;
            }
        }
    };

//    private ViewPager viewPager;
//    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_the);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        findViews();
        fragmentHomePage = new FragmentHomePage();
        fragmentLaunchRoute = new FragmentLaunchRoute();
        fragmentMy = new FragmentMy();
        fragmentTrack = new FragmentTrack();
        Log.e("是否登录",ConfigUtil.isLogin+"");
        if(ConfigUtil.isLogin){
            //判断用户是否领券
            Boolean ticket = sharedPreferences.getBoolean("ticket", false);
            if (!ticket){
                //弹出优惠券
                new EasyDialog(this, R.layout.view_gift_card) {
                    @Override
                    public void onBindViewHolder(DialogViewHolder holder) {
                        ImageView imageView = holder.getView(R.id.img_close);
                        imageView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dismiss();
                            }
                        });
                        Button button = holder.getView(R.id.btn_get);
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //领取代金券
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putBoolean("ticket",true);
                                editor.apply();
                                dismiss();
                            }
                        });
                    }
                }.backgroundLight(0.2)
                        .setCanceledOnTouchOutside(false)
                        .setCancelAble(true)
                        .fromTopToMiddle()
                        .setCustomAnimations(AnimatorHelper.TOP_IN_ANIM, AnimatorHelper.TOP_OUT_ANIM)
                        .showDialog(true);
                sharedPreferences.edit().putBoolean("ticket",true);
                sharedPreferences.edit().commit();
            }
            changeTab(fragmentMy);
            tabMyInit();
            currentFragment = fragmentMy;
        }else {
            changeTab(fragmentHomePage);
            tabInt();
            currentFragment = fragmentHomePage;
        }
    }

    private void findViews(){
        btnHomePage = findViewById(R.id.home_page);
        btnLaunchRoute = findViewById(R.id.launch_route);
        btnMy = findViewById(R.id.my);
        btnTrack = findViewById(R.id.track);
    }

    private void changeTab(Fragment fragment){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        if(currentFragment != fragment){
            if(!fragment.isAdded()){
                transaction.add(R.id.tab_content,fragment);
            }
        }
        transaction.hide(currentFragment);
        transaction.show(fragment);
        transaction.commit();
        currentFragment = fragment;
    }

    public void tabClicked(View view) {
        switch (view.getId()){
            case R.id.home_page:
//                fragmentHomePage = new FragmentHomePage();
//                btnHomePage.setTextColor(Color.RED);
                changeTab(fragmentHomePage);
                btnHomePageClicked();
                break;
            case R.id.launch_route:
//                fragmentLaunchRoute = new FragmentLaunchRoute();
                changeTab(fragmentLaunchRoute);
                btnLaunchRouteClicked();
                break;
            case R.id.my:
//                fragmentMy = new FragmentMy();
                if(!ConfigUtil.isLogin){
                    changeTab(fragmentMy);
                    btnMyClicked();
                } else {
                    Intent intent = new Intent(getApplicationContext(), ActivityLoginPage.class);
                    startActivity(intent);
                }
                break;
            case R.id.track:
                changeTab(fragmentTrack);
                btnTrackClicked();
                break;
        }
    }

    private void tabInt(){
        btnHomePage.setTextColor(Color.BLACK);

        Drawable drawableHomePage = getResources().getDrawable(R.drawable.home_page_clicked);
        drawableHomePage.setBounds(0, 0, drawableHomePage.getMinimumWidth(), drawableHomePage.getMinimumHeight());
        btnHomePage.setCompoundDrawables(null,drawableHomePage,null,null);
    }

    private void tabMyInit(){
//        btnMy.
        btnMy.setTextColor(Color.BLACK);

        Drawable drawableHomePage = getResources().getDrawable(R.drawable.my_clicked);
        drawableHomePage.setBounds(0, 0, drawableHomePage.getMinimumWidth(), drawableHomePage.getMinimumHeight());
        btnMy.setCompoundDrawables(null,drawableHomePage,null,null);
    }

    private void btnHomePageClicked(){
        btnHomePage.setTextColor(Color.BLACK);
        Drawable drawableHomePage = getResources().getDrawable(R.drawable.home_page_clicked);
        drawableHomePage.setBounds(0, 0, drawableHomePage.getMinimumWidth(), drawableHomePage.getMinimumHeight());
        btnHomePage.setCompoundDrawables(null,drawableHomePage,null,null);

        btnLaunchRoute.setTextColor(getResources().getColor(R.color.dark_gray));
        Drawable drawableLaunch = getResources().getDrawable(R.drawable.launch_route);
        drawableLaunch.setBounds(0, 0, drawableLaunch.getMinimumWidth(), drawableLaunch.getMinimumHeight());
        btnLaunchRoute.setCompoundDrawables(null,drawableLaunch,null,null);

        btnMy.setTextColor(getResources().getColor(R.color.dark_gray));
        Drawable drawableMy1 = getResources().getDrawable(R.drawable.my);
        drawableMy1.setBounds(0, 0, drawableMy1.getMinimumWidth(), drawableMy1.getMinimumHeight());
        btnMy.setCompoundDrawables(null,drawableMy1,null,null);

        btnTrack.setTextColor(getResources().getColor(R.color.dark_gray));
        Drawable drawableTrack1 = getResources().getDrawable(R.drawable.track);
        drawableTrack1.setBounds(0,0,drawableTrack1.getMinimumWidth(),drawableTrack1.getMinimumHeight());
        btnTrack.setCompoundDrawables(null,drawableTrack1,null,null);
    }

    private void btnLaunchRouteClicked(){
        btnLaunchRoute.setTextColor(Color.BLACK);
        Drawable drawableLaunch = getResources().getDrawable(R.drawable.launch_route_clicked);
        drawableLaunch.setBounds(0, 0, drawableLaunch.getMinimumWidth(), drawableLaunch.getMinimumHeight());
        btnLaunchRoute.setCompoundDrawables(null,drawableLaunch,null,null);

        btnHomePage.setTextColor(getResources().getColor(R.color.dark_gray));
        Drawable drawableHomePage = getResources().getDrawable(R.drawable.home_page);
        drawableHomePage.setBounds(0, 0, drawableHomePage.getMinimumWidth(), drawableHomePage.getMinimumHeight());
        btnHomePage.setCompoundDrawables(null,drawableHomePage,null,null);

        btnMy.setTextColor(getResources().getColor(R.color.dark_gray));
        Drawable drawableMy2 = getResources().getDrawable(R.drawable.my);
        drawableMy2.setBounds(0, 0, drawableMy2.getMinimumWidth(), drawableMy2.getMinimumHeight());
        btnMy.setCompoundDrawables(null,drawableMy2,null,null);

        btnTrack.setTextColor(getResources().getColor(R.color.dark_gray));
        Drawable drawableTrack1 = getResources().getDrawable(R.drawable.track);
        drawableTrack1.setBounds(0,0,drawableTrack1.getMinimumWidth(),drawableTrack1.getMinimumHeight());
        btnTrack.setCompoundDrawables(null,drawableTrack1,null,null);
    }

    private void btnMyClicked(){
        btnMy.setTextColor(Color.BLACK);
        Drawable drawableMy = getResources().getDrawable(R.drawable.my_clicked);
        drawableMy.setBounds(0, 0, drawableMy.getMinimumWidth(), drawableMy.getMinimumHeight());
        btnMy.setCompoundDrawables(null,drawableMy,null,null);

        btnLaunchRoute.setTextColor(getResources().getColor(R.color.dark_gray));
        Drawable drawableLaunch = getResources().getDrawable(R.drawable.launch_route);
        drawableLaunch.setBounds(0, 0, drawableLaunch.getMinimumWidth(), drawableLaunch.getMinimumHeight());
        btnLaunchRoute.setCompoundDrawables(null,drawableLaunch,null,null);

        btnHomePage.setTextColor(getResources().getColor(R.color.dark_gray));
        Drawable drawableHomePage = getResources().getDrawable(R.drawable.home_page);
        drawableHomePage.setBounds(0, 0, drawableHomePage.getMinimumWidth(), drawableHomePage.getMinimumHeight());
        btnHomePage.setCompoundDrawables(null,drawableHomePage,null,null);

        btnTrack.setTextColor(getResources().getColor(R.color.dark_gray));
        Drawable drawableTrack1 = getResources().getDrawable(R.drawable.track);
        drawableTrack1.setBounds(0,0,drawableTrack1.getMinimumWidth(),drawableTrack1.getMinimumHeight());
        btnTrack.setCompoundDrawables(null,drawableTrack1,null,null);
    }

    private void btnTrackClicked(){
        btnTrack.setTextColor(Color.BLACK);
        Drawable drawableTrack = getResources().getDrawable(R.drawable.track_clicked);
        drawableTrack.setBounds(0,0,drawableTrack.getMinimumWidth(),drawableTrack.getMinimumHeight());
        btnTrack.setCompoundDrawables(null,drawableTrack,null,null);

        btnLaunchRoute.setTextColor(getResources().getColor(R.color.dark_gray));
        Drawable drawableLaunch = getResources().getDrawable(R.drawable.launch_route);
        drawableLaunch.setBounds(0, 0, drawableLaunch.getMinimumWidth(), drawableLaunch.getMinimumHeight());
        btnLaunchRoute.setCompoundDrawables(null,drawableLaunch,null,null);

        btnHomePage.setTextColor(getResources().getColor(R.color.dark_gray));
        Drawable drawableHomePage = getResources().getDrawable(R.drawable.home_page);
        drawableHomePage.setBounds(0, 0, drawableHomePage.getMinimumWidth(), drawableHomePage.getMinimumHeight());
        btnHomePage.setCompoundDrawables(null,drawableHomePage,null,null);

        btnMy.setTextColor(getResources().getColor(R.color.dark_gray));
        Drawable drawableMy2 = getResources().getDrawable(R.drawable.my);
        drawableMy2.setBounds(0, 0, drawableMy2.getMinimumWidth(), drawableMy2.getMinimumHeight());
        btnMy.setCompoundDrawables(null,drawableMy2,null,null);
    }

    public void getAllDrivers(String s){
        new Thread(){
            @Override
            public void run() {
                try {
                    URL url = new URL(s);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    //设置http请求方式，get、post、put、...(默认get请求)
                    connection.setRequestMethod("POST");//设置请求方式

                    //从服务器段获取响应
                    InputStream is = connection.getInputStream();
                    byte[] bytes = new byte[512];
                    int len = is.read(bytes);//将数据保存在bytes中，长度保存在len中
                    String resp = new String(bytes,0,len);
                    Log.e("所有司机",resp);

                    is.close();
//                    借助Message传递数据
                    Message message = new Message();
//                    设置Message对象的参数
                    message.what = 1;
                    message.obj = resp;
//                    发送Message
                    handler.sendMessage(message);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}