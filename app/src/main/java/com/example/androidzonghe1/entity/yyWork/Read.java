package com.example.androidzonghe1.entity.yyWork;

public class Read {
    private int resId;
    private String title;
    private String time;

    public Read() {
    }

    public Read(int resId, String title, String time) {
        this.resId = resId;
        this.title = title;
        this.time = time;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
