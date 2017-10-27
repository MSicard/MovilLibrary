package com.iteso.library.gui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Switch;

import com.iteso.library.R;
import com.iteso.library.common.Utils;

public class ActivitySettings extends ActivityBase {
    private ImageView profile_image;
    private Switch profile;
    private Switch statistics;
    private Switch notifications;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        onCreateDrawer();

        profile = (Switch) findViewById(R.id.activity_settings_switch_show_profile);
        statistics = (Switch) findViewById(R.id.activity_settings_switch_show_statistics);
        notifications = (Switch) findViewById(R.id.activity_settings_switch_show_notifications);

        profile_image = (ImageView) findViewById(R.id.activity_settings_profile_image);
        profile_image.setImageBitmap(Utils.getRoundedShape(BitmapFactory.decodeResource(this.getResources(), R.drawable.profile)));
    }
}
