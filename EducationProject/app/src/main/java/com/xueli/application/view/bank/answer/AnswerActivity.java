package com.xueli.application.view.bank.answer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.xueli.application.R;
import com.xueli.application.common.ConstantStr;
import com.xueli.application.mode.bean.exam.PaperSectionList;
import com.xueli.application.mode.bean.exam.PaperSections;
import com.xueli.application.view.BaseActivity;
import com.xueli.application.view.bank.examination.ExaminationActivity;
import com.xueli.application.view.bank.examination.SubjectAdapter;

import java.util.ArrayList;
import java.util.List;

public class AnswerActivity extends BaseActivity {

    private ArrayList<PaperSections> paperSections;
    private ArrayList<PaperSectionList> paperSectionLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayoutAndToolbar(R.layout.answer_activity, "答案统计");
        paperSections = getIntent().getParcelableArrayListExtra(ConstantStr.KEY_BUNDLE_LIST);
        paperSectionLists = getIntent().getParcelableArrayListExtra(ConstantStr.KEY_BUNDLE_LIST_1);
        if (paperSectionLists == null || paperSections == null) {
            finish();
            return;
        }
        List<PaperSectionList> list = new ArrayList<>();
        for (int i = 0; i < paperSectionLists.size(); i++) {
            if (paperSectionLists.get(i).getType() == 0) {
                list.add(paperSectionLists.get(i));
            }
        }
        for (int i = 0; i < list.size(); i++) {
            int rightCount = 0, faultCount = 0, finishCount = 0;
            for (int j = 0; j < paperSectionLists.size(); j++) {
                if (paperSectionLists.get(j).getType() == 1 && list.get(i).getFlag() == paperSectionLists.get(j).getFlag()) {
                    if (paperSectionLists.get(j).isRight()) {
                        rightCount++;
                    } else {
                        faultCount++;
                    }
                    if (paperSectionLists.get(j).isFinish()) {
                        finishCount++;
                    }
                }
                list.get(i).setRightCount(rightCount);
                list.get(i).setFaultCount(faultCount);
                list.get(i).setFinishCount(finishCount);
            }
        }
        final RecyclerView recyclerView = findViewById(R.id.recycleAnswer);
        final GridLayoutManager manager = new GridLayoutManager(this.getApplicationContext(), 6);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (recyclerView.getAdapter().getItemViewType(position) == 0) {
                    return manager.getSpanCount();
                } else {
                    return 1;
                }
            }
        });
        recyclerView.setLayoutManager(manager);
        SubjectAdapter adapter = new SubjectAdapter(paperSectionLists, getApplicationContext(), true);
        adapter.setOnItemClickListener(new SubjectAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(long id) {
                Intent intent = new Intent(AnswerActivity.this, ExaminationActivity.class);
                intent.putParcelableArrayListExtra(ConstantStr.KEY_BUNDLE_LIST, paperSections);
                intent.putParcelableArrayListExtra(ConstantStr.KEY_BUNDLE_LIST_1, paperSectionLists);
                int position = -1;
                for (int i = 0; i < paperSections.size(); i++) {
                    if (paperSections.get(i).getId() == id) {
                        position = i;
                        break;
                    }
                }
                intent.putExtra(ConstantStr.KEY_BUNDLE_INT, position);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);
    }
}
