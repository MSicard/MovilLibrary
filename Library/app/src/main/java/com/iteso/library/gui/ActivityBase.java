package com.iteso.library.gui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.iteso.library.R;
import com.iteso.library.common.Utils;

/**
 * Created by Maritza on 22/09/2017.
 */

public abstract class ActivityBase extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    protected Toolbar mToolbar;
    protected String[] mActivitiesTitles;
    protected DrawerLayout mDrawerLayout;
    protected NavigationView mNavigationView;
    protected ImageView mPhoto;
    protected TextView mName;
    protected View mNavHeader;


    protected void onCreateDrawer(){
        mActivitiesTitles = getResources().getStringArray(R.array.titles);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();
        mNavigationView.setNavigationItemSelectedListener(this);

        mNavHeader = mNavigationView.getHeaderView(0);


        mPhoto = (ImageView)mNavHeader.findViewById(R.id.nav_img_profile);
        mName = (TextView)mNavHeader.findViewById(R.id.nav_name);

        loadNavHeader();
    }

    private void loadNavHeader() {
        // name, website
        Bitmap photo = BitmapFactory.decodeResource(this.getResources(),
                R.drawable.profile);

        mPhoto.setImageBitmap(Utils.getRoundedShape(photo));
        mName.setText("Ravi Tamada");
    }

    @Override
    public void setTitle(CharSequence title) {
        getSupportActionBar().setTitle(title);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_drawer_example, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Intent intent;
        switch (item.getItemId()){
            case 0:
                break;
            case R.id.nav_my_books:
                intent = new Intent(this, ActivityMyBooks.class);
                startActivity(intent);
                break;
            case R.id.nav_profile:
                intent = new Intent(this, ActivityProfile.class);
                startActivity(intent);
                break;
            case 3:
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
