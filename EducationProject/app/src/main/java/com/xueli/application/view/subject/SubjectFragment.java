package com.xueli.application.view.subject;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.library.widget.HtmlTextView;
import com.xueli.application.R;
import com.xueli.application.common.ConstantStr;
import com.xueli.application.mode.bean.exam.PaperSections;
import com.xueli.application.mode.bean.exam.SectionOption;
import com.xueli.application.view.MvpFragment;
import com.xueli.application.view.WebActivity;
import com.xueli.application.view.bank.examination.IDataChange;
import com.xueli.application.widget.InputTypeAnswerLayout;
import com.xueli.application.widget.InputTypeLayout;
import com.xueli.application.widget.SingleChooseTypeLayout;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * 考试题目
 * Created by pingan on 2018/3/19.
 */

public class SubjectFragment extends MvpFragment implements SingleChooseTypeLayout.IClick, InputTypeLayout.IEnter, InputTypeAnswerLayout.IEnter {

    private List<SingleChooseTypeLayout> chooseTypeLayouts;
    private List<InputTypeLayout> inputTypeLayouts;
    private List<InputTypeAnswerLayout> inputTypeAnsowerLayouts;
    private PaperSections paperSections;
    private List<SectionOption> sectionOptions;
    private boolean showAnswer;
    private IDataChange dataChange;
    private int position;
    private HtmlTextView htmlTextView;

