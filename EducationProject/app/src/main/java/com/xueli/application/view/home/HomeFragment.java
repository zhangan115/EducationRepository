package com.xueli.application.view.home;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.xueli.application.R;
import com.xueli.application.view.MvpFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页
 * Created by pingan on 2018/3/4.
 */

public class HomeFragment extends MvpFragment implements View.OnClickListener {

    private ConvenientBanner convenientBanner;
    private List<Integer> localImages;

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
        convenientBanner = rootView.findViewById(R.id.convenientBanner);
        localImages = new ArrayList<>();
        localImages.add(R.drawable.icon_banner1);
        localImages.add(R.drawable.icon_banner1);
        localImages.add(R.drawable.icon_banner1);
        convenientBanner.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }
        });
        convenientBanner.setPages(new CBViewHolderCreator<LocalImageHolderView>() {
            @Override
            public LocalImageHolderView createHolder() {
                return new LocalImageHolderView();
            }
        }, localImages).setPageIndicator(new int[]{R.drawable.shape_circle_blue, R.drawable.shape_circle_whit})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);
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

    public class LocalImageHolderView implements Holder<Integer> {

        private ImageView imageView;

        @Override
        public View createView(Context context) {
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, final int position, Integer data) {
            imageView.setImageResource(data);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        convenientBanner.startTurning(5000);
    }

    @Override
    public void onPause() {
        super.onPause();
        convenientBanner.stopTurning();
    }
}
