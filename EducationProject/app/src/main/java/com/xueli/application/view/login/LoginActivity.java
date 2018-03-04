package com.xueli.application.view.login;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.xueli.application.R;
import com.xueli.application.mode.Injection;
import com.xueli.application.view.MvpActivity;

/**
 * 登陆界面
 * Created by pingan on 2018/3/4.
 */

public class LoginActivity extends MvpActivity<LoginContract.Presenter> implements LoginContract.View {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.LoginActivityStyle);
        setContentView(R.layout.login_activity);
        transparentStatusBar();
    }

    @Override
    protected void onBindPresenter() {
        new LoginPresenter(Injection.getInjection().provideUserRepository(getApp()), this);
    }

    @Override
    public void loginSuccess() {

    }

    @Override
    public void loginFail() {

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
}
