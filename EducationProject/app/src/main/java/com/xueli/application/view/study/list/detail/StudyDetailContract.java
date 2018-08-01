package com.xueli.application.view.study.list.detail;

import com.xueli.application.base.BasePresenter;
import com.xueli.application.base.BaseView;

interface StudyDetailContract {

    interface Presenter extends BasePresenter {

        void getDetail(long id);
    }

    interface View extends BaseView<Presenter> {

        void showUrl(String details);
    }
}
