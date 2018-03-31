package com.xueli.application.view.web;

import com.xueli.application.base.BasePresenter;
import com.xueli.application.base.BaseView;

interface MessageDetailContract {

    interface Presenter extends BasePresenter {

        void getUrl(long id);
    }

    interface View extends BaseView<Presenter> {

        void showUrl(String url);
    }
}
