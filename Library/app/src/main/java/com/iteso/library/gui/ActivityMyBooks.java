package com.iteso.library.gui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.iteso.library.R;
import com.iteso.library.adapters.AdapterMyBook;
import com.iteso.library.beans.Book;
import com.iteso.library.beans.MyBook;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Maritza on 04/10/2017.
 */

public class ActivityMyBooks extends ActivityBase {

    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mlayoutManager;
    private static int numberOfColumns = 3;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_books);

        onCreateDrawer();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.fragment_my_books_recyclerview);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        ArrayList<Book> myDataSet = new ArrayList<Book>();

        MyBook book = new MyBook("El nombre del viento", "Patrick", "asdfasfd");
        MyBook book1 = new MyBook("El nombre del viento", "Patrick", "asdfasfd");
        MyBook book2 = new MyBook("El nombre del viento", "Patrick", "asdfasfd");
        MyBook book3 = new MyBook("El nombre del viento", "Patrick", "asdfasfd");
        MyBook book4 = new MyBook("El nombre del viento", "Patrick", "asdfasfd");
        MyBook book5 = new MyBook("El nombre del viento", "Patrick", "asdfasfd");
        MyBook book6 = new MyBook("El nombre del viento", "Patrick", "asdfasfd");
        MyBook book7 = new MyBook("El nombre del viento", "Patrick", "asdfasfd");
        myDataSet.add(book);
        myDataSet.add(book1);
        myDataSet.add(book2);
        myDataSet.add(book3);
        myDataSet.add(book4);
        myDataSet.add(book5);
        myDataSet.add(book6);
        myDataSet.add(book7);

        mAdapter = new AdapterMyBook(this, myDataSet);
        recyclerView.setAdapter(mAdapter);

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference books = mDatabase.child("books").child("9786073111607");


        books.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Book book = dataSnapshot.getValue(Book.class);
                Toast.makeText(ActivityMyBooks.this, book.toString(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ActivityMyBooks.this, "Oh no!", Toast.LENGTH_LONG).show();

            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();

    }
}
