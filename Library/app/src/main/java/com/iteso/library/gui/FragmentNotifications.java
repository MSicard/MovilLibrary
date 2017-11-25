package com.iteso.library.gui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iteso.library.R;
import com.iteso.library.adapters.AdapterNotification;
import com.iteso.library.beans.NotificationPublication;
import com.iteso.library.common.Constants;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Maritza on 25/09/2017.
 */

public class FragmentNotifications extends Fragment {

    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mlayoutManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);
        RecyclerView mRecyclerView = (RecyclerView)view.findViewById(R.id.fragment_notification);

        mRecyclerView.setHasFixedSize(true);
        mlayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mlayoutManager);

        ArrayList mDataSet = new ArrayList();
        mDataSet.add(new NotificationPublication("Newton",
                Constants.NOTIFICATION_PUBLICATION,
                new Date(2017, 9, 1, 9, 11, 33),
                R.drawable.profile_newton,
                1));
        mDataSet.add(new NotificationPublication("Newton",
                Constants.NOTIFICATION_PUBLICATION,
                new Date(2017, 8, 25, 22, 15, 45),
                R.drawable.profile_newton, 1));
        mDataSet.add(new NotificationPublication("Leibniz",
                Constants.NOTIFICATION_PUBLICATION,
                new Date(2017, 8, 25, 22, 5, 12),
                R.drawable.profile_leibniz, 3));


        mAdapter = new AdapterNotification(getActivity(), mDataSet);
        mRecyclerView.setAdapter(mAdapter);
        return view;
    }
}
