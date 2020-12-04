package com.example.androidzonghe1.activity.yyWork;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.androidzonghe1.R;
import com.example.androidzonghe1.entity.yyWork.DataMmoney;

import org.greenrobot.eventbus.EventBus;

public class MoneyDialog extends DialogFragment {
    private EditText edtMoney;
    private String money;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recharge_money,container,false);
        edtMoney = view.findViewById(R.id.edt_moy);
        edtMoney.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_DONE){
                    money = edtMoney.getText()+"";
                    DataMmoney.setMoney(money);
                    Log.e("钱",money);
                    final PayPasswordDialog dialog = new PayPasswordDialog(getContext(),R.style.mydialog,2,getDialog(),money);
                    dialog.setDialogClick(new PayPasswordDialog.DialogClick() {
                        @Override
                        public void doConfirm(String password) {
                            dialog.dismiss();
                            Log.e("输入密码为：",password);
                            Log.e("qian",money);
                        }
                    });
                    dialog.show();
                }
                return true;
            }
        });
        return view;
    }
    public String getMyMoney(){
        return money;
    }

}
