package com.iteso.library.beans;

/**
 * Created by Maritza on 23/11/2017.
 */

public class UserState {
    boolean visible_p;
    boolean visible_st;
    long last_month;
    long reading;
    long total;
    boolean notification;

    public UserState() {
    }

    public UserState(boolean visible_p, boolean visible_st, long last_month, long reading, long total, boolean notification) {
        this.visible_p = visible_p;
        this.visible_st = visible_st;
        this.last_month = last_month;
        this.reading = reading;
        this.total = total;
        this.notification = notification;
    }

    public boolean isNotification() {
        return notification;
    }

    public void setNotification(boolean notification) {
        this.notification = notification;
    }

    public boolean isVisible_p() {
        return visible_p;
    }

    public void setVisible_p(boolean visible_p) {
        this.visible_p = visible_p;
    }

    public boolean isVisible_st() {
        return visible_st;
    }

    public void setVisible_st(boolean visible_st) {
        this.visible_st = visible_st;
    }

    public long getLast_month() {
        return last_month;
    }

    public void setLast_month(long last_month) {
        this.last_month = last_month;
    }

    public long getReading() {
        return reading;
    }

    public void setReading(long reading) {
        this.reading = reading;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
