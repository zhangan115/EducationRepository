package com.xueli.application.view.register;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.library.utils.PhoneFormatCheckUtils;
import com.xueli.application.R;
import com.xueli.application.app.App;
import com.xueli.application.common.ConstantStr;
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
    private EditText etUserName, etUserPassWord;
    private Map<String, String> map;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayoutAndToolbar(R.layout.register_sure_activity, "注册");
        findViewById(R.id.btnNextStep).setOnClickListener(this);
        tvGetCode = findViewById(R.id.tvGetCode);
        etUserName = findViewById(R.id.etUserName);
        etUserPassWord = findViewById(R.id.etUserPassWord);
        tvGetCode.setOnClickListener(this);

        map = new HashMap<>();
        String name = getIntent().getStringExtra(ConstantStr.KEY_BUNDLE_STR);
        String pass = getIntent().getStringExtra(ConstantStr.KEY_BUNDLE_STR_1);
        map.put("accountName", name);
        map.put("pssword", pass);
    }

    @Override
    protected void onBindPresenter() {
        new RegisterPresenter(UserRepository.getRepository(this), this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnNextStep:
                String phoneNumber1 = etUserName.getText().toString().trim();
                if (TextUtils.isEmpty(phoneNumber1)) {
                    App.getInstance().showToast("请输入手机号码");
                    return;
                }
                map.put("phone", phoneNumber1);
                String verificationCode = etUserPassWord.getText().toString().trim();
                if (TextUtils.isEmpty(verificationCode)) {
                    App.getInstance().showToast("请输入验证码");
                    return;
                }
                map.put("verificationCode", verificationCode);
                mPresenter.userReg(map);
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
    public void showMessage(String message) {
        App.getInstance().showToast(message);
    }

    @Override
    public void registerSuccess() {
        App.getInstance().showToast("注册成功");
        setResult(Activity.RESULT_OK);
        finish();
    }

    @Override
    public void sendCodeSuccess() {
        App.getInstance().showToast("发送成功");
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
