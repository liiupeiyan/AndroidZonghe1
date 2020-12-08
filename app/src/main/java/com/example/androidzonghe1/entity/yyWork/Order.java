package com.example.androidzonghe1.entity.yyWork;

public class Order {
    private String orderName;
    private String time; //交易时间
    private String balance;//余额
    private String spend;//花费
    private String dName;
    public Order() {
    }

    public Order(String orderName, String time, String balance, String spend, String dName) {
        this.orderName = orderName;
        this.time = time;
        this.balance = balance;
        this.spend = spend;
        this.dName = dName;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getSpend() {
        return spend;
    }

    public void setSpend(String spend) {
        this.spend = spend;
    }

    public String getdName() {
        return dName;
    }

    public void setdName(String dName) {
        this.dName = dName;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderName='" + orderName + '\'' +
                ", time='" + time + '\'' +
                ", balance='" + balance + '\'' +
                ", spend='" + spend + '\'' +
                ", dName='" + dName + '\'' +
                '}';
    }
}
