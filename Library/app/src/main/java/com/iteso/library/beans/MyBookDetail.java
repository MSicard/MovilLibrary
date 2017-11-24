package com.iteso.library.beans;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Maritza on 25/09/2017.
 */

public class MyBookDetail extends Book implements Parcelable {
    protected boolean reading;
    protected String pagesRead;

    public MyBookDetail(String title, String autor, String synapsis, boolean reading, String pagesRead) {
        super(title, autor, synapsis);
        this.reading = reading;
        this.pagesRead = pagesRead;
    }

    public MyBookDetail(String title, String autor, String synapsis){
        super(title, autor, synapsis);
    }

    protected MyBookDetail(Parcel in) {
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

    public static final Creator<MyBookDetail> CREATOR = new Creator<MyBookDetail>() {
        @Override
        public MyBookDetail createFromParcel(Parcel in) {
            return new MyBookDetail(in);
        }

        @Override
        public MyBookDetail[] newArray(int size) {
            return new MyBookDetail[size];
        }
    };
}
