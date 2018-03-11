package com.xueli.application.view.enrol;

import android.os.Bundle;

import com.xueli.application.R;
import com.xueli.application.view.BaseActivity;

/**
 * 我要报名
 * Created by pingan on 2018/3/11.
 */

public class EnrolActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayoutAndToolbar(R.layout.enrol_activity, "我要报名");
    }
}
