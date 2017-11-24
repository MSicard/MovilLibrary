package com.iteso.library.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iteso.library.R;
import com.iteso.library.beans.Book;
import com.iteso.library.gui.ActivityBookDetail;
import com.iteso.library.gui.ActivityHome;
import com.iteso.library.common.DownloadImage;

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_categorized_book, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        DownloadImage downloadImage = new DownloadImage(holder.image, mDataSet.get(position).getImage());
        downloadImage.execute();
        holder.title.setText(mDataSet.get(position).getTitle());

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ActivityBookDetail.class);
                intent.putExtra("book", mDataSet.get(position).getIsbn());
                ((ActivityHome)view.getContext()).startActivity(intent);
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
