package com.xueli.application.view.login;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;

import com.xueli.application.R;
import com.xueli.application.mode.Injection;
import com.xueli.application.view.MvpActivity;
import com.xueli.application.view.main.MainActivity;

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
                mPresenter.login(userNameEt.getText().toString(), userPassWordEt.getText().toString());
                break;
            case R.id.tvForgetPass:

                break;
            case R.id.tvRegister:

                break;
        }
    }
}
