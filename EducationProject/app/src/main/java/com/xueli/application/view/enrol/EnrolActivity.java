package com.xueli.application.view.enrol;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.library.utils.PhoneFormatCheckUtils;
import com.xueli.application.R;
import com.xueli.application.app.App;
import com.xueli.application.mode.enrol.EnrolRepository;
import com.xueli.application.mode.enrol.bean.MajorBean;
import com.xueli.application.mode.enrol.bean.SchoolBean;
import com.xueli.application.view.MvpActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 我要报名
 * Created by pingan on 2018/3/11.
 */

public class EnrolActivity extends MvpActivity<EnrolContract.Presenter> implements EnrolContract.View {

    //view
    private EditText etUserName;
    private TextView tvChooseMajor2;
    private EditText etPhoneNum;
    private ImageView ivMan, ivWoman;
    private TextView tvChooseSchool, tvChooseMajor, tvChooseType;
    //data
    private boolean isMan = true;
    private List<SchoolBean> schoolBeans;
    private List<MajorBean> majorBeans;
    private JSONObject jsonObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayoutAndToolbar(R.layout.enrol_activity, "我要报名");
        etUserName = findViewById(R.id.etUserName);
        tvChooseMajor2 = findViewById(R.id.tvChooseMajor2);
        etPhoneNum = findViewById(R.id.etPhoneNum);
        ivMan = findViewById(R.id.ivMan);
        ivWoman = findViewById(R.id.ivWoman);

        tvChooseSchool = findViewById(R.id.tvChooseSchool);
        tvChooseMajor = findViewById(R.id.tvChooseMajor);
        tvChooseType = findViewById(R.id.tvChooseType);

        findViewById(R.id.llChooseMan).setOnClickListener(this);
        findViewById(R.id.llChooseWoMan).setOnClickListener(this);

        findViewById(R.id.llChooseSchool).setOnClickListener(this);
        findViewById(R.id.llChooseMajor).setOnClickListener(this);
        findViewById(R.id.llChooseMajor2).setOnClickListener(this);
        findViewById(R.id.llChooseType).setOnClickListener(this);

        findViewById(R.id.btnNextStep).setOnClickListener(this);
        jsonObject = new JSONObject();

