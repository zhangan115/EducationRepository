package com.xueli.application.view.register;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.library.utils.PhoneFormatCheckUtils;
import com.xueli.application.R;
import com.xueli.application.app.App;
import com.xueli.application.common.ConstantStr;
import com.xueli.application.mode.bean.user.User;
import com.xueli.application.mode.user.UserRepository;
import com.xueli.application.view.BaseActivity;
import com.xueli.application.view.MvpActivity;

import java.util.HashMap;
import java.util.Map;

/**
 * 注册确认界面
 * Created by pingan on 2018/3/9.
 */

public class RegisterSureActivity extends MvpActivity<RegisterContract.Presenter> implements RegisterContract.View {

    private TextView tvGetCode;
    private EditText etUserName, etUserPassWord, etUserInvitation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayoutAndToolbar(R.layout.register_sure_activity, "注册");
        findViewById(R.id.btnNextStep).setOnClickListener(this);
        tvGetCode = findViewById(R.id.tvGetCode);
        etUserName = findViewById(R.id.etUserName);
        etUserPassWord = findViewById(R.id.etUserPassWord);
        etUserInvitation = findViewById(R.id.etUserInvitation);
        tvGetCode.setOnClickListener(this);
    }

    @Override
    protected void onBindPresenter() {
        new RegisterPresenter(UserRepository.getRepository(this), this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnNextStep:
                Intent intent = new Intent(this, RegisterActivity.class);
                String phoneNumber1 = etUserName.getText().toString().trim();
                if (TextUtils.isEmpty(phoneNumber1)) {
                    App.getInstance().showToast("请输入手机号码");
                    return;
                }
                String verificationCode = etUserPassWord.getText().toString().trim();
                if (TextUtils.isEmpty(verificationCode)) {
                    App.getInstance().showToast("请输入验证码");
                    return;
                }
                String invitationStr = etUserInvitation.getText().toString().trim();
                if (!TextUtils.isEmpty(invitationStr)) {
                    if (invitationStr.length() == 6) {
                        intent.putExtra(ConstantStr.KEY_BUNDLE_STR_2, invitationStr);
                    } else {
                        App.getInstance().showToast("邀请码不合法");
                    }
                }
                intent.putExtra(ConstantStr.KEY_BUNDLE_STR, phoneNumber1);
                intent.putExtra(ConstantStr.KEY_BUNDLE_STR_1, verificationCode);
                startActivityForResult(intent, 100);
                break;
            case R.id.tvGetCode:
                if (mPresenter.isCountDown()) {
                    return;
                }
                String phoneNumber = etUserName.getText().toString().trim();
                if (TextUtils.isEmpty(phoneNumber)) {
                    App.getInstance().showToast("请输入手机号码");
                    break;
                }
                if (!PhoneFormatCheckUtils.isChinaPhoneLegal(phoneNumber)) {
                    App.getInstance().showToast("请输入合法手机号码");
                    break;
                }
                mPresenter.sendPhoneCode(phoneNumber);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
            setResult(Activity.RESULT_OK);
            finish();
        }
    }

    @Override
    public void showMessage(String message) {
        App.getInstance().showToast(message);
    }

    @Override
    public void registerSuccess(User user) {

    }

    @Override
    public void sendCodeSuccess() {
        App.getInstance().showToast("发送成功");
        mPresenter.startCountDown();
    }

    @Override
    public void startCountDown(String time) {
        tvGetCode.setText(time + "s");
    }

    @Override
    public void contDownFinish() {
        tvGetCode.setText("获取验证码");
    }

    @Override
    public void setPresenter(RegisterContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
