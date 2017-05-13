package com.example.yrrah.yrrahmoney;

/**
 * Created by Yrrah on 2017-05-11.
 */

public class SubAmountModel {
    int subAmountId;
    int amount;
    String event;
    String refID; // Reference to Category

    public SubAmountModel(){}

    public SubAmountModel(int subAmountId, int amount, String event, String refID){
        this.subAmountId = subAmountId;
        this.amount = amount;
        this.event = event;
        this.refID = refID;
    }

    public void setSubAmountId(int subAmountId){
        this.subAmountId = subAmountId;
    }

    public void setAmount(int amount){
        this.amount = amount;
    }

    public void setEvent(String event){
        this.event = event;
    }

    public void setRefID(String refID) {
        this.refID = refID;
    }

    public int getSubAmountId() {
        return subAmountId;
    }

    public int getAmount() {
        return amount;
    }

    public String getEvent() {
        return event;
    }

    public String getRefID() {
        return refID;
    }
}
