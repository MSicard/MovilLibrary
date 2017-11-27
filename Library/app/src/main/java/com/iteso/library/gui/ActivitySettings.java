package com.iteso.library.gui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;

import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.widget.ProfilePictureView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.iteso.library.R;
import com.iteso.library.beans.UserState;
import com.iteso.library.common.Constants;
import com.iteso.library.common.Utils;

public class ActivitySettings extends ActivityBase {
    private ProfilePictureView profile_image;
    private Switch profile;
    private Switch statistics;
    private Switch notifications;
    private LoginButton logout;

    private DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance()
            .getReference(Constants.FIREBASE_USER).child(Profile.getCurrentProfile().getId())
            .child(Constants.FIREBASE_USER_STATE);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        onCreateDrawer();

        profile = (Switch) findViewById(R.id.activity_settings_switch_show_profile);
        statistics = (Switch) findViewById(R.id.activity_settings_switch_show_statistics);
        notifications = (Switch) findViewById(R.id.activity_settings_switch_show_notifications);
        logout = (LoginButton)findViewById(R.id.activity_settings_logout);

        profile_image = (ProfilePictureView) findViewById(R.id.activity_settings_profile_image);
        profile_image.setProfileId(Profile.getCurrentProfile().getId());

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                LoginManager.getInstance().logOut();
                Intent intent = new Intent(ActivitySettings.this, ActivityLogin.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        profile.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                DatabaseReference ref = mDatabaseReference.child(Constants.FIREBASE_USER_VISIBLE_P);
                ref.setValue(isChecked);
            }
        });

        statistics.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                DatabaseReference ref = mDatabaseReference.child(Constants.FIREBASE_USER_VISIBLE_ST);
                ref.setValue(isChecked);
            }
        });

        notifications.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                DatabaseReference ref = mDatabaseReference.child(Constants.FIREBASE_USER_NOTIFICATION);
                ref.setValue(isChecked);
            }
        });

        updateGUI();
    }

    public void updateGUI(){
        DatabaseReference ref = mDatabaseReference;
        ref.keepSynced(true);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserState state = dataSnapshot.getValue(UserState.class);
                profile.setChecked(state.isVisible_p());
                statistics.setChecked(state.isVisible_st());
                notifications.setChecked(state.isNotification());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
