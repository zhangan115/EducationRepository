package com.xueli.application.view.bank.list;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.library.adapter.RVAdapter;
import com.library.widget.ExpendRecycleView;
import com.library.widget.RecycleRefreshLoadLayout;
import com.xueli.application.R;
import com.xueli.application.common.ConstantStr;
import com.xueli.application.view.BaseActivity;
import com.xueli.application.view.bank.filter.BankFilterActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 历年真题，模拟试题
 * Created by pingan on 2018/3/13.
 */

public class BankListActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, RecycleRefreshLoadLayout.OnLoadListener {

    private RecycleRefreshLoadLayout refreshLoadLayout;
    private ExpendRecycleView expendRecycleView;
    private List<String> datas;
    private int ACTION_FILTER = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String title = getIntent().getStringExtra(ConstantStr.KEY_BUNDLE_STR);
        setLayoutAndToolbar(R.layout.bank_list_activity, title);
        refreshLoadLayout = findViewById(R.id.rcrLayout);
        expendRecycleView = findViewById(R.id.expendRv);
        refreshLoadLayout.setColorSchemeColors(findColorById(R.color.colorPrimary));
        expendRecycleView.setLayoutManager(new GridLayoutManager(this, 1));
        datas = new ArrayList<>();
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

            }
        });
        refreshLoadLayout.setOnRefreshListener(this);
        refreshLoadLayout.setOnLoadListener(this);
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
            Intent intent = new Intent(this, BankFilterActivity.class);
            startActivityForResult(intent, ACTION_FILTER);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ACTION_FILTER && resultCode == Activity.RESULT_OK) {

        }
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }
}
