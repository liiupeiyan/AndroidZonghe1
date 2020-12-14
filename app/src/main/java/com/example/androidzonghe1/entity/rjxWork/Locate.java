package com.example.androidzonghe1.entity.rjxWork;

public class Locate {
    private int id;
    private int userId;
    private String name;//地名
    private String relationship;
    private double latitude;
    private double longitude;
    private int img;

    public Locate(int id, int userId, String name, String relationship, double latitude, double longitude, int img) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.relationship = relationship;
        this.latitude = latitude;
        this.longitude = longitude;
        this.img = img;
    }

    public Locate() {
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getRelationship() {
        return relationship;
    }
    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public double getLatitude() {
        return latitude;
    }
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    public double getLongitude() {
        return longitude;
    }
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "Locate{" +
                "id=" + id +
                ", userId=" + userId +
                ", name='" + name + '\'' +
                ", relationship='" + relationship + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", img=" + img +
                '}';
    }
}
