package com.xueli.application.view.bank.examination;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.orhanobut.logger.Logger;
import com.xueli.application.R;
import com.xueli.application.view.BaseActivity;
import com.xueli.application.view.subject.SubjectFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 考试
 * Created by pingan on 2018/3/19.
 */

public class ExaminationActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    private ViewPager viewPager;
    private List<String> datas;
    private TextView tvOrder, tvCollection;
    private ImageView ivCollection;
    private MaterialDialog finishDialog, cancelDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayoutAndToolbar(R.layout.examination_activity, "考试");
        datas = new ArrayList<>();
        for (int i = 1; i < 21; i++) {
            datas.add("第" + i + "题");
        }
        viewPager = findViewById(R.id.view_pager);
        viewPager.setOffscreenPageLimit(4);
        viewPager.addOnPageChangeListener(this);
        viewPager.setAdapter(new Adapter(getSupportFragmentManager()));
        tvOrder = findViewById(R.id.tvOrder);
        ivCollection = findViewById(R.id.ivCollection);
        tvCollection = findViewById(R.id.tvCollection);

        findViewById(R.id.llCollection).setOnClickListener(this);
        findViewById(R.id.llOrder).setOnClickListener(this);
        bottomDataChange(0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.submit_examination, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_submit) {
            @SuppressLint("InflateParams")
            View view = getLayoutInflater().inflate(R.layout.complete_exam_dialog, null);
            view.findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finishDialog.dismiss();
                }
            });
            view.findViewById(R.id.btnFinish).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finishDialog.dismiss();
                    // TODO: 2018/3/20 交卷
                }
            });
            if (finishDialog == null) {
                finishDialog = new MaterialDialog.Builder(ExaminationActivity.this)
                        .customView(view, false)
                        .build();
            }
            finishDialog.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llCollection:

                break;
            case R.id.llOrder:

                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        bottomDataChange(position);
    }

    private void bottomDataChange(int position) {
        boolean isCollection = true;
        if (isCollection) {
            ivCollection.setImageDrawable(findDrawById(R.drawable.icon_btn_already_favorite));
            tvCollection.setText("已收藏");
        } else {
            ivCollection.setImageDrawable(findDrawById(R.drawable.icon_btn_favorite));
            tvCollection.setText("收藏");
        }
        String str = (position + 1) + "/" + datas.size() + "题序";
        tvOrder.setText(str);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onBackPressed() {
        showCancelDialog();
    }

    private void showCancelDialog() {
        @SuppressLint("InflateParams")
        View view = getLayoutInflater().inflate(R.layout.cancel_exam_dialog, null);
        view.findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelDialog.dismiss();
            }
        });
        view.findViewById(R.id.btnFinish).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelDialog.dismiss();
                finish();
            }
        });
        if (cancelDialog == null) {
            cancelDialog = new MaterialDialog.Builder(ExaminationActivity.this)
                    .customView(view, false)
                    .build();
        }
        cancelDialog.show();
    }

    @Override
    public void toolBarClick() {
        showCancelDialog();
    }

    private class Adapter extends FragmentStatePagerAdapter {

        Adapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return SubjectFragment.newInstance(datas.get(position));
        }

        @Override
        public int getCount() {
            return datas.size();
        }
    }

}
