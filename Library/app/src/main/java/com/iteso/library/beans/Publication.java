package com.iteso.library.beans;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.Date;

/**
 * Created by Maritza on 18/10/2017.
 */

public class Publication implements Comparable, Parcelable{
    String id;
    long comments;
    long likes;
    String message;
    long time;

    public Publication() {
    }

    protected Publication(Parcel in) {
        id = in.readString();
        comments = in.readLong();
        likes = in.readLong();
        message = in.readString();
        time = in.readLong();
    }

    public static final Creator<Publication> CREATOR = new Creator<Publication>() {
        @Override
        public Publication createFromParcel(Parcel in) {
            return new Publication(in);
        }

        @Override
        public Publication[] newArray(int size) {
            return new Publication[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeLong(comments);
        dest.writeLong(likes);
        dest.writeString(message);
        dest.writeLong(time);
    }
}
