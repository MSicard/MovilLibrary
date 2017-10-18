package com.iteso.library.beans;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Maritza on 22/09/2017.
 */

public class Book implements Parcelable{
    protected String title;
    protected String autor;
    protected String synapsis;
    protected String editorial;
    protected String edicion;
    protected String year;
    protected int pages;
    protected String ISBN;

    public Book(String title, String autor, String synapsis){
        this.title = title;
        this.autor = autor;
        this.synapsis = synapsis;
    }

    protected Book(Parcel in) {
        title = in.readString();
        autor = in.readString();
        synapsis = in.readString();
        editorial = in.readString();
        edicion = in.readString();
        year = in.readString();
        pages = in.readInt();
        ISBN = in.readString();
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getSynapsis() {
        return synapsis;
    }

    public void setSynapsis(String synapsis) {
        this.synapsis = synapsis;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(autor);
        parcel.writeString(synapsis);
        parcel.writeString(editorial);
        parcel.writeString(edicion);
        parcel.writeString(year);
        parcel.writeInt(pages);
        parcel.writeString(ISBN);
    }
}
