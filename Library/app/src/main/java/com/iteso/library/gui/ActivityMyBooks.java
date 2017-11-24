package com.iteso.library.gui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.iteso.library.R;
import com.iteso.library.adapters.AdapterMyBook;
import com.iteso.library.beans.Book;
import com.iteso.library.beans.MyBookDetail;
import com.iteso.library.beans.Publication;
import com.iteso.library.common.Constants;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Maritza on 04/10/2017.
 */

public class ActivityMyBooks extends ActivityBase {

    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mlayoutManager;
    private static int numberOfColumns = 3;
    ArrayList<Book> mDataSet;
    private String id ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_books);
        onCreateDrawer();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.fragment_my_books_recyclerview);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        mDataSet = new ArrayList<Book>();

        id = getIntent().getExtras().getString("ID");
        getBooks();

        mAdapter = new AdapterMyBook(this, mDataSet);
        recyclerView.setAdapter(mAdapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
    }



    private void getBooks(){
        DatabaseReference mDataReference = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_USER)
                .child(id).child(Constants.FIREBASE_USER_BOOKS);
        mDataReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot != null) mDataSet.clear();
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    Book book = data.getValue(Book.class);
                    mDataSet.add(book);
                }
                mAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
