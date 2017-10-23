package com.iteso.library.beans;

import java.util.Date;

/**
 * Created by Maritza on 18/10/2017.
 */

public class Publication {
    private String userName;
    private String comment;
    private int photoUser;
    private Date date;

    public Publication(String userName, String comment, int photoUser, Date date) {
        this.userName = userName;
        this.comment = comment;
        this.photoUser = photoUser;
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String nameUser) {
        this.userName = nameUser;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getPhotoUser() {
        return photoUser;
    }

    public void setPhotoUser(int photoUser) {
        this.photoUser = photoUser;
    }
}
