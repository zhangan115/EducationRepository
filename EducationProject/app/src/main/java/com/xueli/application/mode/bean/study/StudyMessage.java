package com.xueli.application.mode.bean.study;

import android.os.Parcel;
import android.os.Parcelable;

public class StudyMessage implements Parcelable{

    /**
     * 消息类型 1：视频 2：图片 3：纯文本
     */
    private int msgType;
    /**
     * 图片  首页广告中用
     */
    private String firstPic;
    private String title;
    private String brief;
    private String detail;
    private String videoUrl;
    private Long id;
    private int browseCount;
    private String createTimeStr;

    public int getMsgType() {
        return msgType;
    }

    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }

    public String getFirstPic() {
        return firstPic;
    }

    public void setFirstPic(String firstPic) {
        this.firstPic = firstPic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public int getBrowseCount() {
        return browseCount;
    }

    public void setBrowseCount(int browseCount) {
        this.browseCount = browseCount;
    }

    public String getCreateTimeStr() {
        return createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }

    public StudyMessage() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.msgType);
        dest.writeString(this.firstPic);
        dest.writeString(this.title);
        dest.writeString(this.brief);
        dest.writeString(this.detail);
        dest.writeString(this.videoUrl);
        dest.writeValue(this.id);
        dest.writeInt(this.browseCount);
        dest.writeString(this.createTimeStr);
    }

    protected StudyMessage(Parcel in) {
        this.msgType = in.readInt();
        this.firstPic = in.readString();
        this.title = in.readString();
        this.brief = in.readString();
        this.detail = in.readString();
        this.videoUrl = in.readString();
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.browseCount = in.readInt();
        this.createTimeStr = in.readString();
    }

    public static final Creator<StudyMessage> CREATOR = new Creator<StudyMessage>() {
        @Override
        public StudyMessage createFromParcel(Parcel source) {
            return new StudyMessage(source);
        }

        @Override
        public StudyMessage[] newArray(int size) {
            return new StudyMessage[size];
        }
    };
}
