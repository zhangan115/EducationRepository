package com.xueli.application.view.user.vip;

import com.xueli.application.base.BasePresenter;
import com.xueli.application.base.BaseView;
import com.xueli.application.mode.bean.user.VipContent;

import java.util.List;

interface VipContract {

    interface Presenter extends BasePresenter {

        void getVipCardList();

        void payVip(long cardId);

    }

    interface View extends BaseView<Presenter> {

        void showVipContent(List<VipContent> list);

        void paySuccess();
    }
}
