package com.xueli.application.view.user.collection;

import com.xueli.application.base.BasePresenter;
import com.xueli.application.base.BaseView;

interface MyCollectionContract {

    interface View extends BaseView<Presenter> {

        void noData();

        void showLoading();

        void hideLoading();

        void showData();

        void showMessage(String message);
    }

    interface Presenter extends BasePresenter {

        void getMyCollection();
    }
}
