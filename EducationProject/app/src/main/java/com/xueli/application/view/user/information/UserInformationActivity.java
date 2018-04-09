package com.xueli.application.view.user.information;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.library.utils.GlideUtils;
import com.xueli.application.R;
import com.xueli.application.app.App;
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
        ImageView ivUserPhoto = findViewById(R.id.ivUserPhoto);
        GlideUtils.ShowImage(this, App.getInstance().getCurrentUser().getHeadImage(), ivUserPhoto, R.drawable.img_avatar);
        TextView tvUserNickName = findViewById(R.id.tvUserNickName);
        tvUserNickName.setText(App.getInstance().getCurrentUser().getAccountName());

    }
}
