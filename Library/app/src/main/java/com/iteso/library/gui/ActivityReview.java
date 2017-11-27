package com.iteso.library.gui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

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
import com.iteso.library.common.Utils;

import java.sql.Timestamp;
import java.util.ArrayList;

public class ActivityReview extends ActivityBase {
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Review> mDataSet;
    private RecyclerView mRecyclerView;
    private Book book;
    private ImageButton send;
    private RatingBar rating;
    private EditText review;
    private TextView title;
    private ImageView image;
    private String nickname;
    private long summmationRating;
    private boolean userExists;
    private Review userReview;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        onCreateDrawer();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        send = (ImageButton)findViewById(R.id.activity_review_send_review);
        rating = (RatingBar) findViewById(R.id.activity_review_user_rating);
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
                if(Utils.isConnectedWifi(ActivityReview.this) || Utils.isConnectedMobile(ActivityReview.this)){
                    if(!review.getText().toString().equals("")){
                        updateRating();

                        Review msg = new Review(nickname,
                                Profile.getCurrentProfile().getId(),
                                new Timestamp(System.currentTimeMillis()).getTime(),
                                (long) rating.getRating(), review.getText().toString());
                        DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_BOOK_REVIEW)
                                .child(book.getIsbn())
                                .child(Profile.getCurrentProfile().getId());
                        mDatabaseReference.setValue(msg);
                        review.setText("");
                        review.clearFocus();

                        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputMethodManager.hideSoftInputFromWindow(review.getWindowToken(), 0);
                    }
                }else{
                    Toast.makeText(ActivityReview.this,
                            "Sorry :( You need internet. Please connect",
                            Toast.LENGTH_LONG).show();
                }

            }
        });

        rating.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                    float touchPositionX = motionEvent.getX();
                    float width = rating.getWidth();
                    float starsf = (touchPositionX / width) * 5.0f;
                    int stars = (int)starsf + 1;
                    rating.setRating(stars);
                }
                return true;
            }
        });

    }

    public void updateRating(){
        /* This action should not be performed every time a user gives a review, this should be done
            every three days for example. For demonstration purposes we do it in this way.
         */

        DatabaseReference userReviewReference = FirebaseDatabase.getInstance().getReference()
                .child(Constants.FIREBASE_BOOK_REVIEW)
                .child(book.getIsbn());
        DatabaseReference bookReference = FirebaseDatabase.getInstance().getReference()
                .child(Constants.FIREBASE_BOOKS)
                .child(book.getIsbn());
        bookReference.setValue(book);

        summmationRating = (long )mDataSet.size() * book.getRating();

        userReviewReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(Profile.getCurrentProfile().getId()).exists()){
                    userExists = true;
                    userReview = dataSnapshot.child(Profile.getCurrentProfile().getId()).getValue(Review.class);
                }

                else
                    userExists = false;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        int nUsers;
        if(userExists){
            summmationRating = summmationRating - userReview.getRating() + (long)rating.getRating();
            nUsers = mDataSet.size();
        } else{
            summmationRating += (long) rating.getRating();
            if(mDataSet.size() == 0)
                nUsers = 1;
            else
                nUsers = mDataSet.size() + 1;
        }
        book.setRating(summmationRating / nUsers);
        bookReference.setValue(book);
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
