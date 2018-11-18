package com.xueli.application.view.home.school;

import android.support.annotation.Nullable;

import com.xueli.application.base.BasePresenter;
import com.xueli.application.base.BaseView;
import com.xueli.application.mode.bean.school.SchoolBean;

import java.util.List;

interface SchoolContract {

    interface Presenter extends BasePresenter {

        void getSchool();

    }

    interface View extends BaseView<Presenter> {

        void showSchool(List<SchoolBean> list);

        void showMessage(@Nullable String message);

    }

}
