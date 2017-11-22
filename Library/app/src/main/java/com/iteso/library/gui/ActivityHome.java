package com.iteso.library.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SnapHelper;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;

import com.iteso.library.R;
import com.iteso.library.adapters.AdapterCategorizedBook;
import com.iteso.library.adapters.AdapterFeaturedBook;
import com.iteso.library.beans.Book;

import java.util.ArrayList;

public class ActivityHome extends ActivityBase implements SearchView.OnQueryTextListener{
    private RecyclerView.Adapter featuredBooksAdapter;
    private RecyclerView.LayoutManager featuredBooksLayoutManager;
    private ArrayList<Book> featuredBooksDataSet;
    private RecyclerView featuredBooksRecyclerView;

    private RecyclerView.Adapter categorizedBooksAdapter;
    private RecyclerView.LayoutManager categorizedBooksLayoutManager;
    private ArrayList<Book> categorizedBooksDataSet;
    private RecyclerView categorizedBooksRecyclerView;

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
        featuredBooksDataSet.add(new Book("Cronicas de Narnia", "C.S. Lewis", "Esta es la sinapsis del libro, Esta es la sinapsis del libro, Esta es la sinapsis del libro, Esta es la sinapsis del libro, Esta es la sinapsis del libro, Esta es la sinapsis del libro, Esta es la sinapsis del libro,"));
        featuredBooksDataSet.add(new Book("Cronicas de Narnia", "C.S. Lewis", "Esta es la sinapsis del libro, Esta es la sinapsis del libro, Esta es la sinapsis del libro, Esta es la sinapsis del libro, Esta es la sinapsis del libro, Esta es la sinapsis del libro, Esta es la sinapsis del libro,"));
        featuredBooksDataSet.add(new Book("Cronicas de Narnia", "C.S. Lewis", "Esta es la sinapsis del libro, Esta es la sinapsis del libro, Esta es la sinapsis del libro, Esta es la sinapsis del libro, Esta es la sinapsis del libro, Esta es la sinapsis del libro, Esta es la sinapsis del libro,"));
        featuredBooksDataSet.add(new Book("Cronicas de Narnia", "C.S. Lewis", "Esta es la sinapsis del libro, Esta es la sinapsis del libro, Esta es la sinapsis del libro, Esta es la sinapsis del libro, Esta es la sinapsis del libro, Esta es la sinapsis del libro, Esta es la sinapsis del libro,"));
        featuredBooksDataSet.add(new Book("Cronicas de Narnia", "C.S. Lewis", "Esta es la sinapsis del libro, Esta es la sinapsis del libro, Esta es la sinapsis del libro, Esta es la sinapsis del libro, Esta es la sinapsis del libro, Esta es la sinapsis del libro, Esta es la sinapsis del libro,"));


        featuredBooksLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        featuredBooksRecyclerView.setLayoutManager(featuredBooksLayoutManager);

        featuredBooksAdapter = new AdapterFeaturedBook(this, featuredBooksDataSet);
        featuredBooksRecyclerView.setAdapter(featuredBooksAdapter);

        // ------------------------ Libros categoria -------------------------- //
        categorizedBooksRecyclerView = (RecyclerView) findViewById(R.id.activity_home_recycler_categorized_books);
        categorizedBooksRecyclerView.setHasFixedSize(true);

        // Para propósito de pruebas
        categorizedBooksDataSet = new ArrayList<>();
        categorizedBooksDataSet.add(new Book("Star Wars", "Timothy Zahn", "Sinapsis"));
        categorizedBooksDataSet.add(new Book("Star Wars", "Timothy Zahn", "Sinapsis"));
        categorizedBooksDataSet.add(new Book("Star Wars", "Timothy Zahn", "Sinapsis"));
        categorizedBooksDataSet.add(new Book("Star Wars", "Timothy Zahn", "Sinapsis"));
        categorizedBooksDataSet.add(new Book("Star Wars", "Timothy Zahn", "Sinapsis"));


        categorizedBooksLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        categorizedBooksRecyclerView.setLayoutManager(categorizedBooksLayoutManager);

        categorizedBooksAdapter = new AdapterCategorizedBook(this, categorizedBooksDataSet);
        categorizedBooksRecyclerView.setAdapter(categorizedBooksAdapter);
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
