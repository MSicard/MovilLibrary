package com.iteso.library.beans;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Maritza on 22/09/2017.
 */

public class Book implements Parcelable{
    protected String title;
    protected String author;
    protected String synopsis;
    protected String editorial;
    protected String edicion;
    protected String year;
    protected int pages;
    protected String ISBN;
    protected int rating;
    protected String url;

    public Book(String title, String author, String synopsis, String editorial,
                String edicion, String year, int pages, String ISBN, int rating, String url) {
        this.title = title;
        this.author = author;
        this.synopsis = synopsis;
        this.editorial = editorial;
        this.edicion = edicion;
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
                ", edicion='" + edicion + '\'' +
                ", year='" + year + '\'' +
                ", pages=" + pages +
                ", ISBN='" + ISBN + '\'' +
                ", rating=" + rating +
                ", url='" + url + '\'' +
                '}';
    }

    protected Book(Parcel in) {
        title = in.readString();
        author = in.readString();
        synopsis = in.readString();
        editorial = in.readString();
        edicion = in.readString();
        year = in.readString();
        pages = in.readInt();
        ISBN = in.readString();
        rating = in.readInt();
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

    public String getEdicion() {
        return edicion;
    }

    public void setEdicion(String edicion) {
        this.edicion = edicion;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public int getPages() {
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

    public int getRating() {
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
        dest.writeString(edicion);
        dest.writeString(year);
        dest.writeInt(pages);
        dest.writeString(ISBN);
        dest.writeInt(rating);
        dest.writeString(url);
    }
}
