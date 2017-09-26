package com.iteso.library.gui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iteso.library.R;
import com.iteso.library.beans.Book;
import com.iteso.library.beans.MyBook;

import java.util.ArrayList;

/**
 * Created by Maritza on 22/09/2017.
 */

public class FragmentMyBooks extends Fragment {

    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mlayoutManager;
    private static int numberOfColumns = 3;

    public FragmentMyBooks(){
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_books, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.fragment_my_books_recyclerview);
        recyclerView.setHasFixedSize(true);
        mlayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mlayoutManager);

        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumns));
        ArrayList<Book> myDataSet = new ArrayList<Book>();

        MyBook book = new MyBook("El nombre del viento", "Patrick", "asdfasfd");
        MyBook book1 = new MyBook("El nombre del viento", "Patrick", "asdfasfd");
        MyBook book2 = new MyBook("El nombre del viento", "Patrick", "asdfasfd");
        MyBook book3 = new MyBook("El nombre del viento", "Patrick", "asdfasfd");
        myDataSet.add(book);
        myDataSet.add(book1);
        myDataSet.add(book2);
        myDataSet.add(book3);

        mAdapter = new AdapterMyBook(getActivity(), myDataSet);
        recyclerView.setAdapter(mAdapter);
        return view;
    }
}
