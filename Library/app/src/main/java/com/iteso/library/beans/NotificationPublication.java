package com.iteso.library.beans;

import java.util.Date;

/**
 * Created by Maritza on 29/09/2017.
 */

public class NotificationPublication extends Notification {
    private int idPublication;

    public NotificationPublication(String userName, int notificationType, Date date,int image,  int id) {
        super(userName, notificationType, date, image);
        this.idPublication = id;
    }
}
