package com.xueli.application.view.home.pay;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.library.adapter.RVAdapter;
import com.library.widget.ExpendRecycleView;
import com.xueli.application.R;
import com.xueli.application.view.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class ChoosePayActivity extends BaseActivity {

    private ArrayList<String> data;
    private List<Long> ids = new ArrayList<>();
    private long currentId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String title = getIntent().getStringExtra("title");
        data = getIntent().getStringArrayListExtra("data");
        if (data == null){
            finish();
            return;
        }
        final long[] ids = getIntent().getLongArrayExtra("ids");
        currentId = getIntent().getLongExtra("id", -1);
        for (long id : ids) {
            this.ids.add(id);
        }
        setLayoutAndToolbar(R.layout.activity_choose_pay, title);
        ExpendRecycleView recycleView = findViewById(R.id.recycleView);
        recycleView.setLayoutManager(new GridLayoutManager(this, 1));
        RVAdapter<String> adapter = new RVAdapter<String>(recycleView, data, R.layout.item_choose_pay) {
            @Override
            public void showData(ViewHolder vHolder, String data, int position) {
                TextView name = (TextView) vHolder.getView(R.id.name);
                ImageView icon = (ImageView) vHolder.getView(R.id.icon);
                View division = vHolder.getView(R.id.division);
                if (position == 0){
                    division.setVisibility(View.GONE);
                }else {
                    division.setVisibility(View.VISIBLE);
                }
                name.setText(data);
                if (currentId == ChoosePayActivity.this.ids.get(position)) {
                    name.setTextColor(findColorById(R.color.color_fault_blue));
                    icon.setVisibility(View.VISIBLE);
                } else {
                    name.setTextColor(findColorById(R.color.text_gray_666));
                    icon.setVisibility(View.GONE);
                }
            }
        };
        adapter.setOnItemClickListener(new RVAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent();
                intent.putExtra("id", ChoosePayActivity.this.ids.get(position));
                intent.putExtra("name", ChoosePayActivity.this.data.get(position));
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
        recycleView.setAdapter(adapter);
    }
}
