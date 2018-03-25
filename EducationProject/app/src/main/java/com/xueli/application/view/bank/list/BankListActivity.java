package com.xueli.application.view.bank.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.library.adapter.RVAdapter;
import com.library.widget.ExpendRecycleView;
import com.library.widget.RecycleRefreshLoadLayout;
import com.xueli.application.R;
import com.xueli.application.app.App;
import com.xueli.application.common.ConstantStr;
import com.xueli.application.mode.exam.ExamRepository;
import com.xueli.application.view.BaseActivity;
import com.xueli.application.view.MvpActivity;
import com.xueli.application.view.bank.examination.ExaminationActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 历年真题，模拟试题
 * Created by pingan on 2018/3/13.
 */

public class BankListActivity extends MvpActivity<BankListContact.Presenter> implements SwipeRefreshLayout.OnRefreshListener
        , RecycleRefreshLoadLayout.OnLoadListener, BankListContact.View {
    //view
    private RecycleRefreshLoadLayout refreshLoadLayout;
    private ExpendRecycleView expendRecycleView;
    private DrawerLayout drawerLayout;
    private RelativeLayout noDataLayout;
    //data
    private List<String> datas;
    private String type, isYear;
    private Map<String, String> map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.bankFilterStyle);
        transparentStatusBar();
        String tag = getIntent().getStringExtra(ConstantStr.KEY_BUNDLE_STR);
        String[] types = tag.split(",");
        if (types.length == 2) {
            type = String.valueOf(types[0]);
            isYear = String.valueOf(types[1]);
        }
        setLayoutAndToolbar(R.layout.bank_list_activity, isYear.equals("0") ? "模拟试题" : "历年真题");
        refreshLoadLayout = findViewById(R.id.rcrLayout);
        expendRecycleView = findViewById(R.id.expendRv);
        noDataLayout = findViewById(R.id.rlNoData);
        drawerLayout = findViewById(R.id.drawer_layout);
        refreshLoadLayout.setColorSchemeColors(findColorById(R.color.colorPrimary));
        expendRecycleView.setLayoutManager(new GridLayoutManager(this, 1));
        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {
                App.getInstance().hideSoftKeyBoard(BankListActivity.this);
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
        map = new HashMap<>();
        datas = new ArrayList<>();
        setTypeAndYear();
        for (int i = 0; i < 10; i++) {
            datas.add("i+" + i);
        }
        RVAdapter<String> adapter = new RVAdapter<String>(expendRecycleView, datas, R.layout.bank_list_item) {
            @Override
            public void showData(ViewHolder vHolder, String data, int position) {

            }
        };
        expendRecycleView.setAdapter(adapter);
        adapter.setOnItemClickListener(new RVAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                startActivity(new Intent(BankListActivity.this, ExaminationActivity.class));
            }
        });
        refreshLoadLayout.setOnRefreshListener(this);
        refreshLoadLayout.setOnLoadListener(this);
        mPresenter.getBankList(map);
    }

    private void setTypeAndYear() {
        map.put("type", type);
        map.put("isYear", isYear);
    }

    @Override
    protected void onBindPresenter() {
        new BankListPresenter(ExamRepository.getRepository(this), this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.filter, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_filter) {
            drawerLayout.openDrawer(Gravity.RIGHT);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onRefresh() {
        noDataLayout.setVisibility(View.GONE);
        datas.clear();
        expendRecycleView.getAdapter().notifyDataSetChanged();
        mPresenter.getBankList(map);
    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void noData() {
        datas.clear();
        expendRecycleView.getAdapter().notifyDataSetChanged();
        noDataLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void showMessage(String message) {
        App.getInstance().showToast(message);
    }

    @Override
    public void setPresenter(BankListContact.Presenter presenter) {
        mPresenter = presenter;
    }
}
