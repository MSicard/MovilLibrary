package com.iteso.library.beans;

/**
 * Created by Maritza on 09/11/2017.
 */

public class Friend {
    private String name;
    private String url_image;
    private String id;

    public Friend(String name, String url_image, String id) {
        this.name = name;
        this.url_image = url_image;
        this.id = id;
    }

    @Override
    public String toString() {
        return "Friend{" +
                "name='" + name + '\'' +
                ", url_image='" + url_image + '\'' +
                ", id='" + id + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl_image() {
        return url_image;
    }

    public void setUrl_image(String url_image) {
        this.url_image = url_image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
