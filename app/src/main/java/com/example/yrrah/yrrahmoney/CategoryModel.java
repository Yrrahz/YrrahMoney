package com.example.yrrah.yrrahmoney;

/**
 *  This is a model of one table in the database. This model is used to properly implement
 *  data into the database using MVC.
 * Created by Yrrah on 2017-05-05.
 */

public class CategoryModel {
    private String name;
    private int totalAmount;
    private int date;

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

    public void setDate(int date){
        this.date = date;
    }

    public String getName(){
        return name;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public int getDate(){
        return date;
    }

    @Override
    public boolean equals(Object object)
    {
        boolean sameSame = false;

        if (object != null && object instanceof CategoryModel)
        {
            sameSame = this.name.equals(((CategoryModel) object).name);
        }

        return sameSame;
    }
}