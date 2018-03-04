package com.xueli.application.view.login;


import android.support.annotation.Nullable;

import com.xueli.application.base.BasePresenter;
import com.xueli.application.base.BaseView;


/**
 * 用户登录
 * Created by zhangan on 2017-02-16.
 */
interface LoginContract {

    interface Presenter extends BasePresenter {

        void login(String name, String pass);
    }

    interface View extends BaseView<Presenter> {

        void loginSuccess();

        void loginFail();

        void loginLoading();

        void loginHideLoading();

        void showMessage(@Nullable String message);

    }
}
