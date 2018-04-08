package com.xueli.application.view.home;

import com.xueli.application.base.BasePresenter;
import com.xueli.application.base.BaseView;
import com.xueli.application.mode.bean.study.StudyMessage;

import java.util.ArrayList;
import java.util.List;

interface HomeContract {

    interface Presenter extends BasePresenter {

        void getHeaderAd();

        void getHot();

        void checkNewVersion();

        void getMessage();
    }

    interface View extends BaseView<Presenter> {

        void showHeaderAd(List<StudyMessage> list);

        void showHot(ArrayList<StudyMessage> list);

        void showMessageList(List<StudyMessage> list);

        void noMessage();

        void noHeaderAd();

        void showNewVersion();

        void showMessage(String message);
    }
}
