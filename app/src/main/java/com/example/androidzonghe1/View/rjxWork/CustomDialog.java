package com.example.androidzonghe1.View.rjxWork;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.androidzonghe1.R;

public class CustomDialog extends DialogFragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //加载自定义的对话框布局文件
        View view = inflater.inflate(R.layout.custom_dialog,container,false);
        //设置对话框的属性
//        RelativeLayout relativeLayout = view.findViewById(R.id.ll);
//        relativeLayout.getBackground().setAlpha(0);
        ImageView imageView = view.findViewById(R.id.pic);
        //imageView.getBackground().setAlpha(0);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //关闭当前对话框
                getDialog().dismiss();

            }
        });


        return view;
    }

}
