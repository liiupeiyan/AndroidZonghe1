package com.example.androidzonghe1.activity.rjxWork;

import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.androidzonghe1.View.rjxWork.CustomDialog;
import com.example.androidzonghe1.R;

public class CommandActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获取ActionBar
        ActionBar actionBar = getSupportActionBar();
        //隐藏ActionBar
        actionBar.hide();
        setContentView(R.layout.activity_command);

        TextView rule = findViewById(R.id.rule);
        rule.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);//下划线
        rule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //弹出完全自定义对话框
                showCustomDialog();
            }
        });
    }

    //完全自定义内容的对话框
    private void showCustomDialog(){
        //使用FragmentManager显示Fragment
        FragmentManager manager = getSupportFragmentManager();
        //事务(一组原子性的操作，提交后才生效)
        FragmentTransaction transaction = manager.beginTransaction();
        //加载Fragment
        CustomDialog dialog = new CustomDialog();//创建Fragment类的对象
        //添加Fragment(只能添加一次)
        if(!dialog.isAdded()) {//判断是否被添加过，防止重复添加
            transaction.add(dialog, "dialog_tag");
        }
        //显示Fragment
        transaction.show(dialog);
        //提交事务
        transaction.commit();

    }
}