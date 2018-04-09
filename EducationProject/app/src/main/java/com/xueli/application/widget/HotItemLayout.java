package com.xueli.application.widget;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xueli.application.R;

public class HotItemLayout extends LinearLayout {

    private TextView tvTitle;
    private TextView tvTime;

    public HotItemLayout(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        inflate(context, R.layout.item_home_hot, this);
        tvTitle = findViewById(R.id.tvTitle);
        tvTime = findViewById(R.id.tvTime);
    }

    public void setData(String title, String time) {
        tvTitle.setText(title);
        tvTime.setText(time);
    }
}
