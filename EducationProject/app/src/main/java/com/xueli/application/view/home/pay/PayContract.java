package com.xueli.application.view.home.pay;

import android.support.annotation.Nullable;

import com.xueli.application.base.BasePresenter;
import com.xueli.application.base.BaseView;

public interface PayContract {

    interface Presenter extends BasePresenter {

        void getPay();

    }

    interface View extends BaseView<Presenter> {

        void payAli();

        void payWeiXin();

        void loginLoading();

        void loginHideLoading();

        void showMessage(@Nullable String message);

    }
}
