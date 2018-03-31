package com.xueli.application.mode.bean.exam;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class UploadData {

    private String token;
    private String accountId;
    private List<PaperSections> data;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public List<PaperSections> getData() {
        return data;
    }

    public void setData(List<PaperSections> data) {
        this.data = data;
    }

    public UploadData() {
    }

    public UploadData(String token, String accountId, List<PaperSections> data) {
        this.token = token;
        this.accountId = accountId;
        this.data = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            this.data.add(data.get(i).clonePs());
        }
    }

    public String getUploadJson() {
        return new Gson().toJson(this);
    }
}
