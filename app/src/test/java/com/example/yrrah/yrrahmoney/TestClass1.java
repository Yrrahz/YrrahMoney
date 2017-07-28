package com.example.yrrah.yrrahmoney;

import android.test.RenamingDelegatingContext;

import org.junit.Before;
import org.junit.Test;

import static java.security.AccessController.getContext;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


/**
 * First attempt at creating TestClasses
 *
 * Created by Yrrah on 2017-07-27.
 */
public class TestClass1 {

    @Before
    public void setUp() throws Exception {

    }

    public void testDropDB(){
        //assertTrue(mContext.);
    }

    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }
}
