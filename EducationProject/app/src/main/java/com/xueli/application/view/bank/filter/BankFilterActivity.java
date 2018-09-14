package com.xueli.application.view.bank.filter;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.xueli.application.R;
import com.xueli.application.common.ConstantStr;
import com.xueli.application.view.BaseActivity;
import com.xueli.application.view.bank.list.BankListActivity;

public class BankFilterActivity extends BaseActivity {

    private String isYear;
    private String tag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tag = getIntent().getStringExtra(ConstantStr.KEY_BUNDLE_STR);
        if (!TextUtils.isEmpty(tag)) {
            String[] types = tag.split(",");
            if (types.length == 2) {
                isYear = String.valueOf(types[1]);
            }
            setLayoutAndToolbar(R.layout.bank_filter_activity, isYear.equals("0") ? "模拟试题" : "历年真题");
        }
    }

    @Override
    public void onClick(View v) {
        String type = (String) v.getTag();
        Intent intent = new Intent(this, BankListActivity.class);
        intent.putExtra(ConstantStr.KEY_BUNDLE_STR, tag);
        intent.putExtra(ConstantStr.KEY_BUNDLE_STR_1, type);
        startActivity(intent);
    }
}
