package com.iteso.library.beans;

import java.util.Date;

/**
 * Created by Maritza on 29/09/2017.
 */

public class NotificationFriendship extends Notification{
    private int idEmisor;
    private boolean status;

    public NotificationFriendship(String userName, int notificationType, Date date, int image,  int id) {
        super(userName, notificationType, date, image);
        this.idEmisor = id;
    }
}
