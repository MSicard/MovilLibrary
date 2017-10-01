package com.iteso.library.beans;

import java.util.Date;

/**
 * Created by Maritza on 29/09/2017.
 */

public class Notification {
    protected String userName;
    protected int notificationType;
    protected Date date;
    protected int image;

    public Notification(String userName, int notificationType, Date date, int image) {
        this.userName = userName;
        this.notificationType = notificationType;
        this.date = date;
        this.image = image;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getNotificationType() {
        return notificationType;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public void setNotificationType(int notificationType) {
        this.notificationType = notificationType;

    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
