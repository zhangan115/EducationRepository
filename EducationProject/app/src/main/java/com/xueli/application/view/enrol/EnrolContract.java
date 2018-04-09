package com.xueli.application.view.enrol;

import com.xueli.application.base.BasePresenter;
import com.xueli.application.base.BaseView;
import com.xueli.application.mode.enrol.bean.MajorBean;
import com.xueli.application.mode.enrol.bean.SchoolBean;

import org.json.JSONObject;

import java.util.List;

public interface EnrolContract {

    interface Presenter extends BasePresenter {

        void getSchoolList();

        void getMajorList(Long schoolId);

        void uploadData(JSONObject jsonObject);
    }

    interface View extends BaseView<Presenter> {

        void showSchoolList(List<SchoolBean> schoolBeans);

        void schoolError();

        void showMajor(List<MajorBean> majorBeans);

        void majorError();

        void uploadSuccess();

        void uploadFail();

    }

}
