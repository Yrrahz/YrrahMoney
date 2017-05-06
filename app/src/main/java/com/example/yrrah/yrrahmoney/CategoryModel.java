package com.example.yrrah.yrrahmoney;

/**
 * Created by Yrrah on 2017-05-05.
 */

public class CategoryModel {
    private String name;
    private int totalAmount;

    public CategoryModel(String name, int totalAmount){
        this.name = name;
        this.totalAmount = totalAmount;
    }

    public CategoryModel(){

    }

    public void setName(String name){
        this.name = name;
    }

    public void setTotalAmount(int totalAmount){
        this.totalAmount = totalAmount;
    }

    public String getName(){
        return name;
    }

    public int getTotalAmount() {
        return totalAmount;
    }
}
