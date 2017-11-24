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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        onCreateDrawer();

        // ------------------------ Libros destacados -------------------------- //
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



        // ------------------------ Libros categoria -------------------------- //
        computingBooksRecyclerView = (RecyclerView) findViewById(R.id.activity_home_recycler_categorized_books);
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
