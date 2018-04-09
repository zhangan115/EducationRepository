package com.xueli.application.view.web;

import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.WebView;
import android.widget.TextView;

import com.xueli.application.R;
import com.xueli.application.common.ConstantStr;
import com.xueli.application.mode.study.StudyRepository;
import com.xueli.application.view.WebActivity;

public class MessageDetailActivity extends WebActivity implements MessageDetailContract.View {

    private WebView webView;
    private MessageDetailContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String title = getIntent().getStringExtra(ConstantStr.KEY_BUNDLE_STR);
        String content = getIntent().getStringExtra(ConstantStr.KEY_BUNDLE_STR_1);
        long id = getIntent().getLongExtra(ConstantStr.KEY_BUNDLE_LONG, -1);
        if (id == -1 && TextUtils.isEmpty(title) && TextUtils.isEmpty(content)) {
            finish();
            return;
        }
        setLayoutAndToolbar(R.layout.message_detail_activity, title);
        webView = findViewById(R.id.web_view);
        boolean requestUrl = getIntent().getBooleanExtra(ConstantStr.KEY_BUNDLE_BOOLEAN, false);
        if (requestUrl) {
            loadUrl(webView, content);
        } else {
            new MessageDetailPresenter(StudyRepository.getRepository(this), this);
            if (TextUtils.isEmpty(content)) {
                mPresenter.getUrl(id);
            } else {
                showUrl(content);
            }
        }
    }

    @Override
    public void showUrl(String url) {
        showWeb(webView, url);
    }

    @Override
    public void setPresenter(MessageDetailContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
