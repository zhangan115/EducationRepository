package com.xueli.application.view.user.vip;

import com.xueli.application.base.BasePresenter;
import com.xueli.application.base.BaseView;
import com.xueli.application.mode.bean.user.User;
import com.xueli.application.mode.bean.user.VipContent;

import java.util.List;
import java.util.Map;

interface VipContract {

    interface Presenter extends BasePresenter {

        void getVipCardList();

        void payVip(long cardId);

        void getAlOrderString(long cardId);

        void paySchoolAl(Map<String, String> map);

        void paySchoolWeiXin(Map<String, String> map);

        void paySuccessCallBack(Map<String, String> map);
    }

    interface View extends BaseView<Presenter> {

        void showVipContent(List<VipContent> list);

        void paySuccess(User user);

        void showAlOrderStr(String orderStr);

        void payAli(String payMessage);

        void payWeiXin(String payMessage);

        void showMessage(String message);
    }
}
