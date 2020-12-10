package com.example.androidzonghe1.entity.yyWork;

public class Order {
    private int id;
    private String orderName;
    private String time; //交易时间
    private String balance;//余额
    private String spend;//花费
    private String dName;//司机名称
    private String endTime;//结束时间
    private String status; //订单状态
    public Order() {
    }

    public Order(String orderName, String time, String balance, String spend, String dName, String endTime, String status,int id) {
        this.orderName = orderName;
        this.time = time;
        this.balance = balance;
        this.spend = spend;
        this.dName = dName;
        this.endTime = endTime;
        this.status = status;
        this.id = id;
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

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderName='" + orderName + '\'' +
                ", time='" + time + '\'' +
                ", balance='" + balance + '\'' +
                ", spend='" + spend + '\'' +
                ", dName='" + dName + '\'' +
                ", endTime='" + endTime + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
