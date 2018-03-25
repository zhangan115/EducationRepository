package com.xueli.application.mode.bean;

/**
 * 网络请求实体类
 * Created by zhangan on 2016-08-30.
 */
public class Bean<T> {

    private int errorCode;

    private T data;

    private String errorMessage;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
