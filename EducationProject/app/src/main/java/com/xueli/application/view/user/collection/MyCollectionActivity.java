package com.xueli.application.view.user.collection;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.library.adapter.RVAdapter;
import com.xueli.application.R;
import com.xueli.application.app.App;
import com.xueli.application.mode.bean.exam.PaperSections;
import com.xueli.application.mode.exam.ExamRepository;
import com.xueli.application.view.MvpActivity;

import java.util.ArrayList;
import java.util.List;

public class MyCollectionActivity extends MvpActivity<MyCollectionContract.Presenter> implements MyCollectionContract.View, SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout mSwipeLayout;
    private RecyclerView mRecyclerView;
    private RelativeLayout noDataLayout;

    private List<PaperSections> paperSections;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_collection_activity);
        mRecyclerView = findViewById(R.id.recycleCollection);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mSwipeLayout = findViewById(R.id.swipeLayout);
        mSwipeLayout.setColorSchemeColors(findColorById(R.color.colorPrimary));
        mSwipeLayout.setOnRefreshListener(this);
        noDataLayout = findViewById(R.id.rlNoData);
        paperSections = new ArrayList<>();
        RVAdapter<PaperSections> adapter = new RVAdapter<PaperSections>(mRecyclerView, paperSections, R.layout.bank_list_item) {
            @Override
            public void showData(ViewHolder vHolder, PaperSections data, int position) {
                TextView title = (TextView) vHolder.getView(R.id.tvExamName);
                title.setText(data.getQuestion());
            }
        };
        mRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new RVAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }
        });
        adapter.setOnItemLongListener(new RVAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
        onRefresh();
    }

    @Override
    protected void onBindPresenter() {
        new MyCollectionPresenter(ExamRepository.getRepository(this), this);
    }

    @Override
    public void noData() {
        noDataLayout.setVerticalGravity(View.VISIBLE);
    }

    @Override
    public void showLoading() {
        mSwipeLayout.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        mSwipeLayout.setRefreshing(false);
    }

    @Override
    public void showData(List<PaperSections> list) {
        noDataLayout.setVerticalGravity(View.GONE);
        paperSections.clear();
        paperSections.addAll(list);
        mRecyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void showMessage(String message) {
        App.getInstance().showToast(message);
    }

    @Override
    public void setPresenter(MyCollectionContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onRefresh() {
        if (mPresenter != null) {
            paperSections.clear();
            noDataLayout.setVerticalGravity(View.GONE);
            mRecyclerView.getAdapter().notifyDataSetChanged();
            mPresenter.getMyCollection();
        }
    }
}
