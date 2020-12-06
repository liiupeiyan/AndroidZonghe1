package com.example.androidzonghe1.entity.lpyWork;

public class Driver {
    private int id;
    private String name;
    private String phone;
    private int age;
    private String car;
    private String style;
    private String experience;
    private int img;
    private String status;

    public Driver(int id, String name, String phone, int age, String car, String style, String experience, int img, String status) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.age = age;
        this.car = car;
        this.style = style;
        this.experience = experience;
        this.img = img;
        this.status = status;
    }

    public Driver() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getCar() {
        return car;
    }

    public void setCar(String car) {
        this.car = car;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Driver{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone=" + phone +
                ", age=" + age +
                ", car='" + car + '\'' +
                ", style='" + style + '\'' +
                ", experience='" + experience + '\'' +
                ", img='" + img + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
