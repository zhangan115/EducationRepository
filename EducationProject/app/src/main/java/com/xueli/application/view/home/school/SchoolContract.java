package com.xueli.application.view.home.school;

import android.support.annotation.Nullable;

import com.xueli.application.base.BasePresenter;
import com.xueli.application.base.BaseView;

interface SchoolContract {

    interface Presenter extends BasePresenter {

        void getSchool();

    }

    interface View extends BaseView<Presenter> {

        void showSchool();

        void loginLoading();

        void loginHideLoading();

        void showMessage(@Nullable String message);

    }

}
