package com.xueli.application.view.user.collection;

import com.xueli.application.base.BasePresenter;
import com.xueli.application.base.BaseView;
import com.xueli.application.mode.bean.exam.PaperSections;

import java.util.List;

interface MyCollectionContract {

    interface View extends BaseView<Presenter> {

        void noData();

        void showLoading();

        void hideLoading();

        void showData( List<PaperSections> list);

        void showMessage(String message);
    }

    interface Presenter extends BasePresenter {

        void getMyCollection();
    }
}
