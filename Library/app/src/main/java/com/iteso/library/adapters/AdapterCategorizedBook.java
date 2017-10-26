package com.iteso.library.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iteso.library.R;
import com.iteso.library.beans.Book;

import java.util.ArrayList;

/**
 * Created by Desarrollo on 23/10/2017.
 */

public class AdapterCategorizedBook extends RecyclerView.Adapter<AdapterCategorizedBook.ViewHolder>{
    private ArrayList<Book> mDataSet;
    private Context context;

    public AdapterCategorizedBook(Context context, ArrayList<Book> myDataSet) {
        this.mDataSet = myDataSet;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_categorized_books, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.image.setImageResource(R.drawable.nombre_del_viento);    // Cambiar por el de BD
        holder.title.setText(mDataSet.get(position).getTitle());

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Enviar a BookDetail
            }
        });

    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        public RelativeLayout card;
        public ImageView image;
        public TextView title;

        public ViewHolder(View itemView) {
            super(itemView);

            card = (RelativeLayout) itemView.findViewById(R.id.activity_home_categorized_book_card);
            image = (ImageView) itemView.findViewById(R.id.activity_home_categorized_book_image);
            title = (TextView) itemView.findViewById(R.id.activity_home_categorized_book_title);
        }
    }
}
