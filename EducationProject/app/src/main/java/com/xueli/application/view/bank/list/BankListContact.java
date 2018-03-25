package com.xueli.application.view.bank.list;

import com.xueli.application.base.BasePresenter;
import com.xueli.application.base.BaseView;

import java.util.Map;

/**
 * 试卷列表
 * Created by pingan on 2018/3/25.
 */

interface BankListContact {

    interface Presenter extends BasePresenter {

        void getBankList(Map<String, String> map);
    }

    interface View extends BaseView<Presenter> {

        void showLoading();

        void hideLoading();

        void noData();

        void showMessage(String message);
    }
}
