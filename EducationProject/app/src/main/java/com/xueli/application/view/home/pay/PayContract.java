package com.xueli.application.view.home.pay;

import android.support.annotation.Nullable;

import com.xueli.application.base.BasePresenter;
import com.xueli.application.base.BaseView;
import com.xueli.application.mode.bean.user.PaySchoolList;

import java.util.List;

public interface PayContract {

    interface Presenter extends BasePresenter {

        void getPay();

    }

    interface View extends BaseView<Presenter> {

        void payAli();

        void payWeiXin();

        void showPaySchoolList(List<PaySchoolList> list);

        void showMessage(@Nullable String message);

    }
}
