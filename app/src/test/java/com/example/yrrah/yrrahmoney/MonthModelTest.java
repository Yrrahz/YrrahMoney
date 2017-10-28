package com.example.yrrah.yrrahmoney;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Testclass for the MonthModel
 *
 * Created by Yrrah on 2017-10-28.
 */
public class MonthModelTest {
    private MonthModel MMT;

    @Before
    public void setUp() throws Exception {
        MMT = new MonthModel(1,1337,"testClass");
    }

    @Test
    public void setId() throws Exception {
        MMT.setId(1);

        if(MMT.getId() == 1){
            assertTrue(true);
        }else{
            assertTrue(false);
        }
    }

    @Test
    public void setMonth() throws Exception {
        MMT.setMonth(2);

        if(MMT.getMonth() == 2){
            assertTrue(true);
        }else{
            assertTrue(false);
        }
    }

    @Test
    public void setTotalAmount() throws Exception {
        MMT.setTotalAmount(1447);

        if(MMT.getTotalAmount() == 1447){
            assertTrue(true);
        }else{
            assertTrue(false);
        }
    }

    @Test
    public void setText() throws Exception {
        MMT.setText("new");

        if(MMT.getText().equals("new")){
            assertTrue(true);
        }else{
            assertTrue(false);
        }
    }
}