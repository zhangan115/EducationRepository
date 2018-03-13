package com.xueli.application.view.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xueli.application.R;
import com.xueli.application.view.MvpFragment;
import com.xueli.application.view.user.information.UserInformationActivity;

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
        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llUserInfo:
                startActivity(new Intent(getActivity(), UserInformationActivity.class));
                break;
        }
    }
}
