package com.xueli.application.view.home.pay;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.xueli.application.R;
import com.xueli.application.app.App;
import com.xueli.application.mode.bean.user.PaySchoolList;
import com.xueli.application.mode.user.UserRepository;
import com.xueli.application.view.MvpActivity;

import java.util.ArrayList;
import java.util.List;

public class PayActivity extends MvpActivity<PayContract.Presenter> implements PayContract.View {

    //学校
    private long schoolId = 0;
    private String schoolName = "";
    private TextView schoolNameTextView;
    private static int SCHOOL_REQUEST = 100;
    //类型
    private long typeId = 0;
    private String typeName = "";
    private TextView typeNameTextView;
    private static int TYPE_REQUEST = 101;
    //年级
    private long gradeId = 0;
    private String gradeName = "";
    private TextView gradeNameTextView;
    private static int GRADE_REQUEST = 102;
    //data
    private List<PaySchoolList> datas = new ArrayList<>();
    private PaySchoolList currentSchool;
    private PaySchoolList.TuitionListBean currentTuition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayoutAndToolbar(R.layout.activity_pay, "学费缴纳");
        findViewById(R.id.chooseSchoolLayout).setOnClickListener(this);
        findViewById(R.id.chooseTypeLayout).setOnClickListener(this);
        findViewById(R.id.chooseGradeLayout).setOnClickListener(this);
        findViewById(R.id.btnSure).setOnClickListener(this);
        schoolNameTextView = findViewById(R.id.schoolNameTv);
        typeNameTextView = findViewById(R.id.typeNameTv);
        gradeNameTextView = findViewById(R.id.gradeNameTv);
        mPresenter.getPay();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.chooseSchoolLayout:
                if (datas.size() == 0) return;
                Intent schoolIntent = new Intent(this, ChoosePayActivity.class);
                schoolIntent.putExtra("title", "选择院校");
                long[] schoolIds = new long[datas.size()];
                ArrayList<String> schoolList = new ArrayList<>();
                for (int i = 0; i < datas.size(); i++) {
                    schoolIds[i] = datas.get(i).getSchoolId();
                    schoolList.add(datas.get(i).getSchoolName());
                }
                if (schoolId != 0) {
                    schoolIntent.putExtra("id", schoolId);
                }
                schoolIntent.putStringArrayListExtra("data", schoolList);
                schoolIntent.putExtra("ids", schoolIds);
                startActivityForResult(schoolIntent, SCHOOL_REQUEST);
                break;
            case R.id.chooseTypeLayout:
                if (schoolId == 0) {
                    showMessage("请选择院校");
                    return;
                }
                PaySchoolList school = null;
                for (int i = 0; i < datas.size(); i++) {
                    if (datas.get(i).getSchoolId() == schoolId) {
                        school = datas.get(i);
                        break;
                    }
                }
                if (school == null || school.getTuitionList() == null || school.getTuitionList().size() == 0)
                    return;
                Intent typeIntent = new Intent(this, ChoosePayActivity.class);
                typeIntent.putExtra("title", "选择类别");
                long[] typeIds = new long[school.getTuitionList().size()];
                ArrayList<String> typeList = new ArrayList<>();
                for (int i = 0; i < school.getTuitionList().size(); i++) {
                    PaySchoolList.TuitionListBean bean = school.getTuitionList().get(i);
                    typeIds[i] = bean.getTuitionId();
                    typeList.add(bean.getTuitionType());
                }
                if (typeId != 0) {
                    typeIntent.putExtra("id", typeId);
                }
                typeIntent.putStringArrayListExtra("data", typeList);
                typeIntent.putExtra("ids", typeIds);
                startActivityForResult(typeIntent, TYPE_REQUEST);
                break;
            case R.id.chooseGradeLayout:

                break;
            case R.id.btnSure:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SCHOOL_REQUEST) {
                if (data != null) {
                    schoolId = data.getLongExtra("id", 0);
                    schoolName = data.getStringExtra("name");
                    if (schoolId != 0 && !TextUtils.isEmpty(schoolName)) {
                        schoolNameTextView.setText(schoolName);
                    }
                }
            } else if (requestCode == TYPE_REQUEST) {
                typeId = data.getLongExtra("id", 0);
                typeName = data.getStringExtra("name");
                if (typeId != 0 && !TextUtils.isEmpty(typeName)) {
                    typeNameTextView.setText(typeName);
                }
            }
        }
    }

    @Override
    public void payAli() {

    }

    @Override
    public void payWeiXin() {

    }

    @Override
    public void showPaySchoolList(List<PaySchoolList> list) {
        datas.clear();
        datas.addAll(list);
    }

    @Override
    public void showMessage(@Nullable String message) {
        App.getInstance().showToast(message);
    }

    @Override
    public void setPresenter(PayContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    protected void onBindPresenter() {
        new PayPresenter(UserRepository.getRepository(this), this);
    }
}
