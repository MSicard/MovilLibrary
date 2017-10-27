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

import com.iteso.library.R;
import com.iteso.library.adapters.AdapterPublication;
import com.iteso.library.beans.Publication;
import com.iteso.library.common.Utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;


/**
 * Created by Maritza on 18/10/2017.
 */

public class FragmentPublication extends Fragment {

    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mlayoutManager;
    private Button publish;
    private EditText comment;
    private ImageView photo;
    private  ArrayList mDataSet;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_publications, container, false);
        publish = (Button)view.findViewById(R.id.fragment_publication_publish);
        comment = (EditText)view.findViewById(R.id.fragment_publication_comment);
        photo = (ImageView)view.findViewById(R.id.fragment_publication_photo);

        publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ActivityBase)getActivity()).closeSoftKeyBoard();
                if(!comment.getText().toString().equals("")){
                    mDataSet.add(new Publication("Einstein", comment.getText().toString(),
                            R.drawable.profile, new Date(), 0, 0));
                    Collections.sort(mDataSet);
                    mAdapter.notifyDataSetChanged();
                    comment.setText("");
                }
            }
        });

        Bitmap photoA = BitmapFactory.decodeResource(getActivity().getResources(),
                R.drawable.profile);
        photo.setImageBitmap(Utils.getRoundedShape(photoA));

        RecyclerView mRecyclerView = (RecyclerView)view.findViewById(R.id.fragment_publications_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mlayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mlayoutManager);

        mDataSet = new ArrayList();

        mDataSet.add(new Publication("Einstein",
                "Existen dos formas de ver la vida: una es creyendo que no existen los milagros, " +
                        "la otra es creyendo que todo es un milagro",
                R.drawable.profile,
                new Date(2017, 10, 18, 10, 52, 00), 3, 1));

        mDataSet.add(new Publication("Einstein",
                "Somos arquitectos de nuestro propio destino",
                R.drawable.profile,
                new Date(2017, 10, 14), 1, 10));

        mDataSet.add(new Publication("Einstein",
                "Todos somos muy ignorantes, lo que ocurre es que no todos ignoramos las mismas cosas",
                R.drawable.profile,
                new Date(2017, 10, 10), 2, 4));
        Collections.sort(mDataSet);
        mAdapter = new AdapterPublication(getActivity(), mDataSet);
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }

}