    public static SubjectFragment newInstance(PaperSections content, int position, boolean showAnswer) {
        Bundle args = new Bundle();
        args.putParcelable(ConstantStr.KEY_BUNDLE_OBJECT, content);
        args.putBoolean(ConstantStr.KEY_BUNDLE_BOOLEAN, showAnswer);
        args.putInt(ConstantStr.KEY_BUNDLE_INT, position);
        SubjectFragment fragment = new SubjectFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof IDataChange) {
            dataChange = (IDataChange) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.subject_fragment, container, false);
        Bundle bundle = getArguments();
        if (bundle != null) {
            paperSections = bundle.getParcelable(ConstantStr.KEY_BUNDLE_OBJECT);
            showAnswer = bundle.getBoolean(ConstantStr.KEY_BUNDLE_BOOLEAN);
            position = bundle.getInt(ConstantStr.KEY_BUNDLE_INT);
        }
        if (paperSections != null) {
            chooseTypeLayouts = new ArrayList<>();
            inputTypeLayouts = new ArrayList<>();
            inputTypeAnsowerLayouts = new ArrayList<>();
            htmlTextView = rootView.findViewById(R.id.tvQuestion);
            TextView tvSectionType = rootView.findViewById(R.id.tvSectionType);
            TextView tvAnswer = rootView.findViewById(R.id.tvAnswer);
            TextView tvJieShi = rootView.findViewById(R.id.tvJieShi);
            LinearLayout llOptions = rootView.findViewById(R.id.llOptions);
            if (paperSections.getFlag() == 1) {
                tvSectionType.setText("填空题");
            } else if (paperSections.getFlag() == 2) {
                tvSectionType.setText("选择题");
            } else if (paperSections.getFlag() == 3) {
                tvSectionType.setText("多选题");
            } else if (paperSections.getFlag() == 4) {
                tvSectionType.setText("判断题");
            } else if (paperSections.getFlag() == 5) {
                tvSectionType.setText("简答题");
            }
            htmlTextView.setHtmlFromString(paperSections.getQuestion(), false);
            Type type = new TypeToken<List<SectionOption>>() {
            }.getType();
            if (paperSections.getSectionOptions() == null) {
                sectionOptions = new Gson().fromJson(paperSections.getOptions(), type);
                paperSections.setSectionOptions(sectionOptions);
            } else {
                sectionOptions = paperSections.getSectionOptions();
            }
            if (showAnswer) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < sectionOptions.size(); i++) {
                    if (sectionOptions.get(i).getOptSta()) {
                        sb.append(sectionOptions.get(i).getOptVal());
                        sb.append("\n");
                    }
                }
                tvAnswer.setVisibility(View.VISIBLE);
                tvAnswer.setText("正确答案：" + sb.toString());
                if (!TextUtils.isEmpty(paperSections.getJieshi())) {
                    tvJieShi.setVisibility(View.VISIBLE);
                    tvJieShi.setText("答案解析：" + paperSections.getJieshi());
                } else {
                    tvJieShi.setVisibility(View.GONE);
                }
            }
            LinearLayout llAnswer = rootView.findViewById(R.id.llAnswer);
            if (showAnswer) {
                llAnswer.setVisibility(View.VISIBLE);
            } else {
                llAnswer.setVisibility(View.GONE);
            }
            llOptions.removeAllViews();
            if (paperSections.getFlag() == 1) {//填空题
                for (int i = 0; i < sectionOptions.size(); i++) {
                    InputTypeLayout typeLayout = new InputTypeLayout(getActivity());
                    typeLayout.setData(sectionOptions.get(i), i, showAnswer ? null : this);
                    inputTypeLayouts.add(typeLayout);
                    llOptions.addView(typeLayout);
                }
            } else if (paperSections.getFlag() == 2) {//单项选择题
                for (int i = 0; i < sectionOptions.size(); i++) {
                    SingleChooseTypeLayout typeLayout = new SingleChooseTypeLayout(getActivity());
                    typeLayout.setData(sectionOptions.get(i), i, paperSections.getFlag(), showAnswer ? null : this);
                    chooseTypeLayouts.add(typeLayout);
                    llOptions.addView(typeLayout);
                }
            } else if (paperSections.getFlag() == 3) {//多项选择题
                for (int i = 0; i < sectionOptions.size(); i++) {
                    SingleChooseTypeLayout typeLayout = new SingleChooseTypeLayout(getActivity());
                    typeLayout.setData(sectionOptions.get(i), i, paperSections.getFlag(), showAnswer ? null : this);
                    chooseTypeLayouts.add(typeLayout);
                    llOptions.addView(typeLayout);
                }
            } else if (paperSections.getFlag() == 4) {// 判断题
                for (int i = 0; i < sectionOptions.size(); i++) {
                    SingleChooseTypeLayout typeLayout = new SingleChooseTypeLayout(getActivity());
                    typeLayout.setData(sectionOptions.get(i), i, paperSections.getFlag(), showAnswer ? null : this);
                    chooseTypeLayouts.add(typeLayout);
                    llOptions.addView(typeLayout);
                }
            } else if (paperSections.getFlag() == 5) {//简答题
                for (int i = 0; i < sectionOptions.size(); i++) {
                    InputTypeAnswerLayout typeLayout = new InputTypeAnswerLayout(getActivity());
                    typeLayout.setData(sectionOptions.get(i), i, showAnswer ? null : this);
                    inputTypeAnsowerLayouts.add(typeLayout);
                    llOptions.addView(typeLayout);
                }
            }
        }
        return rootView;
    }

    @Override
    public void onItemClick(int position, int flag) {
        if (dataChange == null) {
            return;
        }
        if (flag == 2 || flag == 4) {
            for (int i = 0; i < sectionOptions.size(); i++) {
                if (i == position) {
                    sectionOptions.get(position).setChoose(!sectionOptions.get(position).isChoose());
                } else {
                    sectionOptions.get(i).setChoose(false);
                }
                chooseTypeLayouts.get(i).refreshUI(sectionOptions.get(i), i);
            }
        } else {
            sectionOptions.get(position).setChoose(!sectionOptions.get(position).isChoose());
            chooseTypeLayouts.get(position).refreshUI(sectionOptions.get(position), position);
        }
        if (flag == 2 || flag == 4) {
            paperSections.setbResult(sectionOptions.get(position).getOptSta()
                    && sectionOptions.get(position).isChoose());
        }
        if (flag == 3) {
            int chooseCount = 0, rightCount = 0;
            for (int i = 0; i < sectionOptions.size(); i++) {
                if (sectionOptions.get(position).getOptSta()
                        && sectionOptions.get(position).isChoose()) {
                    chooseCount++;
                }
                if (sectionOptions.get(position).getOptSta()) {
                    rightCount++;
                }
            }
            paperSections.setbResult(chooseCount == rightCount);
        }
        int chooseCount = 0;
        for (int i = 0; i < sectionOptions.size(); i++) {
            if (sectionOptions.get(i).isChoose()) {
                chooseCount++;
                break;
            }
        }
        dataChange.onDataChange(paperSections, this.position, chooseCount != 0);
    }

    @Override
    public void onEnter() {
        if (dataChange == null) {
            return;
        }
        int rightCount = 0;
        int finishCount = 0;
        for (int i = 0; i < sectionOptions.size(); i++) {
            if (!TextUtils.isEmpty(sectionOptions.get(i).getValue())) {
                finishCount++;
            }
            if (sectionOptions.get(i).getOptSta() && sectionOptions.get(i).getValue()
                    .equals(sectionOptions.get(i).getOptVal())) {
                rightCount++;
            }
        }
        paperSections.setbResult(rightCount == sectionOptions.size());
        dataChange.onDataChange(paperSections, this.position
                , finishCount == sectionOptions.size());
    }

}
