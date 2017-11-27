package com.iteso.library.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.login.widget.ProfilePictureView;
import com.iteso.library.R;
import com.iteso.library.beans.Friend;
import com.iteso.library.gui.ActivityProfile;
import com.iteso.library.gui.ActivityProfileFriend;

import java.util.ArrayList;

/**
 * Created by Maritza on 09/11/2017.
 */

public class AdapterFriends extends RecyclerView.Adapter<AdapterFriends.ViewHolder>{

    private ArrayList<Friend> mDataSet;
    private Context context;

    public AdapterFriends(Context context, ArrayList<Friend> data){
        this.mDataSet = data;
        this.context = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_friend, parent, false);
        AdapterFriends.ViewHolder vh = new AdapterFriends.ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.txtName.setText(mDataSet.get(position).getName());
        holder.photo.setProfileId(mDataSet.get(position).getId());
        holder.id.setText(mDataSet.get(position).getId());
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ActivityProfileFriend.class);
                intent.putExtra("ID", mDataSet.get(position).getId());
                try{
                    ((ActivityProfile) v.getContext()).startActivity(intent);
                }catch (Exception e){
                    ((ActivityProfileFriend) v.getContext()).startActivity(intent);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return mDataSet.size();
    }




    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtName;
        TextView id;
        ProfilePictureView photo;
        CardView card;

        public ViewHolder(View itemView) {
            super(itemView);
            txtName = (TextView)itemView.findViewById(R.id.contact_list_item_1);
            id = (TextView)itemView.findViewById(R.id.contact_list_position);
            photo = (ProfilePictureView) itemView.findViewById(R.id.contact_list_image);
            card = (CardView)itemView.findViewById(R.id.contact_list_card);

        }


    }

}
