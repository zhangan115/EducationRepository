package com.xueli.application.view.subject;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.library.widget.HtmlTextView;
import com.orhanobut.logger.Logger;
import com.xueli.application.R;
import com.xueli.application.common.ConstantStr;
import com.xueli.application.mode.bean.exam.PaperSections;
import com.xueli.application.view.MvpFragment;

/**
 * 考试题目
 * Created by pingan on 2018/3/19.
 */

public class SubjectFragment extends MvpFragment {


    private PaperSections paperSections;

    public static SubjectFragment newInstance(PaperSections content) {
        Bundle args = new Bundle();
        args.putParcelable(ConstantStr.KEY_BUNDLE_OBJECT, content);
        SubjectFragment fragment = new SubjectFragment();
        fragment.setArguments(args);
        return fragment;
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
        }
        TextView tvSectionType = rootView.findViewById(R.id.tvSectionType);
        TextView tvQuestion = rootView.findViewById(R.id.tvQuestion);
        LinearLayout llOptions = rootView.findViewById(R.id.llOptions);
        LinearLayout llAnswer = rootView.findViewById(R.id.llAnswer);
        tvSectionType.setText(paperSections.getPaperSectionTitle());
        tvQuestion.setText(Html.fromHtml(paperSections.getQuestion()));

        return rootView;
    }

}
