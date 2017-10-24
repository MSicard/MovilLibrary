package com.iteso.library.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iteso.library.R;
import com.iteso.library.beans.Book;

import java.util.ArrayList;

/**
 * Created by Desarrollo on 23/10/2017.
 */

public class AdapterFeaturedBook extends RecyclerView.Adapter<AdapterFeaturedBook.ViewHolder>{
    private ArrayList<Book> mDataSet;
    private Context context;

    public AdapterFeaturedBook(Context context, ArrayList<Book> myDataSet) {
        this.mDataSet = myDataSet;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_featured_books, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.image.setImageResource(R.drawable.yorobot);  // Cambiar por imagen de BD
        holder.title.setText(mDataSet.get(position).getTitle());
        holder.author.setText(mDataSet.get(position).getAutor());
        holder.rating.setRating((float) 4.5);               // Cambiar por el de la BD
        holder.synapsis.setText(mDataSet.get(position).getSynapsis());

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
        public TextView author;
        public RatingBar rating;
        public TextView synapsis;


        public ViewHolder(View itemView) {
            super(itemView);

            card = (RelativeLayout) itemView.findViewById(R.id.activity_home_featured_book_card);
            image = (ImageView) itemView.findViewById(R.id.activity_home_featured_book_image);
            title = (TextView) itemView.findViewById(R.id.activity_home_featured_book_title);
            author = (TextView) itemView.findViewById(R.id.activity_home_featured_book_author);
            rating = (RatingBar) itemView.findViewById(R.id.activity_home_featured_book_rating);
            synapsis = (TextView) itemView.findViewById(R.id.activity_home_featured_book_synapsis);
        }
    }
}
