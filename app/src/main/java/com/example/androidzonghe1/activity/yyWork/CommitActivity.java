package com.example.androidzonghe1.activity.yyWork;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.androidzonghe1.ConfigUtil;
import com.example.androidzonghe1.R;
import com.example.androidzonghe1.activity.xtWork.ActivityPersonInfo;
import com.example.androidzonghe1.entity.lpyWork.Driver;
import com.example.androidzonghe1.entity.yyWork.DriverOrder;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import cn.smssdk.gui.IdentifyNumPage;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CommitActivity extends AppCompatActivity {
    private TextView tvStar;
    private OkHttpClient okHttpClient;
    private String driver;
    private ImageView ivAdd;
    private ImageView ivDriver;
    private TextView tvDriver;
    private TextView tvCar;
    private TextView tvPhone;
    private TextView tvStatus;
    private Button btnCall;
    private ImageView xig1;
    private ImageView xig2;
    private ImageView xig3;
    private ImageView xig4;
    private ImageView xig5;
    private EditText edtCommit;
    private Button btnSubmit;
    private StringBuffer stringBuffer;
    private int id;
    private int driverId;
    private int commitId;
    private int star;
    private String imagePath;
    private String str;
    private Thread thread;
    private Handler handler =new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    try {
                        JSONObject object = new JSONObject(stringBuffer.toString());
                        tvDriver.setText(object.getString("name"));
                        tvCar.setText(object.getString("car"));
                        tvPhone.setText(object.getString("phone"));
                        tvStatus.setText(object.getString("status"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    RequestOptions options = new RequestOptions().circleCrop();
                    Glide.with(CommitActivity.this)
                            .load(msg.obj)
                            .apply(options)
                            .into(ivDriver);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commit);
        okHttpClient = new OkHttpClient();
        Intent response = getIntent();
        driver = response.getStringExtra("driver");
        id = response.getIntExtra("id",0);
        getViews();
        tvDriver.setText(driver);
        getDriver();
        //显示头像
//        getDriverImage();
        //打电话
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(CommitActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        100);
            }
        });
        //处理星星
        xig1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //修改tvStar
                star = 1;
                tvStar.setText("非常差");
                xig1.setImageResource(R.drawable.xg2);
                xig2.setImageResource(R.drawable.xg1);
                xig3.setImageResource(R.drawable.xg1);
                xig4.setImageResource(R.drawable.xg1);
                xig5.setImageResource(R.drawable.xg1);
            }
        });
        xig2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                star = 2;
                tvStar.setText("差");
                xig1.setImageResource(R.drawable.xg2);
                xig2.setImageResource(R.drawable.xg2);
                xig3.setImageResource(R.drawable.xg1);
                xig4.setImageResource(R.drawable.xg1);
                xig5.setImageResource(R.drawable.xg1);
            }
        });
        xig3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                star = 3;
                tvStar.setText("一般");
                xig1.setImageResource(R.drawable.xg2);
                xig2.setImageResource(R.drawable.xg2);
                xig3.setImageResource(R.drawable.xg2);
                xig4.setImageResource(R.drawable.xg1);
                xig5.setImageResource(R.drawable.xg1);
            }
        });
        xig4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                star = 4;
                tvStar.setText("好");
                xig1.setImageResource(R.drawable.xg2);
                xig2.setImageResource(R.drawable.xg2);
                xig3.setImageResource(R.drawable.xg2);
                xig4.setImageResource(R.drawable.xg2);
                xig5.setImageResource(R.drawable.xg1);
            }
        });
        xig5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                star = 5;
                tvStar.setText("非常好");
                xig1.setImageResource(R.drawable.xg2);
                xig2.setImageResource(R.drawable.xg2);
                xig3.setImageResource(R.drawable.xg2);
                xig4.setImageResource(R.drawable.xg2);
                xig5.setImageResource(R.drawable.xg2);
            }
        });
        //提交按钮
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        //提交parent_id。driver_id。order_id。image。content。star(int类型)
                        try {
                            URL url = new URL(ConfigUtil.xt+"AddDiscuss");
                            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                            connection.setRequestMethod("POST");
                            OutputStream os = connection.getOutputStream();
                            JSONObject object = new JSONObject();
                            object.put("parentid",ConfigUtil.parent.getId());
                            object.put("driverid",driverId);
                            object.put("orderid",id);
                            object.put("content",edtCommit.getText());
                            object.put("star",star);
                            os.write(object.toString().getBytes());
                            InputStream is = connection.getInputStream();
                            int len = 0;
                            byte[] b = new byte[512];
                            if((len = is.read(b))!=-1){
                                str = new String(b,0,len);
                            }
                            if(!str.equals("false")){
                                commitId = Integer.parseInt(str);
                                Log.e("返回的评论id",commitId+"");
                                uploadFile(imagePath);
                            }
                            os.close();
                            is.close();
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }.start();
            }
        });
    }

    private void getDriverImage() {
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    try {
                        thread.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    URL url = new URL(ConfigUtil.xt+"GetDriverImageServlet?id="+driverId);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    InputStream is = connection.getInputStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    Message msg = new Message();
                    msg.obj = bitmap;
                    msg.what=2;
                    handler.sendMessage(msg);
                    is.close();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==100){
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent,200);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==200 && resultCode==RESULT_OK){
            ContentResolver contentResolver = getContentResolver();
            Uri uri = data.getData();
            Cursor cursor = contentResolver.query(uri,null,null,null,null);
            if(cursor.moveToFirst()){
                imagePath = cursor.getString(cursor.getColumnIndex("_data"));
                RequestOptions options = new RequestOptions().centerCrop();
                Glide.with(this)
                        .load(imagePath)
                        .apply(options)
                        .into(ivAdd);
//                uploadFile(imagePath);
            }
        }else if(requestCode == 10 && resultCode==20){
            finish();
        }
    }

    private void uploadFile(String imagePath) {
        //向服务端上传图片
        File file = new File(imagePath);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/octet-stream"),file);
        Request request = new Request.Builder()
                .post(requestBody)
                .url(ConfigUtil.xt+"AcceptDisscussImageServlet?id="+commitId)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                final String result = response.body().string();
                Log.e("上传文件的结果果果",result);
                if(result.trim().equals("true")){
                    //跳转到评论成功界面
                    Intent intent = new Intent(CommitActivity.this,SuccessCommitActivity.class);
                    startActivityForResult(intent,10);
                }
            }
        });
    }


    private void getDriver() {
        thread=new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    stringBuffer = new StringBuffer();
                    URL url = new URL(ConfigUtil.xt+"ShowDriverServlet?id="+id);
                    InputStream is = url.openStream();
                    int len = 0;
                    byte[] b = new byte[1024];
                    while ((len = is.read(b))!=-1){
                        String str = new String(b,0,len);
                        stringBuffer.append(str);
                    }
                    Log.e("获取司机信息的结果",stringBuffer.toString());
                    driverId = new JSONObject(stringBuffer.toString()).getInt("id");
                    Message message = new Message();
                    message.what = 1;
                    handler.sendMessage(message);
                    is.close();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
        getDriverImage();

    }

    private void getViews() {
        tvStar = findViewById(R.id.tv_star);
        ivAdd = findViewById(R.id.iv_add);
        ivDriver = findViewById(R.id.iv_si);
        tvDriver = findViewById(R.id.t_driver0);
        tvCar = findViewById(R.id.t_car);
        tvPhone = findViewById(R.id.t_phone);
        tvStatus = findViewById(R.id.t_status);
        btnCall = findViewById(R.id.btn_call);
        edtCommit = findViewById(R.id.edt_commit);
        btnSubmit = findViewById(R.id.btn_sub);
        xig1 = findViewById(R.id.xig1);
        xig2 = findViewById(R.id.xig2);
        xig3 = findViewById(R.id.xig3);
        xig4 = findViewById(R.id.xig4);
        xig5 = findViewById(R.id.xig5);
    }
}