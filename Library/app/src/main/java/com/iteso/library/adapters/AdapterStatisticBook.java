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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.iteso.library.R;
import com.iteso.library.beans.Book;
import com.iteso.library.common.Constants;
import com.iteso.library.common.DownloadImage;

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
<<<<<<< HEAD
=======

    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.title.setText(mDataSet.get(position).getTitle());
        holder.author.setText(mDataSet.get(position).getAuthor());
        new DownloadImage(holder.image, mDataSet.get(position).getImage()).execute();
        holder.rating.setRating(mDataSet.get(position).getRating());               // Cambiar por el de la BD
        holder.synapsis.setText(mDataSet.get(position).getSynopsis());

>>>>>>> 298b4db4ac3de7825bffa6640c2c3146812ae79a
    public void onBindViewHolder(final ViewHolder holder, int position) {
        DatabaseReference mDataReference = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_BOOKS)
                .child(mDataSet.get(position).getIsbn());

        mDataReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Book book = dataSnapshot.getValue(Book.class);
                holder.rating.setRating(book.getRating());
                holder.synapsis.setText(book.getSynopsis());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
<<<<<<< HEAD


        DownloadImage downloadImage = new DownloadImage(holder.image, mDataSet.get(position).getImage());
        downloadImage.execute();
        holder.title.setText(mDataSet.get(position).getTitle());
        holder.author.setText(mDataSet.get(position).getAuthor());
=======
>>>>>>> 298b4db4ac3de7825bffa6640c2c3146812ae79a
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
