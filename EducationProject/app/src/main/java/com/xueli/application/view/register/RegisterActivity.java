package com.xueli.application.view.register;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.orhanobut.logger.Logger;
import com.xueli.application.R;
import com.xueli.application.app.App;
import com.xueli.application.common.ConstantStr;
import com.xueli.application.view.BaseActivity;

/**
 * 注册界面
 * Created by pingan on 2018/3/9.
 */

public class RegisterActivity extends BaseActivity {

    private EditText userName;
    private EditText etUserPassWord;
    private EditText etUserPassAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayoutAndToolbar(R.layout.register_activity, "注册");
        findViewById(R.id.btnNextStep).setOnClickListener(this);
        userName = findViewById(R.id.etUserName);
        etUserPassWord = findViewById(R.id.etUserPassWord);
        etUserPassAgain = findViewById(R.id.etUserPassAgain);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnNextStep:
                if (checkEnterStr()) {
                    Intent intent = new Intent(this, RegisterSureActivity.class);
                    intent.putExtra(ConstantStr.KEY_BUNDLE_STR, userName.getText().toString());
                    intent.putExtra(ConstantStr.KEY_BUNDLE_STR_1, etUserPassWord.getText().toString());
                    startActivityForResult(intent, 100);
                }
                break;
        }
    }

    private boolean checkEnterStr() {
        String userNameStr = userName.getText().toString();
        String userPassWordStr = etUserPassWord.getText().toString();
        String userPassWordAgainStr = etUserPassAgain.getText().toString();
        if (TextUtils.isEmpty(userNameStr)) {
            App.getInstance().showToast("请输入账号");
            return false;
        }
        if (userNameStr.length() < 3 || !isNumber(userNameStr)) {
            App.getInstance().showToast("用户名由3-12位字母和数字组成,首位必须字母");
            return false;
        }
        if (TextUtils.isEmpty(userPassWordStr)) {
            App.getInstance().showToast("请输入密码");
            return false;
        }
        if (userPassWordStr.length() < 8) {
            App.getInstance().showToast("密码由8-12位的字母和数字组成");
            return false;
        }
        if (TextUtils.isEmpty(userPassWordAgainStr)) {
            App.getInstance().showToast("请再次输入密码");
            return false;
        }
        if (!userPassWordAgainStr.equals(userPassWordStr)) {
            App.getInstance().showToast("两次密码不一致");
            return false;
        }
        return true;
    }

    private boolean isNumber(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        if (str.length() >= 1) {
            String s = str.substring(0, 1);
            try {
                Logger.d(String.valueOf(Integer.valueOf(s)));
            } catch (NumberFormatException e) {
                return true;
            }
            return false;
        } else {
            return false;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
            finish();
        }
    }
}
