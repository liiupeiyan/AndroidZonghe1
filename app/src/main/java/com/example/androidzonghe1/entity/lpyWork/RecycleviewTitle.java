package com.example.androidzonghe1.entity.lpyWork;

public class RecycleviewTitle {
    private String title;

    public RecycleviewTitle(String title) {
        this.title = title;
    }
    public RecycleviewTitle() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "RecycleviewTitle{" +
                "title='" + title + '\'' +
                '}';
    }
}
