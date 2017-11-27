package com.iteso.library.gui;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
import com.iteso.library.beans.Book;
import com.iteso.library.common.Constants;
import com.iteso.library.common.DownloadImage;
import com.iteso.library.common.Utils;

public class ActivityBookDetail extends ActivityBase {
    private ImageView image;
    private TextView title;
    private TextView author;
    private RatingBar rating;
    private TextView synopsis;
    private TextView reviews;
    private Button bibliography;
    private Book book;
    private FloatingActionButton addBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        onCreateDrawer();

        image = (ImageView) findViewById(R.id.activity_book_detail_image);
        title = (TextView) findViewById(R.id.activity_book_detail_title);
        author = (TextView) findViewById(R.id.activity_book_detail_author);
        rating = (RatingBar) findViewById(R.id.activity_book_detail_rating);
        synopsis = (TextView) findViewById(R.id.activity_book_detail_synopsis);
        reviews = (TextView) findViewById(R.id.activity_book_detail_reviews);
        bibliography = (Button) findViewById(R.id.activity_book_detail_btn_bibliografhy);
        addBook = (FloatingActionButton)  findViewById(R.id.activity_book_detail_add_book);

        Intent intent = getIntent();
        String isbn = intent.getStringExtra("book");

        DatabaseReference bookReference = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_BOOKS)
                .child(isbn);

        bookReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                book = dataSnapshot.getValue(Book.class);

                DownloadImage downloadImage = new DownloadImage(image, book.getImage());
                downloadImage.execute();
                title.setText(book.getTitle());
                author.setText(book.getAuthor());
                rating.setRating(book.getRating());
                synopsis.setText(book.getSynopsis());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        bibliography.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createDialogBibliography(book.toString());
            }
        });

        reviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ActivityReview.class);
                intent.putExtra("book", book);
                startActivity(intent);
            }
        });


        addBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Utils.isConnectedMobile(ActivityBookDetail.this) || Utils.isConnectedWifi(ActivityBookDetail.this)){
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_USER)
                            .child(Profile.getCurrentProfile().getId())
                            .child(Constants.FIREBASE_USER_BOOKS)
                            .child(book.getIsbn());

                    databaseReference.setValue(book);
                    addBook.setVisibility(View.INVISIBLE);
                    Toast.makeText(ActivityBookDetail.this, "'" + book.getTitle() + "' has been added to your books", Toast.LENGTH_LONG).show();
                }
                Toast.makeText(ActivityBookDetail.this, "Sorry :( You need internet. Please connect", Toast.LENGTH_LONG).show();
            }
        });

        checkIfAdded();
    }

    private void checkIfAdded(){
        DatabaseReference checkReference = FirebaseDatabase.getInstance().getReference()
                .child(Constants.FIREBASE_USER)
                .child(Profile.getCurrentProfile().getId())
                .child(Constants.FIREBASE_USER_BOOKS);
        checkReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    if (dataSnapshot.child(book.getIsbn()).exists()) {
                        addBook.setVisibility(View.INVISIBLE);
                    } else {
                        addBook.setVisibility(View.VISIBLE);
                    }
                }catch(Exception e){}
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
