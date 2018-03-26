package com.xueli.application.mode.bean.exam;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by pingan on 2018/3/26.
 */

public class PaperSections implements Parcelable {

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public PaperSections() {
    }

    protected PaperSections(Parcel in) {
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
