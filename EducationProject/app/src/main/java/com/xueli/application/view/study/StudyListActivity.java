package com.xueli.application.view.study;

import android.os.Bundle;

import com.library.utils.ActivityUtilsV4;
import com.xueli.application.R;
import com.xueli.application.common.ConstantStr;
import com.xueli.application.view.BaseActivity;
import com.xueli.application.view.study.list.StudyListFragment;

public class StudyListActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String title = getIntent().getStringExtra(ConstantStr.KEY_BUNDLE_STR);
        long id = getIntent().getLongExtra(ConstantStr.KEY_BUNDLE_LONG, -1);
        setLayoutAndToolbar(R.layout.study_list_activity, title);
        StudyListFragment fragment = (StudyListFragment) getSupportFragmentManager().findFragmentById(R.id.frame_container);
        if (fragment == null) {
            fragment = StudyListFragment.newInstance(id);
            ActivityUtilsV4.addFragmentToActivity(getSupportFragmentManager(),fragment,R.id.frame_container);
        }
    }
}
