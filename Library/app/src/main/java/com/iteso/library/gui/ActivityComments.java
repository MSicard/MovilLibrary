package com.iteso.library.gui;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.Profile;
import com.facebook.login.widget.ProfilePictureView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.iteso.library.R;
import com.iteso.library.adapters.AdapterComment;
import com.iteso.library.beans.Comment;
import com.iteso.library.beans.Publication;
import com.iteso.library.beans.User;
import com.iteso.library.common.Constants;

import org.w3c.dom.Text;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.iteso.library.common.Utils.getRoundedShape;

public class ActivityComments extends ActivityBase {
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Comment> mDataSet;
    private RecyclerView mRecyclerView;

    private String id;
    private Publication pub;

    private ProfilePictureView userPhoto;
    private TextView nickname;
    private ImageButton send;
    private EditText comment;
    private TextView time;
    private TextView publication;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        onCreateDrawer();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        id = getIntent().getExtras().getString("ID");
        pub = getIntent().getExtras().getParcelable("publication");

        userPhoto = (ProfilePictureView) findViewById(R.id.activity_comments_user_photo);
        nickname = (TextView)findViewById(R.id.activity_comments_user_name);
        send = (ImageButton)findViewById(R.id.activity_comments_send_comment);
        comment = (EditText)findViewById(R.id.activity_comments_comment);
        publication = (TextView)findViewById(R.id.activity_comments_publication);
        time = (TextView) findViewById(R.id.activity_comments_time);

        publication.setText(pub.getMessage());
        //Agregar el tiempo
        mRecyclerView = (RecyclerView) findViewById(R.id.activity_comments_recycler);
        mRecyclerView.setHasFixedSize(true);
        getNickname();
        getDataProfile();
        mDataSet = new ArrayList<>();
        getData();

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new AdapterComment(this, mDataSet);
        mRecyclerView.setAdapter(mAdapter);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!comment.getText().toString().equals("")){
                    Comment msg = new Comment(Profile.getCurrentProfile().getId(),
                            comment.getText().toString(),
                            name,
                            new Timestamp(System.currentTimeMillis()).getTime());
                    DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_USER)
                            .child(id).child(Constants.FIREBASE_USER_PUBLICATION);
                    msg.setIdComment(mDatabaseReference.push().getKey());
                    DatabaseReference mCommentReference = mDatabaseReference
                            .child(Constants.FIREBASE_USER_PUBLICATION_COMMENTS)
                            .child(pub.getId())
                            .child(msg.getIdComment());
                    mCommentReference.setValue(msg);
                    comment.setText("");
                    comment.clearFocus();

                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(comment.getWindowToken(), 0);


                    DatabaseReference mPublicationReference = mDatabaseReference.child(Constants.FIREBASE_USER_PUBLICATION_INFO)
                            .child(pub.getId()).child(Constants.FIREBASE_USER_PUBLICATION_COUNT_COMMENT);
                    pub.setComments(pub.getComments() + 1);
                    mPublicationReference.setValue(pub.getComments());
                }
            }
        });
    }

    public void getData(){
       DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_USER).child(id)
               .child(Constants.FIREBASE_USER_PUBLICATION).child(Constants.FIREBASE_USER_PUBLICATION_COMMENTS).child(pub.getId());
       mDatabaseReference.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {
               if(dataSnapshot != null) mDataSet.clear();
               for(DataSnapshot data : dataSnapshot.getChildren()){
                   Comment comment = data.getValue(Comment.class);
                   mDataSet.add(comment);
               }
               mAdapter.notifyDataSetChanged();
           }

           @Override
           public void onCancelled(DatabaseError databaseError) {

           }
       });
    }
    public void updateProfile(User user){
        userPhoto.setProfileId(user.getImage());
        nickname.setText(user.getNickname());
    }

    public void getDataProfile(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_USER)
                .child(id).child(Constants.FIREBASE_USER_INFO);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<Map<String,Object>> t = new GenericTypeIndicator<Map<String,Object>>(){};
                Map<String, Object> value = dataSnapshot.getValue(t);
                User user = new User((String)value.get(Constants.FIREBASE_USER_ABOUT),
                        (List)value.get(Constants.FIREBASE_USER_FAVORITE_BOOKS),
                        (String)value.get(Constants.FIREBASE_USER_IMAGE),
                        (String)value.get(Constants.FIREBASE_USER_NICKNAME));
                updateProfile(user);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getNickname(){
        DatabaseReference mDataReference = FirebaseDatabase.getInstance()
                .getReference(Constants.FIREBASE_USER)
                .child(Profile.getCurrentProfile().getId())
                .child(Constants.FIREBASE_USER_INFO)
                .child(Constants.FIREBASE_USER_NICKNAME);

        mDataReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                name = dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
