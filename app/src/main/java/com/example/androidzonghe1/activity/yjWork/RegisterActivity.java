package com.example.androidzonghe1.activity.yjWork;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidzonghe1.ConfigUtil;
import com.example.androidzonghe1.R;
import com.example.androidzonghe1.activity.lpyWork.MyTheActivity;
import com.example.androidzonghe1.activity.lsbWork.ContactorActivity;
import com.example.androidzonghe1.adapter.lsbWork.ContactorAdapter;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.zyyoona7.wheel.WheelView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {
    private ImageView back;
    private TextView text_ship;
    private EditText text_name,text_school,text_pwd;
    private String name,school,ship,phoneNum,pwd;
    private Button btn;
    private WheelView wheel_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        phoneNum = getIntent().getStringExtra("phoneNum");
        back = findViewById(R.id.back_register);
        text_name = findViewById(R.id.register_child_name);
        text_ship = findViewById(R.id.register_child_ship);
        text_school = findViewById(R.id.register_child_school);
        text_pwd = findViewById(R.id.register_pwd);
        name = text_name.getText().toString();
        ship = text_ship.getText().toString();
        school = text_school.getText().toString();
        pwd = text_pwd.getText().toString();
        btn = findViewById(R.id.btn_register);
        wheel_view = findViewById(R.id.register_wheel);
        text_ship.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(RegisterActivity.this);
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
                        text_ship.setText(wheelView.getSelectedItemData());
                        bottomSheetDialog.dismiss();
                    }
                });
                bottomSheetDialog.setContentView(v);
                bottomSheetDialog.setCancelable(true);
                bottomSheetDialog.setCanceledOnTouchOutside(true);
                bottomSheetDialog.show();
            }
        });
        //注册返回按钮
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //设置特殊情况无法点击
        if ((name!=null&&ship!=null&&school!=null&&pwd!=null)||(!name.equals("")&&!ship.equals("")&&!school.equals("")&&!pwd.equals(""))){
            btn.setEnabled(true);
        }else {
            btn.setEnabled(false);
        }
        //注册提交按钮
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = text_name.getText().toString();
                ship = text_ship.getText().toString();
                school = text_school.getText().toString();
                pwd = text_pwd.getText().toString();
                String s = "phoneNum="+phoneNum+"&name="+name+"&ship="+ship+"&school="+school+"&pwd="+pwd;
                Log.e("yj",s);
                ConfigUtil.userName = name+ship;
                RegisterByPhone("http://192.168.43.232:8080/DingDong/RegisterServlet?"+s);
                ConfigUtil.isLogin =true;
                ConfigUtil.userName = name;
                ConfigUtil.phone = phoneNum;
                ConfigUtil.pwd = pwd;
                ConfigUtil.parent.setName(name);
                Intent intent = new Intent(getApplicationContext(), MyTheActivity.class);
                startActivity(intent);
            }
        });
    }

    private void RegisterByPhone(final String s){
        new Thread(){
            @Override
            public void run() {
                try {
                    URL url = new URL(s);
                    url.openStream();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
