package com.iteso.library.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.iteso.library.R;
import com.iteso.library.beans.Notification;
import com.iteso.library.beans.Publication;

import org.w3c.dom.Text;

import java.util.ArrayList;

import static com.iteso.library.common.Utils.getRoundedShape;
import static com.iteso.library.common.Utils.putTime;

/**
 * Created by Maritza on 18/10/2017.
 */

public class AdapterPublication extends RecyclerView.Adapter<AdapterPublication.ViewHolder>{
    private ArrayList<Publication> mDataSet;
    private Context context;

    public AdapterPublication(Context context, ArrayList mDataSet){
        this.context = context;
        this.mDataSet = mDataSet;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_publication, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.name.setText(mDataSet.get(position).getUserName());
        holder.comment.setText(mDataSet.get(position).getComment());
        holder.time.setText(putTime(mDataSet.get(position).getDate()));
        Bitmap photoA = BitmapFactory.decodeResource(context.getResources(),
                mDataSet.get(position).getPhotoUser());
        holder.photo.setImageBitmap(getRoundedShape(photoA));
        holder.countComments.setText(String(mDataSet.get(position).getCountComments()));
        holder.countLikes.setText(mDataSet.get(position).getCountLikes());
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        protected TextView name;
        protected TextView comment;
        protected TextView time;
        protected ImageView photo;
        protected ImageView publicationComment;
        protected ImageView like;
        protected TextView countLikes;
        protected TextView countComments;
        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView)itemView.findViewById(R.id.item_card_publication_name);
            comment = (TextView)itemView.findViewById(R.id.item_card_publication_comment);
            time = (TextView)itemView.findViewById(R.id.item_card_publication_time);
            photo = (ImageView)itemView.findViewById(R.id.item_card_publication_photo);
            publicationComment = (ImageView)itemView.findViewById(R.id.item_card_publication_button_comments);
            like = (ImageView)itemView.findViewById(R.id.item_card_publication_like);
            countLikes = (TextView)itemView.findViewById(R.id.item_card_publication_number_likes);
            countComments = (TextView)itemView.findViewById(R.id.item_card_publication_number_comments);

            //poner función de los botones publicationComment y like
        }
    }
}
