package com.example.androidzonghe1.activity.yjWork;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.androidzonghe1.ConfigUtil;
import com.example.androidzonghe1.Fragment.lpyWork.FragmentMy;
import com.example.androidzonghe1.R;
import com.example.androidzonghe1.View.yjWork.VerifyCodeView;
import com.example.androidzonghe1.activity.lpyWork.MyTheActivity;

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

import static com.mob.MobSDK.getContext;

public class LoginActivity extends AppCompatActivity {
    private String phoneNum;
    private ImageView img_back;
    private TextView tv_phoneNum,tv_second;
    private VerifyCodeView verifyCodeView;
    private EventHandler eh;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 1:
                    String str = (String) msg.obj;
                    String[] all = str.split(":");
                    Intent intent = new Intent(getApplicationContext(), MyTheActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    ConfigUtil.isLogin = true;
                    ConfigUtil.phone = phoneNum;
                    ConfigUtil.userName = all[0];
                    ConfigUtil.parent.setName(all[0]);
                    String id = all[2];
                    ConfigUtil.parent.setId(Integer.parseInt(id));
                    ConfigUtil.pwd = all[1];
                    ConfigUtil.parent.setRelat(all[3]);
                    countDownTimer.cancel();
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        phoneNum = getIntent().getStringExtra("phoneNum");
        verifyCodeView = findViewById(R.id.verify_code_view);
        img_back = findViewById(R.id.back_login);
        tv_phoneNum = findViewById(R.id.login_tel);
        tv_second = findViewById(R.id.resend_code_second);
        StringBuilder sb = new StringBuilder(phoneNum);
        sb.insert(3," ");
        sb.insert(8," ");
        tv_phoneNum.setText(sb);
        countDownTimer.start();
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                countDownTimer.cancel();
                finish();
            }
        });
        eh = new EventHandler() {
            @Override
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE){
                    //回调完成
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        //提交验证码成功
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                                //GetParentId("http://192.168.43.232:8080/DingDong/LoginServlet?tel="+phoneNum);
                            }
                        });
                    }else if (event == SMSSDK.EVENT_GET_VOICE_VERIFICATION_CODE){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(LoginActivity.this,"语音验证发送",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES){
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
                                    Toast.makeText(LoginActivity.this,des,Toast.LENGTH_SHORT).show();
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

        verifyCodeView.setInputCompleteListener(new VerifyCodeView.InputCompleteListener() {
            @Override
            public void inputComplete() {
                SMSSDK.submitVerificationCode("86", phoneNum, verifyCodeView.getEditContent());
                InputMethodManager manager = ((InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE));
                manager.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
                GetParentId(ConfigUtil.xt+"LoginServlet?tel="+phoneNum);
            }

            @Override
            public void invalidContent() {

            }
        });
    }

    private CountDownTimer countDownTimer = new CountDownTimer(60*1000,1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            String value = String.valueOf((int) ( millisUntilFinished/ 1000));
            tv_second.setText(value);
        }

        @Override
        public void onFinish() {
            SMSSDK.getVerificationCode("86", phoneNum);
        }
    };

    private void GetParentId(final String s){
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
