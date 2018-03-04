package com.xueli.application.view;

import android.os.Bundle;

import com.xueli.application.app.App;
import com.xueli.application.base.BasePresenter;

/**
 * mvp activity base
 * Created by pingan on 2018/3/4.
 */

public abstract class MvpActivity<T extends BasePresenter> extends BaseActivity {

    protected T mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onBindPresenter();
        if (mPresenter != null) {
            mPresenter.subscribe();
        }
    }

    protected abstract void onBindPresenter();

    @Override
    protected void onCancel() {
        if (mPresenter != null) {
            mPresenter.unSubscribe();
        }
    }

    public App getApp() {
        return App.getInstance();
    }
}
