package com.xueli.application.view.register;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.xueli.application.R;
import com.xueli.application.view.BaseActivity;

/**
 * 注册界面
 * Created by pingan on 2018/3/9.
 */

public class RegisterActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayoutAndToolbar(R.layout.register_activity, "注册");
        findViewById(R.id.btnNextStep).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnNextStep:
                Intent intent = new Intent(this, RegisterSureActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }
}
