package com.xueli.application.view.home;

import com.xueli.application.base.BasePresenter;
import com.xueli.application.base.BaseView;
import com.xueli.application.mode.bean.study.StudyMessage;

import java.util.List;

interface HomeContract {

    interface Presenter extends BasePresenter {

        void getHeaderAd();

        void checkNewVersion();

        void getMessage();
    }

    interface View extends BaseView<Presenter> {

        void showHeaderAd(List<StudyMessage> list);

        void showMessageList(List<StudyMessage> list);

        void noMessage();

        void noHeaderAd();

        void showNewVersion();

        void showMessage(String message);
    }
}
