package com.iteso.library.gui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.iteso.library.R;
import com.iteso.library.beans.Notification;
import com.iteso.library.common.Constants;
import com.iteso.library.common.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Maritza on 29/09/2017.
 */

public class AdapterNotification extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private ArrayList<Notification> mDataSet;
    private Context context;

    public AdapterNotification(Context context, ArrayList list){
        this.mDataSet = list;
        this.context = context;
    }


    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch(viewType){
            case 2:
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification_friendship, parent, false);
                return new NotificationFriendshipViewHolder(view);
            default:
                View view2 = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification_comment, parent, false);
                return new NotificationPublicationViewHolder(view2);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (mDataSet.get(position).getNotificationType()){
            case 1:
                String mess = mDataSet.get(position).getUserName() + Constants.NOTIFICATION_PUBLICATION_MESSAGE;
                ((NotificationPublicationViewHolder)holder).message.setText(mess);
                Bitmap photoA = BitmapFactory.decodeResource(context.getResources(),
                        mDataSet.get(position).getImage());
                ((NotificationPublicationViewHolder)holder).profileImage.setImageBitmap(Utils.getRoundedShape(photoA));
                ((NotificationPublicationViewHolder)holder).date.setText(putTime(mDataSet.get(position).getDate()));
                break;
            case 2:
                String messa = mDataSet.get(position).getUserName() + Constants.NOTIFICATION_FRIENDSHIP_SEND_MESSAGE;
                ((NotificationFriendshipViewHolder)holder).message.setText(messa);

                Bitmap photoB = BitmapFactory.decodeResource(context.getResources(),
                        mDataSet.get(position).getImage());
                ((NotificationFriendshipViewHolder)holder).profileImage.setImageBitmap(Utils.getRoundedShape(photoB));
                ((NotificationFriendshipViewHolder)holder).date.setText(putTime(mDataSet.get(position).getDate()));
                break;
            case 3:

                String msg = mDataSet.get(position).getUserName() + Constants.NOTIFICATION_PUBLICATION_MESSAGE;
                ((NotificationPublicationViewHolder)holder).message.setText(msg);
                Bitmap photo = BitmapFactory.decodeResource(context.getResources(),
                        mDataSet.get(position).getImage());

                ((NotificationPublicationViewHolder)holder).profileImage.setImageBitmap(Utils.getRoundedShape(photo));
                ((NotificationPublicationViewHolder)holder).date.setText(putTime(mDataSet.get(position).getDate()));
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return mDataSet.get(position).getNotificationType();
    }

    public class NotificationPublicationViewHolder extends RecyclerView.ViewHolder{

        public ImageView profileImage;
        public TextView message;
        public TextView date;


        public NotificationPublicationViewHolder(View itemView) {
            super(itemView);
            profileImage = (ImageView) itemView.findViewById(R.id.fragment_my_books_notification_image);
            message = (TextView)itemView.findViewById(R.id.fragment_my_books_notification_message);
            date = (TextView)itemView.findViewById(R.id.fragment_my_books_notification_date);
        }
    }

    public class NotificationFriendshipViewHolder extends RecyclerView.ViewHolder{
        public ImageView profileImage;
        public TextView message;
        public TextView date;

        public NotificationFriendshipViewHolder(View itemView) {
            super(itemView);
            profileImage = (ImageView) itemView.findViewById(R.id.fragment_my_books_notification_image);
            message = (TextView)itemView.findViewById(R.id.fragment_my_books_notification_message);
            date = (TextView)itemView.findViewById(R.id.fragment_my_books_notification_date);
        }

        public void setData(Notification item){

        }
    }

    private String putTime(Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(date.getYear(), date.getMonth(), date.getDay(), date.getHours(), date.getMinutes());
        Calendar c2 = Calendar.getInstance();
        c2.getTime();

        String pattern = "dd MMMMMM HH:MM";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        if(c2.get(Calendar.YEAR) - c.get(Calendar.YEAR) < 1){
            Log.v("year", Integer.toString(c2.get(Calendar.MONTH) - c.get(Calendar.MONTH)));
            if(c2.get(Calendar.MONTH) - c.get(Calendar.MONTH) < 1){
                if(c2.get(Calendar.DAY_OF_MONTH) - c.get(Calendar.DAY_OF_MONTH)< 1){
                    if(c2.get(Calendar.HOUR) - c.get(Calendar.HOUR) < 1){
                        String minutos = "Hace " + Integer.toString(c2.get(Calendar.MINUTE) - c.get(Calendar.MINUTE)) + " minutos";
                        return minutos;
                    }
                    else{
                        String horas = "Hace " + Integer.toString(c.get(Calendar.HOUR)) + " horas";
                        return horas;
                    }
                }else{
                    return simpleDateFormat.format(date);
                }
            }
            else{
                return simpleDateFormat.format(date);
            }
        }else{
            return simpleDateFormat.format(date);
        }
    }
}
