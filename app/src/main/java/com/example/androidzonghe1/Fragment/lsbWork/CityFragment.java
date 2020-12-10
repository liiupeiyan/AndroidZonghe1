package com.example.androidzonghe1.Fragment.lsbWork;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.textservice.SuggestionsInfo;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.example.androidzonghe1.Bind;
import com.example.androidzonghe1.ConfigUtil;
import com.example.androidzonghe1.MyApplication;
import com.example.androidzonghe1.R;
import com.example.androidzonghe1.entity.lsbWork.CityEntity;
import com.example.androidzonghe1.Utils.lsbWork.JsonReadUtil;
import com.example.androidzonghe1.View.lsbWork.ViewBinder;
import com.example.androidzonghe1.entity.rjxWork.History;
import com.example.androidzonghe1.others.LetterListView;
import com.example.androidzonghe1.others.ScrollWithGridView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CityFragment extends Fragment implements AbsListView.OnScrollListener{
    //文件名称
    private final static String CityFileName = "allcity.json";

    ListView totalCityLv;
    LetterListView lettersLv;
    public static ListView searchCityLv;
    public static HotCityListAdapter adapter;
    TextView noSearchDataTv;
    TextView tvLetter;
    TextView tvLocation;
    GridView hotCityGv;
    Button clearHistory;

    private boolean isScroll = false;

    private HashMap<String, Integer> alphaIndexer;// 存放存在的汉语拼音首字母和与之对应的列表位置

    protected List<CityEntity> totalCityList = new ArrayList<>();
    protected List<CityEntity> curCityList = new ArrayList<>();
    protected List<CityEntity> searchCityList = new ArrayList<>();
    public static List<SuggestionResult.SuggestionInfo> hotCityList = new ArrayList<>();
    protected CityListAdapter cityListAdapter;
    protected SearchCityListAdapter searchCityListAdapter;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    String jsonList = msg.obj.toString();
                    Gson gson = new Gson();
                    Type collectionType = new TypeToken<List<History>>() {}.getType();
                    List<History> histories = gson.fromJson(jsonList, collectionType);
                    for (History history: histories) {
                        SuggestionResult.SuggestionInfo suggestionInfo = new SuggestionResult.SuggestionInfo();
                        suggestionInfo.setKey(history.getKey());
                        suggestionInfo.setCity(history.getCity());
//                        suggestionInfo.setDistrict("裕华区");
                        suggestionInfo.setPt(new LatLng(history.getLatitude(), history.getLongitude()));
//                        suggestionInfo.setUid("42362707d679c71f5cbe86c3");
//                        suggestionInfo.setTag("高校");
//                        suggestionInfo.setAddress("石家庄市-裕华区-南二环东路20号");
                        hotCityList.add(suggestionInfo);
                    };
                    adapter = new HotCityListAdapter(getContext(), hotCityList);
                    Log.e("size",hotCityList.size()+"");
//        adapter.notifyDataSetChanged();
                    hotCityGv.setAdapter(adapter);
                    hotCityGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent response = new Intent();
                            Bundle b = new Bundle();
                            Log.e("info",hotCityList.get(position).toString());
                            b.putParcelable("suggestionInfo", hotCityList.get(position));
                            response.putExtras(b);
                            getActivity().setResult(0, response);
                            getActivity().finish();
                        }
                    });
                    break;
            }
        }
    };

    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_city, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        totalCityLv = view.findViewById(R.id.total_city_lv);
        lettersLv = view.findViewById(R.id.total_city_letters_lv);
        searchCityLv = view.findViewById(R.id.search_city_lv);
        noSearchDataTv = view.findViewById(R.id.no_search_result_tv);
        tvLetter = view.findViewById(R.id.tv_letter);
        tvLocation = view.findViewById(R.id.tv_location);
        hotCityGv = view.findViewById(R.id.recent_city_gv);
        clearHistory = view.findViewById(R.id.clear_all);
