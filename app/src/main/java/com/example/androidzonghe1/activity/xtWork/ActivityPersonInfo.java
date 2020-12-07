package com.example.androidzonghe1.activity.xtWork;

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
import android.renderscript.ScriptGroup;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.baidu.mapapi.search.sug.SuggestionResult;
import com.bumptech.glide.Glide;
import com.example.androidzonghe1.ConfigUtil;
import com.example.androidzonghe1.R;
import com.example.androidzonghe1.activity.lsbWork.SearchActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ThreadLocalRandom;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ActivityPersonInfo extends AppCompatActivity {
    private OkHttpClient okHttpClient;
    private LinearLayout llName;
    private TextView tvName;
    private Button btnAddressUsual;
    private Button btnAddressChild;
    private ImageView imgBack;
    private ImageView ivTou;
    private final int NAME_REQUEST_CODD = 1;
    private final int USUAL_ADDRESS_REQUESTCODD = 2;
    private final int CHILD_ADDRESS_REQUEST_CODD = 3;
    private String name;
    private String relation;
    private String str;
    private TextView tvPhone;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    ivTou.setImageBitmap((Bitmap) msg.obj);
                    break;
                case 2:
                    if(!str.equals("false")){
                        tvName.setText(str);
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.person_info_layout);
        okHttpClient = new OkHttpClient();
        tvPhone = findViewById(R.id.tv_gerenxinxi_phone);
        llName = findViewById(R.id.ll_personInfo_name);
        imgBack =findViewById(R.id.img_back);
        tvName =findViewById(R.id.tv_name);
        ivTou = findViewById(R.id.iv_toux);
        btnAddressChild = findViewById(R.id.et_gerenxinxi_child_adress);
        btnAddressUsual = findViewById(R.id.et_gerenxinxi_address_usually);
        //根据id获取图片
//        Log.e("parent",ConfigUtil.parent.getChild_name());
//        Log.e("parent",ConfigUtil.parent.getRelat());
//        tvName.setText(Html.fromHtml("<u>" + ConfigUtil.parent.getChild_name() + ConfigUtil.parent.getRelat() + "</u>"));

        tvPhone.setText(ConfigUtil.phone);
        getUserImage();
        getInfo();
        llName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityPersonInfo.this, ActivityChangeName.class);
                startActivityForResult(intent,NAME_REQUEST_CODD);
            }
        });
        btnAddressUsual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityPersonInfo.this, SearchActivity.class);
                startActivityForResult(intent,USUAL_ADDRESS_REQUESTCODD);
            }
        });
        btnAddressChild.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityPersonInfo.this, SearchActivity.class);
                startActivityForResult(intent,CHILD_ADDRESS_REQUEST_CODD);
            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() { //返回
            @Override
            public void onClick(View v) {
                Log.e("ActivityPersonInfo", "imgBack onClicked");
                Intent response = new Intent();
                response.putExtra("name",name+relation);
                setResult(1, response);
                finish();
            }
        });
        ivTou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(ActivityPersonInfo.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        100);
            }
        });
    }

    private void getInfo() {
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    URL url = new URL(ConfigUtil.xt+"ShowParentNameServlet?"+ConfigUtil.parent.getId());
                    InputStream inputStream = url.openStream();
                    int len = 0;
                    byte[] b=new byte[1024];
                    if((len = inputStream.read(b))!=-1){
                        str = new String(b,0,len,"UTF-8");
                    }
                    Log.e("获取家长信息结果",str);
                    Message message = new Message();
                    message.what =2;
                    handler.sendMessage(message);
                    inputStream.close();
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
            //打开图库
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
            Cursor cursor = contentResolver.query(uri,null,
                    null,null,null);
            if(cursor.moveToFirst()){
                String imagePath = cursor.getString(cursor.getColumnIndex("_data"));
                Glide.with(this)
                        .load(imagePath)
                        .into(ivTou);
                uploadFile(imagePath);
            }
        }
        switch (requestCode){
            case NAME_REQUEST_CODD:  //ActivityChangeName
                if (resultCode == 1){
                    name = data.getStringExtra("name");
                    relation = data.getStringExtra("relation");
                    tvName.setText(Html.fromHtml("<u>" + name + relation + "</u>"));
                    //根据手机号修改parent的child_name 和 parent_relat
                    updateParentRela();
                }
                break;
            case USUAL_ADDRESS_REQUESTCODD:
                if (resultCode == 0){
                    SuggestionResult.SuggestionInfo info = data.getExtras().getParcelable("suggestionInfo");
                    Log.e("suggestionInfo",info.toString());
                    String schoolName =  info.key;
                    btnAddressUsual.setText(schoolName);
//                    tvSchoolName.setTextSize(18);
                }
                break;
            case CHILD_ADDRESS_REQUEST_CODD:
                if (resultCode == 0){
                    SuggestionResult.SuggestionInfo info = data.getExtras().getParcelable("suggestionInfo");
                    Log.e("suggestionInfo",info.toString());
                    String schoolName =  info.key;
                    btnAddressChild.setText(schoolName);
                }
                break;
        }
    }

    private void uploadFile(String imagePath) {
        File file = new File(imagePath);
        //2.创建请求体对象
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/octet-stream"),file);
        //3.创建请求对象
        Request request = new Request.Builder()
                .post(requestBody)  //使用post请求方法
                .url(ConfigUtil.xt+"UpdateParentImageServlet?phone="+ConfigUtil.phone)
                .build();
        //4.创建Call对象
        Call call = okHttpClient.newCall(request);
        //异步
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.e("上传文件结果",response.body().string());
            }
        });
    }

    private String jsonStr(){
        JSONObject object = new JSONObject();
        try {
            object.put("phone",ConfigUtil.phone);
            object.put("child",name);
            object.put("relation",relation);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object.toString();
    }

    private void updateParentRela() {
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    URL url = new URL(ConfigUtil.xt+"UpdateParentNameServlet");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    OutputStream os = connection.getOutputStream();
                    os.write(jsonStr().getBytes());
                    InputStream is = connection.getInputStream();
                    int len = 0;
                    byte[] bytes = new byte[512];
                    str = new String(bytes,0,len);
                    if(str.equals("true")){
                        //修改成功
                        ConfigUtil.parent.setChild_name(name);
                        ConfigUtil.parent.setRelat(relation);
                        Log.e("1parent",ConfigUtil.parent.getChild_name());
                        Log.e("1parent",ConfigUtil.parent.getRelat());
                    }
                    Log.e("结果",str);
                    os.close();
                    is.close();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    private void getUserImage() {
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    URL url = new URL(ConfigUtil.xt+"ShowParentImageServlet?id="+ConfigUtil.parent.getId());
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    InputStream is = connection.getInputStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    Message msg = new Message();
                    msg.obj = bitmap;
                    msg.what=1;
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
}
