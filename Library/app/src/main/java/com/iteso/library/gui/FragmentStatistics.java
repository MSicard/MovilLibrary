package com.iteso.library.gui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.iteso.library.R;

/**
 * Created by Maritza on 25/09/2017.
 */

public class FragmentStatistics extends Fragment {
    protected ImageButton lastMonth;
    protected ImageButton reading;
    protected ImageButton totalRead;
    protected TextView tv_lastMonth;
    protected TextView tv_reading;
    protected TextView tv_totalRead;
    protected ImageView graphic;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistics, container, false);
        lastMonth = (ImageButton)view.findViewById(R.id.fragment_statistics_button_last_month);
        reading = (ImageButton)view.findViewById(R.id.fragment_statistics_button_reading);
        totalRead = (ImageButton)view.findViewById(R.id.fragment_statistics_button_total_read);
        tv_lastMonth = (TextView)view.findViewById(R.id.fragment_statistics_int_last_month);
        tv_reading = (TextView)view.findViewById(R.id.fragment_statistics_int_reading);
        tv_totalRead = (TextView)view.findViewById(R.id.fragment_statistics_int_total_read);
        graphic = (ImageView)view.findViewById(R.id.fragment_statistics_graphic);
        return view;
    }
}
