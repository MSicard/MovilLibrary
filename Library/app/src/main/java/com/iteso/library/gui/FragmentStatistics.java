package com.iteso.library.gui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
    protected ImageView photo;

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
        Bitmap photoA = BitmapFactory.decodeResource(getActivity().getResources(),
                R.drawable.profile);
        photo = (ImageView)view.findViewById(R.id.fragment_statistics_image_profile);
        photo.setImageBitmap(Utils.getRoundedShape(photoA));

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

        return view;
    }
}
