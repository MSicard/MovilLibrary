package com.iteso.library.gui;

import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.TextView;

import com.facebook.Profile;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.iteso.library.R;
import com.iteso.library.beans.Book;
import com.iteso.library.beans.MyBookDetail;
import com.iteso.library.beans.User;
import com.iteso.library.common.Constants;
import com.iteso.library.common.DownloadImage;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

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

    protected Book b;

    protected TextView mTitle;
    protected TextView mAutor;
    protected RatingBar mRating;
    protected ImageView mCoverPage;
    protected MyBookDetail book;

    DatabaseReference reference;

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

        b = getIntent().getExtras().getParcelable("book");
        reference = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_USER)
                .child(Profile.getCurrentProfile().getId()).child(Constants.FIREBASE_USER_BOOK_STATE).child(b.getIsbn());
        mTitle.setText(b.getTitle());
        mAutor.setText(b.getAuthor());
        mRating.setRating(b.getRating());
        new DownloadImage(mCoverPage, b.getUrl()).execute();

        mDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                book.setDownload(!book.isDownload());
                reference.setValue(book);
            }
        });

        mActualReading.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) book.setStartDate( new Timestamp(System.currentTimeMillis()).getTime());
                book.setReading(isChecked);
                reference.setValue(book);
            }
        });
        onCreateDrawer();
        getState();

        mUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDialogUpdate(mNumberPages);
            }
        });

        mBibliography.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDialogBibliography(b.toString());
            }
        });
    }

    private void getState(){
        DatabaseReference reference1 = reference;
        reference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue() == null){
                    book = new MyBookDetail(false, 0, false, 0);
                }
                else{
                    book = null;
                    book = dataSnapshot.getValue(MyBookDetail.class);
                }
                updateGUI();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void createDialogUpdate(final TextView tv){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();

        View v = inflater.inflate(R.layout.dialog_profile_change, null);
        builder.setView(v);

        final EditText editText = (EditText)v.findViewById(R.id.dialog_profile_change);
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        editText.setHint(tv.getText().toString());
        final TextView textView = (TextView)v.findViewById(R.id.dialog_profile_change_text);
        textView.setText("Update");

        builder.setPositiveButton("ACCEPT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(editText.getText().toString().equals("")) return;
                tv.setText(editText.getText().toString());
                book.setPagesRead(Long.parseLong(editText.getText().toString()));
                book.setReading(true);
                reference.setValue(book);
            }
        }).setNegativeButton("DECLLINE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.create().show();
    }

    public void updateGUI(){
        mNumberPages.setText(String.valueOf(book.getPagesRead()));
        if(book.getStartDate() == 0) mStartDate.setText("No has empezado el libro");
        mStartDate.setText(String.valueOf(book.getStartDate()));
        mActualReading.setChecked(book.isReading());
        int percentage = ((int)book.getPagesRead()/(int)b.getPages()) *100;
        mPercentage.setText(String.valueOf(percentage));
        if(book.isDownload()){
            mDownload.setActivated(false);
            mDownload.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPurple)));
        }
        else {
            mDownload.setActivated(true);
            mDownload.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGray)));
        }
    }
}
