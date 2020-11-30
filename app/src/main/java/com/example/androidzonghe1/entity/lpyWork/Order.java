package com.example.androidzonghe1.entity.lpyWork;

public class Order {
    private String name;
    private String route;
    public Order(String name, String route){
        super();
        this.name = name;
    }
    public Order(){
        super();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    @Override
    public String toString() {
        return "Order{" +
                "name='" + name + '\'' +
                ", route='" + route + '\'' +
                '}';
    }
}
