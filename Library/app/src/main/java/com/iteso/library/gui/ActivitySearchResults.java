package com.iteso.library.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.iteso.library.R;
import com.iteso.library.adapters.AdapterSearchResults;
import com.iteso.library.beans.Book;
import com.iteso.library.common.Constants;

import java.util.ArrayList;

public class ActivitySearchResults extends ActivityBase {
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Book> mDataSet;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        onCreateDrawer();

        Intent intent = getIntent();
        final String query = intent.getStringExtra("searchString");

        mRecyclerView = (RecyclerView) findViewById(R.id.activity_search_results_recycler);
        mRecyclerView.setHasFixedSize(true);

        mDataSet = new ArrayList<>();

        DatabaseReference mDataReference = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_BOOKS);
        mDataReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) mDataSet.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Book book = data.getValue(Book.class);
                    if (book.getTitle().toLowerCase().indexOf(query.toLowerCase()) != -1 || book.getAuthor().toLowerCase().indexOf(query.toLowerCase()) != -1)
                        mDataSet.add(book);
                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new AdapterSearchResults(this, mDataSet);
        mRecyclerView.setAdapter(mAdapter);
    }
}
