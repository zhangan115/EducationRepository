package com.xueli.application.view.user.collection;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.xueli.application.R;
import com.xueli.application.mode.exam.ExamRepository;
import com.xueli.application.view.MvpActivity;

public class MyCollectionActivity extends MvpActivity<MyCollectionContract.Presenter> implements MyCollectionContract.View {

    private SwipeRefreshLayout mSwipeLayout;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_collection_activity);
        mRecyclerView = findViewById(R.id.recycleCollection);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mSwipeLayout = findViewById(R.id.swipeLayout);
        mSwipeLayout.setColorSchemeColors(findColorById(R.color.colorPrimary));

    }

    @Override
    protected void onBindPresenter() {
        new MyCollectionPresenter(ExamRepository.getRepository(this), this);
    }

    @Override
    public void noData() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showData() {

    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void setPresenter(MyCollectionContract.Presenter presenter) {

    }
}
