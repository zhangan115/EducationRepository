package com.xueli.application.view.forget;

import android.os.Bundle;

import com.xueli.application.R;
import com.xueli.application.view.BaseActivity;

/**
 * 忘记密码
 * Created by pingan on 2018/3/10.
 */

public class ForgetPassWordSureActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayoutAndToolbar(R.layout.forget_pass_word_sure_activity, "忘记密码");
    }
}
