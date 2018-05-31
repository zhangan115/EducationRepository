package com.xueli.application.mode.enrol.bean;

import java.util.List;

public class SchoolBean {

    private long schoolId;
    private String schoolName;
    private List<MajorBean> specialtyCatalogs;
    public long getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(long schoolId) {
        this.schoolId = schoolId;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public List<MajorBean> getSpecialtyCatalogs() {
        return specialtyCatalogs;
    }

    public void setSpecialtyCatalogs(List<MajorBean> specialtyCatalogs) {
        this.specialtyCatalogs = specialtyCatalogs;
    }
}
