package com.xueli.application.view.login.bindSchool;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.library.utils.IdcardUtils;
import com.xueli.application.R;
import com.xueli.application.app.App;
import com.xueli.application.common.ConstantStr;
import com.xueli.application.mode.Injection;
import com.xueli.application.view.MvpActivity;
import com.xueli.application.view.main.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class BindSchoolActivity extends MvpActivity<BindSchoolContract.Presenter> implements BindSchoolContract.View {

    private RelativeLayout registerSuccessLayout;
    private EditText userRealName, userCard, etEnterPass, etEnterPassAgain;
    ;
    private TextView zyTv, lxTv;
    private int chooseZy = -1, chooseLx = -1;
    private String[] zyStrList = new String[]{"高起专（文）", "高起专（理）", "专升本（医学类）", "专升本（理工类）", "专升本（经管类）", "专升本（法学类）"
            , "专升本（教育学类）", "专升本（农学类）", "专升本（艺术类）", "专升本（文史中医类）", "高升本（文）", "高升本（理）"};
    private String[] lxStrList = new String[]{"成人高考", "学位考试", "统招专升本", "医学护理"};
    private JSONObject json = new JSONObject();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayoutAndToolbar(R.layout.activity_bind_school, "填写信息");
        String phone = getIntent().getStringExtra(ConstantStr.KEY_BUNDLE_STR);//手机号码
        String verificationCode = getIntent().getStringExtra(ConstantStr.KEY_BUNDLE_STR_1);//手机号码
        int loginType = getIntent().getIntExtra(ConstantStr.KEY_BUNDLE_INT, 0);//1 微信登陆 2 qq登录
        String openId = getIntent().getStringExtra(ConstantStr.KEY_BUNDLE_STR_3);//微信的id
        try {
            json.put("phone", phone);
            json.put("verificationCode", verificationCode);
            json.put("openId", openId);
            json.put("loginType", loginType);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        bindView();
    }

    private void bindView() {
        userRealName = findViewById(R.id.etUserRealName);
        userCard = findViewById(R.id.etUserCard);
        registerSuccessLayout = findViewById(R.id.registerSuccessLayout);
        zyTv = findViewById(R.id.tvZy);
        lxTv = findViewById(R.id.tvLx);
        etEnterPass = findViewById(R.id.etUserPass);
        etEnterPassAgain = findViewById(R.id.etUserPassWordAgain);
        findViewById(R.id.btnSure).setOnClickListener(this);
        findViewById(R.id.llZy).setOnClickListener(this);
        findViewById(R.id.llLx).setOnClickListener(this);
        findViewById(R.id.openHome).setOnClickListener(this);
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
            case R.id.openHome:
                setResult(Activity.RESULT_OK);
                finish();
                break;
            default:
                EditText etUserName = etEnterPass;
                EditText etUserPassWord = etEnterPassAgain;
                if (TextUtils.isEmpty(etUserName.getText().toString())
                        || TextUtils.isEmpty(etUserPassWord.getText().toString())) {
                    showMessage("请输入密码");
                    break;
                }
                if (!etUserName.getText().toString().equals(etUserPassWord.getText().toString())) {
                    App.getInstance().showToast("两次密码不一致");
                    break;
                }
                if (etUserName.getEditableText().toString().length() < 8) {
                    App.getInstance().showToast("密码由8-12位的字母和数字组成");
                    break;
                }
                String password = etUserName.getText().toString();
                String userCard = this.userCard.getText().toString().trim();
                String userRealName = this.userRealName.getText().toString().trim();
                if (TextUtils.isEmpty(userRealName)) {
                    showMessage("请输入真名");
                    break;
                }
                try {
                    json.put("password", password);
                    json.put("realName", userRealName);
                    if (TextUtils.isEmpty(userCard)) {
                        showMessage("请输入身份证号码");
                        break;
                    }
                    if (!IdcardUtils.validateCard(userCard)) {
                        showMessage("输入的身份证不合法");
                        break;
                    }
                    json.put("idcard", userCard);
                    if (chooseZy == -1) {
                        showMessage("请选择报考专业");
                        break;
                    }
                    json.put("suoxuezhuanye", String.valueOf(chooseZy));
                    if (chooseLx == -1) {
                        showMessage("请选择报考类型");
                        break;
                    }
                    json.put("type", String.valueOf(chooseLx));
                    mPresenter.updateUserInfo(json);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    protected void onBindPresenter() {
        new BindSchoolPresenter(Injection.getInjection().provideUserRepository(getApp()), this);
    }

    @Override
    public void updateUserInfoSuccess() {
        registerSuccessLayout.setVisibility(View.VISIBLE);
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
