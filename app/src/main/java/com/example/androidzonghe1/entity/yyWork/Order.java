package com.example.androidzonghe1.entity.yyWork;

public class Order {
    private String orderName;
    private String time; //交易时间
    private double balance;//余额
    private double spend;//花费
    public Order() {
    }
    public Order(String orderName, String time, double balance, double spend) {
        this.orderName = orderName;
        this.time = time;
        this.balance = balance;
        this.spend = spend;
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

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getSpend() {
        return spend;
    }

    public void setSpend(double spend) {
        this.spend = spend;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderName='" + orderName + '\'' +
                ", time='" + time + '\'' +
                ", balance=" + balance +
                ", spend=" + spend +
                '}';
    }
}
