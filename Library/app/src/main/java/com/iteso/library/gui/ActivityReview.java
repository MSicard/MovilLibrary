package com.iteso.library.gui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.WindowManager;

import com.iteso.library.R;
import com.iteso.library.adapters.AdapterReview;
import com.iteso.library.beans.Review;

import java.util.ArrayList;
import java.util.Date;

public class ActivityReview extends ActivityBase {
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Review> mDataSet;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        onCreateDrawer();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        mRecyclerView = (RecyclerView) findViewById(R.id.activity_review_recycler);
        mRecyclerView.setHasFixedSize(true);

        mDataSet = new ArrayList<>();
        mDataSet.add(new Review(R.drawable.profile, "Some guy", new Date(2017, 10, 14), 4, "This is a review"));
        mDataSet.add(new Review(R.drawable.profile, "Some other guy", new Date(2017, 10, 14), 4, "This is a review"));
        mDataSet.add(new Review(R.drawable.profile, "Some other guy", new Date(2017, 10, 14), 4, "This is a review"));
        mDataSet.add(new Review(R.drawable.profile, "Some other guy", new Date(2017, 10, 14), 4, "This is a review"));

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new AdapterReview(this, mDataSet);
        mRecyclerView.setAdapter(mAdapter);
    }
}
