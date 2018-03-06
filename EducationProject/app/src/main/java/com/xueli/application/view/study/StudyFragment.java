package com.xueli.application.view.study;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xueli.application.R;
import com.xueli.application.view.MvpFragment;

/**
 * 学习
 * Created by pingan on 2018/3/4.
 */

public class StudyFragment extends MvpFragment {

    public static StudyFragment newInstance() {
        Bundle args = new Bundle();
        StudyFragment fragment = new StudyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.study_fragment, container, false);
        return rootView;
    }
}
