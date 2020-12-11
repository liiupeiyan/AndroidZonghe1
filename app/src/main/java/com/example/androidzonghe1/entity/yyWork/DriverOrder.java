package com.example.androidzonghe1.entity.yyWork;

public class DriverOrder {
    private String from;//起点
    private String to;//终点
    private String driver;//司机
    private String time;//订单时间
    private String address;//入校/离校
    private String date; //日期
    private double price;//价格
    private String endTime;//结束时间
    private String state;

    public DriverOrder() {
    }

    public DriverOrder(String from, String to, String driver, String time, String address, String date, double price, String endTime, String state) {
        this.from = from;
        this.to = to;
        this.driver = driver;
        this.time = time;
        this.address = address;
        this.date = date;
        this.price = price;
        this.endTime = endTime;
        this.state = state;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "DriverOrder{" +
                "from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", driver='" + driver + '\'' +
                ", time='" + time + '\'' +
                ", address='" + address + '\'' +
                ", date='" + date + '\'' +
                ", price=" + price +
                ", endTime='" + endTime + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
