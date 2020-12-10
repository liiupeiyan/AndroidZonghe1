package com.example.androidzonghe1.entity.yyWork;

import android.graphics.Bitmap;

public class Commit {
    private int id;
    private int star;
    private String userName;
    private String userImage;
    private String time;
    private String driver;
    private String orderName;
    private String content;
    private String contentImage;

    public Commit() {
    }

    public Commit(int id, int star, String userName, String userImage, String time, String driver, String orderName, String content, String contentImage) {
        this.id = id;
        this.star = star;
        this.userName = userName;
        this.userImage = userImage;
        this.time = time;
        this.driver = driver;
        this.orderName = orderName;
        this.content = content;
        this.contentImage = contentImage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public String getContentImage() {
        return contentImage;
    }

    public void setContentImage(String contentImage) {
        this.contentImage = contentImage;
    }

    @Override
    public String toString() {
        return "Commit{" +
                "id=" + id +
                ", star=" + star +
                ", userName='" + userName + '\'' +
                ", userImage='" + userImage + '\'' +
                ", time='" + time + '\'' +
                ", driver='" + driver + '\'' +
                ", orderName='" + orderName + '\'' +
                ", content='" + content + '\'' +
                ", contentImage=" + contentImage +
                '}';
    }
}
