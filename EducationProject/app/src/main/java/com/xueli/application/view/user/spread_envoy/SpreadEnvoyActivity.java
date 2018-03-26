package com.xueli.application.view.user.spread_envoy;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.xueli.application.R;
import com.xueli.application.view.BaseActivity;

/**
 * 推广大使
 * Created by pingan on 2018/3/26.
 */

public class SpreadEnvoyActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayoutAndToolbar(R.layout.spread_envory_activity, "推广大使");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.filter, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
