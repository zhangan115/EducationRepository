package com.xueli.application.view.user.about_us;

import android.os.Bundle;

import com.xueli.application.R;
import com.xueli.application.view.BaseActivity;

/**
 * 关于我们
 * Created by pingan on 2018/3/26.
 */

public class AboutUsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayoutAndToolbar(R.layout.about_us_activity, "关于我们");
        setDarkStatusIcon(true);
    }
}
