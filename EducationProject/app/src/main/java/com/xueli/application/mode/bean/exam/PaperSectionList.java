package com.xueli.application.mode.bean.exam;

import android.os.Parcel;
import android.os.Parcelable;

public class PaperSectionList implements Parcelable{

    private int type;
    private String name;
    private long id;
    private boolean isRight;
    private boolean isFinish;
    private int rightCount;
    private int faultCount;

    public PaperSectionList() {

    }

    public PaperSectionList(int type, String name, long id) {
        this.type = type;
        this.name = name;
        this.id = id;
    }

    public PaperSectionList(int type, String name, long id, boolean isRight, boolean isFinish, int rightCount, int faultCount) {
        this.type = type;
        this.name = name;
        this.id = id;
        this.isRight = isRight;
        this.isFinish = isFinish;
        this.rightCount = rightCount;
        this.faultCount = faultCount;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isRight() {
        return isRight;
    }

    public void setRight(boolean right) {
        isRight = right;
    }

    public boolean isFinish() {
        return isFinish;
    }

    public void setFinish(boolean finish) {
        isFinish = finish;
    }

    public int getRightCount() {
        return rightCount;
    }

    public void setRightCount(int rightCount) {
        this.rightCount = rightCount;
    }

    public int getFaultCount() {
        return faultCount;
    }

    public void setFaultCount(int faultCount) {
        this.faultCount = faultCount;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.type);
        dest.writeString(this.name);
        dest.writeLong(this.id);
        dest.writeByte(this.isRight ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isFinish ? (byte) 1 : (byte) 0);
        dest.writeInt(this.rightCount);
        dest.writeInt(this.faultCount);
    }

    protected PaperSectionList(Parcel in) {
        this.type = in.readInt();
        this.name = in.readString();
        this.id = in.readLong();
        this.isRight = in.readByte() != 0;
        this.isFinish = in.readByte() != 0;
        this.rightCount = in.readInt();
        this.faultCount = in.readInt();
    }

    public static final Creator<PaperSectionList> CREATOR = new Creator<PaperSectionList>() {
        @Override
        public PaperSectionList createFromParcel(Parcel source) {
            return new PaperSectionList(source);
        }

        @Override
        public PaperSectionList[] newArray(int size) {
            return new PaperSectionList[size];
        }
    };
}
