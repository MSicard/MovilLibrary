package com.iteso.library.gui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
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
import com.iteso.library.beans.User;
import com.iteso.library.common.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Maritza on 04/10/2017.
 */

public class FragmentProfile extends Fragment implements OnClickListener{

    protected TextView mName;
    protected TextView mFavoriteBooks;
    protected TextView mAboutMe;
    protected ProfilePictureView mPhoto;
    protected ImageView mNameB;
    protected ImageView mFavoriteBooksB;
    protected ImageView mAboutMeB;
    protected String id;
    private User user;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        mName = (TextView)view.findViewById(R.id.fragment_profile_name);
        mFavoriteBooks = (TextView)view.findViewById(R.id.fragment_profile_favorite_books);
        mAboutMe = (TextView)view.findViewById(R.id.fragment_profile_about);
        mPhoto = (ProfilePictureView) view.findViewById(R.id.fragment_profile_image_profile);
        mNameB = (ImageView)view.findViewById(R.id.fragment_profile_change_name);
        mFavoriteBooksB = (ImageView) view.findViewById(R.id.fragment_profile_change_favorite_books);
        mAboutMeB = (ImageView)view.findViewById(R.id.fragment_profile_change_about);
        mNameB.setOnClickListener(this);
        mFavoriteBooksB.setOnClickListener(this);
        mAboutMeB.setOnClickListener(this);
        return view;
    }

    public void updateProfile(User user){
        mPhoto.setProfileId(user.getImage());
        mName.setText(user.getNickname());
        mAboutMe.setText(user.getAbout_me());
        String favorite = "";

        if(user.getFavoriteBooks() != null) {
            for (int i = 0; i < user.getFavoriteBooks().size(); i++) {
                favorite += user.getFavoriteBooks().get(i) + "\n";
            }
            mFavoriteBooks.setText(favorite);
        }
        Toast.makeText(getActivity(), user.toString(), Toast.LENGTH_LONG);
    }

    //CREACION DE DIALOGOS

    private ListView lv;
    public void createDialogFavorite(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View convertView = (View) inflater.inflate(R.layout.dialog_favorite_books, null);
        alertDialog.setView(convertView);
        lv = (ListView) convertView.findViewById(R.id.dialog_favorite_list);
        Button add = (Button)convertView.findViewById(R.id.dialog_favorite_book_save);
        final EditText favorite = (EditText)convertView.findViewById(R.id.dialog_favorite_book_edit_text);

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_multiple_choice,user.getFavoriteBooks());
        lv.setAdapter(adapter);
        lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        lv.setDivider(null);

        add.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_USER).child(Profile.getCurrentProfile().getId())
                        .child(Constants.FIREBASE_USER_INFO).child(Constants.FIREBASE_USER_FAVORITE_BOOKS);
                user.getFavoriteBooks().add(favorite.getText().toString());
                reference.setValue(user.getFavoriteBooks());
                adapter.notifyDataSetChanged();
            }
        });

        alertDialog.setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SparseBooleanArray positions = lv.getCheckedItemPositions();
                for(int i = 0; i < user.getFavoriteBooks().size(); i++){
                    if(positions.get(i)){
                        user.getFavoriteBooks().remove(i);
                    }
                }
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_USER).child(Profile.getCurrentProfile().getId())
                        .child(Constants.FIREBASE_USER_INFO).child(Constants.FIREBASE_USER_FAVORITE_BOOKS);
                reference.setValue(user.getFavoriteBooks());
                adapter.notifyDataSetChanged();
            }
        });
        alertDialog.setNegativeButton("Cancel", null);

        alertDialog.show();

    }

    public void createDialogAbout(final TextView tv){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View v = inflater.inflate(R.layout.dialog_profile_change, null);
        builder.setView(v);

        final EditText editText = (EditText)v.findViewById(R.id.dialog_profile_change);
        editText.setHint(tv.getText().toString());
        final TextView textView = (TextView)v.findViewById(R.id.dialog_profile_change_text);
        textView.setText("About You");

        builder.setPositiveButton("CHANGE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_USER).child(id)
                        .child(Constants.FIREBASE_USER_INFO).child(Constants.FIREBASE_USER_ABOUT);
                ref.setValue(editText.getText().toString());
                tv.setText(editText.getText().toString());
            }
        }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.create().show();
    }

    public void createDialogName(final TextView tv){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View v = inflater.inflate(R.layout.dialog_profile_change, null);
        builder.setView(v);

        final EditText editText = (EditText)v.findViewById(R.id.dialog_profile_change);
        editText.setHint(tv.getText().toString());
        final TextView textView = (TextView)v.findViewById(R.id.dialog_profile_change_text);
        textView.setText("New name");

        builder.setPositiveButton("CHANGE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_USER).child(id)
                        .child(Constants.FIREBASE_USER_INFO).child(Constants.FIREBASE_USER_NICKNAME);
                ref.setValue(editText.getText().toString());
                tv.setText(editText.getText().toString());
            }
        }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.create().show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fragment_profile_change_name:
                createDialogName(mName);
                break;
            case R.id.fragment_profile_change_favorite_books:
                createDialogFavorite();
                break;
            case R.id.fragment_profile_change_about:
                createDialogAbout(mAboutMe);
                break;
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        id = getActivity().getIntent().getStringExtra("ID");
        if(!id.equals(Profile.getCurrentProfile().getId())){
            mNameB.setVisibility(View.GONE);
            mFavoriteBooksB.setVisibility(View.GONE);
            mAboutMeB.setVisibility(View.GONE);
        }
        getData();
    }

    public void getData(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_USER)
                .child(id).child(Constants.FIREBASE_USER_INFO);
        reference.keepSynced(true);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<Map<String,Object>> t = new GenericTypeIndicator<Map<String,Object>>(){};
                Map<String, Object> value = dataSnapshot.getValue(t);

                if(value.get(Constants.FIREBASE_USER_FAVORITE_BOOKS) == null){
                    List l = new ArrayList();
                    l.clear();
                    user = new User((String)value.get(Constants.FIREBASE_USER_ABOUT),
                            l,
                            (String)value.get(Constants.FIREBASE_USER_IMAGE),
                            (String)value.get(Constants.FIREBASE_USER_NICKNAME));

                }else{
                    user = new User((String)value.get(Constants.FIREBASE_USER_ABOUT),
                            (List)value.get(Constants.FIREBASE_USER_FAVORITE_BOOKS),
                            (String)value.get(Constants.FIREBASE_USER_IMAGE),
                            (String)value.get(Constants.FIREBASE_USER_NICKNAME));
                }


                updateProfile(user);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
