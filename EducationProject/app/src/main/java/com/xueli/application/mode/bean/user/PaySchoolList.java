package com.xueli.application.mode.bean.user;

import java.util.List;

public class PaySchoolList {

    /**
     * schoolName : 湖南中医药大学
     * tuitionList : [{"desc":"","grade1Tuition":"3580","grade2Tuition":"3200","grade3Tuition":"3580","netStudyFee":"380","netStudyYear":"2.5","tuitionId":1,"tuitionStandard":"3200","tuitionType":"医学类"}]
     * schoolId : 6
     */

    private String schoolName;
    private long schoolId;
    private List<TuitionListBean> tuitionList;

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public long getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(long schoolId) {
        this.schoolId = schoolId;
    }

    public List<TuitionListBean> getTuitionList() {
        return tuitionList;
    }

    public void setTuitionList(List<TuitionListBean> tuitionList) {
        this.tuitionList = tuitionList;
    }

    public static class TuitionListBean {
        /**
         * desc :
         * grade1Tuition : 3580
         * grade2Tuition : 3200
         * grade3Tuition : 3580
         * netStudyFee : 380
         * netStudyYear : 2.5
         * tuitionId : 1
         * tuitionStandard : 3200
         * tuitionType : 医学类
         */

        private String desc;
        private String grade1Tuition;
        private String grade2Tuition;
        private String grade3Tuition;
        private String netStudyFee;
        private String netStudyYear;
        private long tuitionId;
        private String tuitionStandard;
        private String tuitionType;

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getGrade1Tuition() {
            return grade1Tuition;
        }

        public void setGrade1Tuition(String grade1Tuition) {
            this.grade1Tuition = grade1Tuition;
        }

        public String getGrade2Tuition() {
            return grade2Tuition;
        }

        public void setGrade2Tuition(String grade2Tuition) {
            this.grade2Tuition = grade2Tuition;
        }

        public String getGrade3Tuition() {
            return grade3Tuition;
        }

        public void setGrade3Tuition(String grade3Tuition) {
            this.grade3Tuition = grade3Tuition;
        }

        public String getNetStudyFee() {
            return netStudyFee;
        }

        public void setNetStudyFee(String netStudyFee) {
            this.netStudyFee = netStudyFee;
        }

        public String getNetStudyYear() {
            return netStudyYear;
        }

        public void setNetStudyYear(String netStudyYear) {
            this.netStudyYear = netStudyYear;
        }

        public long getTuitionId() {
            return tuitionId;
        }

        public void setTuitionId(long tuitionId) {
            this.tuitionId = tuitionId;
        }

        public String getTuitionStandard() {
            return tuitionStandard;
        }

        public void setTuitionStandard(String tuitionStandard) {
            this.tuitionStandard = tuitionStandard;
        }

        public String getTuitionType() {
            return tuitionType;
        }

        public void setTuitionType(String tuitionType) {
            this.tuitionType = tuitionType;
        }
    }
}
