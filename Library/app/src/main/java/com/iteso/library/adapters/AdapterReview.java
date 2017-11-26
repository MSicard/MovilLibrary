package com.iteso.library.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.facebook.login.widget.ProfilePictureView;
import com.iteso.library.R;
import com.iteso.library.beans.Review;
import com.iteso.library.common.DownloadImage;

import java.util.ArrayList;
import java.util.Date;

import static com.iteso.library.common.Utils.getRoundedShape;
import static com.iteso.library.common.Utils.putTime;

/**
 * Created by Desarrollo on 26/10/2017.
 */

public class AdapterReview extends RecyclerView.Adapter<AdapterReview.ViewHolder> {
    private ArrayList<Review> mDataSet;
    private Context context;

    public AdapterReview(Context context, ArrayList<Review> myDataSet) {
        this.mDataSet = myDataSet;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_review, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.image.setProfileId(mDataSet.get(position).getId());
        holder.userName.setText(mDataSet.get(position).getNickname());
        holder.date.setText(putTime(new Date(mDataSet.get(position).getDate())));
        holder.rating.setRating(mDataSet.get(position).getRating());
        holder.userReview.setText(mDataSet.get(position).getReview());
    }

    @Override
    public int getItemCount() {
       return mDataSet.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ProfilePictureView image;
        private TextView userName;
        private TextView date;
        private RatingBar rating;
        private TextView userReview;

        public ViewHolder(View itemView) {
            super(itemView);

            image = (ProfilePictureView) itemView.findViewById(R.id.item_card_review_photo);
            userName = (TextView) itemView.findViewById(R.id.item_card_review_user_name);
            date = (TextView) itemView.findViewById(R.id.item_card_review_time);
            rating = (RatingBar) itemView.findViewById(R.id.item_card_review_rating);
            userReview = (TextView) itemView.findViewById(R.id.item_card_review_user_review);
        }
    }
}
