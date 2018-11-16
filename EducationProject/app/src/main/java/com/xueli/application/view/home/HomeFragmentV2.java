package com.xueli.application.view.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.library.utils.DisplayUtil;
import com.library.utils.GlideUtils;
import com.library.utils.SPHelper;
import com.xueli.application.R;
import com.xueli.application.app.App;
import com.xueli.application.common.ConstantStr;
import com.xueli.application.mode.bean.study.StudyMessage;
import com.xueli.application.mode.study.StudyRepository;
import com.xueli.application.view.MvpFragment;
import com.xueli.application.view.enrol.EnrolActivity;
import com.xueli.application.view.home.hot.HotListActivity;
import com.xueli.application.view.home.pay.PayActivity;
import com.xueli.application.view.home.school.SchoolListActivity;
import com.xueli.application.view.study.StudyListActivity;
import com.xueli.application.view.web.MessageDetailActivity;
import com.xueli.application.widget.HotItemLayout;
import com.xueli.application.widget.marqueeText;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页
 * Created by pingan on 2018/3/4.
 */

public class HomeFragmentV2 extends MvpFragment implements View.OnClickListener, HomeContract.View {

    private ConvenientBanner convenientBanner;
    private LinearLayout llMessage;
    private marqueeText tvNotify;

    private List<Integer> localImages;
    private List<StudyMessage> messageList;
    private HomeContract.Presenter mPresenter;
    private List<StudyMessage> studyMessages;

    public static HomeFragmentV2 newInstance() {
        Bundle args = new Bundle();
        HomeFragmentV2 fragment = new HomeFragmentV2();
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
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.home_fragment_v2, container, false);
        convenientBanner = rootView.findViewById(R.id.convenientBanner);
        tvNotify = rootView.findViewById(R.id.tvNotify);
        localImages = new ArrayList<>();
        messageList = new ArrayList<>();

        rootView.findViewById(R.id.moreSchool).setOnClickListener(this);
        rootView.findViewById(R.id.llSignUpCondition).setOnClickListener(this);

        llMessage = rootView.findViewById(R.id.llMessage);
        llMessage.setOnClickListener(this);

        rootView.findViewById(R.id.llSignUpTime).setTag(R.id.tag_id, 1L);
        rootView.findViewById(R.id.llSignUpTime).setTag(R.id.tag_title, "报名时间");
        rootView.findViewById(R.id.llSignUpTime).setOnClickListener(clickListener);

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
        rootView.findViewById(R.id.llCourse).setTag(R.id.tag_title, "考试技巧");
        rootView.findViewById(R.id.llCourse).setOnClickListener(clickListener);

        rootView.findViewById(R.id.llConsultation).setTag(R.id.tag_id, 7L);
        rootView.findViewById(R.id.llConsultation).setTag(R.id.tag_title, "视频课堂");
        rootView.findViewById(R.id.llConsultation).setOnClickListener(clickListener);

        rootView.findViewById(R.id.llEducationQuery).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MessageDetailActivity.class);
                intent.putExtra(ConstantStr.KEY_BUNDLE_BOOLEAN, true);
                intent.putExtra(ConstantStr.KEY_BUNDLE_STR_1, "http://www.chsi.com.cn");
                intent.putExtra(ConstantStr.KEY_BUNDLE_STR, "学历查询");
                startActivity(intent);
            }
        });
        ImageView icon1 = rootView.findViewById(R.id.icon_1);
        ImageView icon2 = rootView.findViewById(R.id.icon_2);
        if ( getActivity()!=null){
            final int width = (getResources().getDisplayMetrics().widthPixels - DisplayUtil.dip2px(getActivity(), 30)) / 2;
            final int height = width * 15 / 16;
            LinearLayout.LayoutParams params1 = (LinearLayout.LayoutParams) icon1.getLayoutParams();
            if (params1 != null) {
                params1.height = height;
                params1.width = width;
                icon1.setLayoutParams(params1);
            }
            LinearLayout.LayoutParams params2 = (LinearLayout.LayoutParams) icon2.getLayoutParams();
            if (params2 != null) {
                params2.height = height;
                params2.width = width;
                icon2.setLayoutParams(params2);
            }
        }
        noHeaderAd();
        mPresenter.getHeaderAd();
        mPresenter.getMessage();
        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llMessage:
                if (studyMessages == null) {
                    return;
                }
                Intent messageIntent = new Intent(getActivity(), MessageDetailActivity.class);
                messageIntent.putExtra(ConstantStr.KEY_BUNDLE_STR, "通知");
                if (getActivity() != null) {
                    SPHelper.write(getActivity(), ConstantStr.SP_CACHE, ConstantStr.SP_MESSAGE_DETAIL, studyMessages.get(0).getDetail());
                }
                startActivity(messageIntent);
                break;
            case R.id.moreSchool:
                //to show more school
                startActivity(new Intent(getActivity(),SchoolListActivity.class));
                break;
            case R.id.llSignUpCondition:
                //to pay
                startActivity(new Intent(getActivity(),PayActivity.class));
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
                String content = list.get(position).getDetail();
                String title = list.get(position).getTitle();
                Intent intent = new Intent(getActivity(), MessageDetailActivity.class);
                intent.putExtra(ConstantStr.KEY_BUNDLE_STR, title);
                if (getActivity() != null) {
                    SPHelper.write(getActivity(), ConstantStr.SP_CACHE, ConstantStr.SP_MESSAGE_DETAIL, content);
                }
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
    public void showHot(final ArrayList<StudyMessage> list) {
    }

    @Override
    public void showMessageList(List<StudyMessage> list) {
        studyMessages = list;
        llMessage.setVisibility(View.VISIBLE);
        if (list.size() > 0) {
            tvNotify.setText(list.get(0).getTitle());
        }
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
            if (getActivity() != null) {
                SPHelper.write(getActivity(), ConstantStr.SP_CACHE, ConstantStr.SP_MESSAGE_DETAIL, "");
            }
            startActivity(intent);
        }
    };

}
