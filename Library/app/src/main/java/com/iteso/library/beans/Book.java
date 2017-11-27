package com.iteso.library.beans;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.HashMap;

/**
 * Created by Maritza on 22/09/2017.
 */

public class Book implements Parcelable, Comparable<Book>{
    protected String title;
    protected String author;
    protected String synopsis;
    protected String editorial;
    protected String edition;
    protected String year;
    protected long pages;
    protected String isbn;
    protected String image;
    protected long rating;
    protected String url;
    protected String audio;

    public Book(String title, String author, String synopsis, String editorial, String edition, String year, long pages, String isbn, String image, long rating, String url, String audio) {
        this.title = title;
        this.author = author;
        this.synopsis = synopsis;
        this.editorial = editorial;
        this.edition = edition;
        this.year = year;
        this.pages = pages;
        this.isbn = isbn;
        this.image = image;
        this.rating = rating;
        this.url = url;
        this.audio = audio;
    }

    public Book(String title, String autor, String synapsis){
        this.title = title;
        this.author = autor;
        this.synopsis = synapsis;
    }

    public Book(){

    }

    protected Book(Parcel in) {
        title = in.readString();
        author = in.readString();
        synopsis = in.readString();
        editorial = in.readString();
        edition = in.readString();
        year = in.readString();
        pages = in.readLong();
        isbn = in.readString();
        image = in.readString();
        rating = in.readLong();
        url = in.readString();
        audio = in.readString();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    @Override
    public String toString() {
        return "Title: " + title + "\n" +
                "Author: " + author + "\n" +
                "Editorial: " + editorial + "\n" +
                "Edition: " + edition + "\n" +
                "Year: " + year + "\n" +
                "Pages: " + pages + "\n" +
                "ISBN: " + isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public long getPages() {
        return pages;
    }

    public void setPages(long pages) {
        this.pages = pages;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public long getRating() {
        return rating;
    }

    public void setRating(long rating) {
        this.rating = rating;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(author);
        dest.writeString(synopsis);
        dest.writeString(editorial);
        dest.writeString(edition);
        dest.writeString(year);
        dest.writeLong(pages);
        dest.writeString(isbn);
        dest.writeString(image);
        dest.writeLong(rating);
        dest.writeString(url);
        dest.writeString(audio);
    }

    @Override
    public int compareTo(@NonNull Book book) {
        // Logic is inverted in order to sort them from best to worse
        if(rating < book.getRating())
            return 1;
        if(rating > book.getRating())
            return -1;
        return 0;
    }
}
