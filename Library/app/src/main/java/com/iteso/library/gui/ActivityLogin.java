package com.iteso.library.gui;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.iteso.library.R;
import com.iteso.library.beans.User;
import com.iteso.library.beans.UserState;
import com.iteso.library.common.Constants;

import java.util.Arrays;

public class ActivityLogin extends AppCompatActivity {
    protected LoginButton login;
    private CallbackManager callbackManager;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseStateListener;
    protected ProgressBar progressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this);
        callbackManager = CallbackManager.Factory.create();

        setContentView(R.layout.activity_login);
        progressbar = (ProgressBar)findViewById(R.id.activity_login_progress_bar);
        login = (LoginButton) findViewById(R.id.activity_login_sign_in);

        login.setReadPermissions(Arrays.asList("email"));
        login.setReadPermissions(Arrays.asList("user_friends"));

        login.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            //Se conectó a Facebook
            @Override
            public void onSuccess(LoginResult loginResult) {
                //Se tiene que mandar el token a Firebase para la auth
                firebaseSendAuth(loginResult.getAccessToken());
                goActivityHome();
            }

            @Override
            public void onCancel() {
                errorToast();
            }

            @Override
            public void onError(FacebookException error) {
                errorToast();
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();

        firebaseStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    //Encontrar si ya existe un usuario en la base de datos
                    DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance()
                            .getReference(Constants.FIREBASE_USER);
                    DatabaseReference userExits = mDatabaseReference.child(Profile.getCurrentProfile().getId());
                    userExits.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            User value = dataSnapshot.getValue(User.class);
                            if(value == null){
                                DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_USER);
                                Profile profile = Profile.getCurrentProfile();
                                value = new User(profile.getId(), profile.getName());
                                mDatabaseReference.child(AccessToken.getCurrentAccessToken().getUserId())
                                        .child(Constants.FIREBASE_USER_INFO).setValue(value);
                                UserState state = new UserState(true, true, 0, 0, 0, true);
                                mDatabaseReference.child(AccessToken.getCurrentAccessToken().getUserId())
                                        .child(Constants.FIREBASE_USER_STATE).setValue(state);
                            }
                            goActivityHome();

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }
        };
    }

    private void firebaseSendAuth(AccessToken accessToken) {
        progressbar.setVisibility(View.VISIBLE);
        login.setVisibility(View.GONE);
        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!task.isSuccessful()){
                    errorToast();
                }
                progressbar.setVisibility(View.GONE);
                login.setVisibility(View.VISIBLE);
            }
        });
    }

    private void errorToast(){
        Toast.makeText(this, "Lo sentimos, error al inciar sesión", Toast.LENGTH_LONG);

    }

    private void goActivityHome(){
        Intent intent = new Intent(this, ActivityHome.class);
        startActivity(intent);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(firebaseStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseAuth.removeAuthStateListener(firebaseStateListener);
    }
}
