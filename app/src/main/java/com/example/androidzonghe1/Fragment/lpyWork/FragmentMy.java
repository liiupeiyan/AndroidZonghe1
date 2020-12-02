package com.example.androidzonghe1.Fragment.lpyWork;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidzonghe1.ConfigUtil;
import com.example.androidzonghe1.Fragment.yyWork.ThirdFragment;
import com.example.androidzonghe1.MyApplication;
import com.example.androidzonghe1.R;
import com.example.androidzonghe1.activity.lsbWork.ContactorActivity;
import com.example.androidzonghe1.activity.lsbWork.KidsActivity;
import com.example.androidzonghe1.activity.rjxWork.CommandActivity;
import com.example.androidzonghe1.activity.rjxWork.TicketActivity;
import com.example.androidzonghe1.activity.xtWork.AcitivityChangeName;
import com.example.androidzonghe1.activity.xtWork.ActivityPersonInfo;
import com.example.androidzonghe1.activity.yyWork.ActivityNewRead;
import com.example.androidzonghe1.adapter.lpyWork.RecycleAdapterDayTrip;
import com.example.androidzonghe1.adapter.xtWork.RecycleAdapterFragmentMy;
import com.example.androidzonghe1.entity.lpyWork.RecycleviewTitle;
import com.example.androidzonghe1.entity.xtWork.RvFragmentMy;

import java.util.ArrayList;
import java.util.List;

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
            RvFragmentMy my4 = new RvFragmentMy(R.drawable.lianxi,"联系客服",R.drawable.right_xt);
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

    class MyListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            Intent intent ;
            switch (view.getId()){
                case R.id.ll_fragment_name:
                    Log.e("fragmetn","intent");
                    intent = new Intent(getContext(), ActivityPersonInfo.class);
                    startActivity(intent);
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
