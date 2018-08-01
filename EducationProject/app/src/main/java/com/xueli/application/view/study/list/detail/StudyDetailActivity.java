package com.xueli.application.view.study.list.detail;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;

import com.library.utils.SPHelper;
import com.xueli.application.R;
import com.xueli.application.common.ConstantStr;
import com.xueli.application.mode.study.StudyRepository;
import com.xueli.application.view.WebActivity;

public class StudyDetailActivity extends WebActivity implements StudyDetailContract.View {

    private WebView webView;

    private StudyDetailContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String title = getIntent().getStringExtra(ConstantStr.KEY_BUNDLE_STR);
        long id = getIntent().getLongExtra(ConstantStr.KEY_BUNDLE_LONG, -1);
        if (id == -1 && TextUtils.isEmpty(title)) {
            finish();
            return;
        }
        setLayoutAndToolbar(R.layout.message_detail_activity, title);
        webView = findViewById(R.id.web_view);
        webView.setVisibility(View.VISIBLE);
        new StudyDetailPresenter(StudyRepository.getRepository(this), this);
        mPresenter.getDetail(id);
    }

    @Override
    public void showUrl(String url) {
        showWeb(webView, url);
    }

    @Override
    public void setPresenter(StudyDetailContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
