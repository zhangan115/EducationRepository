package com.xueli.application.view.bank.examination;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.library.utils.SPHelper;
import com.xueli.application.R;
import com.xueli.application.app.App;
import com.xueli.application.common.ConstantStr;
import com.xueli.application.mode.bean.exam.PaperSectionList;
import com.xueli.application.mode.bean.exam.PaperSections;
import com.xueli.application.mode.exam.ExamRepository;
import com.xueli.application.view.MvpActivity;
import com.xueli.application.view.bank.answer.AnswerActivity;
import com.xueli.application.view.subject.SubjectFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 考试
 * Created by pingan on 2018/3/19.
 */

public class ExaminationActivity extends MvpActivity<ExaminationContract.Presenter> implements ViewPager.OnPageChangeListener
        , ExaminationContract.View, IDataChange {
    //view
    private ViewPager viewPager;
    private TextView tvOrder, tvCollection;
    private TextView tvExamState, tvExamScore;
    private ImageView ivCollection;
    private MaterialDialog finishDialog, cancelDialog;
    private RecyclerView subjectRecycleView;
    private LinearLayout llNote;
    //data
    private List<PaperSections> uploadData;
    private ArrayList<PaperSections> datas;
    private ArrayList<PaperSectionList> examListBeans;
    private boolean showAnswer, isFaultExam;
    private int currentPosition = -1;
    private String NOTE = "note";
    private int totalScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        datas = new ArrayList<>();
        examListBeans = new ArrayList<>();
        if (getIntent() != null) {
            ArrayList<PaperSections> datas = getIntent().getParcelableArrayListExtra(ConstantStr.KEY_BUNDLE_LIST);
            if (datas != null) {
                this.datas.addAll(datas);
            }
            ArrayList<PaperSectionList> examListBeans = getIntent().getParcelableArrayListExtra(ConstantStr.KEY_BUNDLE_LIST_1);
            if (examListBeans != null) {
                this.examListBeans.addAll(examListBeans);
            }
            currentPosition = getIntent().getIntExtra(ConstantStr.KEY_BUNDLE_INT, -1);
            isFaultExam = getIntent().getBooleanExtra(ConstantStr.KEY_BUNDLE_BOOLEAN, false);
        }
        showAnswer = isFaultExam || currentPosition != -1;
        setLayoutAndToolbar(R.layout.examination_activity, showAnswer ? "答案" : "考试");
        uploadData = new ArrayList<>();
        viewPager = findViewById(R.id.view_pager);
        llNote = findViewById(R.id.llNote);
        viewPager.setOffscreenPageLimit(4);
        viewPager.addOnPageChangeListener(this);
        tvOrder = findViewById(R.id.tvOrder);
        tvExamState = findViewById(R.id.tvExamState);
        tvExamScore = findViewById(R.id.tvExamScore);
        ivCollection = findViewById(R.id.ivCollection);
        tvCollection = findViewById(R.id.tvCollection);
        subjectRecycleView = findViewById(R.id.recycleSubject);
        final GridLayoutManager manager = new GridLayoutManager(this.getApplicationContext(), 6);
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
        SubjectAdapter adapter = new SubjectAdapter(examListBeans, getApplicationContext());
        adapter.setOnItemClickListener(new SubjectAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(long id) {
                for (int i = 0; i < datas.size(); i++) {
                    if (datas.get(i).getId() == id) {
                        if (i != currentPosition) {
                            viewPager.setCurrentItem(i, true);
                        }
                        break;
                    }
                }
                if (subjectRecycleView.getVisibility() == View.VISIBLE) {
                    subjectRecycleView.setVisibility(View.GONE);
                } else {
                    subjectRecycleView.setVisibility(View.VISIBLE);
                }
            }
        });
        subjectRecycleView.setAdapter(adapter);
        findViewById(R.id.llCollection).setOnClickListener(this);
        findViewById(R.id.llOrder).setOnClickListener(this);
        long examId = getIntent().getLongExtra(ConstantStr.KEY_BUNDLE_LONG, -1);
        if (!showAnswer) {
            currentPosition = 0;
            mPresenter.getPaperSections(examId);
        } else if (isFaultExam) {
            currentPosition = 0;
            findViewById(R.id.llExamState).setVisibility(View.GONE);
            mPresenter.getFaultExamPaperSections(examId);
        } else {
            viewPager.setAdapter(new Adapter(getSupportFragmentManager()));
            viewPager.setCurrentItem(currentPosition);
            bottomDataChange(currentPosition);
        }
        llNote.setVisibility(SPHelper.readBoolean(getApplicationContext()
                , ConstantStr.USER_DATA, NOTE, false) ? View.GONE : View.VISIBLE);
        findViewById(R.id.ivKnow).setOnClickListener(this);
    }

    @Override
    protected void onBindPresenter() {
        new ExaminationPresenter(this, ExamRepository.getRepository(getApplicationContext()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.submit_examination, menu);
        return !showAnswer;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        uploadData.clear();
        if (id == R.id.action_submit) {
            for (int i = 0; i < datas.size(); i++) {
                if (datas.get(i).isFinish()) {
                    uploadData.add(datas.get(i));
                }
            }
            if (uploadData.size() == datas.size()) {
                showUploadDataDialog();
            } else {
                showCancelDialog();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llCollection:
                if (!datas.get(currentPosition).isCollect() && datas.get(currentPosition).getId() != 0) {
                    mPresenter.collectPaper(datas.get(currentPosition).getId()
                            , App.getInstance().getCurrentUser().getId());
                }
                break;
            case R.id.llOrder:
                if (subjectRecycleView.getVisibility() == View.VISIBLE) {
                    subjectRecycleView.setVisibility(View.GONE);
                } else {
                    subjectRecycleView.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.ivKnow:
                SPHelper.write(getApplicationContext()
                        , ConstantStr.USER_DATA, NOTE, true);
                llNote.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        currentPosition = position;
        bottomDataChange(position);
    }

    private void bottomDataChange(int position) {
        boolean isCollection = datas.get(position).isCollect();
        if (isCollection) {
            ivCollection.setImageDrawable(findDrawById(R.drawable.icon_btn_already_favorite));
            tvCollection.setText("已收藏");
        } else {
            ivCollection.setImageDrawable(findDrawById(R.drawable.icon_btn_favorite));
            tvCollection.setText("收藏");
        }
        String str = (position + 1) + "/" + datas.size() + "题序";
        tvOrder.setText(str);
        tvExamState.setText(String.valueOf(position + 1) + "/" + datas.size());
        tvExamScore.setText("本题" + datas.get(position).getScore() + "分,共" + totalScore + "分");
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public void showUploadDataDialog() {
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
                uploadData();
            }
        });
        if (finishDialog == null) {
            finishDialog = new MaterialDialog.Builder(ExaminationActivity.this)
                    .customView(view, false)
                    .build();
        }
        finishDialog.show();
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
                uploadData();
            }
        });
        if (cancelDialog == null) {
            cancelDialog = new MaterialDialog.Builder(ExaminationActivity.this)
                    .customView(view, false)
                    .build();
        }
        cancelDialog.show();
    }

    public void uploadData() {
        if (uploadData.size() == 0) {
            handExam();
        } else {
            mPresenter.uploadData(uploadData);
        }
    }

    public void handExam() {
        Intent intent = new Intent(ExaminationActivity.this, AnswerActivity.class);
        intent.putParcelableArrayListExtra(ConstantStr.KEY_BUNDLE_LIST, datas);
        intent.putParcelableArrayListExtra(ConstantStr.KEY_BUNDLE_LIST_1, examListBeans);
        startActivity(intent);
        finish();
    }

    @Override
    public void showData(List<PaperSections> list) {
        datas.clear();
        datas.addAll(list);
        viewPager.setAdapter(new Adapter(getSupportFragmentManager()));
        for (int i = 0; i < list.size(); i++) {
            totalScore = totalScore + list.get(i).getScore();
        }
        bottomDataChange(0);
    }

    @Override
    public void showPaperSectionListData(List<PaperSectionList> list) {
        examListBeans.clear();
        examListBeans.addAll(list);
        subjectRecycleView.getAdapter().notifyDataSetChanged();
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
    public void collectStateChange(long id, boolean isCollect) {
        for (int i = 0; i < datas.size(); i++) {
            if (datas.get(i).getId() == id) {
                datas.get(i).setCollect(isCollect);
                if (i == currentPosition) {
                    bottomDataChange(currentPosition);
                }
            }
        }
    }

    @Override
    public void showUploadDialog() {
        showProgressDialog("提交中...");
    }

    @Override
    public void hideUploadDialog() {
        hideProgressDialog();
    }

    @Override
    public void uploadSuccess() {
        App.getInstance().showToast("交卷成功");
        handExam();
    }

    @Override
    public void setPresenter(ExaminationContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onDataChange(PaperSections sectionOptions, int position, boolean isFinish) {
        sectionOptions.setFinish(isFinish);
        datas.remove(position);
        datas.add(position, sectionOptions);
        for (int i = 0; i < examListBeans.size(); i++) {
            if (examListBeans.get(i).getType() == 0) {
                continue;
            }
            if (datas.get(position).getId() == examListBeans.get(i).getId()) {
                examListBeans.get(i).setFinish(isFinish);
                examListBeans.get(i).setRight(sectionOptions.isbResult());
                subjectRecycleView.getAdapter().notifyDataSetChanged();
                break;
            }
        }
    }

    private class Adapter extends FragmentStatePagerAdapter {

        Adapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return SubjectFragment.newInstance(datas.get(position), position, showAnswer);
        }

        @Override
        public int getCount() {
            return datas.size();
        }
    }

}
