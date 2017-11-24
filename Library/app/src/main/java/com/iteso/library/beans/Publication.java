package com.iteso.library.beans;

import android.support.annotation.NonNull;

import java.util.Date;

/**
 * Created by Maritza on 18/10/2017.
 */

public class Publication implements Comparable{
    String id;
    long comments;
    long likes;
    String message;
    long time;

    public Publication() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Publication(String id, long comments, long likes, String message, long time) {
        this.id = id;
        this.comments = comments;
        this.likes = likes;
        this.message = message;
        this.time = time;
    }

    public Publication(long comments, long likes, String message, long time) {
        this.comments = comments;
        this.likes = likes;
        this.message = message;
        this.time = time;
    }

    public long getComments() {
        return comments;
    }

    public void setComments(long comments) {
        this.comments = comments;
    }

    public long getLikes() {
        return likes;
    }

    public void setLikes(long likes) {
        this.likes = likes;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    //Comparación de tiempo
    @Override
    public int compareTo(@NonNull Object o) {
        if(time > ((Publication)o).getTime()) return 1;
        else return 0;
    }
}
