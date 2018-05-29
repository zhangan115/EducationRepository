package com.xueli.application.view.forget;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.xueli.application.R;
import com.xueli.application.app.App;
import com.xueli.application.common.ConstantStr;
import com.xueli.application.mode.user.UserRepository;
import com.xueli.application.view.BaseActivity;
import com.xueli.application.view.MvpActivity;

import java.util.HashMap;
import java.util.Map;

/**
 * 忘记密码
 * Created by pingan on 2018/3/10.
 */

public class ForgetPassWordSureActivity extends MvpActivity<ForgetPassWordSureContract.Presenter> implements ForgetPassWordSureContract.View {

    private Map<String, String> map;
    private String userName;
    private String userPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayoutAndToolbar(R.layout.forget_pass_word_sure_activity, "忘记密码");
        map = new HashMap<>();
        userName = getIntent().getStringExtra(ConstantStr.KEY_BUNDLE_STR);
        userPhone = getIntent().getStringExtra(ConstantStr.KEY_BUNDLE_STR_1);
        String code = getIntent().getStringExtra(ConstantStr.KEY_BUNDLE_STR_2);
        findViewById(R.id.btnNextStep).setOnClickListener(this);
        map.put("accountName", userName);
        map.put("phone", userPhone);
        map.put("verificationCode", code);
    }

    @Override
    protected void onBindPresenter() {
        new ForgetPassWordSurePresenter(UserRepository.getRepository(this), this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnNextStep:
                EditText etUserName = findViewById(R.id.etUserName);
                EditText etUserPassWord = findViewById(R.id.etUserPassWord);
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
                map.put("pssword", etUserName.getText().toString());
                mPresenter.changePass(map);
                break;
        }
    }

    @Override
    public void showMessage(String message) {
        App.getInstance().showToast(message);
    }

    @Override
    public void changeSuccess() {
        Intent intent = new Intent();
        intent.putExtra(ConstantStr.KEY_BUNDLE_STR_1, userPhone);
        intent.putExtra(ConstantStr.KEY_BUNDLE_STR_2, map.get("pssword"));
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    @Override
    public void changeFail(String message) {
        App.getInstance().showToast(message);
    }

    @Override
    public void setPresenter(ForgetPassWordSureContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
