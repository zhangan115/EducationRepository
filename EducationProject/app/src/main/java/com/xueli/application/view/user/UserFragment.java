package com.xueli.application.view.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.library.utils.GlideUtils;
import com.library.utils.SPHelper;
import com.xueli.application.R;
import com.xueli.application.app.App;
import com.xueli.application.common.ConstantStr;
import com.xueli.application.util.APKVersionCodeUtils;
import com.xueli.application.util.UserUtils;
import com.xueli.application.view.MvpFragment;
import com.xueli.application.view.login.LoginActivity;
import com.xueli.application.view.user.about_us.AboutUsActivity;
import com.xueli.application.view.user.collection.MyCollectionActivity;
import com.xueli.application.view.user.information.UserInformationActivity;
import com.xueli.application.view.user.point_rule.PointRuleActivity;
import com.xueli.application.view.user.spread_envoy.SpreadEnvoyActivity;
import com.xueli.application.view.user.subject_error.ErrorSubjectActivity;
import com.xueli.application.view.user.vip.VipActivity;

import java.text.MessageFormat;

/**
 * 我的
 * Created by pingan on 2018/3/4.
 */

public class UserFragment extends MvpFragment implements View.OnClickListener {

    public static UserFragment newInstance() {
        Bundle args = new Bundle();
        UserFragment fragment = new UserFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.user_fragment, container, false);
        rootView.findViewById(R.id.llUserInfo).setOnClickListener(this);
        rootView.findViewById(R.id.llAboutUS).setOnClickListener(this);
        rootView.findViewById(R.id.llErrorSubject).setOnClickListener(this);
        rootView.findViewById(R.id.llPointRule).setOnClickListener(this);
        rootView.findViewById(R.id.tvUserExit).setOnClickListener(this);
        rootView.findViewById(R.id.llSpreadEnvoy).setOnClickListener(this);
        rootView.findViewById(R.id.llCollection).setOnClickListener(this);
        rootView.findViewById(R.id.llVIP).setOnClickListener(this);
        TextView tvUserName = rootView.findViewById(R.id.tvUserName);
        TextView tvIntegral = rootView.findViewById(R.id.tvIntegral);
        tvIntegral.setText(MessageFormat.format("积分 {0}", App.getInstance().getCurrentUser().getIntegral()));
        tvUserName.setText(App.getInstance().getCurrentUser().getAccountName());
        ImageView ivUserPhoto = rootView.findViewById(R.id.ivUserPhoto);
        if (getActivity() != null) {
            ((TextView) rootView.findViewById(R.id.tvVersion)).setText("当前版本V" + APKVersionCodeUtils.getVerName(getActivity()));
        }
        GlideUtils.ShowCircleImage(getActivity(), App.getInstance().getCurrentUser().getHeadImage(), ivUserPhoto, R.drawable.img_avatar_default);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshVipUi(getView());
    }

    public void refreshVipUi(View rootView) {
        ImageView ivUserVip = rootView.findViewById(R.id.ivUserVip);
        if (UserUtils.isVip(App.getInstance().getCurrentUser())) {
            ivUserVip.setImageDrawable(findDrawById(R.drawable.tag_vip));
        } else {
            ivUserVip.setImageDrawable(findDrawById(R.drawable.tag_vip2));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llUserInfo:
                startActivity(new Intent(getActivity(), UserInformationActivity.class));
                break;
            case R.id.llCollection:
                startActivity(new Intent(getActivity(), MyCollectionActivity.class));
                break;
            case R.id.llAboutUS:
                startActivity(new Intent(getActivity(), AboutUsActivity.class));
                break;
            case R.id.llErrorSubject:
                startActivity(new Intent(getActivity(), ErrorSubjectActivity.class));
                break;
            case R.id.llPointRule:
                startActivity(new Intent(getActivity(), PointRuleActivity.class));
                break;
            case R.id.llVIP:
                startActivity(new Intent(getActivity(), VipActivity.class));
                break;
            case R.id.llSpreadEnvoy:
                startActivity(new Intent(getActivity(), SpreadEnvoyActivity.class));
                break;
            case R.id.tvUserExit:
                if (getActivity() == null) {
                    return;
                }
                new MaterialDialog.Builder(getActivity())
                        .content("确定退出账号?")
                        .positiveText("确定")
                        .negativeText("取消")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                SPHelper.remove(getActivity(), ConstantStr.USER_INFO, ConstantStr.TOKEN);
                                App.getInstance().exitApp();
                                startActivity(new Intent(getActivity(), LoginActivity.class));
                            }
                        })
                        .build()
                        .show();
                break;
        }
    }
}
