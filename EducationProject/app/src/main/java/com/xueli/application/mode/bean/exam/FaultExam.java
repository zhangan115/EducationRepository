package com.xueli.application.mode.bean.exam;

public class FaultExam {
    private long examedPaperId;
    private long id;
    private String title;
    private int type;
    private QuestionCatalog questionCatalog;

    public static class QuestionCatalog {
        private long id;
        private String title;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    public long getExamedPaperId() {
        return examedPaperId;
    }

    public void setExamedPaperId(long examedPaperId) {
        this.examedPaperId = examedPaperId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public QuestionCatalog getQuestionCatalog() {
        return questionCatalog;
    }

    public void setQuestionCatalog(QuestionCatalog questionCatalog) {
        this.questionCatalog = questionCatalog;
    }
}
