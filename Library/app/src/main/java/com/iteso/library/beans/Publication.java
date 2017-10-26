package com.iteso.library.beans;

import android.support.annotation.NonNull;

import java.util.Date;

/**
 * Created by Maritza on 18/10/2017.
 */

public class Publication implements Comparable{
    private String userName;
    private String comment;
    private int photoUser;
    private Date date;
    private int countComments;
    private int countLikes;

    public Publication(String userName, String comment, int photoUser, Date date, int countComments, int countLikes) {
        this.userName = userName;
        this.comment = comment;
        this.photoUser = photoUser;
        this.date = date;
        this.countComments = countComments;
        this.countLikes = countLikes;
    }

    public int getCountComments() {
        return countComments;
    }

    public void setCountComments(int countComments) {
        this.countComments = countComments;
    }

    public int getCountLikes() {
        return countLikes;
    }

    public void setCountLikes(int countLikes) {
        this.countLikes = countLikes;
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

    @Override
    public int compareTo(@NonNull Object o) {
        return this.getDate().compareTo(((Publication)o).getDate());
    }
}
