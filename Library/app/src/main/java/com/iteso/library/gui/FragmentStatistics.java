package com.iteso.library.gui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.Telephony;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.Profile;
import com.facebook.login.widget.ProfilePictureView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.iteso.library.R;
import com.iteso.library.beans.UserState;
import com.iteso.library.common.Constants;
import com.iteso.library.common.Utils;

/**
 * Created by Maritza on 25/09/2017.
 */

public class FragmentStatistics extends Fragment {
    protected ImageView lastMonth;
    protected ImageView reading;
    protected ImageView totalRead;
    protected TextView tv_lastMonth;
    protected TextView tv_reading;
    protected TextView tv_totalRead;
    protected ImageView graphic;
    protected ProfilePictureView photo;
    protected TextView nickname;
    private String id;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistics, container, false);
        lastMonth = (ImageView)view.findViewById(R.id.fragment_statistics_button_last_month);
        reading = (ImageView)view.findViewById(R.id.fragment_statistics_button_reading);
        totalRead = (ImageView)view.findViewById(R.id.fragment_statistics_button_total_read);
        tv_lastMonth = (TextView)view.findViewById(R.id.fragment_statistics_int_last_month);
        tv_reading = (TextView)view.findViewById(R.id.fragment_statistics_int_reading);
        tv_totalRead = (TextView)view.findViewById(R.id.fragment_statistics_int_total_read);
        graphic = (ImageView)view.findViewById(R.id.fragment_statistics_graphic);
        photo = (ProfilePictureView)view.findViewById(R.id.fragment_statistics_image_profile);
        nickname = (TextView)view.findViewById(R.id.fragment_statistics_name);

        //id =
        photo.setProfileId(Profile.getCurrentProfile().getId());

        lastMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ActivityStatisticsBooks.class);
                ((ActivityProfile)view.getContext()).startActivity(intent);
            }
        });

        reading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ActivityStatisticsBooks.class);
                ((ActivityProfile)view.getContext()).startActivity(intent);
            }
        });

        totalRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ActivityStatisticsBooks.class);
                ((ActivityProfile)view.getContext()).startActivity(intent);
            }
        });

        DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_USER)
                .child(Profile.getCurrentProfile().getId()).child(Constants.FIREBASE_USER_STATE);
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserState state = dataSnapshot.getValue(UserState.class);
                tv_lastMonth.setText(String.valueOf(state.getLast_month()));
                tv_reading.setText(String.valueOf(state.getReading()));
                tv_totalRead.setText(String.valueOf(state.getTotal()));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return view;
    }

    private void getNickname(){
        DatabaseReference mDataReference = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_USER)
                .child(id).child(Constants.FIREBASE_USER_INFO).child(Constants.FIREBASE_USER_NICKNAME);
        mDataReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
