package com.example.demo;

public class Application {
    private String name;
    private int profit;


    public Application() { }

    public Application(String name, int profit){
        this.name=name;
        this.profit=profit;
    }

    public String getName(){
        return name;
    }
    public int getProfit(){
        return profit;
    }

}
