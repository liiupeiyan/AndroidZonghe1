package com.example.androidzonghe1.activity.yjWork;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.androidzonghe1.R;

public class UserAgreementActivity extends AppCompatActivity {
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_agreement);
        toolbar = findViewById(R.id.toolBar);

        if (getActionBar() != null){
            getActionBar().hide();
        }
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("UserAgreementActivity", "toolbar back onClicked");
                finish();
            }
        });
    }
}
