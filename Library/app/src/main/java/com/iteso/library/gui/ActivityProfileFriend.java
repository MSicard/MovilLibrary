package com.iteso.library.gui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.iteso.library.R;
import com.iteso.library.beans.UserState;
import com.iteso.library.common.Constants;

import java.util.Locale;

/**
 * Created by Maritza on 26/11/2017.
 */

public class ActivityProfileFriend extends ActivityBase{

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    protected Bundle bundle;
    SectionsPagerAdapter mSectionsPagerAdapter;
    String id;
    int count = 3;
    private UserState state;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        state = new UserState(true, true, 1, 1, 1, true);
        id = getIntent().getExtras().getString("ID");
        getSetting();
        setContentView(R.layout.activity_profile);
        onCreateDrawer();
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mSectionsPagerAdapter);
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch(position){
                case 0: return new FragmentPublication();
                case 1: if(state.isVisible_p()) return new FragmentProfile();
                case 2: if(state.isVisible_st()) return new FragmentStatistics();
            }
            return null;
        }

        @Override
        public int getCount() {
            return count;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position){
                case 0: return getString(R.string.activity_profile_publications);
                case 1: if(state.isVisible_p()) return getString(R.string.activity_profile_profile);
                case 2: if(state.isVisible_st()) return getString(R.string.activity_profile_statistics);
            }
            return null;
        }
    }

    public void getSetting(){
        DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_USER)
                .child(id).child(Constants.FIREBASE_USER_STATE);
        mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                state = dataSnapshot.getValue(UserState.class);
                if(!state.isVisible_p()) count--;
                if(!state.isVisible_st())count--;
                mSectionsPagerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
