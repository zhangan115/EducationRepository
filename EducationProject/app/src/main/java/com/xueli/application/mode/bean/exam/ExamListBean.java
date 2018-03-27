package com.xueli.application.mode.bean.exam;

public class ExamListBean {

    private int type;
    private String title;
    private PaperSections paperSections;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public PaperSections getPaperSections() {
        return paperSections;
    }

    public void setPaperSections(PaperSections paperSections) {
        this.paperSections = paperSections;
    }
}
