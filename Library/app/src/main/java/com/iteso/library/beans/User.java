package com.iteso.library.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maritza on 24/10/2017.
 */

public class User {
    private String about_me;
    private List favoriteBooks;
    private String image;
    private String nickname;

    public User(String about_me, List favoriteBooks, String image, String nickname) {
        this.about_me = about_me;
        this.favoriteBooks = favoriteBooks;
        this.image = image;
        this.nickname = nickname;
    }

    public User(String image, String nickname) {
        this.image = image;
        this.nickname = nickname;
        this.about_me = "¿Quien eres?";
        this.favoriteBooks = new ArrayList();
        favoriteBooks.add("Agrega libros!");
    }

    public User(){
    }

    public String getAbout_me() {
        return about_me;
    }

    public void setAbout_me(String about_me) {
        this.about_me = about_me;
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

    public List getFavoriteBooks() {
        return favoriteBooks;
    }

    public void setFavoriteBooks(ArrayList favoriteBooks) {
        this.favoriteBooks = favoriteBooks;
    }
}
