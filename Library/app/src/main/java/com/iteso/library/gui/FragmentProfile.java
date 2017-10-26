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

import com.iteso.library.R;
import com.iteso.library.adapters.AdapterListDialog;
import com.iteso.library.common.Utils;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Maritza on 04/10/2017.
 */

public class FragmentProfile extends Fragment implements OnClickListener{

    protected TextView mName;
    protected TextView mEmail;
    protected TextView mFavoriteBooks;
    protected TextView mAboutMe;
    protected ImageView mPhoto;
    protected ImageView mNameB;
    protected ImageView mEmailB;
    protected ImageView mFavoriteBooksB;
    protected ImageView mAboutMeB;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        mName = (TextView)view.findViewById(R.id.fragment_profile_name);
        mEmail = (TextView)view.findViewById(R.id.fragment_profile_email);
        mFavoriteBooks = (TextView)view.findViewById(R.id.fragment_profile_favorite_books);
        mAboutMe = (TextView)view.findViewById(R.id.fragment_profile_about);
        mPhoto = (ImageView) view.findViewById(R.id.fragment_profile_image_profile);
        mNameB = (ImageView)view.findViewById(R.id.fragment_profile_change_name);
        mEmailB = (ImageView)view.findViewById(R.id.fragment_profile_change_email);
        mFavoriteBooksB = (ImageView) view.findViewById(R.id.fragment_profile_change_favorite_books);
        mAboutMeB = (ImageView)view.findViewById(R.id.fragment_profile_change_about);

        Bitmap photoA = BitmapFactory.decodeResource(getActivity().getResources(),
                R.drawable.profile);
        mPhoto.setImageBitmap(Utils.getRoundedShape(photoA));

        mNameB.setOnClickListener(this);
        mEmailB.setOnClickListener(this);
        mFavoriteBooksB.setOnClickListener(this);
        mAboutMeB.setOnClickListener(this);
        return view;
    }

    //CREACION DE DIALOGOS
    public void createDialogEmail(final TextView tv){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View v = inflater.inflate(R.layout.dialog_profile_change, null);
        builder.setView(v);

        final EditText editText = (EditText)v.findViewById(R.id.dialog_profile_change);
        editText.setHint(tv.getText().toString());
        final TextView textView = (TextView)v.findViewById(R.id.dialog_profile_change_text);
        textView.setText("New Email");

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
            case R.id.fragment_profile_change_email:
                createDialogEmail(mEmail);
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

}
