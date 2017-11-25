package com.iteso.library.beans;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Maritza on 25/09/2017.
 */

public class MyBookDetail {
    protected boolean reading;
    protected long pagesRead;
    protected boolean download;
    protected long startDate;

    public MyBookDetail() {
    }

    public MyBookDetail(boolean reading, long pagesRead, boolean download, long startDate) {
        this.reading = reading;
        this.pagesRead = pagesRead;
        this.download = download;
        this.startDate = startDate;
    }

    public boolean isReading() {
        return reading;
    }

    public void setReading(boolean reading) {
        this.reading = reading;
    }

    public long getPagesRead() {
        return pagesRead;
    }

    public void setPagesRead(long pagesRead) {
        this.pagesRead = pagesRead;
    }

    public boolean isDownload() {
        return download;
    }

    public void setDownload(boolean download) {
        this.download = download;
    }

    public long getStartDate() {
        return startDate;
    }

    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }
}
