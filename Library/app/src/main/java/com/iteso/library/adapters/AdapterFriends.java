package com.iteso.library.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.iteso.library.R;
import com.iteso.library.beans.Friend;

import java.util.ArrayList;

/**
 * Created by Maritza on 09/11/2017.
 */

public class AdapterFriends extends ArrayAdapter<Friend>{

    private ArrayList mDataSet;
    private Context context;

    public AdapterFriends(Context context, ArrayList data){
        super(context, R.layout.fragment_my_friends, data);
        mDataSet = data;
        this.context = context;
    }

    private static class ViewHolder {
        TextView txtName;
        TextView id;
        ImageView photo;
    }

    private int lastPosition = -1;

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Friend friend = getItem(position);
        ViewHolder viewHolder;

        final View result;
        if(convertView != null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_friend, parent, false);
            viewHolder.txtName = (TextView)convertView.findViewById(R.id.contact_list_item_1);
            viewHolder.id = (TextView)convertView.findViewById(R.id.contact_list_position);
            viewHolder.photo = (ImageView)convertView.findViewById(R.id.contact_list_image);

            result = convertView;
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        viewHolder.txtName.setText(friend.getName());
        viewHolder.id.setText(friend.getId());
        //Poner la imagen
        return convertView;
    }

    @Override
    public int getCount() {
        return mDataSet.size();
    }
}
