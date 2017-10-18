package com.iteso.library.gui;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.iteso.library.R;

/**
 * Created by Maritza on 04/10/2017.
 */

public class FragmentProfile extends Fragment implements OnClickListener{

    protected TextView mName;
    protected TextView mEmail;
    protected TextView mFavoriteBooks;
    protected TextView mAboutMe;
    protected ImageView mPhoto;
    protected ImageButton mNameB;
    protected ImageButton mEmailB;
    protected ImageButton mFavoriteBooksB;
    protected ImageButton mAboutMeB;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        mName = (TextView)view.findViewById(R.id.fragment_profile_name);
        mEmail = (TextView)view.findViewById(R.id.fragment_profile_email);
        mFavoriteBooks = (TextView)view.findViewById(R.id.fragment_profile_favorite_books);
        mAboutMe = (TextView)view.findViewById(R.id.fragment_profile_about);
        mPhoto = (ImageView) view.findViewById(R.id.fragment_profile_image_profile);
        mNameB = (ImageButton)view.findViewById(R.id.fragment_profile_change_name);
        mEmailB = (ImageButton)view.findViewById(R.id.fragment_profile_change_favorite_books);
        mFavoriteBooksB = (ImageButton)view.findViewById(R.id.fragment_profile_change_favorite_books);
        mAboutMeB = (ImageButton)view.findViewById(R.id.fragment_profile_change_about);

        mNameB.setOnClickListener(this);
        mEmailB.setOnClickListener(this);
        mFavoriteBooksB.setOnClickListener(this);
        mAboutMeB.setOnClickListener(this);
        return view;
    }


    public void createDialog(String text, final TextView tv){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View v = inflater.inflate(R.layout.dialog_profile_change, null);
        builder.setView(v);

        final EditText editText = (EditText)v.findViewById(R.id.dialog_profile_change);
        editText.setHint(tv.getText().toString());
        final TextView textView = (TextView)v.findViewById(R.id.dialog_profile_change_text);
        textView.setText(text);

        builder.setPositiveButton("CHANGE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
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
                createDialog("New name", mName);
                break;
            case R.id.fragment_profile_change_email:
                createDialog("New Email", mEmail);
                break;
            case R.id.fragment_profile_change_favorite_books:
                createDialog("Favorite Books", mFavoriteBooks);
                break;
            case R.id.fragment_profile_change_about:
                createDialog("About You", mAboutMe);
                break;
        }
    }

}
