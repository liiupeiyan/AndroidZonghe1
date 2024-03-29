package com.example.androidzonghe1.activity.yjWork;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidzonghe1.ConfigUtil;
import com.example.androidzonghe1.R;
import com.example.androidzonghe1.Utils.yjWork.Utils;
import com.example.androidzonghe1.activity.lpyWork.MyTheActivity;
import com.example.androidzonghe1.entity.yjWork.EditTextClearTools;
import com.mob.MobSDK;
import com.mob.OperationCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class ActivityLoginPage extends AppCompatActivity {
    private Button buttonCode;
    private EditText editTextPhoneNum;
    private String phoneNum,attention;
    private TextView tv_attention;
    private ImageView i,close;
    private EventHandler eh;
    private Handler handler =new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 1:
                    String s = (String) msg.obj;
                    Log.e("yj",s);
                    if (s.equals("Yes")){
                        //登陆界面，发送验证码
                        SMSSDK.getVerificationCode("86", phoneNum);
                        Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                        intent.putExtra("phoneNum",phoneNum);
                        startActivity(intent);
                    }else{
                        //注册界面
                        Intent intent = new Intent(getApplicationContext(),RegisterActivity.class);
                        intent.putExtra("phoneNum",phoneNum);
                        startActivity(intent);
                    }
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

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
            Intent intent = new Intent(getApplicationContext(), MyTheActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }


        editTextPhoneNum = findViewById(R.id.editTextPhoneNum);
        i = findViewById(R.id.del_phonenumber);
        close = findViewById(R.id.login_close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        buttonCode = findViewById(R.id.buttonCode);
        EditTextClearTools.addclerListener(editTextPhoneNum,i);
        attention ="登录即代表您已经同意《叮咚接送用户协议》、《叮咚接送专业接送信息平台服务协议》和《隐私政策》。";
        tv_attention = findViewById(R.id.attention);
        SpannableStringBuilder ssb = new SpannableStringBuilder(attention);
        InitAttention(ssb);
        eh = new EventHandler() {
            @Override
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE){
                    //回调完成
                    if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){
                        //获取验证码成功
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(ActivityLoginPage.this,"验证码已发送",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES){
                        Log.i("test","test");
                    }
                }else{
                    ((Throwable)data).printStackTrace();
                    Throwable throwable = (Throwable) data;
                    throwable.printStackTrace();
                    Log.i("1234",throwable.toString());
                    try {
                        JSONObject obj = new JSONObject(throwable.getMessage());
                        final String des = obj.optString("detail");
                        if (!TextUtils.isEmpty(des)){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(ActivityLoginPage.this,des,Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        //注册一个事件回调监听，用于处理SMSSDK接口请求的结果
        SMSSDK.registerEventHandler(eh);
        buttonCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneNum = editTextPhoneNum.getText().toString();
                if(!phoneNum.isEmpty()){
                    if(Utils.checkTel(phoneNum)){ //利用正则表达式获取检验手机号
                        //判断是否注册
                        JudgeIsRegister(ConfigUtil.xt+"JudgeIsRegisterServlet?tel="+phoneNum);
                    }else{
                        Toast.makeText(getApplicationContext(),"请输入有效的手机号",Toast.LENGTH_LONG).show();
                        return;
                    }
                }else {
                    Toast.makeText(getApplicationContext(),"请输入手机号",Toast.LENGTH_LONG).show();
                    return;
                }
            }
        });
    }
    // 使用完EventHandler需注销，否则可能出现内存泄漏
    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(eh);
    }
    //初始化自定义Text view 点击事件
    private void InitAttention(SpannableStringBuilder ssb){
        ClickableSpan cs1 = new MyClickableSpan("《叮咚接送用户协议》");
        ssb.setSpan(cs1, 10, 20, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ClickableSpan cs2 = new MyClickableSpan("《叮咚接送专业接送信息平台服务协议》");
        ssb.setSpan(cs2, 21, 39, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ClickableSpan cs3 = new MyClickableSpan("《隐私政策》");
        ssb.setSpan(cs3, 40, 46, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_attention.setText(ssb);
        tv_attention.setMovementMethod(LinkMovementMethod.getInstance());
        tv_attention.setHighlightColor(Color.TRANSPARENT);
    }
    //自定义Text view 点击类
    class MyClickableSpan extends ClickableSpan{
        private String group;

        public MyClickableSpan(){}
        public MyClickableSpan(String group){
            this.group = group;
        }

        @Override
        public void updateDrawState(@NonNull TextPaint ds) {
            //设置颜色
            ds.setColor(ds.linkColor);
            //去除下划线
            ds.setUnderlineText(false);
        }

        @Override
        public void onClick(View widget) {
            switch (group){
                case "《叮咚接送用户协议》":
                    Intent intent1 = new Intent(getApplicationContext(),UserAgreementActivity.class);
                    startActivity(intent1);
                    break;
                case "《叮咚接送专业接送信息平台服务协议》":
                    Intent intent2 = new Intent(getApplicationContext(),ServiceAgreementActivity.class);
                    startActivity(intent2);
                    break;
                case "《隐私政策》":
                    Intent intent3 = new Intent(getApplicationContext(),PolicyActivity.class);
                    startActivity(intent3);
                    break;
            }
        }
    }
    private void submitPrivacyGrantResult(boolean granted) {
        MobSDK.submitPolicyGrantResult(granted, new OperationCallback<Void>() {
            @Override
            public void onComplete(Void data) {
                Log.d("xxhh", "隐私协议授权结果提交：成功");
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d("xxhh", "隐私协议授权结果提交：失败");
            }
        });
    }
    //判断是否已经注册
    private void JudgeIsRegister(final String s){
        new Thread(){
            @Override
            public void run() {
                try {
                    URL url = new URL(s);
                    URLConnection connection = url.openConnection();
                    InputStream inputStream = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream,"utf-8"));
                    String str=reader.readLine();
                    reader.close();
                    inputStream.close();
                    Message msg = new Message();
                    msg.what=1;
                    msg.obj=str;
                    Log.e("yj",str);
                    handler.sendMessage(msg);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }
}
