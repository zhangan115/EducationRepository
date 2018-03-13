package com.xueli.application.view.user.information;

import android.os.Bundle;

import com.xueli.application.R;
import com.xueli.application.view.BaseActivity;

/**
 * 用户信息
 * Created by pingan on 2018/3/13.
 */

public class UserInformationActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayoutAndToolbar(R.layout.user_information_activity, "个人信息");

    }
}
