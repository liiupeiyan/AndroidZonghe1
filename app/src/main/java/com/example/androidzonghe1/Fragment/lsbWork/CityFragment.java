package com.example.androidzonghe1.Fragment.lsbWork;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.androidzonghe1.Bind;
import com.example.androidzonghe1.MyApplication;
import com.example.androidzonghe1.R;
import com.example.androidzonghe1.entity.lsbWork.CityEntity;
import com.example.androidzonghe1.Utils.lsbWork.JsonReadUtil;
import com.example.androidzonghe1.View.lsbWork.ViewBinder;
import com.example.androidzonghe1.others.LetterListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CityFragment extends Fragment implements AbsListView.OnScrollListener{
    //文件名称
    private final static String CityFileName = "allcity.json";

    ListView totalCityLv;
    LetterListView lettersLv;
    ListView searchCityLv;
    TextView noSearchDataTv;
    TextView tvLetter;
    TextView tvLocation;

    private boolean isScroll = false;

    private HashMap<String, Integer> alphaIndexer;// 存放存在的汉语拼音首字母和与之对应的列表位置

    protected List<CityEntity> totalCityList = new ArrayList<>();
    protected List<CityEntity> curCityList = new ArrayList<>();
    protected List<CityEntity> searchCityList = new ArrayList<>();
    protected CityListAdapter cityListAdapter;
    protected SearchCityListAdapter searchCityListAdapter;

    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_city, container, false);

        totalCityLv = view.findViewById(R.id.total_city_lv);
        lettersLv = view.findViewById(R.id.total_city_letters_lv);
        searchCityLv = view.findViewById(R.id.search_city_lv);
        noSearchDataTv = view.findViewById(R.id.no_search_result_tv);
        tvLetter = view.findViewById(R.id.tv_letter);
        tvLocation = view.findViewById(R.id.tv_location);

        initView();
        initData();
        return view;
    }
    private void initView() {
        searchCityListAdapter = new SearchCityListAdapter(getContext(), searchCityList);
        searchCityLv.setAdapter(searchCityListAdapter);
    }

    public void setTvLocation(String location){
        tvLocation.setText(location);
    }

    private void initData() {
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
}