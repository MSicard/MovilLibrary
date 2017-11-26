package com.iteso.library.gui;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import com.iteso.library.R;

public class ActivityMusicPlayer extends ActivityBase implements View.OnClickListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnPreparedListener{
    protected ImageButton forward, backward, pause, play;
    private MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);
        forward = (ImageButton)findViewById(R.id.activity_music_forward);
        backward = (ImageButton)findViewById(R.id.activity_music_backward);
        pause = (ImageButton)findViewById(R.id.activity_music_pause);
        play = (ImageButton)findViewById(R.id.activity_music_play);
        //mediaPlayer = MediaPlayer
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.start();
            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.pause();
            }
        });
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {

    }

    @Override
    public void onCompletion(MediaPlayer mp) {

    }

    @Override
    public void onPrepared(MediaPlayer mp) {

    }

    @Override
    public void onClick(View v) {

    }
}
