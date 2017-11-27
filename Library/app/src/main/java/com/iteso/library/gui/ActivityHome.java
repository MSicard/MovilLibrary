package com.iteso.library.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.iteso.library.R;
import com.iteso.library.adapters.AdapterCategorizedBook;
import com.iteso.library.adapters.AdapterFeaturedBook;
import com.iteso.library.beans.Book;
import com.iteso.library.common.Constants;

import java.util.ArrayList;

public class ActivityHome extends ActivityBase implements SearchView.OnQueryTextListener{
    private RecyclerView.Adapter featuredBooksAdapter;
    private RecyclerView.LayoutManager featuredBooksLayoutManager;
    private ArrayList<Book> featuredBooksDataSet;
    private RecyclerView featuredBooksRecyclerView;

    private RecyclerView.Adapter computingBooksAdapter;
    private RecyclerView.LayoutManager computingBooksLayoutManager;
    private ArrayList<Book> computingBooksDataSet;
    private RecyclerView computingBooksRecyclerView;

    private RecyclerView.Adapter mathBooksAdapter;
    private RecyclerView.LayoutManager mathBooksLayoutManager;
    private ArrayList<Book> mathBooksDataSet;
    private RecyclerView mathBooksRecyclerView;

    private RecyclerView.Adapter philosophyBooksAdapter;
    private RecyclerView.LayoutManager philosophyBooksLayoutManager;
    private ArrayList<Book> philosophyBooksDataSet;
    private RecyclerView philosophyBooksRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        onCreateDrawer();

        loadFeaturedBooks();
        loadComputingBooks();
        loadMathBooks();
        loadPhilosophyBooks();
    }

    private void loadPhilosophyBooks() {
        philosophyBooksRecyclerView = (RecyclerView) findViewById(R.id.activity_home_recycler_philosophy_books);
        philosophyBooksRecyclerView.setHasFixedSize(true);
        philosophyBooksDataSet = new ArrayList<>();

        DatabaseReference philosophyReference = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_BOOKS_CATEGORY)
                .child(Constants.FIREBASE_BOOKS_CATEGORY_PHILOSOPHY);
        philosophyReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot != null) philosophyBooksDataSet.clear();
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    Book book = data.getValue(Book.class);
                    philosophyBooksDataSet.add(book);
                }
                philosophyBooksAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        philosophyBooksLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        philosophyBooksRecyclerView.setLayoutManager(philosophyBooksLayoutManager);

        philosophyBooksAdapter = new AdapterCategorizedBook(this, philosophyBooksDataSet);
        philosophyBooksRecyclerView.setAdapter(philosophyBooksAdapter);
    }

    private void loadMathBooks() {
        mathBooksRecyclerView = (RecyclerView) findViewById(R.id.activity_home_recycler_math_books);
        mathBooksRecyclerView.setHasFixedSize(true);
        mathBooksDataSet = new ArrayList<>();

        DatabaseReference mathReference = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_BOOKS_CATEGORY)
                .child(Constants.FIREBASE_BOOKS_CATEGORY_MATH);
        mathReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot != null) mathBooksDataSet.clear();
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    Book book = data.getValue(Book.class);
                    mathBooksDataSet.add(book);
                }
                mathBooksAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mathBooksLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mathBooksRecyclerView.setLayoutManager(mathBooksLayoutManager);

        mathBooksAdapter = new AdapterCategorizedBook(this, mathBooksDataSet);
        mathBooksRecyclerView.setAdapter(mathBooksAdapter);
    }

    private void loadFeaturedBooks() {
        featuredBooksRecyclerView = (RecyclerView) findViewById(R.id.activity_home_recycler_featured_books);
        featuredBooksRecyclerView.setHasFixedSize(true);

        // Para propósito de pruebas
        featuredBooksDataSet = new ArrayList<>();

        DatabaseReference featuredReference = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_BOOKS_CATEGORY)
                .child(Constants.FIREBASE_BOOKS_CATEGORY_FEATURED);

        featuredReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot != null) featuredBooksDataSet.clear();
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    Book book = data.getValue(Book.class);
                    featuredBooksDataSet.add(book);
                }
                featuredBooksAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        featuredBooksLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        featuredBooksRecyclerView.setLayoutManager(featuredBooksLayoutManager);

        featuredBooksAdapter = new AdapterFeaturedBook(this, featuredBooksDataSet);
        featuredBooksRecyclerView.setAdapter(featuredBooksAdapter);
    }

    private void loadComputingBooks() {
        computingBooksRecyclerView = (RecyclerView) findViewById(R.id.activity_home_recycler_computing_books);
        computingBooksRecyclerView.setHasFixedSize(true);
        computingBooksDataSet = new ArrayList<>();

        DatabaseReference computingReference = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_BOOKS_CATEGORY)
                .child(Constants.FIREBASE_BOOKS_CATEGORY_COMPUTING);
        computingReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot != null) computingBooksDataSet.clear();
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    Book book = data.getValue(Book.class);
                    computingBooksDataSet.add(book);
                }
                computingBooksAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        computingBooksLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        computingBooksRecyclerView.setLayoutManager(computingBooksLayoutManager);

        computingBooksAdapter = new AdapterCategorizedBook(this, computingBooksDataSet);
        computingBooksRecyclerView.setAdapter(computingBooksAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Intent intent = new Intent(this, ActivitySearchResults.class);
        intent.putExtra("searchString", query);
        startActivity(intent);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
