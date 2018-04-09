package com.xueli.application.view.forget;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.xueli.application.R;
import com.xueli.application.view.BaseActivity;
import com.xueli.application.view.register.RegisterSureActivity;

/**
 * 忘记密码
 * Created by pingan on 2018/3/10.
 */

public class ForgetPassWordActivity extends BaseActivity {

    private EditText etUserName, etUserPassWord, etUserPassAgain;
    private TextView tvGetCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayoutAndToolbar(R.layout.forget_pass_word_activity, "忘记密码");
        findViewById(R.id.btnNextStep).setOnClickListener(this);
        tvGetCode = findViewById(R.id.tvGetCode);
        findViewById(R.id.tvGetCode).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnNextStep:
                Intent intent = new Intent(this, ForgetPassWordSureActivity.class);

                startActivity(intent);
                finish();
                break;
        }
    }
}
