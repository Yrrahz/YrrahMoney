package com.example.yrrah.yrrahmoney;

import android.support.annotation.NonNull;

/**
 * Created by Yrrah on 2017-05-11.
 */

public class SubAmountModel implements Comparable{
    private int subAmountId; // Barely used because DB handles it itself by auto-increment
    private int amount;
    private int date;
    String event;
    String refID; // Reference to Category

    public SubAmountModel(){
        this.date = 0;
    }

    public SubAmountModel(int subAmountId, int amount, String event, String refID, int date){
        this.subAmountId = subAmountId;
        this.amount = amount;
        this.event = event;
        this.refID = refID;
        this.date = date;
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

    public void setDate(int date){
        this.date = date;
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

    public int getDate(){
        return date;
    }

    @Override
    public int compareTo(@NonNull Object o) {
        if(o instanceof SubAmountModel && ((SubAmountModel) o).getSubAmountId() == this.subAmountId){
            return 1;
        }
        return 0;
    }
}
