package com.xueli.application.view.login.bindSchool;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.library.utils.IdcardUtils;
import com.xueli.application.R;
import com.xueli.application.common.ConstantStr;
import com.xueli.application.mode.Injection;
import com.xueli.application.view.MvpActivity;
import com.xueli.application.view.main.MainActivity;

import java.util.HashMap;
import java.util.Map;

public class BindSchoolActivity extends MvpActivity<BindSchoolContract.Presenter> implements BindSchoolContract.View {

    private EditText userRealName, userCard;
    private TextView zyTv, lxTv;
    private int chooseZy = -1, chooseLx = -1;
    private String[] zyStrList = new String[]{"高起专（文）", "高起专（理）", "专升本（医学类）", "专升本（理工类）", "专升本（经管类）", "专升本（法学类）"
            , "专升本（教育学类）", "专升本（农学类）", "专升本（艺术类）", "专升本（文史中医类）", "高升本（文）", "高升本（理）"};
    private String[] lxStrList = new String[]{"成人高考", "学位考试", "统招专升本", "医学护理"};
    private Map<String, String> map = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayoutAndToolbar(R.layout.activity_bind_school, "填写信息");
        String phone = getIntent().getStringExtra(ConstantStr.KEY_BUNDLE_STR);//手机号码
        String verificationCode = getIntent().getStringExtra(ConstantStr.KEY_BUNDLE_STR_1);//手机号码
        String password = getIntent().getStringExtra(ConstantStr.KEY_BUNDLE_STR_2);//手机号码
        map.put("phone", phone);
        map.put("verificationCode", verificationCode);
        map.put("password", password);
        bindView();
    }

    private void bindView() {
        userRealName = findViewById(R.id.etUserRealName);
        userCard = findViewById(R.id.etUserCard);
        zyTv = findViewById(R.id.tvZy);
        lxTv = findViewById(R.id.tvLx);
        findViewById(R.id.btnSure).setOnClickListener(this);
        findViewById(R.id.llZy).setOnClickListener(this);
        findViewById(R.id.llLx).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.llZy:
                new MaterialDialog.Builder(this).items(this.zyStrList).itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                        BindSchoolActivity.this.chooseZy = position + 1;
                        zyTv.setText(text);
                    }
                }).build().show();
                break;
            case R.id.llLx:
                new MaterialDialog.Builder(this).items(this.lxStrList).itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                        BindSchoolActivity.this.chooseLx = position + 1;
                        lxTv.setText(text);
                    }
                }).build().show();
                break;
            default:
                //sure
                String userCard = this.userCard.getText().toString().trim();
                String userRealName = this.userRealName.getText().toString().trim();
                if (TextUtils.isEmpty(userRealName)) {
                    showMessage("请输入真名");
                    break;
                }
                map.put("realName", userRealName);
                if (TextUtils.isEmpty(userCard)) {
                    showMessage("请输入身份证号码");
                    break;
                }
                if (!IdcardUtils.validateCard(userCard)) {
                    showMessage("输入的身份证不合法");
                    break;
                }
                map.put("idcard", userCard);
                if (chooseZy == -1) {
                    showMessage("请选择报考专业");
                    break;
                }
                map.put("suoxuezhuanye", String.valueOf(chooseZy));
                if (chooseLx == -1) {
                    showMessage("请选择报考类型");
                    break;
                }
                map.put("type", String.valueOf(chooseLx));
                mPresenter.updateUserInfo(map);
                break;
        }
    }

    @Override
    protected void onBindPresenter() {
        new BindSchoolPresenter(Injection.getInjection().provideUserRepository(getApp()), this);
    }

    @Override
    public void updateUserInfoSuccess() {
        Intent intent = new Intent(this, MainActivity.class);
        if (getPackageManager().resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY) != null) {
            try {
                startActivity(intent);
                finish();
            } catch (ActivityNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void showMessage(@Nullable String message) {
        getApp().showToast(message);
    }

    @Override
    public void setPresenter(BindSchoolContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
