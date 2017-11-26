package com.iteso.library.gui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.Profile;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.iteso.library.R;
import com.iteso.library.adapters.AdapterReview;
import com.iteso.library.beans.Book;
import com.iteso.library.beans.Comment;
import com.iteso.library.beans.Review;
import com.iteso.library.common.Constants;
import com.iteso.library.common.DownloadImage;

import java.sql.Timestamp;
import java.util.ArrayList;

public class ActivityReview extends ActivityBase {
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Review> mDataSet;
    private RecyclerView mRecyclerView;
    private Book book;
    private ImageButton send;
    private EditText review;
    private TextView title;
    private ImageView image;
    private String nickname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        onCreateDrawer();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        send = (ImageButton)findViewById(R.id.activity_review_send_review);
        review = (EditText)findViewById(R.id.activity_review_user_review);
        title = (TextView)findViewById(R.id.activity_review_title);
        image = (ImageView)findViewById(R.id.activity_review_book_image);

        getNickname();

        mRecyclerView = (RecyclerView) findViewById(R.id.activity_review_recycler);
        mRecyclerView.setHasFixedSize(true);

        book = getIntent().getExtras().getParcelable("book");
        title.setText(book.getTitle());
        new DownloadImage(image, book.getImage()).execute();

        mDataSet = new ArrayList<>();
        getData();

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new AdapterReview(this, mDataSet);
        mRecyclerView.setAdapter(mAdapter);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!review.getText().toString().equals("")){
                    Review msg = new Review(nickname,
                            Profile.getCurrentProfile().getId(),
                            new Timestamp(System.currentTimeMillis()).getTime(),
                            0, review.getText().toString());
                    DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_BOOK_REVIEW)
                            .child(book.getIsbn());
                    mDatabaseReference.push().setValue(msg);
                    review.setText("");
                }
            }
        });
    }

    public void getData(){
        DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_BOOK_REVIEW).child(book.getIsbn());
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot != null) mDataSet.clear();
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    Review rv = data.getValue(Review.class);
                    mDataSet.add(rv);
                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getNickname(){
        DatabaseReference mDataReference = FirebaseDatabase.getInstance()
                .getReference(Constants.FIREBASE_USER)
                .child(Profile.getCurrentProfile().getId())
                .child(Constants.FIREBASE_USER_INFO)
                .child(Constants.FIREBASE_USER_NICKNAME);

        mDataReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                nickname = dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
