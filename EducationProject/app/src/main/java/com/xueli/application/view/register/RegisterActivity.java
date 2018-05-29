package com.xueli.application.view.register;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.xueli.application.R;
import com.xueli.application.app.App;
import com.xueli.application.common.ConstantStr;
import com.xueli.application.mode.user.UserRepository;
import com.xueli.application.view.MvpActivity;

import java.util.HashMap;
import java.util.Map;

/**
 * 注册界面
 * Created by pingan on 2018/3/9.
 */

public class RegisterActivity extends MvpActivity<RegisterContract.Presenter> implements RegisterContract.View {

    private EditText etUserPassWord;
    private EditText etUserPassAgain;
    private Map<String, String> map;
    private String phone;
    private String userPassWordStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayoutAndToolbar(R.layout.register_activity, "注册");
        findViewById(R.id.btnNextStep).setOnClickListener(this);
        etUserPassWord = findViewById(R.id.etUserPassWord);
        etUserPassAgain = findViewById(R.id.etUserPassAgain);

        map = new HashMap<>();
        phone = getIntent().getStringExtra(ConstantStr.KEY_BUNDLE_STR);
        String verificationCode = getIntent().getStringExtra(ConstantStr.KEY_BUNDLE_STR_1);
        String invitationStr = getIntent().getStringExtra(ConstantStr.KEY_BUNDLE_STR_2);
        map.put("phone", phone);
        map.put("verificationCode", verificationCode);
        if (!TextUtils.isEmpty(invitationStr)) {
            map.put("inviteCode", invitationStr);
        }
    }

    @Override
    protected void onBindPresenter() {
        new RegisterPresenter(UserRepository.getRepository(this), this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnNextStep:
                if (checkEnterStr()) {
                    mPresenter.userReg(map);
                }
                break;
        }
    }

    private boolean checkEnterStr() {
        userPassWordStr = etUserPassWord.getText().toString();
        String userPassWordAgainStr = etUserPassAgain.getText().toString();
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
        map.put("pssword", userPassWordStr);
        return true;
    }


    @Override
    public void showMessage(String message) {
        App.getInstance().showToast(message);
    }

    @Override
    public void registerSuccess() {
        App.getInstance().showToast("注册成功");
        Intent intent = new Intent();
        intent.putExtra(ConstantStr.KEY_BUNDLE_STR_1, phone);
        intent.putExtra(ConstantStr.KEY_BUNDLE_STR_2, userPassWordStr);
        setResult(Activity.RESULT_OK);
        finish();
    }

    @Override
    public void sendCodeSuccess() {

    }

    @Override
    public void startCountDown(String time) {

    }

    @Override
    public void contDownFinish() {

    }

    @Override
    public void setPresenter(RegisterContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
