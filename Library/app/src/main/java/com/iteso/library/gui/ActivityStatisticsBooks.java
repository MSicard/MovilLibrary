package com.iteso.library.gui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.iteso.library.R;
import com.iteso.library.adapters.AdapterSearchResults;
import com.iteso.library.beans.Book;
import com.iteso.library.beans.LastMonth;
import com.iteso.library.common.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ActivityStatisticsBooks extends ActivityBase {
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Book> mDataSet;
    private RecyclerView mRecyclerView;
    private String id;

    private DatabaseReference mDataReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics_books);
        onCreateDrawer();

        mRecyclerView = (RecyclerView) findViewById(R.id.activity_statistics_books_recycler);
        mRecyclerView.setHasFixedSize(true);

        int type = getIntent().getExtras().getInt("TYPE");
        id = getIntent().getExtras().getString("ID");


        switch (type){
            case R.string.type_month:
                mDataReference = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_USER).child(id)
                        .child(Constants.FIREBASE_USER_STATISTICS).child(Constants.FIREBASE_USER_LAST_MONTH);
                getDataMonth();
                break;
            case R.string.type_reading:
                mDataReference.child(Constants.FIREBASE_USER_READING);
                getData();
                break;
            case R.string.type_total:
                mDataReference.child(Constants.FIREBASE_USER_TOTAL);
                getData();
                break;
        }
        mDataSet = new ArrayList<>();

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new AdapterSearchResults(this, mDataSet);
        mRecyclerView.setAdapter(mAdapter);

    }

    public void getDataMonth(){
        mDataReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mDataSet.clear();
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    LastMonth month = data.getValue(LastMonth.class);
                    Log.v("month", month.toString());
                    Log.v("month", data.toString());
                    DatabaseReference referenceBook = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_BOOKS)
                            .child(month.getIsbn());
                    referenceBook.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Book book = dataSnapshot.getValue(Book.class);
                            mDataSet.add(book);
                            mAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
                mAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void getData(){
        mDataReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<List<Map<String,Object>>> t = new GenericTypeIndicator<List<Map<String,Object>>>(){};
                List<Map<String, Object>> value = dataSnapshot.getValue(t);
               /* for(int i = 0; i < value.size(); i++){
                    DatabaseReference bookReference = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_BOOKS)
                            .child(value.get(i).get());
                    bookReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Book book = dataSnapshot.getValue(Book.class);
                            mDataSet.add(book);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }*/
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
