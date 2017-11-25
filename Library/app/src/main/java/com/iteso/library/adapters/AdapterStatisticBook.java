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
 * Created by Desarrollo on 26/10/2017.
 */

public class AdapterStatisticBook extends RecyclerView.Adapter<AdapterStatisticBook.ViewHolder> {
    private ArrayList<Book> mDataSet;
    private Context context;

    public AdapterStatisticBook(Context context, ArrayList<Book> myDataSet) {
        this.mDataSet = myDataSet;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_statistics_book, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.image.setImageResource(R.drawable.yorobot);  // Cambiar por imagen de BD
        holder.title.setText(mDataSet.get(position).getTitle());
        holder.author.setText(mDataSet.get(position).getAuthor());
        holder.rating.setRating((float) 4.5);               // Cambiar por el de la BD
        holder.synapsis.setText(mDataSet.get(position).getSynopsis());
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

            card = (RelativeLayout) itemView.findViewById(R.id.item_card_statistics_book_card);
            image = (ImageView) itemView.findViewById(R.id.item_card_statistics_book_image);
            title = (TextView) itemView.findViewById(R.id.item_card_statistics_book_title);
            author = (TextView) itemView.findViewById(R.id.item_card_statistics_book_author);
            rating = (RatingBar) itemView.findViewById(R.id.item_card_statistics_book_rating);
            synapsis = (TextView) itemView.findViewById(R.id.item_card_statistics_book_synapsis);
        }
    }
}
