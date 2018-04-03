package com.xueli.application.view.bank.examination;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.xueli.application.mode.bean.exam.PaperCollection;
import com.xueli.application.mode.bean.exam.PaperSectionList;
import com.xueli.application.mode.bean.exam.PaperSections;
import com.xueli.application.mode.callback.IListCallBack;
import com.xueli.application.mode.callback.IObjectCallBack;
import com.xueli.application.mode.exam.ExamDataSource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import rx.subscriptions.CompositeSubscription;

/**
 * 考试
 * Created by pingan on 2018/3/26.
 */

class ExaminationPresenter implements ExaminationContract.Presenter {

    private final ExaminationContract.View mView;
    private final ExamDataSource mDataSource;
    private CompositeSubscription mSubscriptions;

    ExaminationPresenter(ExaminationContract.View mView, ExamDataSource mDataSource) {
        this.mView = mView;
        this.mDataSource = mDataSource;
        mView.setPresenter(this);
    }

    @Override
    public void getPaperSections(long id) {
        mView.showLoading();
        mSubscriptions.add(mDataSource.getPaperSections(id, new IListCallBack<PaperSections>() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onData(@NonNull List<PaperSections> list) {
                mView.showData(list);
                Map<String, List<PaperSections>> map = new HashMap<>();
                for (int i = 0; i < list.size(); i++) {
                    if (map.containsKey(String.valueOf(list.get(i).getFlag()))) {
                        map.get(String.valueOf(list.get(i).getFlag())).add(list.get(i));
                    } else {
                        ArrayList<PaperSections> paperSections = new ArrayList<>();
                        paperSections.add(list.get(i));
                        map.put(String.valueOf(list.get(i).getFlag()), paperSections);
                    }
                }
                Iterator<String> iterator = map.keySet().iterator();
                List<PaperSectionList> paperSectionLists = new ArrayList<>();
                int count = 0;
                while (iterator.hasNext()) {
                    count++;
                    String key = iterator.next();
                    String name = "";
                    for (int i = 0; i < list.size(); i++) {
                        if (Integer.valueOf(key) == list.get(i).getFlag()) {
                            name = list.get(i).getPaperSectionTitle();
                            break;
                        }
                    }
                    paperSectionLists.add(new PaperSectionList(0, name, count, Integer.valueOf(key)));
                    List<PaperSections> paperSections = map.get(key);
                    for (int i = 0; i < paperSections.size(); i++) {
                        paperSectionLists.add(new PaperSectionList(1, String.valueOf(i + 1), paperSections.get(i).getId(), Integer.valueOf(key)));
                    }
                }
                mView.showPaperSectionListData(paperSectionLists);
            }

            @Override
            public void onError(@Nullable String message) {
                mView.showMessage(message);
            }

            @Override
            public void onFinish() {
                mView.hideLoading();
            }

            @Override
            public void noData() {
                mView.noData();
            }
        }));
    }

    @Override
    public void getFaultExamPaperSections(long id) {
        mSubscriptions.add(mDataSource.getMyFaultExamPaper(id, new IListCallBack<PaperSections>() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onData(@NonNull List<PaperSections> list) {
                mView.showData(list);
                Map<String, List<PaperSections>> map = new HashMap<>();
                for (int i = 0; i < list.size(); i++) {
                    if (map.containsKey(String.valueOf(list.get(i).getFlag()))) {
                        map.get(String.valueOf(list.get(i).getFlag())).add(list.get(i));
                    } else {
                        ArrayList<PaperSections> paperSections = new ArrayList<>();
                        paperSections.add(list.get(i));
                        map.put(String.valueOf(list.get(i).getFlag()), paperSections);
                    }
                }
                Iterator<String> iterator = map.keySet().iterator();
                List<PaperSectionList> paperSectionLists = new ArrayList<>();
                int count = 0;
                while (iterator.hasNext()) {
                    count++;
                    String key = iterator.next();
                    String name = "";
                    for (int i = 0; i < list.size(); i++) {
                        if (Integer.valueOf(key) == list.get(i).getFlag()) {
                            name = list.get(i).getPaperSectionTitle();
                            break;
                        }
                    }
                    paperSectionLists.add(new PaperSectionList(0, name, count, Integer.valueOf(key)));
                    List<PaperSections> paperSections = map.get(key);
                    for (int i = 0; i < paperSections.size(); i++) {
                        paperSectionLists.add(new PaperSectionList(1, String.valueOf(i + 1), paperSections.get(i).getId(), Integer.valueOf(key)));
                    }
                }
                mView.showPaperSectionListData(paperSectionLists);
            }

            @Override
            public void onError(@Nullable String message) {
                mView.showMessage(message);
            }

            @Override
            public void onFinish() {
                mView.hideLoading();
            }

            @Override
            public void noData() {
                mView.noData();
            }
        }));
    }

    @Override
    public void collectPaper(final long paperQuestionId, long accountId) {
        mSubscriptions.add(mDataSource.collectionPaper(paperQuestionId, accountId, new IObjectCallBack<PaperCollection>() {
            @Override
            public void onSuccess() {
                mView.collectStateChange(paperQuestionId, true);
            }

            @Override
            public void onData(@NonNull PaperCollection paperCollection) {

            }

            @Override
            public void onError(@Nullable String message) {
                mView.showMessage(message);
            }

            @Override
            public void noData() {

            }

            @Override
            public void onFinish() {

            }
        }));
    }

    @Override
    public void uploadData(List<PaperSections> list) {
        mView.showUploadDialog();
        mSubscriptions.add(mDataSource.uploadData(list, new IObjectCallBack<String>() {
            @Override
            public void onSuccess() {
                mView.uploadSuccess();
            }

            @Override
            public void onData(@NonNull String s) {

            }

            @Override
            public void onError(@Nullable String message) {

            }

            @Override
            public void noData() {

            }

            @Override
            public void onFinish() {
                mView.hideUploadDialog();
            }
        }));
    }

    @Override
    public void subscribe() {
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void unSubscribe() {
        mSubscriptions.clear();
    }
}
