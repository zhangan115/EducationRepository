package com.xueli.application.view.forget;

import com.xueli.application.base.BasePresenter;
import com.xueli.application.base.BaseView;

public interface ForgetPassWordContract {

    interface Presenter extends BasePresenter {

        void sendPhoneCode(String phoneNum);

        boolean isCountDown();

        void startCountDown();
    }

    interface View extends BaseView<Presenter> {

        void showMessage(String message);

        void sendCodeSuccess();

        void startCountDown(String time);

        void contDownFinish();

    }
}
