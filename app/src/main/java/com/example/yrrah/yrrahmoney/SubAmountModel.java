package com.example.yrrah.yrrahmoney;

import android.support.annotation.NonNull;

import java.util.Calendar;

/**
 * Model for SubAmount
 *
 * Created by Yrrah on 2017-05-11.
 */

public class SubAmountModel implements Comparable{
    private int subAmountId; // Barely used because DB handles it itself by auto-increment
    private int amount;
    private int date;
    String event;
    private String refID; // Reference to Category

    public SubAmountModel(){
        this.date = 0; // this is to ensure we always have an int value, incase some method would crash if it would getDate() and receive a null value.
    }

    public SubAmountModel(int subAmountId, int amount, String event, String refID, int date){
        this.subAmountId = subAmountId;
        this.amount = amount;
        this.event = event;
        this.refID = refID;
        if(date == 0){
            setDate(0);
        }else{
            this.date = date;
        }
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
        Calendar now = Calendar.getInstance();
        if (date == 0) {
            this.date = now.get(Calendar.YEAR)*10000 + (now.get(Calendar.MONTH)+1)*100 + now.get(Calendar.DATE);
        } else {
            this.date = date;
        }
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
