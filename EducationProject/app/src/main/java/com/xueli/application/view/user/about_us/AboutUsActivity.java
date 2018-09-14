package com.xueli.application.view.user.about_us;

import android.os.Bundle;
import android.widget.TextView;

import com.xueli.application.R;
import com.xueli.application.util.APKVersionCodeUtils;
import com.xueli.application.view.BaseActivity;

import org.w3c.dom.Text;

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
        TextView versionText = findViewById(R.id.tvVersion);
        versionText.setText("当前版本V" + APKVersionCodeUtils.getVerName(this));
    }
}
