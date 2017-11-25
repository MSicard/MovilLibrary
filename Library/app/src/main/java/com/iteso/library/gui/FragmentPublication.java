package com.iteso.library.gui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.facebook.login.widget.ProfilePictureView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.iteso.library.R;
import com.iteso.library.adapters.AdapterPublication;
import com.iteso.library.beans.Publication;
import com.iteso.library.common.Constants;
import com.iteso.library.common.Utils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;


/**
 * Created by Maritza on 18/10/2017.
 */

public class FragmentPublication extends Fragment {

    private AdapterPublication mAdapter;
    private RecyclerView.LayoutManager mlayoutManager;
    private Button publish;
    private EditText comment;
    private ProfilePictureView photo;
    private  ArrayList mDataSet;
    private String id;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_publications, container, false);
        publish = (Button)view.findViewById(R.id.fragment_publication_publish);
        comment = (EditText)view.findViewById(R.id.fragment_publication_comment);
        photo = (ProfilePictureView) view.findViewById(R.id.fragment_publication_photo);

        publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ActivityBase)getActivity()).closeSoftKeyBoard();
               if(!comment.getText().toString().equals("")){
                   Publication publication = new Publication(0, 0, comment.getText().toString(), new Timestamp(System.currentTimeMillis()).getTime());
                   DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_USER)
                           .child(id).child(Constants.FIREBASE_USER_PUBLICATION).child(Constants.FIREBASE_USER_PUBLICATION_INFO);
                   publication.setId(mDatabaseReference.push().getKey());
                   mDatabaseReference.child(publication.getId()).setValue(publication);
                   comment.setText("");
                }
            }
        });

        RecyclerView mRecyclerView = (RecyclerView)view.findViewById(R.id.fragment_publications_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mlayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mlayoutManager);

        mDataSet = new ArrayList();
        mAdapter = new AdapterPublication(getActivity(), mDataSet);
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        id = getActivity().getIntent().getStringExtra("ID");
        photo.setProfileId(id);
        mAdapter.setId(id);
        getData();
    }

    public void getData(){
        DatabaseReference mDataReference = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_USER)
                .child(id).child(Constants.FIREBASE_USER_PUBLICATION).child(Constants.FIREBASE_USER_PUBLICATION_INFO);
        mDataReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot != null) mDataSet.clear();
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    Publication publication = data.getValue(Publication.class);
                    mDataSet.add(publication);
                }
                Collections.sort(mDataSet);
                mAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
