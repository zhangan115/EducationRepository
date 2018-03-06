package com.xueli.application.view.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xueli.application.R;
import com.xueli.application.view.MvpFragment;

/**
 * 首页
 * Created by pingan on 2018/3/4.
 */

public class HomeFragment extends MvpFragment implements View.OnClickListener {

    public static HomeFragment newInstance() {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.home_fragment, container, false);
        rootView.findViewById(R.id.tvSignUp).setOnClickListener(this);
        rootView.findViewById(R.id.llSignUpTime).setOnClickListener(this);
        rootView.findViewById(R.id.llSignUpCondition).setOnClickListener(this);
        rootView.findViewById(R.id.llSchoolMajor).setOnClickListener(this);
        rootView.findViewById(R.id.llExamTime).setOnClickListener(this);
        rootView.findViewById(R.id.llEducation).setOnClickListener(this);
        rootView.findViewById(R.id.llCourse).setOnClickListener(this);
        rootView.findViewById(R.id.llConsultation).setOnClickListener(this);
        rootView.findViewById(R.id.llEducationQuery).setOnClickListener(this);
        rootView.findViewById(R.id.llDegreeEnglish).setOnClickListener(this);
        rootView.findViewById(R.id.llComputerTrain).setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvSignUp:
                break;
            case R.id.llSignUpTime:
                break;
            case R.id.llSignUpCondition:
                break;
            case R.id.llSchoolMajor:
                break;
            case R.id.llExamTime:
                break;
            case R.id.llEducation:
                break;
            case R.id.llCourse:
                break;
            case R.id.llConsultation:
                break;
            case R.id.llEducationQuery:
                break;
            case R.id.llDegreeEnglish:
                break;
            case R.id.llComputerTrain:
                break;
        }
    }
}
