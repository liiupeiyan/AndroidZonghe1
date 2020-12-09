package com.example.androidzonghe1.entity.rjxWork;

public class History {
    private int id;
    private String userPhone;
    private String city;
    private String key;
    private double latitude;
    private double longitude;
    private int count;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getUserPhone() {
        return userPhone;
    }
    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public History(int id, String userPhone, String city, String key, double latitude, double longitude, int count) {
        this.id = id;
        this.userPhone = userPhone;
        this.city = city;
        this.key = key;
        this.latitude = latitude;
        this.longitude = longitude;
        this.count = count;
    }

    public History() {
    }

    @Override
    public String toString() {
        return "History{" +
                "id=" + id +
                ", userPhone='" + userPhone + '\'' +
                ", city='" + city + '\'' +
                ", key='" + key + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", count=" + count +
                '}';
    }
}
