package com.xueli.application.view.subject;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.library.widget.HtmlTextView;
import com.orhanobut.logger.Logger;
import com.xueli.application.R;
import com.xueli.application.common.ConstantStr;
import com.xueli.application.mode.bean.exam.PaperSections;
import com.xueli.application.mode.bean.exam.SectionOption;
import com.xueli.application.view.MvpFragment;
import com.xueli.application.view.bank.examination.ExaminationActivity;
import com.xueli.application.view.bank.examination.IDataChange;
import com.xueli.application.widget.InputTypeLayout;
import com.xueli.application.widget.SingleChooseTypeLayout;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * 考试题目
 * Created by pingan on 2018/3/19.
 */

public class SubjectFragment extends MvpFragment implements SingleChooseTypeLayout.IClick, InputTypeLayout.IEnter {

    private List<SingleChooseTypeLayout> chooseTypeLayouts;
    private List<InputTypeLayout> inputTypeLayouts;
    private PaperSections paperSections;
    private List<SectionOption> sectionOptions;
    private boolean showAnswer;
    private IDataChange dataChange;
    private int position;

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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        chooseTypeLayouts = new ArrayList<>();
        inputTypeLayouts = new ArrayList<>();
        TextView tvSectionType = rootView.findViewById(R.id.tvSectionType);
        TextView tvQuestion = rootView.findViewById(R.id.tvQuestion);
        TextView tvAnswer = rootView.findViewById(R.id.tvAnswer);
        LinearLayout llOptions = rootView.findViewById(R.id.llOptions);
        tvSectionType.setText(paperSections.getPaperSectionTitle());
        tvQuestion.setText(Html.fromHtml(paperSections.getQuestion()));
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
                typeLayout.setData(sectionOptions.get(i), String.valueOf((i + 1)), showAnswer ? null : this);
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
    public void onEnter(boolean isFinish, boolean isRight) {
        if (dataChange == null) {
            return;
        }
        paperSections.setbResult(isRight);
        dataChange.onDataChange(paperSections, this.position, isFinish);
    }

}
