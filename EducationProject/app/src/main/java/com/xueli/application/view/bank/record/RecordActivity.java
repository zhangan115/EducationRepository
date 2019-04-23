package com.xueli.application.view.bank.record;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.library.adapter.RVAdapter;
import com.library.widget.ExpendRecycleView;
import com.library.widget.HtmlTextView;
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

public class RecordActivity extends MvpActivity<RecordContact.Presenter> implements RecordContact.View, RecycleRefreshLoadLayout.OnLoadListener, SwipeRefreshLayout.OnRefreshListener {

    private RecycleRefreshLoadLayout refreshLoadLayout;
    private ExpendRecycleView expendRecycleView;
    private RelativeLayout rlNoData;

    private List<PaperSections> dataList = new ArrayList<>();
    private Long lastId = null;
    private String bResult = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent().hasExtra(ConstantStr.KEY_BUNDLE_STR)) {
            bResult = getIntent().getStringExtra(ConstantStr.KEY_BUNDLE_STR);
            setLayoutAndToolbar(R.layout.record_activity, "我的错题");
        }else{
            setLayoutAndToolbar(R.layout.record_activity, "做题记录");
        }
        rlNoData = findViewById(R.id.rlNoData);
        refreshLoadLayout = findViewById(R.id.refreshLayout);
        expendRecycleView = findViewById(R.id.recycleRecord);
        refreshLoadLayout.setColorSchemeColors(findColorById(R.color.colorPrimary));
        expendRecycleView.setLayoutManager(new GridLayoutManager(this, 1));
        refreshLoadLayout.setOnRefreshListener(this);
        refreshLoadLayout.setOnLoadListener(this);
        refreshLoadLayout.setViewFooter(LayoutInflater.from(this).inflate(R.layout.view_load_more, null));
        final RVAdapter<PaperSections> adapter = new RVAdapter<PaperSections>(expendRecycleView, dataList, R.layout.item_record) {
            @Override
            public void showData(ViewHolder vHolder, PaperSections data, int position) {
                final HtmlTextView name = (HtmlTextView) vHolder.getView(R.id.tvQuestion);
                name.setHtmlFromString(data.getQuestion(), false);
                name.setTag(position);
                name.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int position = (int)name.getTag();
                        Intent intent = new Intent(RecordActivity.this, SubjectActivity.class);
                        intent.putExtra(ConstantStr.KEY_BUNDLE_OBJECT, dataList.get(position));
                        startActivity(intent);
                    }
                });
            }
        };
        expendRecycleView.setAdapter(adapter);
        mPresenter.getBankList(bResult, lastId);
    }

    @Override
    public void onLoadMore() {
        mPresenter.getBankList(bResult, lastId);
    }

    @Override
    public void onRefresh() {
        this.lastId = null;
        refreshLoadLayout.setNoMoreData(false);
        rlNoData.setVisibility(View.GONE);
        mPresenter.getBankList(bResult, lastId);
    }

    @Override
    protected void onBindPresenter() {
        new RecordPresenter(ExamRepository.getRepository(this), this);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void noData() {
        if (lastId != null) {
            this.refreshLoadLayout.setNoMoreData(true);
        } else {
            rlNoData.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showData(List<PaperSections> datas) {
        if (lastId == null) {
            this.refreshLoadLayout.setRefreshing(false);
            this.dataList.clear();
        } else {
            if (datas.size() < 10) {
                this.refreshLoadLayout.setNoMoreData(true);
            } else {
                this.refreshLoadLayout.loadFinish();
            }
        }
        this.dataList.addAll(datas);
        expendRecycleView.getAdapter().notifyDataSetChanged();
        lastId = this.dataList.get(this.dataList.size() - 1).getId();
    }

    @Override
    public void showMessage(String message) {
        App.getInstance().showToast(message);
    }

    @Override
    public void setPresenter(RecordContact.Presenter presenter) {
        mPresenter = presenter;
    }


}
