package com.xueli.application.view.bank;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.xueli.application.BuildConfig;
import com.xueli.application.R;
import com.xueli.application.app.App;
import com.xueli.application.common.ConstantStr;
import com.xueli.application.util.UserUtils;
import com.xueli.application.view.MvpFragment;
import com.xueli.application.view.bank.filter.BankFilterActivity;
import com.xueli.application.view.bank.list.BankListActivity;
import com.xueli.application.view.user.collection.MyCollectionActivity;
import com.xueli.application.view.user.subject_error.ErrorSubjectActivity;
import com.xueli.application.view.user.vip.VipActivity;

/**
 * 题库
 * Created by pingan on 2018/3/4.
 */

public class BankFragment extends MvpFragment implements View.OnClickListener {

    public static BankFragment newInstance() {
        Bundle args = new Bundle();
        BankFragment fragment = new BankFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.bank_fragment, container, false);
        rootView.findViewById(R.id.iv1).setOnClickListener(this);
        rootView.findViewById(R.id.iv2).setOnClickListener(this);
        rootView.findViewById(R.id.iv3).setOnClickListener(this);
        rootView.findViewById(R.id.iv4).setOnClickListener(this);
        rootView.findViewById(R.id.iv5).setOnClickListener(this);
        rootView.findViewById(R.id.iv6).setOnClickListener(this);
        rootView.findViewById(R.id.llRecord).setOnClickListener(this);
        rootView.findViewById(R.id.llWrongQuestions).setOnClickListener(this);
        rootView.findViewById(R.id.llMyFavorite).setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.llRecord){
            return;
        }
        if (v.getId() == R.id.llWrongQuestions){
            startActivity(new Intent(getActivity(), ErrorSubjectActivity.class));
            return;
        }
        if (v.getId() == R.id.llMyFavorite){
            startActivity(new Intent(getActivity(), MyCollectionActivity.class));
            return;
        }
        String tag = (String) v.getTag();
        Intent intent;
        if (tag.startsWith("1")) {
            intent = new Intent(getActivity(), BankFilterActivity.class);
        } else {
            intent = new Intent(getActivity(), BankListActivity.class);
        }
        if (!BuildConfig.DEBUG) {
            if (tag.startsWith("1")) {
                if (!UserUtils.isVip1(App.getInstance().getCurrentUser())) {
                    showVipDialog();
                    return;
                }
            } else if (tag.startsWith("2")) {
                if (!UserUtils.isVip2(App.getInstance().getCurrentUser())) {
                    showVipDialog();
                    return;
                }
            } else {
                if (!UserUtils.isVip3(App.getInstance().getCurrentUser())) {
                    showVipDialog();
                    return;
                }
            }
        }
        intent.putExtra(ConstantStr.KEY_BUNDLE_STR, tag);
        startActivity(intent);
    }

    private void showVipDialog() {
        if (getActivity() == null) return;
        new MaterialDialog.Builder(getActivity()).title("提示").content("马上充值成为会员，信息浏览将畅通无阻")
                .positiveText("马上充值").negativeText("暂不充值").onPositive(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                startActivity(new Intent(getActivity(), VipActivity.class));
            }
        }).show();
    }
}
