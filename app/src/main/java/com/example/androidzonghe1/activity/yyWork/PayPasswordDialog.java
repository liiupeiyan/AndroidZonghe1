package com.example.androidzonghe1.activity.yyWork;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.Gravity;
import android.view.KeyboardShortcutGroup;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.androidzonghe1.R;
import com.example.androidzonghe1.activity.lsbWork.WalletActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class PayPasswordDialog extends Dialog implements View.OnClickListener {
    Context context;
    private TextView tvReturn;
    private PayPasswordView payPassword;
    private TextView tv1;
    private TextView tv2;
    private TextView tv3;
    private TextView tv4;
    private TextView tv5;
    private TextView tv6;
    private TextView tv7;
    private TextView tv8;
    private TextView tv9;
    private TextView tv;
    private TextView tvDel;
    private String pwd;
    private FragmentManager manager;
    private Activity activity;
    private int code;
    private Dialog mDia;
    private String money;
    public PayPasswordDialog(@NonNull Context context, int themeResId,int code,Dialog dialog,String mon) {
        super(context, themeResId);
        this.context = context;
        this.code = code;
        mDia = dialog;
        money = mon;
    }
    public PayPasswordDialog(@NonNull Context context, int themeResId, String order, FragmentManager fragmentManager, Activity activity,int code) {
        super(context, themeResId);
        this.context = context;
        this.pwd = order;
        manager = fragmentManager;
        this.code = code;
        activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        View view = LayoutInflater.from(context).inflate(R.layout.pay_password, null);
        setContentView(view);
        initView();

        Window window = getWindow();
        WindowManager.LayoutParams mParams = window.getAttributes();
        mParams.width= WindowManager.LayoutParams.MATCH_PARENT;
        window.setGravity(Gravity.BOTTOM);
        window.setAttributes(mParams);
        setCanceledOnTouchOutside(true);
        tvReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        tvDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payPassword.delLastPassword();
            }
        });
        payPassword.setPayPasswordEndListener(new PayPasswordView.PayEndListener() {
            @Override
            public void doEnd(String password) {
                if (dialogClick!=null){
                    dialogClick.doConfirm(password);
                }
                if(code==1){
                    //判断pwd.equals(password)。弹出支付成功界面
                    showCustomeDialog();
                }else if(code==2){
                    //返回WalletActivity
                    EventBus.getDefault().post(money);
                    mDia.dismiss();
                    //判断pwd.equals(password)。相等关闭Fragment
//                    showMoneyDialog();
                }
            }
        });

        tv.setOnClickListener(this);
        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        tv3.setOnClickListener(this);
        tv4.setOnClickListener(this);
        tv5.setOnClickListener(this);
        tv6.setOnClickListener(this);
        tv7.setOnClickListener(this);
        tv8.setOnClickListener(this);
        tv9.setOnClickListener(this);
    }

    private void showMoneyDialog() {
        FragmentTransaction transaction = manager.beginTransaction();
        MoneyDialog dialog = new MoneyDialog();
        if(!dialog.isAdded()){
            transaction.add(dialog,"dialog_tag");
        }
        transaction.show(dialog);
        transaction.commit();
    }

    private void showCustomeDialog() {
        FragmentTransaction transaction = manager.beginTransaction();
        PaySucc dialog = new PaySucc();
        if(!dialog.isAdded()){
            transaction.add(dialog,"dialog_tag");
        }
        transaction.show(dialog);
        transaction.commit();
        //结束OrderDetailsActivity
        //activity.finish();
    }

    private void initView() {
        tvReturn = (TextView) findViewById(R.id.tv_return);
        payPassword = (PayPasswordView) findViewById(R.id.pay_password);
        payPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
        tv1 = (TextView) findViewById(R.id.tv1);
        tv2 = (TextView) findViewById(R.id.tv2);
        tv3 = (TextView) findViewById(R.id.tv3);
        tv4 = (TextView) findViewById(R.id.tv4);
        tv5 = (TextView) findViewById(R.id.tv5);
        tv6 = (TextView) findViewById(R.id.tv6);
        tv7 = (TextView) findViewById(R.id.tv7);
        tv8 = (TextView) findViewById(R.id.tv8);
        tv9 = (TextView) findViewById(R.id.tv9);
        tv = (TextView) findViewById(R.id.tv);
        tvDel = (TextView) findViewById(R.id.tv_del);
    }

    DialogClick dialogClick;
    public void setDialogClick(DialogClick dialogClick){
        this.dialogClick=dialogClick;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv:
                payPassword.addPassword("0");
                break;
            case R.id.tv1:
                payPassword.addPassword("1");
                break;
            case R.id.tv2:
                payPassword.addPassword("2");
                break;
            case R.id.tv3:
                payPassword.addPassword("3");
                break;
            case R.id.tv4:
                payPassword.addPassword("4");
                break;
            case R.id.tv5:
                payPassword.addPassword("5");
                break;
            case R.id.tv6:
                payPassword.addPassword("6");
                break;
            case R.id.tv7:
                payPassword.addPassword("7");
                break;
            case R.id.tv8:
                payPassword.addPassword("8");
                break;
            case R.id.tv9:
                payPassword.addPassword("9");
                break;
        }
    }

    public interface DialogClick{
        void doConfirm(String password);
    }
}