        mPresenter.getSchoolList();
    }

    @Override
    protected void onBindPresenter() {
        new EnrolPresenter(EnrolRepository.getRepository(this), this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llChooseMan:
                isMan = true;
                ivMan.setImageDrawable(findDrawById(R.drawable.icon_btn_radio2));
                ivWoman.setImageDrawable(findDrawById(R.drawable.icon_btn_radio1));
                break;
            case R.id.llChooseWoMan:
                isMan = false;
                ivMan.setImageDrawable(findDrawById(R.drawable.icon_btn_radio1));
                ivWoman.setImageDrawable(findDrawById(R.drawable.icon_btn_radio2));
                break;
            case R.id.llChooseSchool:
                ArrayList<String> list = new ArrayList<>();
                if (schoolBeans != null && schoolBeans.size() > 0) {
                    for (int i = 0; i < schoolBeans.size(); i++) {
                        list.add(schoolBeans.get(i).getSchoolName());
                    }
                    new MaterialDialog.Builder(this)
                            .items(list)
                            .itemsCallback(new MaterialDialog.ListCallback() {
                                @Override
                                public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                                    try {
                                        jsonObject.put("schoolId", schoolBeans.get(position).getSchoolId());
                                        jsonObject.remove("specialtyCatalogId");
                                        tvChooseMajor.setText("");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    tvChooseSchool.setText(schoolBeans.get(position).getSchoolName());
                                    EnrolActivity.this.majorBeans = schoolBeans.get(position).getSpecialtyCatalogs();
//                                mPresenter.getMajorList(schoolBeans.get(position).getSchoolId());
                                }
                            }).show();
                }
                break;
            case R.id.llChooseMajor:
                if (majorBeans != null && majorBeans.size() > 0) {
                    ArrayList<String> list1 = new ArrayList<>();
                    for (int i = 0; i < majorBeans.size(); i++) {
                        if (!TextUtils.isEmpty(majorBeans.get(i).getTitle())) {
                            list1.add(majorBeans.get(i).getTitle());
                        }
                    }
                    if (list1.size() == 0) {
                        App.getInstance().showToast("该校暂时没有专业");
                        return;
                    }
                    new MaterialDialog.Builder(this)
                            .items(list1)
                            .itemsCallback(new MaterialDialog.ListCallback() {
                                @Override
                                public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                                    try {
                                        jsonObject.put("specialtyCatalogId", majorBeans.get(position).getId());
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    tvChooseMajor.setText(majorBeans.get(position).getTitle());
                                }
                            }).show();
                } else {
                    App.getInstance().showToast("该校暂时没有专业");
                }
                break;
            case R.id.llChooseMajor2:
                if (majorBeans != null && majorBeans.size() > 0) {
                    ArrayList<String> list1 = new ArrayList<>();
                    for (int i = 0; i < majorBeans.size(); i++) {
                        if (!TextUtils.isEmpty(majorBeans.get(i).getTitle())) {
                            list1.add(majorBeans.get(i).getTitle());
                        }
                    }
                    if (list1.size() == 0) {
                        App.getInstance().showToast("该校暂时没有专业");
                        return;
                    }
                    new MaterialDialog.Builder(this)
                            .items(list1)
                            .itemsCallback(new MaterialDialog.ListCallback() {
                                @Override
                                public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                                    try {
                                        jsonObject.put("specialtyCatalogId2", majorBeans.get(position).getId());
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    tvChooseMajor2.setText(majorBeans.get(position).getTitle());
                                }
                            }).show();
                } else {
                    App.getInstance().showToast("该校暂时没有专业");
                }
                break;
            case R.id.llChooseType:
                ArrayList<String> list1 = new ArrayList<>();
                list1.add("高中起点本科");
                list1.add("高中起点专科");
                list1.add("专科起点本科");
                new MaterialDialog.Builder(this)
                        .items(list1)
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                                try {
                                    jsonObject.put("applyType", position + 1);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                tvChooseType.setText(text);
                            }
                        }).show();
                break;
            case R.id.btnNextStep:
                try {
                    if (!jsonObject.has("applyType")) {
                        App.getInstance().showToast("请选择报考类型");
                        return;
                    }
                    if (!jsonObject.has("specialtyCatalogId")) {
                        App.getInstance().showToast("请选专业");
                        return;
                    }
                    if (!jsonObject.has("schoolId")) {
                        App.getInstance().showToast("请选学校");
                        return;
                    }
                    String phone = etPhoneNum.getText().toString();
                    if (!PhoneFormatCheckUtils.isChinaPhoneLegal(phone)) {
                        App.getInstance().showToast("请输入合法手机号");
                        return;
                    }
                    jsonObject.put("phone", phone);
                    String name = etUserName.getText().toString();
                    if (TextUtils.isEmpty(name)) {
                        App.getInstance().showToast("请输入姓名");
                        return;
                    }
                    jsonObject.put("userName", name);
                    jsonObject.put("sex", isMan ? "1" : "0");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mPresenter.uploadData(jsonObject);
                break;
        }
    }

    @Override
    public void showSchoolList(List<SchoolBean> schoolBeans) {
        this.schoolBeans = schoolBeans;
    }

    @Override
    public void schoolError() {

    }

    @Override
    public void showMajor(List<MajorBean> majorBeans) {
        this.majorBeans = majorBeans;
    }

    @Override
    public void majorError() {

    }

    @Override
    public void uploadSuccess() {
        App.getInstance().showToast("报名成功");
        finish();
    }

    @Override
    public void uploadFail() {

    }

    @Override
    public void setPresenter(EnrolContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
