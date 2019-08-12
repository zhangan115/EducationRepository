package com.xueli.application.view.login.bindPhone;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.library.utils.PhoneFormatCheckUtils;
import com.library.utils.SPHelper;
import com.xueli.application.R;
import com.xueli.application.app.App;
import com.xueli.application.common.ConstantStr;
import com.xueli.application.mode.Injection;
import com.xueli.application.mode.bean.user.User;
import com.xueli.application.mode.bean.user.VerificationCode;
import com.xueli.application.view.MvpActivity;
import com.xueli.application.view.login.bindSchool.BindSchoolActivity;
import com.xueli.application.view.main.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class BindPhoneActivity extends MvpActivity<BindPhoneContract.Presenter> implements BindPhoneContract.View {

    private TextView tvGetCode;
    private EditText etPhoneNum, etPhoneCode;
    private String phoneNumber;
    private VerificationCode verificationCode;
    private String openId = "";
    private int loginType = 0;//1 微信登陆 2 qq登录
    private final static int START_BIND_SCHOOL = 103;
    private String phoneCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayoutAndToolbar(R.layout.activity_bind_phone, "绑定手机号码");
        tvGetCode = findViewById(R.id.tvGetCode);
        etPhoneCode = findViewById(R.id.etPhoneCode);
        tvGetCode.setOnClickListener(this);
        etPhoneNum = findViewById(R.id.etPhoneNum);

        findViewById(R.id.btnSure).setOnClickListener(this);
        openId = getIntent().getStringExtra(ConstantStr.KEY_BUNDLE_STR);
        loginType = getIntent().getIntExtra(ConstantStr.KEY_BUNDLE_INT, 0);
        if (loginType == 0) {
            this.getTitleTv().setText("注册");
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tvGetCode:
                phoneNumber = etPhoneNum.getText().toString().trim();
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
                verificationCode = null;
                mPresenter.getCode(phoneNumber);
                break;
            case R.id.btnSure:
                phoneCode = etPhoneCode.getText().toString().trim();
                if (TextUtils.isEmpty(phoneCode)) {
                    showMessage("请输入验证码");
                    break;
                }
                if (verificationCode == null) {
                    showMessage("请获取验证码");
                    break;
                }
                if (!TextUtils.equals(phoneCode, verificationCode.getVerificationCode())) {
                    showMessage("验证码错误");
                    break;
                }
                if (loginType == 0){//注册的。
                    needReg();
                    break;
                }
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("loginType",loginType);
                    jsonObject.put("phone",phoneNumber);
                    jsonObject.put("verificationCode",phoneCode);
                    jsonObject.put("openId",openId);
                    mPresenter.getUserInfo(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == START_BIND_SCHOOL) {
            setResult(Activity.RESULT_OK);
            finish();
        }
    }

    @Override
    protected void onBindPresenter() {
        new BindPhonePresenter(Injection.getInjection().provideUserRepository(getApp()), this);
    }

    @Override
    public void sendCodeSuccess(VerificationCode verificationCode) {
        this.verificationCode = verificationCode;
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
    public void showMessage(@Nullable String message) {
        getApp().showToast(message);
    }

    @Override
    public void requestIUser(User user) {
        App.getInstance().setCurrentUser(user);
        SPHelper.write(App.getInstance(), ConstantStr.USER_INFO, ConstantStr.TOKEN, user.getToken());
        setResult(Activity.RESULT_OK);
        finish();
    }

    @Override
    public void needReg() {
        Intent intent = new Intent(this, BindSchoolActivity.class);
        intent.putExtra(ConstantStr.KEY_BUNDLE_STR, phoneNumber);
        intent.putExtra(ConstantStr.KEY_BUNDLE_STR_1, phoneCode);
        intent.putExtra(ConstantStr.KEY_BUNDLE_STR_3, openId);
        intent.putExtra(ConstantStr.KEY_BUNDLE_INT, loginType);
        startActivityForResult(intent, START_BIND_SCHOOL);
    }

    @Override
    public void setPresenter(BindPhoneContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
