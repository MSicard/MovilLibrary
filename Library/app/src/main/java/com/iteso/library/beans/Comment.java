package com.iteso.library.beans;

import java.util.Date;

/**
 * Created by Desarrollo on 26/10/2017.
 */

public class Comment {
   private String id;
   private String message;
   private String nickname;
   private long time;
   private String idComment;

    public String getIdComment() {
        return idComment;
    }

    public void setIdComment(String idComment) {
        this.idComment = idComment;
    }

    public Comment() {
    }

    public Comment(String id, String message, String nickname, long time) {
        this.id = id;
        this.message = message;
        this.nickname = nickname;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
