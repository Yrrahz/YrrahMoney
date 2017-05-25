package com.example.yrrah.yrrahmoney;

/**
 * Created by Yrrah on 2017-05-25.
 */

public class MonthModel {
    private int month;
    private int totalAmount;
    private String text;

    public MonthModel(int month, int totalAmount, String text){
        this.month = month;
        this.totalAmount = totalAmount;
        this.text = text;
    }

    public MonthModel(){}

    public void setMonth(int month){
        this.month = month;
    }

    public void setTotalAmount(int totalAmount){
        this.totalAmount = totalAmount;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getMonth() {
        return month;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public String getText() {
        return text;
    }
}
