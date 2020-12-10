package com.example.androidzonghe1.activity.yjWork;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ListView;

import com.example.androidzonghe1.ConfigUtil;
import com.example.androidzonghe1.R;
import com.example.androidzonghe1.adapter.yjWork.MyTripAdapter;
import com.example.androidzonghe1.entity.lpyWork.DayTrip;

import java.time.LocalDate;
import java.util.ArrayList;

public class CalendarActivity extends AppCompatActivity {

    private CalendarView view;
    private Toolbar toolbar;
    private View view_none;
    private ListView listView;
    private MyTripAdapter adapter;
    private ArrayList<DayTrip> trips = new ArrayList<>();
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        LocalDate date = LocalDate.now();
        String date_now = date.toString();
        for (DayTrip trip : ConfigUtil.trips){
            if (trip.getDate().equals(date_now)){
                trips.add(trip);
            }
        }
        adapter = new MyTripAdapter(this,trips,R.layout.my_trip_item);
        view = findViewById(R.id.calendar_view);
        toolbar = findViewById(R.id.toolBar);
        view_none = findViewById(R.id.calendar_none);
        listView = findViewById(R.id.lv_calendar_trip);
        if (trips!=null&&trips.size()!=0){
            view_none.setVisibility(View.GONE);
        }else {
            view_none.setVisibility(View.VISIBLE);
        }
        if (getActionBar() != null){
            getActionBar().hide();
        }
        setSupportActionBar(toolbar);
        listView.setAdapter(adapter);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("CalendarActivity", "toolbar back onClicked");
                finish();
            }
        });
        view.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                trips.clear();
                String date1 = i+"-"+(i1+1)+"-"+i2;
                for (DayTrip trip : ConfigUtil.trips){
                    if (trip.getDate().equals(date1)){
                        trips.add(trip);
                    }
                }
                if (trips!=null&&trips.size()!=0){
                    view_none.setVisibility(View.GONE);
                    adapter = new MyTripAdapter(getApplicationContext(),trips,R.layout.my_trip_item);
                    listView.setAdapter(adapter);
                }else {
                    view_none.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}
