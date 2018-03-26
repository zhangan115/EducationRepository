package com.xueli.application.view.bank.examination;

import com.xueli.application.base.BasePresenter;
import com.xueli.application.base.BaseView;
import com.xueli.application.mode.bean.exam.PaperSections;

import java.util.List;

/**
 * 考试
 * Created by pingan on 2018/3/26.
 */

interface ExaminationContract {

    interface Presenter extends BasePresenter {

        void getPaperSections(long id);
    }

    interface View extends BaseView<Presenter> {

        void showData(List<PaperSections> list);

        void showLoading();

        void hideLoading();

        void noData();

        void showMessage(String message);
    }
}
