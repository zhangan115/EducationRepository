package com.xueli.application.view.home.pay;

import android.support.annotation.Nullable;

import com.xueli.application.base.BasePresenter;
import com.xueli.application.base.BaseView;
import com.xueli.application.mode.bean.user.PaySchoolList;

import java.util.List;
import java.util.Map;

public interface PayContract {

    interface Presenter extends BasePresenter {

        void getPay();

        void paySchoolAl(Map<String, String> map);

        void paySchoolWeiXin(Map<String, String> map);

        void paySuccessCallBack(Map<String, String> map);
    }

    interface View extends BaseView<Presenter> {

        void payAli(String payMessage);

        void payWeiXin(String payMessage);

        void showPaySchoolList(List<PaySchoolList> list);

        void showMessage(@Nullable String message);

        void showLoading();

        void hideLoading();

        void finishSuccess();

        void payFinish();

    }
}
