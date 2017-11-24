package com.iteso.library.gui;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.Profile;
import com.facebook.login.widget.ProfilePictureView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.iteso.library.R;
import com.iteso.library.adapters.AdapterListDialog;
import com.iteso.library.beans.User;
import com.iteso.library.common.Constants;
import com.iteso.library.common.Utils;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
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
        for(int i = 0; i < user.getFavoriteBooks().size(); i++){
            favorite += user.getFavoriteBooks().get(i) + "\n";
        }
        mFavoriteBooks.setText(favorite);
        Toast.makeText(getActivity(), user.toString(), Toast.LENGTH_LONG);
    }

    //CREACION DE DIALOGOS

    public static class FavoriteBooksDialog extends DialogFragment{
        private Context context;
        private ListView list;
        private EditText newBook;
        private Button save;

        private ArrayList dataSet;
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            Bundle bundle = getArguments();
            if (bundle != null) {
                dataSet = getArguments().getStringArrayList("values");
            }
            else dataSet = null;

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            View view = LayoutInflater.from(context).inflate(R.layout.dialog_favorite_books, null);
            list = (ListView)view.findViewById(R.id.dialog_favorite_list);
            newBook = (EditText)view.findViewById(R.id.dialog_favorite_book_edit_text);
            save = (Button)view.findViewById(R.id.dialog_favorite_book_save);
            AdapterListDialog adapter = new AdapterListDialog(context, dataSet);
            list.setAdapter(adapter);
            builder.setView(view);

            builder.setPositiveButton("CHANGE", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Bundle bundle = new Bundle();
                    bundle.putStringArrayList("values", dataSet);
                    getFragmentManager().beginTransaction();
                }
            }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            return builder.create();
        }

        @Override
        public void onAttach(Context context) {
            super.onAttach(context);
            this.context = context;
        }

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
                ArrayList array = new ArrayList();
                array.add("hola");
                array.add("asdfas");
                array.add("holasfda");
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("values", array);

                FavoriteBooksDialog dialog = new FavoriteBooksDialog();
                dialog.setArguments(bundle);
                FragmentManager fm = new FragmentManager() {
                    @Override
                    public FragmentTransaction beginTransaction() {
                        return null;
                    }

                    @Override
                    public boolean executePendingTransactions() {
                        return false;
                    }

                    @Override
                    public android.app.Fragment findFragmentById(int id) {
                        return null;
                    }

                    @Override
                    public android.app.Fragment findFragmentByTag(String tag) {
                        return null;
                    }

                    @Override
                    public void popBackStack() {

                    }

                    @Override
                    public boolean popBackStackImmediate() {
                        return false;
                    }

                    @Override
                    public void popBackStack(String name, int flags) {

                    }

                    @Override
                    public boolean popBackStackImmediate(String name, int flags) {
                        return false;
                    }

                    @Override
                    public void popBackStack(int id, int flags) {

                    }

                    @Override
                    public boolean popBackStackImmediate(int id, int flags) {
                        return false;
                    }

                    @Override
                    public int getBackStackEntryCount() {
                        return 0;
                    }

                    @Override
                    public BackStackEntry getBackStackEntryAt(int index) {
                        return null;
                    }

                    @Override
                    public void addOnBackStackChangedListener(OnBackStackChangedListener listener) {

                    }

                    @Override
                    public void removeOnBackStackChangedListener(OnBackStackChangedListener listener) {

                    }

                    @Override
                    public void putFragment(Bundle bundle, String key, android.app.Fragment fragment) {

                    }

                    @Override
                    public android.app.Fragment getFragment(Bundle bundle, String key) {
                        return null;
                    }

                    @Override
                    public android.app.Fragment.SavedState saveFragmentInstanceState(android.app.Fragment f) {
                        return null;
                    }

                    @Override
                    public boolean isDestroyed() {
                        return false;
                    }

                    @Override
                    public void dump(String prefix, FileDescriptor fd, PrintWriter writer, String[] args) {

                    }
                };
                dialog.show(fm, "hola");
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

        getData();
    }

    public void getData(){
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
}
