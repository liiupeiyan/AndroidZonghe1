package com.example.androidzonghe1.activity.yyWork;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTabHost;

import com.example.androidzonghe1.Fragment.yyWork.FirstFragment;
import com.example.androidzonghe1.Fragment.yyWork.SecondFragment;
import com.example.androidzonghe1.Fragment.yyWork.ThirdFragment;
import com.example.androidzonghe1.R;

import java.util.HashMap;
import java.util.Map;

public class YYMainAcitivity extends AppCompatActivity {
    private Map<String, ImageView> imageViewMap = new HashMap<>();
    private Map<String, TextView> textViewMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yy_main);

        //获取FragmentTabHost的引用
        FragmentTabHost fragmentTabHost = findViewById(android.R.id.tabhost);
        //初始化
        fragmentTabHost.setup(this,//环境上下文
                getSupportFragmentManager(),//获取管理器,管理多个Fragment对象的管理器
                android.R.id.tabcontent);//显示内容页面的控件的id，FrameLayout
        //创建内容页面TabSpec对象
        TabHost.TabSpec tab1 = fragmentTabHost.newTabSpec("first_tab")
                .setIndicator(getTabSpecView("first_tab","首页",R.drawable.news));
//                .setIndicator("短信");
        //Class参数：①类名.class;②对象.getClass();③Class.forName(类名)
        fragmentTabHost.addTab(tab1,
                FirstFragment.class,//FirstFragment类的Class对象
                null);//传递数据时使用，不需要时传null
        TabHost.TabSpec tab2 = fragmentTabHost.newTabSpec("second_tab")
                .setIndicator(getTabSpecView("second_tab","发起新路线",R.drawable.person));
//                .setIndicator("联系人");
        fragmentTabHost.addTab(tab2,
                SecondFragment.class,
                null);
        TabHost.TabSpec tab3 = fragmentTabHost.newTabSpec("third_tab")
                .setIndicator(getTabSpecView("third_tab","新人必读",R.drawable.person));
        fragmentTabHost.addTab(tab3,
                ThirdFragment.class,null);
        //处理fragmentTabHost选项切换的事件
        fragmentTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                //修改图片和文字的颜色
                switch (tabId){
                    case "first_tab": // 选中了短信
                        imageViewMap.get("first_tab").setImageResource(R.drawable.news1);
                        imageViewMap.get("second_tab").setImageResource(R.drawable.person);
                        imageViewMap.get("third_tab").setImageResource(R.drawable.person);
                        break;
                    case "second_tab"://选中了联系人
                        imageViewMap.get("first_tab").setImageResource(R.drawable.news);
                        imageViewMap.get("second_tab").setImageResource(R.drawable.person1);
                        imageViewMap.get("third_tab").setImageResource(R.drawable.person);
                        break;
                    case "third_tab":
                        imageViewMap.get("first_tab").setImageResource(R.drawable.news);
                        imageViewMap.get("second_tab").setImageResource(R.drawable.person);
                        imageViewMap.get("third_tab").setImageResource(R.drawable.person1);
                }
            }
        });
        //设置FragmentTagHost默认选中的标签页：参数时下标
        fragmentTabHost.setCurrentTab(0);
        imageViewMap.get("first_tab").setImageResource(R.drawable.news1);
        //不能直接传color的资源id
        textViewMap.get("first_tab").setTextColor(getResources().getColor(R.color.myColor));
        //android.R.color.需要的颜色，例：red
    }

    public View getTabSpecView(String tag, String title, int drawable){
        View view = getLayoutInflater().inflate(R.layout.tab_spec_layout,null);

        //获取tab_spec_layout布局当中视图控件的引用
        ImageView icon = view.findViewById(R.id.icon);
        icon.setImageResource(drawable);

        //将ImageView对象存储到Map中
        imageViewMap.put(tag,icon);

        TextView tvTitle = view.findViewById(R.id.title);
        tvTitle.setText(title);
        textViewMap.put(tag,tvTitle);

        return view;
    }
}
