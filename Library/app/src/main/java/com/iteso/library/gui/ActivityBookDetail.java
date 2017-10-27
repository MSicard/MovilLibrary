package com.iteso.library.gui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.iteso.library.R;

public class ActivityBookDetail extends ActivityBase {
    private TextView reviews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        onCreateDrawer();

        reviews = (TextView) findViewById(R.id.activity_book_detail_reviews);
        reviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ActivityReview.class);
                startActivity(intent);
            }
        });
    }
}
