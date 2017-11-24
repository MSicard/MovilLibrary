package com.iteso.library.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.Profile;
import com.facebook.login.widget.ProfilePictureView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.iteso.library.R;
import com.iteso.library.beans.Notification;
import com.iteso.library.beans.Publication;
import com.iteso.library.common.Constants;
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
    String name;
    String idPub;
    boolean likeButton;

    private DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_USER)
            .child(id);

    public AdapterPublication(Context context, ArrayList mDataSet){
        this.context = context;
        this.mDataSet = mDataSet;
        this.name = "";
    }

    public void setId(String id){
        this.id = id;
        getUserName();
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_publication, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.name.setText(name);
        holder.comment.setText(mDataSet.get(position).getMessage());
        holder.countComments.setText(String.valueOf(mDataSet.get(position).getComments()));
        holder.countLikes.setText(String.valueOf(mDataSet.get(position).getLikes()));
        holder.photo.setProfileId(id);
        idPub = mDataSet.get(position).getId();
        holder.idPublication.setText(idPub);
        getColorLikeButton();
        if(likeButton) holder.like.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPurple));
        else holder.like.setBackgroundColor(ContextCompat.getColor(context, R.color.colorGray));
        //Falta poner el tiempo :)
        //holder.time.setText(putTime(mDataSet.get(position).getDate()));

        holder.publicationComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ActivityComments.class);
                ((ActivityProfile)view.getContext()).startActivity(intent);
            }
        });

        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getColorLikeButton();
                DatabaseReference mRef = mDatabaseReference.child(Constants.FIREBASE_USER_PUBLICATION_LIKE)
                        .child(idPub).child(id);
                if(likeButton){
                    mRef.removeValue();
                    holder.like.setBackgroundColor(ContextCompat.getColor(context, R.color.colorGray));
                }
                else{
                    mRef.setValue(true);
                    holder.like.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPurple));
                }

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
        protected TextView idPublication;
        public ViewHolder(View itemView) {
            super(itemView);
            idPublication = (TextView)itemView.findViewById(R.id.item_card_publication_card_id);
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

    public void getUserName(){
        DatabaseReference data = mDatabaseReference.child(Constants.FIREBASE_USER_INFO).child(Constants.FIREBASE_USER_NICKNAME);
        data.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                name = dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.v("Error", "Error al encontrar el nombre de usuario por id");
            }
        });
    }

    public void getColorLikeButton(){
        Query dataRef = mDatabaseReference.child(Constants.FIREBASE_USER_PUBLICATION_LIKE)
                .child(idPub).equalTo(id);
        dataRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot == null){
                    likeButton = false;
                }
                else{
                    likeButton = true;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


}
