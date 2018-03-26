package com.xueli.application.mode.bean.user;

/**
 * 用户数据
 * Created by pingan on 2018/3/2.
 */

public class User {
    private String accountName;
    private boolean bIntegrated;
    private String headImage;
    private long id;
    private boolean locked;
    private String phone;
    private String token;

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public boolean isbIntegrated() {
        return bIntegrated;
    }

    public void setbIntegrated(boolean bIntegrated) {
        this.bIntegrated = bIntegrated;
    }

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
