package com.xueli.application.view.splash;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.xueli.application.R;
import com.xueli.application.app.App;
import com.xueli.application.mode.Injection;
import com.xueli.application.view.MvpActivity;
import com.xueli.application.view.login.LoginActivity;
import com.xueli.application.view.main.MainActivity;

/**
 * 启动界面
 * Created by pingan on 2018/3/4.
 */

public class SplashActivity extends MvpActivity<SplashContract.Presenter> implements SplashContract.View {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.LoginActivityStyle);
        setContentView(R.layout.splash_activity);
        transparentStatusBar();
    }

    @Override
    protected void onBindPresenter() {
        new SplashPresenter(Injection.getInjection().provideUserRepository(App.getInstance()), this);
    }

    @Override
    public void needLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startXueLiActivity(intent);
    }

    private void startXueLiActivity(Intent intent) {
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
    public void showWelcome() {

    }

    @Override
    public void openHome() {
        Intent intent = new Intent(this, MainActivity.class);
        startXueLiActivity(intent);
    }

    @Override
    public void showMessage(@Nullable String message) {
        getApp().showToast(message);
    }

    @Override
    public void setPresenter(SplashContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
