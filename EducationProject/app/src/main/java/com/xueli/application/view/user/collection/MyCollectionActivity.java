package com.xueli.application.view.user.collection;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.library.adapter.RVAdapter;
import com.library.widget.ExpendRecycleView;
import com.library.widget.RecycleRefreshLoadLayout;
import com.xueli.application.R;
import com.xueli.application.app.App;
import com.xueli.application.common.ConstantStr;
import com.xueli.application.mode.bean.exam.PaperSections;
import com.xueli.application.mode.exam.ExamRepository;
import com.xueli.application.view.MvpActivity;
import com.xueli.application.view.subject.SubjectActivity;

import java.util.ArrayList;
import java.util.List;

public class MyCollectionActivity extends MvpActivity<MyCollectionContract.Presenter> implements MyCollectionContract.View, SwipeRefreshLayout.OnRefreshListener {

    private RecycleRefreshLoadLayout mSwipeLayout;
    private ExpendRecycleView mRecyclerView;
    private RelativeLayout noDataLayout;

    private List<PaperSections> paperSections;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayoutAndToolbar(R.layout.my_collection_activity, "我的收藏");
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
                title.setText(Html.fromHtml(data.getQuestion()));
            }
        };
        mRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new RVAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(MyCollectionActivity.this, SubjectActivity.class);
                intent.putExtra(ConstantStr.KEY_BUNDLE_OBJECT, paperSections.get(position));
                startActivity(intent);
            }
        });
        adapter.setOnItemLongListener(new RVAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(View view, final int position) {
                new MaterialDialog.Builder(MyCollectionActivity.this)
                        .content("是否取消改题的收藏")
                        .positiveText("确定")
                        .negativeText("取消")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                mPresenter.cancelCollection(paperSections.get(position).getId());
                            }
                        })
                        .build().show();
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
        noDataLayout.setVisibility(View.VISIBLE);
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
        noDataLayout.setVisibility(View.GONE);
        paperSections.clear();
        paperSections.addAll(list);
        mRecyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void showMessage(String message) {
        App.getInstance().showToast(message);
    }

    @Override
    public void onCancelSuccess() {
        onRefresh();
    }

    @Override
    public void setPresenter(MyCollectionContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onRefresh() {
        if (mPresenter != null) {
            paperSections.clear();
            noDataLayout.setVisibility(View.GONE);
            mRecyclerView.getAdapter().notifyDataSetChanged();
            mPresenter.getMyCollection();
        }
    }
}
