package com.iteso.library.gui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;


import com.iteso.library.R;

import java.util.Locale;

/**
 * Created by Maritza on 22/09/2017.
 */

public class ActivityProfile extends ActivityBase {
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    SectionsPagerAdapter mSectionsPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        onCreateDrawer();
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch(position){
                case 0: return new FragmentProfile();
                case 1: return new FragmentStatistics();
                case 2: return new FragmentNotifications();
                case 3: return new FragmentPublication();
                case 4: return new FragmentMyFriends();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position){
                case 0: return getString(R.string.activity_profile_profile);
                case 1: return getString(R.string.activity_profile_statistics);
                case 2: return getString(R.string.activity_profile_notifications);
                case 3: return getString(R.string.activity_profile_publications);
                case 4: return getString(R.string.activity_profile_friends);
            }
            return null;
        }
    }
}
