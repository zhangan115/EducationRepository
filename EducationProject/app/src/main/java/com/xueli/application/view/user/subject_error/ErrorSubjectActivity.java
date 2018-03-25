package com.xueli.application.view.user.subject_error;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.GridLayoutManager;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.library.adapter.RVAdapter;
import com.library.widget.ExpendRecycleView;
import com.xueli.application.R;
import com.xueli.application.app.App;
import com.xueli.application.mode.exam.ExamRepository;
import com.xueli.application.view.MvpActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 过往答错
 * Created by pingan on 2018/3/25.
 */

public class ErrorSubjectActivity extends MvpActivity<ErrorSubjectContract.Presenter> implements ErrorSubjectContract.View {

    private ExpendRecycleView mRecycleView;
    private RelativeLayout noDataLayout;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.bankFilterStyle);
        transparentStatusBar();
        setLayoutAndToolbar(R.layout.error_subject_activity, "过往答错");
        mRecycleView = findViewById(R.id.recycleErrorSubject);
        noDataLayout = findViewById(R.id.rlNoData);
        drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {
                App.getInstance().hideSoftKeyBoard(ErrorSubjectActivity.this);
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
        mRecycleView.setLayoutManager(new GridLayoutManager(this, 1));
        List<String> datas = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            datas.add("===>" + 1);
        }
        RVAdapter<String> adapter = new RVAdapter<String>(mRecycleView, datas, R.layout.subject_error_item) {
            @Override
            public void showData(ViewHolder vHolder, String data, int position) {
                TextView errorSubject = (TextView) vHolder.getView(R.id.tvSubjectContent);
                errorSubject.setText(data);
            }
        };
        mRecycleView.setAdapter(adapter);
        adapter.setOnItemClickListener(new RVAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }
        });
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
    protected void onBindPresenter() {
        new ErrorSubjectPresenter(ExamRepository.getRepository(this), this);
    }

    @Override
    public void showMessage(String message) {
        App.getInstance().showToast(message);
    }

    @Override
    public void showData() {
        noDataLayout.setVisibility(View.GONE);
        // TODO: 2018/3/25
        mRecycleView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void noData() {
        noDataLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void setPresenter(ErrorSubjectContract.Presenter presenter) {

    }
}
