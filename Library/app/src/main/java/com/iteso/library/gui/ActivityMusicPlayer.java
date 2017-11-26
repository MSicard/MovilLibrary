package com.iteso.library.gui;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.iteso.library.R;
import com.iteso.library.beans.Book;
import com.iteso.library.common.DownloadImage;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ActivityMusicPlayer extends Activity implements View.OnClickListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnPreparedListener{
    protected ImageButton forward, backward, play;
    private MediaPlayer mediaPlayer;
    protected ProgressBar progressBar;
    private boolean wasPlaying = false;
    private boolean isPrepared = false;
    private final Handler handler = new Handler();
    private ImageView image;
    private int mediaFileLengthInMilliseconds;
    private TextView author;
    private TextView title;
    private TextView duration;
    private Runnable notification;


    protected SeekBar seek;
    private Book book;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);
        play = (ImageButton)findViewById(R.id.activity_music_play);
        image = (ImageView)findViewById(R.id.activity_music_image);
        progressBar = (ProgressBar)findViewById(R.id.activity_music_progressbar);
        seek = (SeekBar)findViewById(R.id.activity_music_seekbar);
        author = (TextView)findViewById(R.id.activity_music_author);
        title = (TextView)findViewById(R.id.activity_music_title);
        duration = (TextView)findViewById(R.id.activity_music_duration);

        book = getIntent().getExtras().getParcelable("book");
        seek.setMax(99);
        title.setText(book.getTitle());
        author.setText(book.getAuthor());
        play.setOnClickListener(this);
        new DownloadImage(image, book.getImage()).execute();


        if (mediaPlayer == null){
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setOnBufferingUpdateListener(this);
            mediaPlayer.setOnCompletionListener(this);
            mediaPlayer.setOnPreparedListener(this);
        }
        Date minutes = new Date(mediaPlayer.getDuration() / 1000);
        String pattern = "MM:SS";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        duration.setText(simpleDateFormat.format(minutes));

        seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(mediaPlayer != null && fromUser){
                    mediaPlayer.seekTo((progress * mediaFileLengthInMilliseconds) / 100);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        seek.setSecondaryProgress(percent);

    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        play.setImageResource(android.R.drawable.ic_media_play);

    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        isPrepared = true;
        progressBar.setVisibility(View.GONE);
        play.setImageResource(android.R.drawable.ic_media_pause);
        play.setVisibility(View.VISIBLE);
        mediaPlayer.start();
        mediaFileLengthInMilliseconds = mediaPlayer.getDuration(); // gets the song length in milliseconds from URL
        primarymSeekBarProgressUpdater();
    }

    @Override
    public void onClick(View v) {
        try {
            if(mediaPlayer != null) {
                if (!mediaPlayer.isPlaying()) {

                    if(!isPrepared) {
                        progressBar.setVisibility(View.VISIBLE);
                        resetMediaPlayer();
                    }else {
                        play.setImageResource(android.R.drawable.ic_media_pause);
                        mediaPlayer.start();
                        primarymSeekBarProgressUpdater();
                    }
                    wasPlaying = false;
                } else {
                    wasPlaying = true;
                    mediaPlayer.pause();
                    play.setImageResource(android.R.drawable.ic_media_play);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void primarymSeekBarProgressUpdater() {
        if(mediaPlayer != null){
            seek.setProgress((int) (((float) mediaPlayer.getCurrentPosition() / mediaFileLengthInMilliseconds) * 100)); // This math construction give a percentage of "was playing"/"song length"
            if (mediaPlayer.isPlaying()) {
                notification = new Runnable() {
                    public void run() {
                        primarymSeekBarProgressUpdater();
                    }
                };
                handler.postDelayed(notification, 1000);
            }
        }
    }

    public void resetMediaPlayer(){
        isPrepared = false;
        if(mediaPlayer != null)
            try {
                mediaPlayer.stop();
                mediaPlayer.reset();
                mediaPlayer.setDataSource(book.getAudio()); // setup song from http://www.hrupin.com/wp-content/uploads/mp3/testsong_20_sec.mp3 URL to mediaplayer data source
                if(!wasPlaying || !isPrepared)
                    mediaPlayer.prepareAsync(); // you must call this method after setup the datasource in setDataSource method. After calling prepare() the instance of MediaPlayer starts load data from URL to internal buffer.
                seek.setProgress(0);
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mediaPlayer.isPlaying()){
            mediaPlayer.pause();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mediaPlayer.isPlaying()){
            mediaPlayer.stop();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mediaPlayer.isPlaying()){
            mediaPlayer.stop();
        }
        mediaPlayer = null;
    }
}
