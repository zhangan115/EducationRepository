package com.xueli.application.mode.bean.exam;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by pingan on 2018/3/26.
 */

public class QuestionCatalog implements Parcelable {

    private int id;
    private String title;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
    }

    public QuestionCatalog() {
    }

    protected QuestionCatalog(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
    }

    public static final Creator<QuestionCatalog> CREATOR = new Creator<QuestionCatalog>() {
        @Override
        public QuestionCatalog createFromParcel(Parcel source) {
            return new QuestionCatalog(source);
        }

        @Override
        public QuestionCatalog[] newArray(int size) {
            return new QuestionCatalog[size];
        }
    };
}
