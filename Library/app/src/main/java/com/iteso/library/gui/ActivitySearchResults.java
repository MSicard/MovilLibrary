package com.iteso.library.gui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.iteso.library.R;

public class ActivitySearchResults extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        Intent intent = getIntent();
        String query = intent.getStringExtra("searchString");
        Toast.makeText(this, query, Toast.LENGTH_LONG).show();
    }
}
