package com.iteso.library.beans;

/**
 * Created by Maritza on 24/11/2017.
 */

public class LasthMonth {
    private String isbn;
    private long time;

    public LasthMonth() {
    }

    public LasthMonth(String isbn, long time) {
        this.isbn = isbn;
        this.time = time;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
