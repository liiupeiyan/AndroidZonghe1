package com.example.androidzonghe1.entity.lpyWork;

public class Message {
    private String Title;
    private String date;
    private String type;
    private String img;

    public Message(String title, String date, String type, String img) {
        Title = title;
        this.date = date;
        this.type = type;
        this.img = img;
    }

    public Message() {
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "Message{" +
                "Title='" + Title + '\'' +
                ", date='" + date + '\'' +
                ", type='" + type + '\'' +
                ", img='" + img + '\'' +
                '}';
    }
}
