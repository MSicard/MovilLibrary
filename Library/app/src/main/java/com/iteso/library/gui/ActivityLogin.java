package com.iteso.library.gui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.iteso.library.R;

public class ActivityLogin extends AppCompatActivity {
    protected EditText username;
    protected EditText password;
    protected Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (EditText) findViewById(R.id.activity_login_username);
        password = (EditText) findViewById(R.id.activity_login_password);
        login = (Button) findViewById(R.id.activity_login_sign_in);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(username.getText().toString().trim().equals("")){
                    username.setError("Username vacío!");
                }

                if(password.getText().toString().trim().equals("")){
                    password.setError("Contraseña vacía!");
                }
            }
        });
    }
}
