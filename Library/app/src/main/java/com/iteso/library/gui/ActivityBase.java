package com.iteso.library.gui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.Profile;
import com.facebook.login.widget.ProfilePictureView;
import com.iteso.library.R;
import com.iteso.library.common.Utils;

import org.json.JSONObject;

/**
 * Created by Maritza on 22/09/2017.
 */

public abstract class ActivityBase extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    protected Toolbar mToolbar;
    protected String[] mActivitiesTitles;
    protected DrawerLayout mDrawerLayout;
    protected NavigationView mNavigationView;
    protected ProfilePictureView mPhoto;
    protected TextView mName;
    protected View mNavHeader;
    protected ProfilePictureView picture;

    CallbackManager callbackManager;
    AccessTokenTracker accessTokenTracker;
    JSONObject user;
    String FIELDS = "friends";
    private String REQUEST_FRIENDS = TextUtils.join(",",
            new String[]{"name", "id", "picture"});


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


        mPhoto = (ProfilePictureView)mNavHeader.findViewById(R.id.nav_img_profile);
        mName = (TextView)mNavHeader.findViewById(R.id.nav_name);

        loadNavHeader();
    }

    private void loadNavHeader() {
        if(AccessToken.getCurrentAccessToken() == null){
            Bitmap photo = BitmapFactory.decodeResource(this.getResources(),
                    R.drawable.profile);
            //mandar a login :)
            mName.setText("Ravi Tamada");
        }
        else{
            Profile profile = Profile.getCurrentProfile();
            if(profile != null){
                mName.setText(profile.getName());
                mPhoto.setProfileId(profile.getId());

            }else{
                Profile.fetchProfileForCurrentAccessToken();
            }
        }
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Intent intent;
        switch (item.getItemId()){
            case R.id.nav_home:
                intent = new Intent(this, ActivityHome.class);
                startActivity(intent);
                break;
            case R.id.nav_my_books:
                intent = new Intent(this, ActivityMyBooks.class);
                intent.putExtra("ID", Profile.getCurrentProfile().getId());
                startActivity(intent);
                break;
            case R.id.nav_profile:
                intent = new Intent(this, ActivityProfile.class);
                intent.putExtra("ID", Profile.getCurrentProfile().getId());
                startActivity(intent);
                break;
            case R.id.nav_settings:
                intent = new Intent(this, ActivitySettings.class);
                startActivity(intent);
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void createDialogBibliography(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();

        View v = inflater.inflate(R.layout.dialog_bibliography, null);
        builder.setView(v);

        final TextView bibliography = (TextView) v.findViewById(R.id.dialog__bibliography_bibliography);
        final TextView textView = (TextView)v.findViewById(R.id.dialog_bibliography_title);
        textView.setText("Bibliography");

        builder.setPositiveButton("ACCEPT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.create().show();
    }


    //Cierra el teclado
    public void closeSoftKeyBoard() {
        InputMethodManager inputMethodManager =
                (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
    }
}
