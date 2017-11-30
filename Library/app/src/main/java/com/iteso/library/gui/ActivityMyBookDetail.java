package com.iteso.library.gui;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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
import android.widget.Toast;

import com.facebook.Profile;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.iteso.library.R;
import com.iteso.library.beans.Book;
import com.iteso.library.beans.LastMonth;
import com.iteso.library.beans.MyBookDetail;
import com.iteso.library.beans.UserState;
import com.iteso.library.common.Constants;
import com.iteso.library.common.DownloadImage;
import com.iteso.library.common.DownloadPDF;
import com.iteso.library.common.Utils;

import java.io.File;
import java.sql.Timestamp;
import java.util.Date;

import static com.iteso.library.common.Constants.MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE;

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
    protected ImageButton mAudio;

    protected Book b;
    protected MyBookDetail book;

    protected TextView mTitle;
    protected TextView mAutor;
    protected RatingBar mRating;
    protected ImageView mCoverPage;

    DatabaseReference reference;
    DatabaseReference referenceState;
    private UserState state;
    private boolean add = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_book_detail);
        onCreateDrawer();

        referenceState = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_USER)
                .child(Profile.getCurrentProfile().getId()).child(Constants.FIREBASE_USER_STATE);

        b = getIntent().getExtras().getParcelable("book");


        reference = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_USER)
                .child(Profile.getCurrentProfile().getId()).child(Constants.FIREBASE_USER_BOOK_STATE).child(b.getIsbn());

        getState();

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
        mAudio = (ImageButton)findViewById(R.id.activity_my_book_detail_audio);

        if(b.getAudio() != null){
            mAudio.setVisibility(View.VISIBLE);
            mAudio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ActivityMyBookDetail.this, ActivityMusicPlayer.class);
                    intent.putExtra("book", b);
                    startActivity(intent);
                }
            });
        }

        mTitle.setText(b.getTitle());
        mAutor.setText(b.getAuthor());
        mRating.setRating(b.getRating());
        new DownloadImage(mCoverPage, b.getImage()).execute();

        getUserState();

        mOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File pdfFile = new File(Environment.getExternalStorageDirectory() + "/Download/" + b.getIsbn() +".pdf");
                Uri path = Uri.fromFile(pdfFile);
                Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
                pdfIntent.setDataAndType(path, "application/pdf");
                pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                try{
                    startActivity(pdfIntent);
                }catch(ActivityNotFoundException e){
                    Toast.makeText(v.getContext(), "No Application available to view PDF", Toast.LENGTH_SHORT).show();
                }
            }
        });


        mDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(ActivityMyBookDetail.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                    if(mDownload.isActivated())
                        // Download
                        new DownloadPDF(ActivityMyBookDetail.this, b.getUrl(), b.getIsbn(), reference, book).execute();
                    else{
                        // Delete book
                        AlertDialog.Builder builder;
                        builder = new AlertDialog.Builder(ActivityMyBookDetail.this);
                        builder.setTitle("Delete book from device")
                                .setMessage("Are you sure?")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        deletePDF();
                                    }
                                })
                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                })
                        .show();

                    }

                    reference.setValue(book);
                }else{
                    Toast.makeText(ActivityMyBookDetail.this, "Storage permission is required to download files", Toast.LENGTH_LONG).show();
                }
            }
        });

        mActualReading.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    DatabaseReference refReading = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_USER)
                            .child(Profile.getCurrentProfile().getId()).child(Constants.FIREBASE_USER_STATISTICS)
                            .child(Constants.FIREBASE_USER_BOOK_READING).child(b.getIsbn());
                    refReading.setValue(b.getIsbn());
                    book.setStartDate(new Timestamp(System.currentTimeMillis()).getTime());
                    if(add){
                        add = false;
                    }else{
                        state.setReading(state.getReading() + 1);
                        add = false;
                    }
                }
                else{
                    DatabaseReference refReading = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_USER)
                            .child(Profile.getCurrentProfile().getId()).child(Constants.FIREBASE_USER_STATISTICS)
                            .child(Constants.FIREBASE_USER_BOOK_READING).child(b.getIsbn());
                    refReading.removeValue();
                    book.setStartDate(0);
                    book.setPagesRead(0);
                    if(state.getReading() > 0){
                        state.setReading(state.getReading() - 1);
                    }
                    add = false;
                }
                book.setReading(isChecked);
                reference.setValue(book);

                DatabaseReference refAddReading = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_USER)
                        .child(Profile.getCurrentProfile().getId()).child(Constants.FIREBASE_USER_STATE)
                        .child(Constants.FIREBASE_USER_READING);
                refAddReading.setValue(state.getReading());
            }
        });

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

    private void deletePDF(){
        String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
        File folder = new File(extStorageDirectory, "Download");
        folder.mkdir();

        File pdfFile = new File(folder, b.getIsbn() + ".pdf");
        pdfFile.delete();
        book.setDownload(false);
        reference.setValue(book);
        Toast.makeText(ActivityMyBookDetail.this, "'" + b.getTitle() + "' has been deleted from your device", Toast.LENGTH_LONG).show();
    }

    private void checkPDF() {
        String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
        File pdf = new File(extStorageDirectory, "Download/" + b.getIsbn() + ".pdf");

       if(pdf.exists())
           book.setDownload(true);
       else
           book.setDownload(false);

       reference.setValue(book);
    }

    private void getState(){
        DatabaseReference reference1 = reference;
        reference1.keepSynced(true);
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
                checkPDF();
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
                if(!book.isReading()){
                    book.setReading(true);
                    book.setStartDate(new Timestamp(System.currentTimeMillis()).getTime());
                }
                reference.setValue(book);
            }
        }).setNegativeButton("DECLLINE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }).setNeutralButton("FINISHED", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DatabaseReference refTotalISBN = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_USER)
                        .child(Profile.getCurrentProfile().getId()).child(Constants.FIREBASE_USER_STATISTICS).child(Constants.FIREBASE_USER_TOTAL).child(b.getIsbn());
                refTotalISBN.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.getValue() == null){
                            DatabaseReference refAddTotal = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_USER)
                                    .child(Profile.getCurrentProfile().getId()).child(Constants.FIREBASE_USER_STATE).child(Constants.FIREBASE_USER_TOTAL);
                            refAddTotal.setValue(state.getTotal() + 1);
                            DatabaseReference refAddMonth = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_USER)
                                    .child(Profile.getCurrentProfile().getId()).child(Constants.FIREBASE_USER_STATE)
                                    .child(Constants.FIREBASE_USER_LAST_MONTH);
                            refAddMonth.setValue(state.getLast_month() + 1);

                            book.setStartDate( new Timestamp(System.currentTimeMillis()).getTime());
                            DatabaseReference refMonth = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_USER)
                                    .child(Profile.getCurrentProfile().getId()).child(Constants.FIREBASE_USER_STATISTICS)
                                    .child(Constants.FIREBASE_USER_LAST_MONTH).child(b.getIsbn());
                            LastMonth month = new LastMonth(b.getIsbn(), new Timestamp(System.currentTimeMillis()).getTime());
                            refMonth.setValue(month);
                            DatabaseReference ref = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_USER)
                                    .child(Profile.getCurrentProfile().getId()).child(Constants.FIREBASE_USER_STATISTICS)
                                    .child(Constants.FIREBASE_USER_TOTAL).child(b.getIsbn());
                            ref.setValue(b.getIsbn());
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });




                book.setReading(false);
                book.setStartDate(0);
                book.setPagesRead(0);
                reference.setValue(book);


                //Saber si el libro ya fue leído :)


            }
        });
        builder.create().show();
    }

    public void updateGUI(){
        mNumberPages.setText(String.valueOf(book.getPagesRead()));
        if(book.getStartDate() == 0) mStartDate.setText("No has empezado el libro");
        else mStartDate.setText(Utils.putTime(new Date(book.getStartDate())));
        mActualReading.setChecked(book.isReading());
        float percentage = ((float)book.getPagesRead()/(float)b.getPages())*(float)100;
        mPercentage.setText(String.valueOf((int)percentage) + "%");
        if(book.isDownload()){
            mDownload.setImageResource(R.drawable.ic_delete_black_24dp);
            mDownload.setActivated(false);
            mOpen.setEnabled(true);
        }
        else {
            mDownload.setImageResource(R.drawable.ic_file_download_blue_24dp);
            mDownload.setActivated(true);
            mOpen.setEnabled(false);
            mDownload.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGray)));
        }
    }

    public void getUserState(){
        referenceState.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                state = dataSnapshot.getValue(UserState.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ContextCompat.checkSelfPermission(ActivityMyBookDetail.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(ActivityMyBookDetail.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
        }

        if(book != null)
            checkPDF();
    }
}
