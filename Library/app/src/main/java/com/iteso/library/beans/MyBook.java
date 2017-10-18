package com.iteso.library.beans;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Maritza on 25/09/2017.
 */

public class MyBook extends Book implements Parcelable {
    protected boolean reading;
    protected String pagesRead;

    public MyBook(String title, String autor, String synapsis, boolean reading, String pagesRead) {
        super(title, autor, synapsis);
        this.reading = reading;
        this.pagesRead = pagesRead;
    }

    public MyBook(String title, String autor, String synapsis){
        super(title, autor, synapsis);
    }

    protected MyBook(Parcel in) {
        super(in);
        reading = in.readByte() != 0;
        pagesRead = in.readString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeByte((byte) (reading ? 1: 0));
        parcel.writeString(pagesRead);
    }

    public static final Creator<MyBook> CREATOR = new Creator<MyBook>() {
        @Override
        public MyBook createFromParcel(Parcel in) {
            return new MyBook(in);
        }

        @Override
        public MyBook[] newArray(int size) {
            return new MyBook[size];
        }
    };
}
