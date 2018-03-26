package com.xueli.application.mode.bean.exam;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pingan on 2018/3/26.
 */

public class ExamList implements Parcelable {

    private long id;
    private String isYear;
    private String pagerType;
    private String pagerYear;
    private String title;
    private String type;
    private QuestionCatalog questionCatalog;
    private List<PaperSections> paperSections;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIsYear() {
        return isYear;
    }

    public void setIsYear(String isYear) {
        this.isYear = isYear;
    }

    public String getPagerType() {
        return pagerType;
    }

    public void setPagerType(String pagerType) {
        this.pagerType = pagerType;
    }

    public String getPagerYear() {
        return pagerYear;
    }

    public void setPagerYear(String pagerYear) {
        this.pagerYear = pagerYear;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public QuestionCatalog getQuestionCatalog() {
        return questionCatalog;
    }

    public void setQuestionCatalog(QuestionCatalog questionCatalog) {
        this.questionCatalog = questionCatalog;
    }

    public List<PaperSections> getPaperSections() {
        return paperSections;
    }

    public void setPaperSections(List<PaperSections> paperSections) {
        this.paperSections = paperSections;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.isYear);
        dest.writeString(this.pagerType);
        dest.writeString(this.pagerYear);
        dest.writeString(this.title);
        dest.writeString(this.type);
        dest.writeParcelable(this.questionCatalog, flags);
        dest.writeList(this.paperSections);
    }

    public ExamList() {
    }

    protected ExamList(Parcel in) {
        this.id = in.readLong();
        this.isYear = in.readString();
        this.pagerType = in.readString();
        this.pagerYear = in.readString();
        this.title = in.readString();
        this.type = in.readString();
        this.questionCatalog = in.readParcelable(QuestionCatalog.class.getClassLoader());
        this.paperSections = new ArrayList<PaperSections>();
        in.readList(this.paperSections, PaperSections.class.getClassLoader());
    }

    public static final Creator<ExamList> CREATOR = new Creator<ExamList>() {
        @Override
        public ExamList createFromParcel(Parcel source) {
            return new ExamList(source);
        }

        @Override
        public ExamList[] newArray(int size) {
            return new ExamList[size];
        }
    };
}
