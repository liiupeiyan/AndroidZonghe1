package com.example.androidzonghe1.Fragment.lsbWork;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.baidu.mapapi.search.sug.SuggestionResult;
import com.example.androidzonghe1.R;
import com.example.androidzonghe1.adapter.lsbWork.SiteAdapter;

import java.util.List;

public class SiteFragment extends Fragment {
    RecyclerView listView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e("SiteFragment", "onCreateView");
        View view = inflater.inflate(R.layout.fragment_site, container, false);
        listView = view.findViewById(R.id.list_site);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        listView.setLayoutManager(linearLayoutManager);
        initListView();
        return view;
    }
    public void initListView(){
        Log.e("SiteFragment", "initListView");
        Bundle bundle = getArguments();
        if (bundle != null && bundle.getParcelable("suggestionResult") != null){
            List<SuggestionResult.SuggestionInfo> suggestionInfos = ((SuggestionResult)bundle.getParcelable("suggestionResult")).getAllSuggestions();;
            SiteAdapter siteAdapter = new SiteAdapter(suggestionInfos);
            siteAdapter.setOnItemClickListener((parent, view, position, data) -> {
                Intent response = new Intent();
                Bundle b = new Bundle();
                b.putParcelable("suggestionInfo", data);
                response.putExtras(b);
                getActivity().setResult(1, response);
                getActivity().finish();
            });
            listView.setAdapter(siteAdapter);
        }
    }
}