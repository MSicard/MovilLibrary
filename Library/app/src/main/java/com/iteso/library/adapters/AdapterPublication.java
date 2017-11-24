package com.iteso.library.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.Profile;
import com.facebook.login.widget.ProfilePictureView;
import com.iteso.library.R;
import com.iteso.library.beans.Notification;
import com.iteso.library.beans.Publication;
import com.iteso.library.common.Utils;
import com.iteso.library.gui.ActivityComments;
import com.iteso.library.gui.ActivityProfile;

import org.w3c.dom.Text;

import java.sql.Timestamp;
import java.util.ArrayList;

import static com.iteso.library.common.Utils.getRoundedShape;
import static com.iteso.library.common.Utils.putTime;

/**
 * Created by Maritza on 18/10/2017.
 */

public class AdapterPublication extends RecyclerView.Adapter<AdapterPublication.ViewHolder>{
    private ArrayList<Publication> mDataSet;
    private Context context;
    private String id;
    public AdapterPublication(Context context, ArrayList mDataSet){
        this.context = context;
        this.mDataSet = mDataSet;
    }

    public void setId(String id){
        this.id = id;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_publication, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.name.setText(Utils.getUserName(id));
        holder.comment.setText(mDataSet.get(position).getMessage());
        holder.countComments.setText(String.valueOf(mDataSet.get(position).getComments()));
        holder.countLikes.setText(String.valueOf(mDataSet.get(position).getLikes()));
        holder.photo.setProfileId(id);
        //Falta poner el tiempo :)
        //holder.time.setText(putTime(mDataSet.get(position).getDate()));
        //holder.photo.setProfileId(Profile.getCurrentProfile());

        holder.publicationComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ActivityComments.class);
                ((ActivityProfile)view.getContext()).startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        protected LinearLayout card;
        protected TextView name;
        protected TextView comment;
        protected TextView time;
        protected ProfilePictureView photo;
        protected ImageView publicationComment;
        protected ImageView like;
        protected TextView countLikes;
        protected TextView countComments;
        public ViewHolder(View itemView) {
            super(itemView);
            card = (LinearLayout) itemView.findViewById(R.id.item_card_publication_card);
            name = (TextView)itemView.findViewById(R.id.item_card_publication_name);
            comment = (TextView)itemView.findViewById(R.id.item_card_publication_comment);
            time = (TextView)itemView.findViewById(R.id.item_card_publication_time);
            photo = (ProfilePictureView) itemView.findViewById(R.id.item_card_publication_photo);
            publicationComment = (ImageView)itemView.findViewById(R.id.item_card_publication_button_comments);
            like = (ImageView)itemView.findViewById(R.id.item_card_publication_like);
            countLikes = (TextView)itemView.findViewById(R.id.item_card_publication_number_likes);
            countComments = (TextView)itemView.findViewById(R.id.item_card_publication_number_comments);

            //poner función de los botones publicationComment y like
        }
    }

}
