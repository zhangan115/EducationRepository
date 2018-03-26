package com.xueli.application.view.bank.examination;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.library.adapter.VRVAdapter;
import com.orhanobut.logger.Logger;
import com.xueli.application.R;
import com.xueli.application.app.App;
import com.xueli.application.common.ConstantStr;
import com.xueli.application.mode.bean.exam.PaperSections;
import com.xueli.application.mode.exam.ExamRepository;
import com.xueli.application.view.BaseActivity;
import com.xueli.application.view.MvpActivity;
import com.xueli.application.view.subject.SubjectFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 考试
 * Created by pingan on 2018/3/19.
 */

public class ExaminationActivity extends MvpActivity<ExaminationContract.Presenter> implements ViewPager.OnPageChangeListener, ExaminationContract.View {

    private ViewPager viewPager;
    private List<PaperSections> datas;
    private TextView tvOrder, tvCollection;
    private ImageView ivCollection;
    private MaterialDialog finishDialog, cancelDialog;
    private RecyclerView subjectRecycleView;
    private List<TestData> testData;

    public static class TestData {
        private int type;
        private int position;

        public TestData() {
        }

        public TestData(int type, int position) {
            this.type = type;
            this.position = position;

        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayoutAndToolbar(R.layout.examination_activity, "考试");
        datas = new ArrayList<>();
        viewPager = findViewById(R.id.view_pager);
        viewPager.setOffscreenPageLimit(4);
        viewPager.addOnPageChangeListener(this);
        viewPager.setAdapter(new Adapter(getSupportFragmentManager()));
        tvOrder = findViewById(R.id.tvOrder);
        ivCollection = findViewById(R.id.ivCollection);
        tvCollection = findViewById(R.id.tvCollection);
        subjectRecycleView = findViewById(R.id.recycleSubject);
        testData = new ArrayList<>();
        final GridLayoutManager manager = new GridLayoutManager(this.getApplicationContext(), 6);
        for (int i = 0; i < 40; i++) {
            if (i == 0 || i == 19) {
                testData.add(new TestData(0, i));
            } else {
                testData.add(new TestData(1, i));
            }
        }
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (subjectRecycleView.getAdapter().getItemViewType(position) == 0) {
                    return manager.getSpanCount();
                } else {
                    return 1;
                }
            }
        });
        subjectRecycleView.setLayoutManager(manager);
        SubjectAdapter adapter = new SubjectAdapter(testData, getApplicationContext());
        subjectRecycleView.setAdapter(adapter);
        findViewById(R.id.llCollection).setOnClickListener(this);
        findViewById(R.id.llOrder).setOnClickListener(this);
        bottomDataChange(0);
        long id = getIntent().getLongExtra(ConstantStr.KEY_BUNDLE_LONG, -1);
        mPresenter.getPaperSections(id);
    }

    @Override
    protected void onBindPresenter() {
        new ExaminationPresenter(this, ExamRepository.getRepository(getApplicationContext()));
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
                if (subjectRecycleView.getVisibility() == View.VISIBLE) {
                    subjectRecycleView.setVisibility(View.GONE);
                } else {
                    subjectRecycleView.setVisibility(View.VISIBLE);
                }
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

    @Override
    public void showData(List<PaperSections> list) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void noData() {

    }

    @Override
    public void showMessage(String message) {
        App.getInstance().showToast(message);
    }

    @Override
    public void setPresenter(ExaminationContract.Presenter presenter) {
        mPresenter = presenter;
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
