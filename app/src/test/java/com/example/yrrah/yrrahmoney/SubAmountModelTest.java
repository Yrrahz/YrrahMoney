package com.example.yrrah.yrrahmoney;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Attempting to create more testClasses.
 *
 * Created by DHA10134 on 2017-10-09.
 */
public class SubAmountModelTest {
    private SubAmountModel SAMT;

    @Before
    public void setUp() throws Exception {
        SAMT = new SubAmountModel(10,1000,"Test Event","Reference ID");
    }

    @Test
    public void setSubAmountId() throws Exception {
        SAMT.setSubAmountId(11);

        if(SAMT.getSubAmountId() == 11){
            assertTrue(true);
        }else{
            assertTrue(false);
        }
    }

    @Test
    public void setAmount() throws Exception {
        SAMT.setAmount(1337);

        if(SAMT.getAmount() == 1337){
            assertTrue(true);
        }else{
            assertTrue(false);
        }
    }

    @Test
    public void setEvent() throws Exception {
        SAMT.setEvent("Test SetEvent()");

        if(SAMT.getEvent().equals("Test SetEvent()")){
            assertTrue(true);
        }else{
            assertTrue(false);
        }
    }

    @Test
    public void setRefID() throws Exception {
        SAMT.setRefID("Test RefID");

        if(SAMT.getRefID().equals("Test RefID")){
            assertTrue(true);
        }else{
            assertTrue(false);
        }
    }

    @Test
    public void compareTo(){
        SubAmountModel anotherSAMT1 = new SubAmountModel(10,1000,"Test Event","Reference ID");
        SubAmountModel anotherSAMT2 = new SubAmountModel(11,1000,"Test Event","Reference ID");

        if(SAMT.compareTo(anotherSAMT1) == 1 && SAMT.compareTo(anotherSAMT2) == 0){
            assertTrue(true);
        }else{
            assertTrue(false);
        }
    }
}