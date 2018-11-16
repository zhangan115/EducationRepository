package com.xueli.application.view.user.about_us;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.xueli.application.R;
import com.xueli.application.common.ConstantStr;
import com.xueli.application.util.APKVersionCodeUtils;
import com.xueli.application.view.BaseActivity;
import com.xueli.application.view.WebActivity;
import com.xueli.application.view.web.MessageDetailActivity;

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
        findViewById(R.id.showInfoTv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AboutUsActivity.this, MessageDetailActivity.class);
                intent.putExtra(ConstantStr.KEY_BUNDLE_BOOLEAN, true);
                intent.putExtra(ConstantStr.KEY_BUNDLE_STR_1, "http://39.106.210.43/FxExam/resources/banquan.html");
                intent.putExtra(ConstantStr.KEY_BUNDLE_STR, "版权声明");
                startActivity(intent);
            }
        });
    }
}
