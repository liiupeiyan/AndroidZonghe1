package com.example.androidzonghe1.activity.lsbWork;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidzonghe1.ConfigUtil;
import com.example.androidzonghe1.R;
import com.example.androidzonghe1.adapter.lsbWork.ContactorAdapter;
import com.example.androidzonghe1.entity.xtWork.Contactor;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.zyyoona7.wheel.WheelView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ContactorActivity extends AppCompatActivity {

    private ArrayList<Contactor> contactors=new ArrayList<>();
    private ImageView imgBack;
    private RecyclerView recyclerView;
    private Button btnInsert;
    private ContactorAdapter contactorAdapter;
    private Handler handler= new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    contactorAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactor);
        recyclerView = findViewById(R.id.lv_contactor);
        imgBack = findViewById(R.id.img_back);
        btnInsert = findViewById(R.id.btn_insert);
        //准备数据源
        add();
        //点击选择关系
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        contactorAdapter = new ContactorAdapter(contactors,getApplicationContext());
        contactorAdapter.setOnItemClickListener(new ContactorAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position, Contactor data) {
                Log.e("ContactorActivity", "item onClick position:" + position);
                Button btnRelation = (Button) view;
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(ContactorActivity.this);
                View v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.relation_dialog, null);
                Button btnCancel = v.findViewById(R.id.btn_cancel);
                Button btnConfirm = v.findViewById(R.id.btn_confirm);
                WheelView<String> wheelView = v.findViewById(R.id.wheel_view);
                List<String> relations = new ArrayList<String>();
                relations.add("爸爸");
                relations.add("妈妈");
                relations.add("爷爷");
                relations.add("奶奶");
                relations.add("外公");
                relations.add("外婆");
                relations.add("叔叔");
                relations.add("阿姨");
                wheelView.setData(relations);
                wheelView.setTextSize(100);
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bottomSheetDialog.dismiss();
                    }
                });
                btnConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        ((Button) view).setText(wheelView.getSelectedItemData());
                        btnRelation.setText(wheelView.getSelectedItemData());
                        bottomSheetDialog.dismiss();
                    }
                });
                bottomSheetDialog.setContentView(v);
                bottomSheetDialog.setCancelable(true);
                bottomSheetDialog.setCanceledOnTouchOutside(true);
                bottomSheetDialog.show();
            }
        });
        recyclerView.setAdapter(contactorAdapter);
        //点击添加
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("ContactorActivity", "btnInsert onClick");
                contactorAdapter.insertData();
            }
        });
        //点击返回
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("COntactorActivity", "imgBack onClick");
                finish();
            }
        });
    }

    private void add() {
        new Thread(){
            @Override
            public void run() {
                try {
                    URL url=new URL(ConfigUtil.xt+"QueryContactorServlet");
                    //HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    //connection.setRequestMethod("POST");//设置请求方式
                    InputStream inputStream = url.openStream();
                    byte[] bytes = new byte[256];
                    int len=-1;
                    StringBuffer buffer=new StringBuffer();
                    while((len=inputStream.read(bytes))!=-1) {
                        buffer.append(new String(bytes,0,len));
                    }
                    String arr=buffer.toString();
                    JSONArray jsonArray= null;
                    jsonArray=new JSONArray(arr);
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        Contactor con=new Contactor();
                        con.setId(jsonObject.getInt("contactor_id"));
                        con.setRelat(jsonObject.getString("relat"));
                        con.setPhone(jsonObject.getString("contactor_phone"));
                        con.setChild_id(jsonObject.getInt("user_id"));
                        Log.e("hhhhhh",con.toString());
                        contactors.add(con);
                    }
                    Message message = new Message();
                    message.what = 1;
                    handler.sendMessage(message);
                    inputStream.close();
//                    Log.e("亲属",contactors.get(0).toString());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }

}