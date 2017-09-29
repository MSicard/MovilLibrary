package com.iteso.library.gui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.iteso.library.R;
import com.iteso.library.common.Utils;

/**
 * Created by Maritza on 22/09/2017.
 */

public abstract class ActivityBase extends AppCompatActivity {

    protected String[] mActivitiesTitles;
    protected DrawerLayout mDrawerLayout;
    protected NavigationView mNavigationView;
    protected ImageView mHeaderBg;
    protected ImageView mPhoto;
    protected TextView mName;
    protected View mNavHeader;


    protected void onCreateDrawer(){
        mActivitiesTitles = getResources().getStringArray(R.array.titles);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);

        mNavHeader = mNavigationView.getHeaderView(0);

        mHeaderBg = (ImageView) mNavHeader.findViewById(R.id.nav_img_header_bg);
        mPhoto = (ImageView)mNavHeader.findViewById(R.id.nav_img_profile);
        mName = (TextView)mNavHeader.findViewById(R.id.nav_name);

        loadNavHeader();
    }

    private void loadNavHeader() {
        // name, website
        mName.setText("Ravi Tamada");

        mHeaderBg.setImageResource(R.drawable.navigation_bg);
        Bitmap photo = BitmapFactory.decodeResource(this.getResources(),
                R.drawable.profile);

        mPhoto.setImageBitmap(Utils.getRoundedShape(photo));
    }

    @Override
    public void setTitle(CharSequence title) {
        getSupportActionBar().setTitle(title);
    }

    private void selectItem(int position){
        Intent intent;
        switch (position){
            case 0:
                break;
            case 1:
                intent = new Intent(this, ActivityMyBooks.class);
                startActivity(intent);
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
        }
    }

}
