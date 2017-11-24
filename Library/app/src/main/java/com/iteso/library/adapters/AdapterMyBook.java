package com.iteso.library.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.iteso.library.R;
import com.iteso.library.beans.Book;
import com.iteso.library.common.DownloadImage;
import com.iteso.library.gui.ActivityMyBookDetail;

import java.util.ArrayList;

/**
 * Created by Maritza on 22/09/2017.
 */

public class AdapterMyBook extends RecyclerView.Adapter<AdapterMyBook.ViewHolder> implements View.OnClickListener{

    private ArrayList<Book> mDataSet;
    private Context context;

    public AdapterMyBook(Context context, ArrayList<Book> myDataSet) {
        this.mDataSet = myDataSet;
        this.context = context;
    }


    @Override
    public AdapterMyBook.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_book, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(AdapterMyBook.ViewHolder holder, int position) {
        holder.title.setText(mDataSet.get(position).getTitle());
        holder.autor.setText(mDataSet.get(position).getAuthor());
        new DownloadImage(holder.image, mDataSet.get(position).getImage()).execute();
        holder.image.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(context, ActivityMyBookDetail.class);
        context.startActivity(intent);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView title;
        public TextView autor;
        public ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.fragment_my_books_card_book_title);
            autor = (TextView) itemView.findViewById(R.id.fragment_my_books_card_book_autor);
            image = (ImageView) itemView.findViewById(R.id.fragment_my_books_card_book_image);
        }
    }
}
