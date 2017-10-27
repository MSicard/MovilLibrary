package com.iteso.library.gui;

import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.iteso.library.R;
import com.iteso.library.adapters.AdapterComment;
import com.iteso.library.beans.Comment;

import java.util.ArrayList;
import java.util.Date;

import static com.iteso.library.common.Utils.getRoundedShape;

public class ActivityComments extends ActivityBase {
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Comment> mDataSet;
    private RecyclerView mRecyclerView;

    private ImageView userPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        onCreateDrawer();

        userPhoto = (ImageView) findViewById(R.id.activity_comments_user_photo);
        userPhoto.setImageBitmap(getRoundedShape(BitmapFactory.decodeResource(this.getResources(), R.drawable.profile)));

        mRecyclerView = (RecyclerView) findViewById(R.id.activity_comments_recycler);
        mRecyclerView.setHasFixedSize(true);

        mDataSet = new ArrayList<>();
        mDataSet.add(new Comment(R.drawable.profile, "Some guy",  new Date(2017, 10, 14), "This is a comment"));
        mDataSet.add(new Comment(R.drawable.profile, "Some other guy",  new Date(2017, 10, 14), "This is a comment"));
        mDataSet.add(new Comment(R.drawable.profile, "Some other guy",  new Date(2017, 10, 14), "This is a comment"));
        mDataSet.add(new Comment(R.drawable.profile, "Some other guy",  new Date(2017, 10, 14), "This is a comment"));

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new AdapterComment(this, mDataSet);
        mRecyclerView.setAdapter(mAdapter);
    }
}
