package com.example.yrrah.yrrahmoney;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * New testClass for another model. This class has an 'equals' method instead of 'compareTo',
 * I wonder what is best, if both are ok, if both should exist or just one??
 *
 * Created by DHA10134 on 2017-10-23.
 */
public class CategoryModelTest {
    private CategoryModel categoryModel;

    @Before
    public void setUp() throws Exception {
        categoryModel = new CategoryModel("cmTest", 1337);
    }

    @Test
    public void setName() throws Exception {
        categoryModel.setName("newName");

        if(categoryModel.getName().equals("newName")){
            assertTrue(true);
        }else{
            assertTrue("categoryModel did not receive a new name.",false);
        }
    }

    @Test
    public void setTotalAmount() throws Exception {
        categoryModel.setTotalAmount(1338);

        if(categoryModel.getTotalAmount() == 1338){
            assertTrue(true);
        }else{
            assertTrue("categoryModel did not receive a new TotalAmount.",false);
        }
    }

    @Test
    public void equals() throws Exception {
        CategoryModel categoryModelTestEqual = new CategoryModel("cmTest", 1337);
        CategoryModel categoryModelTestNotEqual = new CategoryModel("cmTest2", 1337);

        if(categoryModelTestEqual.equals(categoryModel) && !categoryModelTestNotEqual.equals(categoryModel)){
            assertTrue(true);
        }else{
            assertTrue(false);
        }
    }
}