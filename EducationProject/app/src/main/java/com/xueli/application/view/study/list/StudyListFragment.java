package com.xueli.application.view.study.list;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.library.adapter.RVAdapter;
import com.library.widget.ExpendRecycleView;
import com.library.widget.RecycleRefreshLoadLayout;
import com.xueli.application.R;
import com.xueli.application.app.App;
import com.xueli.application.mode.bean.study.StudyMessage;
import com.xueli.application.mode.study.StudyRepository;
import com.xueli.application.view.LazyLoadFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 展示学习列表
 * Created by pingan on 2018/3/7.
 */

public class StudyListFragment extends LazyLoadFragment implements RecycleRefreshLoadLayout.OnLoadListener
        , SwipeRefreshLayout.OnRefreshListener
        , StudyListContract.View {

    private static final String SHOW_LIST_TYPE = "show_type";
    private RecycleRefreshLoadLayout refreshLoadLayout;
    private ExpendRecycleView expendRecycleView;
    private RelativeLayout noDataLayout;
    private long position;
    private StudyListContract.Presenter mPresenter;
    private List<StudyMessage> datas;

    public static StudyListFragment newInstance(long position) {
        Bundle args = new Bundle();
        args.putLong(SHOW_LIST_TYPE, position);
        StudyListFragment fragment = new StudyListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new StudyListPresenter(StudyRepository.getRepository(getActivity()), this);
        if (getArguments() != null) {
            position = getArguments().getLong(SHOW_LIST_TYPE);
        }
    }

    @Override
    public void requestData() {
        this.datas.clear();
        expendRecycleView.getAdapter().notifyDataSetChanged();
        mPresenter.getStudyMessage(position);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.study_show_lsit_fragment, container, false);
        refreshLoadLayout = rootView.findViewById(R.id.rlyLayout);
        expendRecycleView = rootView.findViewById(R.id.expendRv);
        refreshLoadLayout.setColorSchemeColors(findColorById(R.color.colorPrimary));
        refreshLoadLayout.setOnRefreshListener(this);
        expendRecycleView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        noDataLayout = rootView.findViewById(R.id.rlNoData);
        datas = new ArrayList<>();
        RVAdapter<StudyMessage> adapter = new RVAdapter<StudyMessage>(expendRecycleView, datas, R.layout.show_study_item) {
            @Override
            public void showData(ViewHolder vHolder, StudyMessage data, int position) {

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
        requestData();
    }

    @Override
    public void showLoading() {
        refreshLoadLayout.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        refreshLoadLayout.setRefreshing(false);
    }

    @Override
    public void noData() {
        noDataLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void showData(List<StudyMessage> datas) {
        noDataLayout.setVisibility(View.GONE);
        this.datas.clear();
        this.datas.addAll(datas);
        expendRecycleView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void showMessage(String message) {
        App.getInstance().showToast(message);
    }

    @Override
    public void setPresenter(StudyListContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
