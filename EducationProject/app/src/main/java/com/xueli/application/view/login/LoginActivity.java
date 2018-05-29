package com.xueli.application.view.login;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.xueli.application.R;
import com.xueli.application.app.App;
import com.xueli.application.common.ConstantStr;
import com.xueli.application.mode.Injection;
import com.xueli.application.view.MvpActivity;
import com.xueli.application.view.forget.ForgetPassWordActivity;
import com.xueli.application.view.main.MainActivity;
import com.xueli.application.view.register.RegisterSureActivity;

/**
 * 登陆界面
 * Created by pingan on 2018/3/4.
 */

public class LoginActivity extends MvpActivity<LoginContract.Presenter> implements LoginContract.View {

    private EditText userNameEt, userPassWordEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.LoginActivityStyle);
        setContentView(R.layout.login_activity);
        transparentStatusBar();
        initView();
    }

    private void initView() {
        userNameEt = findViewById(R.id.etUserName);
        userPassWordEt = findViewById(R.id.etUserPassWord);
        findViewById(R.id.btnLogin).setOnClickListener(this);
        findViewById(R.id.tvForgetPass).setOnClickListener(this);
        findViewById(R.id.tvRegister).setOnClickListener(this);
    }

    @Override
    protected void onBindPresenter() {
        new LoginPresenter(Injection.getInjection().provideUserRepository(getApp()), this);
    }

    @Override
    public void loginSuccess() {
        Intent intent = new Intent(this, MainActivity.class);
        if (getPackageManager().resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY) != null) {
            try {
                startActivity(intent);
                finish();
            } catch (ActivityNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void loginLoading() {
        showProgressDialog("登陆中...");
    }

    @Override
    public void loginHideLoading() {
        hideProgressDialog();
    }

    @Override
    public void showMessage(@Nullable String message) {
        getApp().showToast(message);
    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btnLogin:
                if (TextUtils.isEmpty(userNameEt.getText().toString())) {
                    App.getInstance().showToast("请输入用户名");
                    return;
                }
                if (TextUtils.isEmpty(userPassWordEt.getText().toString())) {
                    App.getInstance().showToast("请输入密码");
                    return;
                }
                mPresenter.login(userNameEt.getText().toString(), userPassWordEt.getText().toString());
                break;
            case R.id.tvForgetPass:
                Intent forgetPassInt = new Intent(this, ForgetPassWordActivity.class);
                startActivityForResult(forgetPassInt, START_FORGET);
                break;
            case R.id.tvRegister:
                Intent intent = new Intent(this, RegisterSureActivity.class);
                startActivityForResult(intent, START_REGISTER);
                break;
        }
    }

    private final int START_REGISTER = 100;
    private final int START_FORGET = 101;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == START_FORGET && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                String name = data.getStringExtra(ConstantStr.KEY_BUNDLE_STR_1);
                String pass = data.getStringExtra(ConstantStr.KEY_BUNDLE_STR_2);
                if (!TextUtils.isEmpty(name)) {
                    userNameEt.setText(name);
                }
                if (!TextUtils.isEmpty(pass)) {
                    userPassWordEt.setText(pass);
                }
            }
        } else if (requestCode == START_REGISTER && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                String phone = data.getStringExtra(ConstantStr.KEY_BUNDLE_STR_1);
                String pass = data.getStringExtra(ConstantStr.KEY_BUNDLE_STR_2);
                if (!TextUtils.isEmpty(phone)) {
                    userNameEt.setText(phone);
                }
                if (!TextUtils.isEmpty(pass)) {
                    userPassWordEt.setText(pass);
                }
            }

        }
    }
}
