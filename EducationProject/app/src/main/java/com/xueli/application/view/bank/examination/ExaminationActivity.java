package com.xueli.application.view.bank.examination;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;

import com.xueli.application.R;
import com.xueli.application.view.BaseActivity;
import com.xueli.application.view.subject.SubjectFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 考试
 * Created by pingan on 2018/3/19.
 */

public class ExaminationActivity extends BaseActivity {

    private ViewPager viewPager;
    private List<String> datas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayoutAndToolbar(R.layout.examination_activity, "考试");
        datas = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            datas.add("第" + i + "题");
        }
        viewPager = findViewById(R.id.view_pager);
        viewPager.setOffscreenPageLimit(4);
        viewPager.setAdapter(new Adapter(getSupportFragmentManager()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.submit_examination, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_submit) {

            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private class Adapter extends FragmentStatePagerAdapter {

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return SubjectFragment.newInstance(datas.get(position));
        }

        @Override
        public int getCount() {
            return datas.size();
        }
    }

}
