package com.xueli.application.view.bank.filter;

import android.os.Bundle;
import android.view.View;

import com.xueli.application.R;
import com.xueli.application.view.BaseActivity;

/**
 * 筛选试题
 * Created by pingan on 2018/3/13.
 */

public class BankFilterActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTranslucent);
        setContentView(R.layout.bank_filter_activity);
        transparentStatusBar();
        findViewById(R.id.llEmpty).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llEmpty:
                break;
        }
    }
}
