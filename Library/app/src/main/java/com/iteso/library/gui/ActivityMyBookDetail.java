package com.iteso.library.gui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.TextView;

import com.iteso.library.R;
import com.iteso.library.beans.MyBook;

import org.w3c.dom.Text;

/**
 * Created by Maritza on 01/10/2017.
 */

public class ActivityMyBookDetail extends ActivityBase {

    protected ImageButton mDownload;
    protected TextView mNumberPages;
    protected TextView mPercentage;
    protected Switch mActualReading;
    protected TextView mStartDate;
    protected Button mUpdate;
    protected Button mOpen;
    protected Button mBibliography;

    protected TextView mTitle;
    protected TextView mAutor;
    protected RatingBar mRating;
    protected ImageView mCoverPage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_book_detail);
        mDownload = (ImageButton)findViewById(R.id.activity_my_book_detail_download);
        mNumberPages = (TextView)findViewById(R.id.activity_my_book_detail_read_pages);
        mPercentage = (TextView)findViewById(R.id.activity_my_book_detail_percentage);
        mActualReading = (Switch)findViewById(R.id.activity_my_book_detail_reading);
        mStartDate = (TextView)findViewById(R.id.activity_my_book_detail_start_date);
        mUpdate = (Button)findViewById(R.id.activity_my_book_detail_update);
        mOpen = (Button)findViewById(R.id.activity_my_book_detail_open);
        mBibliography = (Button)findViewById(R.id.activity_my_book_detail_bibliography);
        mTitle = (TextView)findViewById(R.id.activity_my_book_detail_title);
        mAutor = (TextView)findViewById(R.id.activity_my_book_detail_autor);
        mRating = (RatingBar)findViewById(R.id.activity_my_book_detail_rating);
        mCoverPage = (ImageView)findViewById(R.id.activity_my_book_detail_cover_page);

        onCreateDrawer();
    }

    private void setMyBook(MyBook b){
        mTitle.setText(b.getTitle());
        mAutor.setText(b.getAutor());
        mRating.setRating(5);
    }
}
