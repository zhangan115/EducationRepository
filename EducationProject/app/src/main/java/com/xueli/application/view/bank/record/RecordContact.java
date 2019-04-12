package com.xueli.application.view.bank.record;

import com.xueli.application.base.BasePresenter;
import com.xueli.application.base.BaseView;
import com.xueli.application.mode.bean.exam.PaperSections;

import java.util.List;

interface RecordContact {

    interface Presenter extends BasePresenter {

        void getBankList(String bResult, Long lastId);
    }

    interface View extends BaseView<Presenter> {

        void showLoading();

        void hideLoading();

        void noData();

        void showData(List<PaperSections> datas);

        void showMessage(String message);

    }
}
