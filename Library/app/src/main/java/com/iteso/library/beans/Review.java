package com.iteso.library.beans;

import java.sql.Timestamp;

/**
 * Created by Desarrollo on 26/10/2017.
 */

public class Review {
    private String nickname;
    private String id;
    private long date;
    private long rating;
    private String review;

    public Review() {
    }

    public Review(String nickname, String id, long date, long rating, String review) {
        this.nickname = nickname;
        this.id = id;
        this.date = date;
        this.rating = rating;
        this.review = review;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public long getRating() {
        return rating;
    }

    public void setRating(long rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }
}
