package com.xueli.application.view.home.hot;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.library.adapter.RVAdapter;
import com.xueli.application.R;
import com.xueli.application.common.ConstantStr;
import com.xueli.application.mode.bean.study.StudyMessage;
import com.xueli.application.view.BaseActivity;
import com.xueli.application.view.web.MessageDetailActivity;

import java.util.ArrayList;

public class HotListActivity extends BaseActivity {

    private ArrayList<StudyMessage> studyMessages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        studyMessages = getIntent().getParcelableArrayListExtra(ConstantStr.KEY_BUNDLE_LIST);
        setLayoutAndToolbar(R.layout.study_message_list_activity, "热门咨询");
        RecyclerView recyclerView = findViewById(R.id.recycleMessage);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RVAdapter<StudyMessage> adapter = new RVAdapter<StudyMessage>(recyclerView, studyMessages, R.layout.item_home_hot) {
            @Override
            public void showData(ViewHolder vHolder, StudyMessage data, int position) {
                TextView title = (TextView) vHolder.getView(R.id.tvTitle);
                TextView time = (TextView) vHolder.getView(R.id.tvTime);
                title.setText(data.getTitle());

            }
        };
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new RVAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent messageIntent = new Intent(HotListActivity.this, MessageDetailActivity.class);
                messageIntent.putExtra(ConstantStr.KEY_BUNDLE_STR, studyMessages.get(position).getTitle());
                messageIntent.putExtra(ConstantStr.KEY_BUNDLE_STR_1, studyMessages.get(position).getDetail());
                startActivity(messageIntent);
            }
        });
    }
}
