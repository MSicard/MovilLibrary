package com.iteso.library.gui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.iteso.library.R;
import com.iteso.library.adapters.AdapterStatisticBook;
import com.iteso.library.beans.Book;

import java.util.ArrayList;

public class ActivityStatisticsBooks extends ActivityBase {
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Book> mDataSet;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics_books);
        onCreateDrawer();

        mRecyclerView = (RecyclerView) findViewById(R.id.activity_statistics_books_recycler);
        mRecyclerView.setHasFixedSize(true);

        mDataSet = new ArrayList<>();
        mDataSet.add(new Book("Cronicas de Narnia", "C.S. Lewis", "Esta es la sinapsis del libro, Esta es la sinapsis del libro, Esta es la sinapsis del libro, Esta es la sinapsis del libro, Esta es la sinapsis del libro, Esta es la sinapsis del libro, Esta es la sinapsis del libro,"));
        mDataSet.add(new Book("Cronicas de Narnia", "C.S. Lewis", "Esta es la sinapsis del libro, Esta es la sinapsis del libro, Esta es la sinapsis del libro, Esta es la sinapsis del libro, Esta es la sinapsis del libro, Esta es la sinapsis del libro, Esta es la sinapsis del libro,"));
        mDataSet.add(new Book("Cronicas de Narnia", "C.S. Lewis", "Esta es la sinapsis del libro, Esta es la sinapsis del libro, Esta es la sinapsis del libro, Esta es la sinapsis del libro, Esta es la sinapsis del libro, Esta es la sinapsis del libro, Esta es la sinapsis del libro,"));
        mDataSet.add(new Book("Cronicas de Narnia", "C.S. Lewis", "Esta es la sinapsis del libro, Esta es la sinapsis del libro, Esta es la sinapsis del libro, Esta es la sinapsis del libro, Esta es la sinapsis del libro, Esta es la sinapsis del libro, Esta es la sinapsis del libro,"));
        mDataSet.add(new Book("Cronicas de Narnia", "C.S. Lewis", "Esta es la sinapsis del libro, Esta es la sinapsis del libro, Esta es la sinapsis del libro, Esta es la sinapsis del libro, Esta es la sinapsis del libro, Esta es la sinapsis del libro, Esta es la sinapsis del libro,"));


        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new AdapterStatisticBook(this, mDataSet);
        mRecyclerView.setAdapter(mAdapter);

    }
}
