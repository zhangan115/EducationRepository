package com.xueli.application.view.register;

import com.xueli.application.base.BasePresenter;
import com.xueli.application.base.BaseView;

import java.util.Map;

/**
 * 注册
 * Created by pingan on 2018/3/25.
 */

interface RegisterContract {

    interface Presenter extends BasePresenter {

        void sendPhoneCode(String phoneNum);

        boolean isCountDown();

        void startCountDown();

        void userReg(Map<String, String> map);
    }

    interface View extends BaseView<Presenter> {

        void showMessage(String message);

        void registerSuccess();

        void sendCodeSuccess();

        void startCountDown(String time);

        void contDownFinish();

    }
}
