package com.example.androidzonghe1.entity.lpyWork;

public class Driver {
    private String name;
    private int phone;

    public Driver(String name, int phone) {
        this.name = name;
        this.phone = phone;
    }

    public Driver() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Driver{" +
                "name='" + name + '\'' +
                ", phone=" + phone +
                '}';
    }
}
