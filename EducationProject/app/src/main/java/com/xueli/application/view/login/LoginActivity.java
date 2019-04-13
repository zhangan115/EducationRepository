package com.xueli.application.view.login;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.library.utils.SPHelper;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.xueli.application.R;
import com.xueli.application.app.App;
import com.xueli.application.common.ConstantStr;
import com.xueli.application.mode.Injection;
import com.xueli.application.mode.bean.user.WeiXinLoginBean;
import com.xueli.application.view.MvpActivity;
import com.xueli.application.view.forget.ForgetPassWordActivity;
import com.xueli.application.view.login.bindPhone.BindPhoneActivity;
import com.xueli.application.view.main.MainActivity;
import com.xueli.application.view.register.RegisterSureActivity;

/**
 * 登陆界面
 * Created by pingan on 2018/3/4.
 */

public class LoginActivity extends MvpActivity<LoginContract.Presenter> implements LoginContract.View {

    private EditText userNameEt, userPassWordEt;
    private LinearLayout otherLoginLayout;
    private ImageButton weChatLoginBtn, qqLoginBtn;

    private IWXAPI mIWxapi;
    private Tencent mTencent;
    private final int START_REGISTER = 100;
    private final int START_FORGET = 101;
    private final static int START_BIND_PHONE = 102;

    private BroadcastReceiver weiXinLoginBr = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (TextUtils.equals(intent.getAction(), "Action_weiXin_code")) {
                String code = intent.getStringExtra("code");
                showProgressDialog("登陆中...");
                mPresenter.weiXinLogin(code);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.LoginActivityStyle);
        setContentView(R.layout.login_activity);
        transparentStatusBar();
        initView();
        mIWxapi = WXAPIFactory.createWXAPI(this, "wx1c0c07722cf3fe96");
        mTencent = Tencent.createInstance("", this.getApplicationContext());
        registerReceiver(weiXinLoginBr, new IntentFilter("Action_weiXin_code"));
    }

    private void initView() {
        userNameEt = findViewById(R.id.etUserName);
        userPassWordEt = findViewById(R.id.etUserPassWord);
        weChatLoginBtn = findViewById(R.id.weChatBtn);
        qqLoginBtn = findViewById(R.id.qqBtn);
        otherLoginLayout = findViewById(R.id.otherLoginLL);
        findViewById(R.id.btnLogin).setOnClickListener(this);
        findViewById(R.id.tvForgetPass).setOnClickListener(this);
        findViewById(R.id.tvRegister).setOnClickListener(this);
        weChatLoginBtn.setOnClickListener(this);
        qqLoginBtn.setOnClickListener(this);
    }

    @Override
    protected void onBindPresenter() {
        new LoginPresenter(Injection.getInjection().provideUserRepository(getApp()), this);
    }

    @Override
    public void loginSuccess() {
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
    public void loginLoading() {
        showProgressDialog("登陆中...");
    }

    @Override
    public void loginHideLoading() {
        hideProgressDialog();
    }

    @Override
    public void showMessage(@Nullable String message) {
        getApp().showToast(message);
    }

    @Override
    public void showWeiXinBean(WeiXinLoginBean bean) {
        if (bean.getUser() == null) {//没有用户信息 去完善信息
            Intent intent = new Intent(this, BindPhoneActivity.class);
            intent.putExtra(ConstantStr.KEY_BUNDLE_STR, bean.getOpenId());
            startActivityForResult(intent, START_BIND_PHONE);
        } else {
            App.getInstance().setCurrentUser(bean.getUser());
            SPHelper.write(App.getInstance(), ConstantStr.USER_INFO, ConstantStr.TOKEN, bean.getUser().getToken());
            loginSuccess();
        }
    }


    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btnLogin:
                if (TextUtils.isEmpty(userNameEt.getText().toString())) {
                    App.getInstance().showToast("请输入用户名");
                    return;
                }
                if (TextUtils.isEmpty(userPassWordEt.getText().toString())) {
                    App.getInstance().showToast("请输入密码");
                    return;
                }
                mPresenter.login(userNameEt.getText().toString(), userPassWordEt.getText().toString());
                break;
            case R.id.tvForgetPass:
                Intent forgetPassInt = new Intent(this, ForgetPassWordActivity.class);
                startActivityForResult(forgetPassInt, START_FORGET);
                break;
            case R.id.tvRegister:
                Intent intent = new Intent(this, BindPhoneActivity.class);
                startActivityForResult(intent, START_BIND_PHONE);
                break;
            case R.id.weChatBtn:
                SendAuth.Req req = new SendAuth.Req();
                req.scope = "snsapi_userinfo,snsapi_friend,snsapi_message,snsapi_contact";
                req.state = "none";
                mIWxapi.sendReq(req);
                break;
            case R.id.qqBtn:
                mTencent.login(this, "all", listener, false);
                break;
        }
    }

    IUiListener listener = new IUiListener() {

        @Override
        public void onComplete(Object o) {

        }

        @Override
        public void onError(UiError uiError) {

        }

        @Override
        public void onCancel() {

        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == START_FORGET && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                String name = data.getStringExtra(ConstantStr.KEY_BUNDLE_STR_1);
                String pass = data.getStringExtra(ConstantStr.KEY_BUNDLE_STR_2);
                if (!TextUtils.isEmpty(name)) {
                    userNameEt.setText(name);
                }
                if (!TextUtils.isEmpty(pass)) {
                    userPassWordEt.setText(pass);
                }
            }
        } else if (requestCode == START_REGISTER && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                String phone = data.getStringExtra(ConstantStr.KEY_BUNDLE_STR_1);
                String pass = data.getStringExtra(ConstantStr.KEY_BUNDLE_STR_2);
                if (!TextUtils.isEmpty(phone)) {
                    userNameEt.setText(phone);
                }
                if (!TextUtils.isEmpty(pass)) {
                    userPassWordEt.setText(pass);
                }
            }
        } else if (requestCode == START_BIND_PHONE && resultCode == Activity.RESULT_OK) {
            loginSuccess();
        } else {
            Tencent.onActivityResultData(requestCode, resultCode, data, listener);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            if (weiXinLoginBr != null)
                unregisterReceiver(weiXinLoginBr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
