package com.example.androidzonghe1.Fragment.lpyWork;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.renderscript.ScriptGroup;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.androidzonghe1.ConfigUtil;
import com.example.androidzonghe1.R;
import com.example.androidzonghe1.activity.lsbWork.ContactorActivity;
import com.example.androidzonghe1.activity.lsbWork.KidsActivity;
import com.example.androidzonghe1.activity.rjxWork.CommandActivity;
import com.example.androidzonghe1.activity.rjxWork.TicketActivity;
import com.example.androidzonghe1.activity.xtWork.ActivityPersonInfo;
import com.example.androidzonghe1.activity.yyWork.ActivityNewRead;
import com.example.androidzonghe1.adapter.xtWork.RecycleAdapterFragmentMy;
import com.example.androidzonghe1.entity.xtWork.RvFragmentMy;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ThreadLocalRandom;

public class FragmentMy extends Fragment {
    private View view;
    private RecyclerView recyclerView;
    private RecycleAdapterFragmentMy adapter;
    private LinearLayout llName;
    private LinearLayout llMyChild;
    private LinearLayout llContact;
    private LinearLayout llAwardCommend;
    private LinearLayout llNewRead;
    private LinearLayout llNewGetTicket;
    private ImageView imageView;
    private TextView myName;
    private String str;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    RequestOptions options = new RequestOptions().circleCrop();
                    Glide.with(getContext())
                            .load(msg.obj)
                            .apply(options)
                            .into(imageView);
//                    imageView.setImageBitmap((Bitmap) msg.obj);
                    break;
                case 2:
                    if(!str.equals("false")){
                        myName.setText(str);
                    }
                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my, container, false);

        findViews();
        setOnClickedListener();
        return view;
    }

    private void findViews(){
        recyclerView = view.findViewById(R.id.rv_fragment_my);
        if (ConfigUtil.mys.size() == 0){
            RvFragmentMy my1 = new RvFragmentMy(R.drawable.trip,"我的行程",R.drawable.right_xt);
            RvFragmentMy my2 = new RvFragmentMy(R.drawable.order,"我的订单",R.drawable.right_xt);
            RvFragmentMy my3 = new RvFragmentMy(R.drawable.wallet,"我的钱包",R.drawable.right_xt);
            RvFragmentMy my4 = new RvFragmentMy(R.drawable.lianxi,"联系客服",R.drawable.phone_xt);
            RvFragmentMy my5 = new RvFragmentMy(R.drawable.setting,"设置",R.drawable.right_xt);
            RvFragmentMy my6 = new RvFragmentMy(R.drawable.question,"常见问题",R.drawable.right_xt);
            ConfigUtil.mys.add(my1);
            ConfigUtil.mys.add(my2);
            ConfigUtil.mys.add(my3);
            ConfigUtil.mys.add(my4);
            ConfigUtil.mys.add(my5);
            ConfigUtil.mys.add(my6);
        }
        adapter = new RecycleAdapterFragmentMy(ConfigUtil.mys);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        llMyChild = view.findViewById(R.id.ll_fragment_my_child);
        llName = view.findViewById(R.id.ll_fragment_name);
        llAwardCommend = view.findViewById(R.id.ll_fragment_award_recommend);
        llContact = view.findViewById(R.id.ll_fragment_my_contact);
        llNewRead = view.findViewById(R.id.ll_fragment_new_read);
        llNewGetTicket = view.findViewById(R.id.ll_fragment_new_get_ticket);
        myName = view.findViewById(R.id.my_name);
        imageView = view.findViewById(R.id.iv_img);
        if(ConfigUtil.isLogin){
            //根据id获取头像
            getUserImage();
            getInfo();
        }else {
            myName.setText("昵称");
            RequestOptions options = new RequestOptions().circleCrop();
            Glide.with(getContext())
                    .load(R.drawable.father)
                    .apply(options)
                    .into(imageView);
        }
    }

    private void getInfo() {
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    URL url = new URL(ConfigUtil.xt+"ShowParentNameServlet?id="+ConfigUtil.parent.getId());
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

    public void setOnClickedListener(){
        MyListener myListener = new MyListener();
        llName.setOnClickListener(myListener);
        llMyChild.setOnClickListener(myListener);
        llContact.setOnClickListener(myListener);
        llAwardCommend.setOnClickListener(myListener);
        llNewRead.setOnClickListener(myListener);
        llNewGetTicket.setOnClickListener(myListener);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==1&&requestCode==100){
            myName.setText(data.getStringExtra("name"));
            getUserImage();
            getInfo();
        }
    }

    class MyListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            Intent intent ;
            switch (view.getId()){
                case R.id.ll_fragment_name:
                    Log.e("fragmetn","intent");
                    intent = new Intent(getContext(), ActivityPersonInfo.class);
                    startActivityForResult(intent,100);
                    break;
                case R.id.ll_fragment_my_child:
                    intent = new Intent(getContext(), KidsActivity.class);
                    startActivity(intent);
                    break;
                case R.id.ll_fragment_my_contact:
                    intent = new Intent(getContext(),ContactorActivity.class);
                    startActivity(intent);
                    break;
                case R.id.ll_fragment_award_recommend:
                    intent = new Intent(getContext(), CommandActivity.class);
                    startActivity(intent);
                    break;
                case R.id.ll_fragment_new_read:
                    intent = new Intent(getContext(), ActivityNewRead.class);
                    startActivity(intent);
                    break;
                case R.id.ll_fragment_new_get_ticket:
                    intent = new Intent(getContext(),TicketActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    }
}
