package com.iteso.library.beans;

import java.util.Date;

/**
 * Created by Desarrollo on 26/10/2017.
 */

public class Review {
    private int image;
    private String userName;
    private Date date;
    private int rating;
    private String userReview;

    public Review(int image, String userName, Date date, int rating, String userReview) {
        this.image = image;
        this.userName = userName;
        this.date = date;
        this.rating = rating;
        this.userReview = userReview;
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

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getUserReview() {
        return userReview;
    }

    public void setUserReview(String userReview) {
        this.userReview = userReview;
    }
}
