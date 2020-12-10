package com.example.androidzonghe1.activity.yyWork;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ListView;

import com.example.androidzonghe1.ConfigUtil;
import com.example.androidzonghe1.R;
import com.example.androidzonghe1.adapter.yyWork.MyCommitAdapter;
import com.example.androidzonghe1.entity.yyWork.Commit;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MyCommitActivity extends AppCompatActivity {
    private List<Commit> commits = new ArrayList<>();
    private MyCommitAdapter myCommitAdapter;
    private ListView listView;
    private Thread thread;
    private StringBuffer stringBuffer;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    myCommitAdapter = new MyCommitAdapter(MyCommitActivity.this,R.layout.commit_item,commits);
                    listView.setAdapter(myCommitAdapter);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_commit);
        getCommits();
        listView = findViewById(R.id.listview);
    }

    private void getCommits() {
        thread=new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    URL url = new URL(ConfigUtil.xt+"ShowALLDiscussServlet?id="+ConfigUtil.parent.getId());
                    InputStream is = url.openStream();
                    int len = 0;
                    byte[] b = new byte[1024];
                    stringBuffer = new StringBuffer();
                    while((len = is.read(b))!=-1){
                        String str = new String(b,0,len,"UTF-8");
                        stringBuffer.append(str);
                    }
                    Log.e("查询所有评论的结果",stringBuffer.toString());
                    JSONArray jsonArray = new JSONArray(stringBuffer.toString());
                    for(int i = 0;i<jsonArray.length();i++){
                        JSONObject object = new JSONObject(jsonArray.getJSONObject(i)+"");
                        Commit commit = new Commit();
                        commit.setId(object.getInt("id"));
                        commit.setContent(object.getString("content"));
                        commit.setDriver(object.getString("drivername"));
                        commit.setOrderName(object.getString("ordername"));
                        commit.setTime(object.getString("time"));
                        commit.setStar(object.getInt("star"));
                        commit.setUserName(object.getString("parentname"));
                        commits.add(commit);
                    }
                    Log.e("订单列表",commits.toString());
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
    }
}