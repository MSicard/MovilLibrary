package com.iteso.library.beans;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Maritza on 22/09/2017.
 */

public class Book implements Parcelable{
    protected String title;
    protected String author;
    protected String synopsis;
    protected String editorial;
    protected String edition;
    protected String year;
    protected long pages;
    protected String ISBN;
    protected long rating;
    protected String url;
    protected HashMap<String, String> category = new HashMap<String, String>();

    public Book(String title, String author, String synopsis, String editorial,
                String edition, String year, long pages, String ISBN, long rating, String url) {
        this.title = title;
        this.author = author;
        this.synopsis = synopsis;
        this.editorial = editorial;
        this.edition = edition;
        this.year = year;
        this.pages = pages;
        this.ISBN = ISBN;
        this.rating = rating;
        this.url = url;
    }

    public Book(String title, String autor, String synapsis){
        this.title = title;
        this.author = autor;
        this.synopsis = synapsis;
    }

    public Book(){

    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", synopsis='" + synopsis + '\'' +
                ", editorial='" + editorial + '\'' +
                ", edition='" + edition + '\'' +
                ", year='" + year + '\'' +
                ", pages=" + pages +
                ", ISBN='" + ISBN + '\'' +
                ", rating=" + rating +
                ", url='" + url + '\'' +
                '}' + category.get("one");
    }

    public Book(String title, String author, String synopsis, String editorial, String edition, String year, long pages, String ISBN, long rating, String url, HashMap<String, String> category) {
        this.title = title;
        this.author = author;
        this.synopsis = synopsis;
        this.editorial = editorial;
        this.edition = edition;
        this.year = year;
        this.pages = pages;
        this.ISBN = ISBN;
        this.rating = rating;
        this.url = url;
        this.category = category;
    }

    protected Book(Parcel in) {
        title = in.readString();
        author = in.readString();
        synopsis = in.readString();
        editorial = in.readString();
        edition = in.readString();
        year = in.readString();
        pages = in.readLong();
        ISBN = in.readString();
        rating = in.readLong();
        url = in.readString();
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

    public void setPages(int pages) {
        this.pages = pages;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public long getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
        dest.writeString(ISBN);
        dest.writeLong(rating);
        dest.writeString(url);
    }
}
