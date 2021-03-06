package com.xueli.application.view.study.list;

import com.xueli.application.base.BasePresenter;
import com.xueli.application.base.BaseView;
import com.xueli.application.mode.bean.study.StudyMessage;

import java.util.List;

public interface StudyListContract {

    interface Presenter extends BasePresenter {

        void getStudyMessage(long id);

        void getStudyMessage(long id, long lastId);
    }

    interface View extends BaseView<Presenter> {

        void showLoading();

        void hideLoading();

        void noData();

        void showData(List<StudyMessage> datas);

        void showMoreData(List<StudyMessage> datas);

        void showMessage(String message);

        void noMoreData();

        void hideLoadingMore();
    }
}
