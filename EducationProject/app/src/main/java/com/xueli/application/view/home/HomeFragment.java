package com.xueli.application.view.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.library.utils.GlideUtils;
import com.xueli.application.R;
import com.xueli.application.app.App;
import com.xueli.application.common.ConstantStr;
import com.xueli.application.mode.bean.study.StudyMessage;
import com.xueli.application.mode.study.StudyRepository;
import com.xueli.application.view.MvpFragment;
import com.xueli.application.view.enrol.EnrolActivity;
import com.xueli.application.view.study.StudyListActivity;
import com.xueli.application.view.web.MessageDetailActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页
 * Created by pingan on 2018/3/4.
 */

public class HomeFragment extends MvpFragment implements View.OnClickListener, HomeContract.View {

    private ConvenientBanner convenientBanner;
    private LinearLayout llMessage;

    private List<Integer> localImages;
    private List<StudyMessage> messageList;
    private HomeContract.Presenter mPresenter;

    public static HomeFragment newInstance() {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new HomePresenter(StudyRepository.getRepository(getActivity()), this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.home_fragment, container, false);
        convenientBanner = rootView.findViewById(R.id.convenientBanner);
        localImages = new ArrayList<>();
        messageList = new ArrayList<>();

        rootView.findViewById(R.id.tvSignUp).setOnClickListener(this);
        llMessage = rootView.findViewById(R.id.llMessage);

        rootView.findViewById(R.id.llSignUpTime).setTag(R.id.tag_id, 1L);
        rootView.findViewById(R.id.llSignUpTime).setTag(R.id.tag_title, "报名时间");
        rootView.findViewById(R.id.llSignUpTime).setOnClickListener(clickListener);

        rootView.findViewById(R.id.llSignUpCondition).setTag(R.id.tag_id, 2L);
        rootView.findViewById(R.id.llSignUpCondition).setTag(R.id.tag_title, "报名条件");
        rootView.findViewById(R.id.llSignUpCondition).setOnClickListener(clickListener);

        rootView.findViewById(R.id.llSchoolMajor).setTag(R.id.tag_id, 3L);
        rootView.findViewById(R.id.llSchoolMajor).setTag(R.id.tag_title, "院校专业");
        rootView.findViewById(R.id.llSchoolMajor).setOnClickListener(clickListener);

        rootView.findViewById(R.id.llExamTime).setTag(R.id.tag_id, 4L);
        rootView.findViewById(R.id.llExamTime).setTag(R.id.tag_title, "考试时间");
        rootView.findViewById(R.id.llExamTime).setOnClickListener(clickListener);

        rootView.findViewById(R.id.llEducation).setTag(R.id.tag_id, 5L);
        rootView.findViewById(R.id.llEducation).setTag(R.id.tag_title, "学历介绍");
        rootView.findViewById(R.id.llEducation).setOnClickListener(clickListener);

        rootView.findViewById(R.id.llCourse).setTag(R.id.tag_id, 6L);
        rootView.findViewById(R.id.llCourse).setTag(R.id.tag_title, "开设课程");
        rootView.findViewById(R.id.llCourse).setOnClickListener(clickListener);

        rootView.findViewById(R.id.llConsultation).setTag(R.id.tag_id, 7L);
        rootView.findViewById(R.id.llConsultation).setTag(R.id.tag_title, "招生咨询");
        rootView.findViewById(R.id.llConsultation).setOnClickListener(clickListener);

        rootView.findViewById(R.id.llEducationQuery).setTag(R.id.tag_id, 8L);
        rootView.findViewById(R.id.llEducationQuery).setTag(R.id.tag_title, "学历查询");
        rootView.findViewById(R.id.llEducationQuery).setOnClickListener(clickListener);

        rootView.findViewById(R.id.llDegreeEnglish).setTag(R.id.tag_id, 5L);
        rootView.findViewById(R.id.llDegreeEnglish).setTag(R.id.tag_title, "学位英语");
        rootView.findViewById(R.id.llDegreeEnglish).setOnClickListener(studyClick);

        rootView.findViewById(R.id.llComputerTrain).setTag(R.id.tag_id, 6L);
        rootView.findViewById(R.id.llComputerTrain).setTag(R.id.tag_title, "计算机培训");
        rootView.findViewById(R.id.llComputerTrain).setOnClickListener(studyClick);
        noHeaderAd();
        mPresenter.getHeaderAd();
        mPresenter.getMessage();
        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvSignUp:
                startActivity(new Intent(getActivity(), EnrolActivity.class));
                break;
        }
    }

    @Override
    public void showHeaderAd(final List<StudyMessage> list) {
        messageList.clear();
        messageList.addAll(list);
        convenientBanner.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                long id = list.get(position).getId();
                String title = list.get(position).getTitle();
                Intent intent = new Intent(getActivity(), StudyListActivity.class);
                intent.putExtra(ConstantStr.KEY_BUNDLE_LONG, id);
                intent.putExtra(ConstantStr.KEY_BUNDLE_STR, title);
                startActivity(intent);
            }
        });
        convenientBanner.setPages(new CBViewHolderCreator<ImageHolderView>() {
            @Override
            public ImageHolderView createHolder() {
                return new ImageHolderView();
            }
        }, messageList).setPageIndicator(new int[]{R.drawable.shape_circle_blue, R.drawable.shape_circle_whit})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);
    }

    @Override
    public void showMessageList(List<StudyMessage> list) {
        llMessage.setVisibility(View.VISIBLE);
    }

    @Override
    public void noMessage() {
        llMessage.setVisibility(View.GONE);
    }

    @Override
    public void noHeaderAd() {
        localImages.clear();
        localImages.add(R.drawable.icon_banner1);
        convenientBanner.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                startActivity(new Intent(getActivity(), EnrolActivity.class));
            }
        });
        convenientBanner.setPages(new CBViewHolderCreator<LocalImageHolderView>() {
            @Override
            public LocalImageHolderView createHolder() {
                return new LocalImageHolderView();
            }
        }, localImages).setPageIndicator(new int[]{R.drawable.shape_circle_blue, R.drawable.shape_circle_whit})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);
    }

    @Override
    public void showNewVersion() {

    }

    @Override
    public void showMessage(String message) {
        App.getInstance().showToast(message);
    }

    @Override
    public void setPresenter(HomeContract.Presenter presenter) {
        mPresenter = presenter;
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

    public class ImageHolderView implements Holder<StudyMessage> {

        private ImageView imageView;

        @Override
        public View createView(Context context) {
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, final int position, StudyMessage data) {
            GlideUtils.ShowImage(context, data.getFirstPic(), imageView, R.drawable.icon_banner1);
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


    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            long id = (long) v.getTag(R.id.tag_id);
            String title = (String) v.getTag(R.id.tag_title);
            Intent intent = new Intent(getActivity(), MessageDetailActivity.class);
            intent.putExtra(ConstantStr.KEY_BUNDLE_LONG, id);
            intent.putExtra(ConstantStr.KEY_BUNDLE_STR, title);
            startActivity(intent);
        }
    };

    View.OnClickListener studyClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            long id = (long) v.getTag(R.id.tag_id);
            String title = (String) v.getTag(R.id.tag_title);
            Intent intent = new Intent(getActivity(), StudyListActivity.class);
            intent.putExtra(ConstantStr.KEY_BUNDLE_LONG, id);
            intent.putExtra(ConstantStr.KEY_BUNDLE_STR, title);
            startActivity(intent);
        }
    };
}
