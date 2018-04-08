package com.xueli.application.mode.bean.exam;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pingan on 2018/3/26.
 */

public class PaperSections implements Parcelable {

    private long id;
    private long paperSectionId;
    private int flag;
    private String questionCode;
    private long pagerId;
    private int score;
    private String answer;
    private String question;
    private String paperSectionTitle;
    private String options;
    private boolean bResult;
    private List<SectionOption> sectionOptions;// 本地属性 题的选择列表
    private boolean isCollect;//是否收藏
    private boolean isFinish;//本地属性 是否完成
    private String stdAnswer;

    public PaperSections clonePs() {
        PaperSections paperSections = new PaperSections();
        paperSections.setId(id);
        paperSections.setPaperSectionId(paperSectionId);
        paperSections.setFlag(flag);
        paperSections.setQuestionCode(questionCode);
        paperSections.setPagerId(pagerId);
        paperSections.setScore(score);
        paperSections.setStdAnswer(answer);
        paperSections.setQuestion(question);
        paperSections.setPaperSectionTitle(paperSectionTitle);
        paperSections.setOptions(options);
        paperSections.setbResult(bResult);
        List<SectionOptionClone> options = new ArrayList<>();
        for (int i = 0; i < sectionOptions.size(); i++) {
            if (sectionOptions.get(i).isChoose()) {
                options.add(sectionOptions.get(i).cloneSection());
            }
        }
        paperSections.setAnswer(new Gson().toJson(options));
        return paperSections;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPaperSectionId() {
        return paperSectionId;
    }

    public void setPaperSectionId(long paperSectionId) {
        this.paperSectionId = paperSectionId;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getQuestionCode() {
        return questionCode;
    }

    public void setQuestionCode(String questionCode) {
        this.questionCode = questionCode;
    }

    public long getPagerId() {
        return pagerId;
    }

    public void setPagerId(long pagerId) {
        this.pagerId = pagerId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getPaperSectionTitle() {
        return paperSectionTitle;
    }

    public void setPaperSectionTitle(String paperSectionTitle) {
        this.paperSectionTitle = paperSectionTitle;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public boolean isbResult() {
        return bResult;
    }

    public void setbResult(boolean bResult) {
        this.bResult = bResult;
    }

    public List<SectionOption> getSectionOptions() {
        return sectionOptions;
    }

    public void setSectionOptions(List<SectionOption> sectionOptions) {
        this.sectionOptions = sectionOptions;
    }

    public boolean isCollect() {
        return isCollect;
    }

    public void setCollect(boolean collect) {
        isCollect = collect;
    }

    public boolean isFinish() {
        return isFinish;
    }

    public void setFinish(boolean finish) {
        isFinish = finish;
    }

    public String getStdAnswer() {
        return stdAnswer;
    }

    public void setStdAnswer(String stdAnswer) {
        this.stdAnswer = stdAnswer;
    }

    public PaperSections() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeLong(this.paperSectionId);
        dest.writeInt(this.flag);
        dest.writeString(this.questionCode);
        dest.writeLong(this.pagerId);
        dest.writeInt(this.score);
        dest.writeString(this.answer);
        dest.writeString(this.question);
        dest.writeString(this.paperSectionTitle);
        dest.writeString(this.options);
        dest.writeByte(this.bResult ? (byte) 1 : (byte) 0);
        dest.writeTypedList(this.sectionOptions);
        dest.writeByte(this.isCollect ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isFinish ? (byte) 1 : (byte) 0);
        dest.writeString(this.stdAnswer);
    }

    protected PaperSections(Parcel in) {
        this.id = in.readLong();
        this.paperSectionId = in.readLong();
        this.flag = in.readInt();
        this.questionCode = in.readString();
        this.pagerId = in.readLong();
        this.score = in.readInt();
        this.answer = in.readString();
        this.question = in.readString();
        this.paperSectionTitle = in.readString();
        this.options = in.readString();
        this.bResult = in.readByte() != 0;
        this.sectionOptions = in.createTypedArrayList(SectionOption.CREATOR);
        this.isCollect = in.readByte() != 0;
        this.isFinish = in.readByte() != 0;
        this.stdAnswer = in.readString();
    }

    public static final Creator<PaperSections> CREATOR = new Creator<PaperSections>() {
        @Override
        public PaperSections createFromParcel(Parcel source) {
            return new PaperSections(source);
        }

        @Override
        public PaperSections[] newArray(int size) {
            return new PaperSections[size];
        }
    };
}
