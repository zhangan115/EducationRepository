package com.xueli.application.view.subject;

import android.os.Bundle;

import com.library.utils.ActivityUtilsV4;
import com.xueli.application.R;
import com.xueli.application.common.ConstantStr;
import com.xueli.application.mode.bean.exam.PaperSections;
import com.xueli.application.view.BaseActivity;

public class SubjectActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayoutAndToolbar(R.layout.subject_activity, "题目");
        PaperSections content = getIntent().getParcelableExtra(ConstantStr.KEY_BUNDLE_OBJECT);
        SubjectFragment fragment = (SubjectFragment) getSupportFragmentManager().findFragmentById(R.id.frame_container);
        if (fragment == null) {
            fragment = SubjectFragment.newInstance(content, 0, true);
            ActivityUtilsV4.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.frame_container);
        }
    }
}
