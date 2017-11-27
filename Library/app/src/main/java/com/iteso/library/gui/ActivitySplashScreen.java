package com.iteso.library.gui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.iteso.library.R;

import java.util.Timer;
import java.util.TimerTask;

public class ActivitySplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        if(user != null){
            Intent intent;
            intent = new Intent(ActivitySplashScreen.this, ActivityHome.class);

            startActivity(intent);
            finish();
        }else{
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    Intent intent;
                    intent = new Intent(ActivitySplashScreen.this, ActivityLogin.class);

                    startActivity(intent);
                    finish();
                }
            };

            Timer timer = new Timer();
            timer.schedule(task, 2500);
        }
    }
}
