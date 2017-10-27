package com.iteso.library.beans;

import java.util.Date;

/**
 * Created by Desarrollo on 26/10/2017.
 */

public class Comment {
    private int image;
    private String userName;
    private Date date;
    private String userComment;

    public Comment(int image, String userName, Date date, String userComment) {
        this.image = image;
        this.userName = userName;
        this.date = date;
        this.userComment = userComment;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getUserComment() {
        return userComment;
    }

    public void setUserComment(String userComment) {
        this.userComment = userComment;
    }
}
