package com.xueli.application.view.subject;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xueli.application.R;
import com.xueli.application.common.ConstantStr;
import com.xueli.application.mode.bean.exam.PaperSections;
import com.xueli.application.view.MvpFragment;

/**
 * 考试题目
 * Created by pingan on 2018/3/19.
 */

public class SubjectFragment extends MvpFragment {


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
            PaperSections paperSections = bundle.getParcelable(ConstantStr.KEY_BUNDLE_OBJECT);
        }
        return rootView;
    }

}
