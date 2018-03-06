package com.xueli.application.view.main;

import android.support.annotation.Nullable;

import com.xueli.application.base.BasePresenter;
import com.xueli.application.base.BaseView;

/**
 * 首页
 * Created by pingan on 2018/3/4.
 */

public interface MainContract {

    interface Presenter extends BasePresenter {

    }

    interface View extends BaseView<Presenter> {

        void showMessage(@Nullable String message);

    }
}
