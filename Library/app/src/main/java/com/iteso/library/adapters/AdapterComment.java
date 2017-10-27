package com.iteso.library.adapters;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.iteso.library.R;
import com.iteso.library.beans.Comment;

import java.util.ArrayList;

import static com.iteso.library.common.Utils.getRoundedShape;
import static com.iteso.library.common.Utils.putTime;

/**
 * Created by Desarrollo on 26/10/2017.
 */

public class AdapterComment extends RecyclerView.Adapter<AdapterComment.ViewHolder>{
    private ArrayList<Comment> mDataSet;
    private Context context;

    public AdapterComment(Context context, ArrayList<Comment> myDataSet) {
        this.mDataSet = myDataSet;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_comment, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.image.setImageBitmap(getRoundedShape(BitmapFactory.decodeResource(context.getResources(), mDataSet.get(position).getImage())));
        holder.userName.setText(mDataSet.get(position).getUserName());
        holder.date.setText(putTime(mDataSet.get(position).getDate()));
        holder.userComment.setText(mDataSet.get(position).getUserComment());
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView image;
        private TextView userName;
        private TextView date;
        private TextView userComment;

        public ViewHolder(View itemView) {
            super(itemView);

            image = (ImageView) itemView.findViewById(R.id.item_card_comment_photo);
            userName = (TextView) itemView.findViewById(R.id.item_card_comment_user_name);
            date = (TextView) itemView.findViewById(R.id.item_card_comment_time);
            userComment = (TextView) itemView.findViewById(R.id.item_card_comment_user_comment);
        }
    }
}
