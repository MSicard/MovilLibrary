package com.iteso.library.beans;

import java.util.ArrayList;

/**
 * Created by Maritza on 24/10/2017.
 */

public class User {
    private String about_me;
    private ArrayList books;
    private String[] favoriteBooks;
    private String image;
    private String nickname;
    private boolean visible_p;
    private boolean visible_st;
    private String email;

    public User(String image, String nickname, boolean visible_p, boolean visible_st) {
        this.image = image;
        this.nickname = nickname;
        this.visible_p = visible_p;
        this.visible_st = visible_st;
    }
    public User(){

    }

    public String getAbout_me() {
        return about_me;
    }

    public void setAbout_me(String about_me) {
        this.about_me = about_me;
    }

    public ArrayList getBooks() {
        return books;
    }

    public void setBooks(ArrayList books) {
        this.books = books;
    }

    public String[] getFavoriteBooks() {
        return favoriteBooks;
    }

    public void setFavoriteBooks(String[] favoriteBooks) {
        this.favoriteBooks = favoriteBooks;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
