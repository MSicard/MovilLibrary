package com.iteso.library.beans;

/**
 * Created by Maritza on 24/11/2017.
 */

public class LastMonth {
    private String isbn;
    private long time;

    @Override
    public String toString() {
        return "LastMonth{" +
                "isbn='" + isbn + '\'' +
                ", time=" + time +
                '}';
    }

    public LastMonth() {
    }

    public LastMonth(String isbn, long time) {
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
