package com.xueli.application.view.login.bindPhone;


import android.support.annotation.Nullable;

import com.xueli.application.base.BasePresenter;
import com.xueli.application.base.BaseView;
import com.xueli.application.mode.bean.user.VerificationCode;


/**
 * 用户登录
 * Created by zhangan on 2017-02-16.
 */
interface BindPhoneContract {

    interface Presenter extends BasePresenter {

        boolean isCountDown();

        void startCountDown();

        void getCode(String phoneNum);
    }

    interface View extends BaseView<Presenter> {

        void sendCodeSuccess(VerificationCode verificationCode);

        void startCountDown(String time);

        void contDownFinish();

        void showMessage(@Nullable String message);

    }
}
