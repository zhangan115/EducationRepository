package com.xueli.application.mode.bean;

/**
 * Created by pingan on 2018/3/25.
 */

public class RegTime {
    private boolean afterNow;
    private boolean beforeNow;
    private int centuryOfEra;
    private int dayOfMonth;
    private int dayOfWeek;
    private int dayOfYear;
    private boolean equalNow;
    private int era;
    private int hourOfDay;
    private long millis;
    private long millisOfDay;
    private long millisOfSecond;

    public boolean isAfterNow() {
        return afterNow;
    }

    public void setAfterNow(boolean afterNow) {
        this.afterNow = afterNow;
    }

    public boolean isBeforeNow() {
        return beforeNow;
    }

    public void setBeforeNow(boolean beforeNow) {
        this.beforeNow = beforeNow;
    }

    public int getCenturyOfEra() {
        return centuryOfEra;
    }

    public void setCenturyOfEra(int centuryOfEra) {
        this.centuryOfEra = centuryOfEra;
    }

    public int getDayOfMonth() {
        return dayOfMonth;
    }

    public void setDayOfMonth(int dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public int getDayOfYear() {
        return dayOfYear;
    }

    public void setDayOfYear(int dayOfYear) {
        this.dayOfYear = dayOfYear;
    }

    public boolean isEqualNow() {
        return equalNow;
    }

    public void setEqualNow(boolean equalNow) {
        this.equalNow = equalNow;
    }

    public int getEra() {
        return era;
    }

    public void setEra(int era) {
        this.era = era;
    }

    public int getHourOfDay() {
        return hourOfDay;
    }

    public void setHourOfDay(int hourOfDay) {
        this.hourOfDay = hourOfDay;
    }

    public long getMillis() {
        return millis;
    }

    public void setMillis(long millis) {
        this.millis = millis;
    }

    public long getMillisOfDay() {
        return millisOfDay;
    }

    public void setMillisOfDay(long millisOfDay) {
        this.millisOfDay = millisOfDay;
    }

    public long getMillisOfSecond() {
        return millisOfSecond;
    }

    public void setMillisOfSecond(long millisOfSecond) {
        this.millisOfSecond = millisOfSecond;
    }
}
