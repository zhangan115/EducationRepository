package com.xueli.application.mode.bean.exam;

import android.os.Parcel;
import android.os.Parcelable;

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
    }

    public PaperSections() {
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
