package com.example.yrrah.yrrahmoney;

/**
 * Model of the monthTable in the database.
 *
 * Created by Yrrah on 2017-05-25.
 */

public class MonthModel {
    private int id;
    private int month;
    private int totalAmount;
    private String text;

    // Id is handled by the database and therefore not included in the constructor.
    public MonthModel(int month, int totalAmount, String text){
        this.month = month;
        this.totalAmount = totalAmount;
        this.text = text;
    }

    public MonthModel(){}

    public void setId(int id) {
        this.id = id;
    }

    public void setMonth(int month){
        this.month = month;
    }

    public void setTotalAmount(int totalAmount){
        this.totalAmount = totalAmount;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getId() {
        return id;
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