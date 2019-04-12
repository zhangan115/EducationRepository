package com.xueli.application.view.bank.record;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.library.adapter.RVAdapter;
import com.library.widget.ExpendRecycleView;
import com.library.widget.RecycleRefreshLoadLayout;
import com.xueli.application.R;
import com.xueli.application.app.App;
import com.xueli.application.mode.bean.exam.PaperSections;
import com.xueli.application.mode.exam.ExamRepository;
import com.xueli.application.view.MvpActivity;

import java.util.ArrayList;
import java.util.List;

public class RecordActivity extends MvpActivity<RecordContact.Presenter> implements RecordContact.View, RecycleRefreshLoadLayout.OnLoadListener, SwipeRefreshLayout.OnRefreshListener {

    private RecycleRefreshLoadLayout refreshLoadLayout;
    private ExpendRecycleView expendRecycleView;
    private RelativeLayout rlNoData;

    private List<PaperSections> dataList = new ArrayList<>();
    private Long lastId = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayoutAndToolbar(R.layout.record_activity, "做题记录");
        rlNoData = findViewById(R.id.rlNoData);
        refreshLoadLayout = findViewById(R.id.refreshLayout);
        expendRecycleView = findViewById(R.id.recycleRecord);
        refreshLoadLayout.setColorSchemeColors(findColorById(R.color.colorPrimary));
        expendRecycleView.setLayoutManager(new GridLayoutManager(this, 1));
        refreshLoadLayout.setOnRefreshListener(this);
        refreshLoadLayout.setOnLoadListener(this);
        final RVAdapter<PaperSections> adapter = new RVAdapter<PaperSections>(expendRecycleView, dataList, R.layout.item_record) {
            @Override
            public void showData(ViewHolder vHolder, PaperSections data, int position) {
                TextView name = (TextView) vHolder.getView(R.id.name);
                name.setText(data.getQuestion());
            }
        };
        expendRecycleView.setAdapter(adapter);
        adapter.setOnItemClickListener(new RVAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }
        });
        mPresenter.getBankList(null, lastId);
    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void onRefresh() {
        this.lastId = null;
        rlNoData.setVisibility(View.GONE);
        mPresenter.getBankList(null, lastId);
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
        rlNoData.setVisibility(View.VISIBLE);
    }

    @Override
    public void showData(List<PaperSections> datas) {
        if (lastId == null) {
            this.dataList.clear();
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
