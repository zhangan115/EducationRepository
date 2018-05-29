package com.xueli.application.view.forget;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.library.utils.PhoneFormatCheckUtils;
import com.orhanobut.logger.Logger;
import com.xueli.application.R;
import com.xueli.application.app.App;
import com.xueli.application.common.ConstantStr;
import com.xueli.application.mode.user.UserRepository;
import com.xueli.application.view.BaseActivity;
import com.xueli.application.view.MvpActivity;
import com.xueli.application.view.register.RegisterSureActivity;

/**
 * 忘记密码
 * Created by pingan on 2018/3/10.
 */

public class ForgetPassWordActivity extends MvpActivity<ForgetPassWordContract.Presenter> implements ForgetPassWordContract.View {

    private EditText etUserName, etUserPassWord, etUserPassAgain;
    private TextView tvGetCode;
    private int REQUEST_FORGET_PASS = 100;
    private String userName;
    private String phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayoutAndToolbar(R.layout.forget_pass_word_activity, "忘记密码");
        findViewById(R.id.btnNextStep).setOnClickListener(this);
        tvGetCode = findViewById(R.id.tvGetCode);
        etUserName = findViewById(R.id.etUserName);
        etUserPassWord = findViewById(R.id.etUserPassWord);
        etUserPassAgain = findViewById(R.id.etUserPassAgain);
        tvGetCode.setOnClickListener(this);
    }

    @Override
    protected void onBindPresenter() {
        new ForgetPassWordPresenter(UserRepository.getRepository(this), this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnNextStep:
                String code = etUserPassAgain.getText().toString().trim();
                if (!phoneNumber.equals(etUserPassWord.getText().toString().trim())) {
                    showMessage("请重新获取验证码");
                    return;
                }
                if (TextUtils.isEmpty(code)) {
                    showMessage("请输入验证码");
                    return;
                }
                Intent intent = new Intent(this, ForgetPassWordSureActivity.class);
                intent.putExtra(ConstantStr.KEY_BUNDLE_STR, phoneNumber);
                intent.putExtra(ConstantStr.KEY_BUNDLE_STR_1, phoneNumber);
                intent.putExtra(ConstantStr.KEY_BUNDLE_STR_2, code);
                startActivityForResult(intent, REQUEST_FORGET_PASS);
                break;
            case R.id.tvGetCode:
                userName = etUserName.getText().toString().trim();
                phoneNumber = etUserPassWord.getText().toString().trim();
                if (mPresenter.isCountDown()) {
                    break;
                }
                if (TextUtils.isEmpty(phoneNumber)) {
                    App.getInstance().showToast("请输入手机号码");
                    break;
                }
                if (!PhoneFormatCheckUtils.isChinaPhoneLegal(phoneNumber)) {
                    App.getInstance().showToast("请输入合法手机号码");
                    break;
                }
                mPresenter.sendPhoneCode(phoneNumber, phoneNumber);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_FORGET_PASS && resultCode == Activity.RESULT_OK) {
            setResult(Activity.RESULT_OK, data);
            finish();
        }
    }

    @Override
    public void showMessage(String message) {
        App.getInstance().showToast(message);
    }

    @Override
    public void sendCodeSuccess() {
        showMessage("验证码发送成功");
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
    public void setPresenter(ForgetPassWordContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