//        ConfigUtil.phone = "123456";
        initView();


        setUsePosition();

        clearHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hotCityList.clear();
                //清除数据库所有数据
                clearHistorys(ConfigUtil.Url+"DeleteHistoryServlet");
            }
        });

        return view;
    }
    private void initView() {
        searchCityListAdapter = new SearchCityListAdapter(getContext(), searchCityList);
        searchCityLv.setAdapter(searchCityListAdapter);
        initTotalCityList();
        cityListAdapter = new CityListAdapter(getContext(), totalCityList);
        totalCityLv.setAdapter(cityListAdapter);
        totalCityLv.setOnScrollListener(this);
        totalCityLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CityEntity cityEntity = totalCityList.get(position);
                showSetCityDialog(cityEntity.getName(), cityEntity.getCityCode());
            }
        });
        lettersLv.setOnTouchingLetterChangedListener(new LetterListViewListener());
        searchCityLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CityEntity cityEntity = searchCityList.get(position);
                showSetCityDialog(cityEntity.getName(), cityEntity.getCityCode());
            }
        });

    }

    public void setTvLocation(String location){
        tvLocation.setText(location);
    }

    //设置常用地点
    public void setUsePosition(){
        hotCityList.clear();
        //获取所有的
        getHistorys(ConfigUtil.Url+"GetHistoryServlet");
//        SuggestionResult.SuggestionInfo suggestionInfo = new SuggestionResult.SuggestionInfo();
//        suggestionInfo.setKey("河北师范大学");
//        suggestionInfo.setCity("石家庄市");
//        suggestionInfo.setDistrict("裕华区");
//        suggestionInfo.setPt(new LatLng(38.003617, 114.526421));
//        suggestionInfo.setUid("42362707d679c71f5cbe86c3");
//        suggestionInfo.setTag("高校");
//        suggestionInfo.setAddress("石家庄市-裕华区-南二环东路20号");
//        suggestionInfo.setPoiChildrenInfoList(null);
//        hotCityList.add(suggestionInfo);
    }



    /**
     * 设置搜索数据展示
     */
    public void setSearchCityList(String content) {
        searchCityList.clear();
        if (TextUtils.isEmpty(content)) {
            totalCityLv.setVisibility(View.VISIBLE);
            lettersLv.setVisibility(View.VISIBLE);
            searchCityLv.setVisibility(View.GONE);
            noSearchDataTv.setVisibility(View.GONE);
        } else {
            totalCityLv.setVisibility(View.GONE);
            lettersLv.setVisibility(View.GONE);
            for (int i = 0; i < curCityList.size(); i++) {
                CityEntity cityEntity = curCityList.get(i);
                if (cityEntity.getName().contains(content) || cityEntity.getPinyin().contains(content)
                        || cityEntity.getFirst().contains(content)) {
                    searchCityList.add(cityEntity);
                }
            }

            if (searchCityList.size() != 0) {
                noSearchDataTv.setVisibility(View.GONE);
                searchCityLv.setVisibility(View.VISIBLE);
            } else {
                noSearchDataTv.setVisibility(View.VISIBLE);
                searchCityLv.setVisibility(View.GONE);
            }

            searchCityListAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 初始化全部城市列表
     */
    public void initTotalCityList() {
        totalCityList.clear();
        curCityList.clear();
        String cityListJson = JsonReadUtil.getJsonStr(getContext(), CityFileName);
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(cityListJson);
            JSONArray array = jsonObject.getJSONArray("City");
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                String name = object.getString("name");
                String key = object.getString("key");
                String pinyin = object.getString("full");
                String first = object.getString("first");
                String cityCode = object.getString("code");
                CityEntity cityEntity = new CityEntity();
                cityEntity.setName(name);
                cityEntity.setKey(key);
                cityEntity.setPinyin(pinyin);
                cityEntity.setFirst(first);
                cityEntity.setCityCode(cityCode);
                curCityList.add(cityEntity);
                totalCityList.add(cityEntity);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == SCROLL_STATE_TOUCH_SCROLL
                || scrollState == SCROLL_STATE_FLING) {
            isScroll = true;
        } else {
            isScroll = false;
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (!isScroll) {
            return;
        }
        String key = getAlpha(totalCityList.get(firstVisibleItem).getKey());
        tvLetter.setText(key);
    }

    /**
     * 总城市适配器
     */
    private class CityListAdapter extends BaseAdapter {
        private Context context;
        private List<CityEntity> totalCityList;
        private LayoutInflater inflater;

        CityListAdapter(Context context, List<CityEntity> totalCityList) {
            this.context = context;
            this.totalCityList = totalCityList;
            inflater = LayoutInflater.from(context);
            alphaIndexer = new HashMap<>();
            for (int i = 0; i < totalCityList.size(); i++) {
                // 当前汉语拼音首字母
                String currentStr = totalCityList.get(i).getKey();
                String previewStr = (i - 1) >= 0 ? totalCityList.get(i - 1).getKey() : " ";
                if (!previewStr.equals(currentStr)) {
                    String name = getAlpha(currentStr);
                    alphaIndexer.put(name, i);
                }
            }
        }

        @Override
        public int getCount() {
            return totalCityList == null ? 0 : totalCityList.size();
        }

        @Override
        public Object getItem(int position) {
            return totalCityList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (null == convertView) {
                holder = new ViewHolder();
                convertView = inflater.inflate(R.layout.city_list_item_layout, null);
                ViewBinder.bind(holder, convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            CityEntity cityEntity = totalCityList.get(position);
            holder.cityKeyTv.setVisibility(View.VISIBLE);
            holder.cityKeyTv.setText(getAlpha(cityEntity.getKey()));
            holder.cityNameTv.setText(cityEntity.getName());

            if (position >= 1) {
                CityEntity preCity = totalCityList.get(position - 1);
                if (preCity.getKey().equals(cityEntity.getKey())) {
                    holder.cityKeyTv.setVisibility(View.GONE);
                } else {
                    holder.cityKeyTv.setVisibility(View.VISIBLE);
                }
            }
            return convertView;
        }

        private class ViewHolder {
            @Bind(R.id.city_name_tv)
            TextView cityNameTv;
            @Bind(R.id.city_key_tv)
            TextView cityKeyTv;
        }
    }
    /**
     * 搜索城市列表适配器
     */
    public class SearchCityListAdapter extends BaseAdapter {

        private List<CityEntity> cityEntities;
        private LayoutInflater inflater;

        SearchCityListAdapter(Context mContext, List<CityEntity> cityEntities) {
            this.cityEntities = cityEntities;
            inflater = LayoutInflater.from(mContext);
        }

        @Override
        public int getCount() {
            return cityEntities == null ? 0 : cityEntities.size();
        }

        @Override
        public Object getItem(int position) {
            return cityEntities.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (null == convertView) {
                holder = new ViewHolder();
                convertView = inflater.inflate(R.layout.city_list_item_layout, null);
                ViewBinder.bind(holder, convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            CityEntity cityEntity = cityEntities.get(position);
            holder.cityKeyTv.setVisibility(View.GONE);
            holder.cityNameTv.setText(cityEntity.getName());

            return convertView;
        }

        private class ViewHolder {
            @Bind(R.id.city_name_tv)
            TextView cityNameTv;
            @Bind(R.id.city_key_tv)
            TextView cityKeyTv;
        }
    }
    /**
     * 常用地点适配器
     */
    private class HotCityListAdapter extends BaseAdapter {

        private List<SuggestionResult.SuggestionInfo> cityEntities;
        private LayoutInflater inflater;

        HotCityListAdapter(Context mContext, List<SuggestionResult.SuggestionInfo> cityEntities) {
            this.cityEntities = cityEntities;
            inflater = LayoutInflater.from(mContext);
        }

        @Override
        public int getCount() {
            return cityEntities == null ? 0 : cityEntities.size();
        }

        @Override
        public Object getItem(int position) {
            return cityEntities.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (null == convertView) {
                holder = new ViewHolder();
                convertView = inflater.inflate(R.layout.city_list_grid_item_layout, null);
                ViewBinder.bind(holder, convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            SuggestionResult.SuggestionInfo suggestionInfo = cityEntities.get(position);
            holder.cityNameTv.setText(suggestionInfo.getKey());

            return convertView;
        }

        private class ViewHolder {
            @Bind(R.id.city_list_grid_item_name_tv)
            TextView cityNameTv;
        }
    }

    /**
     * 获得首字母
     */
    private String getAlpha(String key) {
        return key;
    }

    private class LetterListViewListener implements LetterListView.OnTouchingLetterChangedListener {

        @Override
        public void onTouchingLetterChanged(final String s) {
            isScroll = false;
            if (alphaIndexer.get(s) != null) {
                int position = alphaIndexer.get(s);
                totalCityLv.setSelection(position);
                tvLetter.setText(s);
            }
        }
    }

    /**
     * 点击城市选择
     */
    private void showSetCityDialog(final String curCity, final String cityCode) {
        Message msg = new Message();
        msg.what = 1;
        msg.obj = curCity;
        MyApplication myApplication = (MyApplication) getActivity().getApplication();
        myApplication.getHandler().sendMessage(msg);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    //删除所有搜索记录
    private void clearHistorys(final String str){
        new Thread(){
            @Override
            public void run() {
                super.run();
                //获取搜索地址名和城市名和当前用户名手机号
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("phone",ConfigUtil.phone);
                    URL url = new URL(str);
                    //获取网络连接对象URLConnection
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    OutputStream os = connection.getOutputStream();
                    os.write(jsonObject.toString().getBytes());
                    os.flush();
                    //获取网络输入流
                    InputStream is = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is,"UTF-8"));
                    String deleteFlag = reader.readLine();
                    System.out.println(deleteFlag);
                    is.close();
                    os.close();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    //获取用户搜索记录
    private void getHistorys(final String str) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                //获取搜索地址名和城市名和当前用户名手机号
                try {
                    URL url = new URL(str+"?phone=123456");
                    //获取网络连接对象URLConnection
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//                    OutputStream os = connection.getOutputStream();
//                    Gson gson
//                    os.write();
                    //获取网络输入流
                    InputStream is = connection.getInputStream();
                    connection.setRequestMethod("POST");
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                    String historyList = reader.readLine();
                    System.out.println(historyList);
                    Message message = new Message();
                    message.what = 1;
                    message.obj = historyList;
                    handler.sendMessage(message);
                    Log.e("list",hotCityList.toString());
                    is.close();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}