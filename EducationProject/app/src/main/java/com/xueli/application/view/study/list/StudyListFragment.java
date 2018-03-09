package com.xueli.application.view.study.list;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.library.adapter.RVAdapter;
import com.library.widget.ExpendRecycleView;
import com.library.widget.RecycleRefreshLoadLayout;
import com.xueli.application.R;
import com.xueli.application.view.MvpFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 展示学习列表
 * Created by pingan on 2018/3/7.
 */

public class StudyListFragment extends MvpFragment implements RecycleRefreshLoadLayout.OnLoadListener, SwipeRefreshLayout.OnRefreshListener {

    private static final String SHOW_LIST_TYPE = "show_type";
    private RecycleRefreshLoadLayout refreshLoadLayout;
    private ExpendRecycleView expendRecycleView;
    private int position;

    public static StudyListFragment newInstance(int position) {
        Bundle args = new Bundle();
        args.putInt(SHOW_LIST_TYPE, position);
        StudyListFragment fragment = new StudyListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            position = getArguments().getInt(SHOW_LIST_TYPE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.study_show_lsit_fragment, container, false);
        refreshLoadLayout = rootView.findViewById(R.id.rlyLayout);
        expendRecycleView = rootView.findViewById(R.id.expendRv);
        refreshLoadLayout.setColorSchemeColors(findColorById(R.color.colorPrimary));
        refreshLoadLayout.setOnRefreshListener(this);
        refreshLoadLayout.setOnLoadListener(this);
        expendRecycleView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        List<String> datas = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            datas.add(String.valueOf(i));
        }
        RVAdapter<String> adapter = new RVAdapter<String>(expendRecycleView, datas, R.layout.show_study_item) {
            @Override
            public void showData(ViewHolder vHolder, String data, int position) {

            }
        };
        expendRecycleView.setAdapter(adapter);
        return rootView;
    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void onRefresh() {

    }
}
