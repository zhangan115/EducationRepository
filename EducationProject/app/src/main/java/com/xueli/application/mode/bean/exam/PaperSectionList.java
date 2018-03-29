package com.xueli.application.mode.bean.exam;

public class PaperSectionList {

    private int type;
    private String name;
    private long id;
    private boolean isRight;
    private boolean isFinish;

    public PaperSectionList() {

    }

    public PaperSectionList(int type, String name, long id) {
        this.type = type;
        this.name = name;
        this.id = id;
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
}
