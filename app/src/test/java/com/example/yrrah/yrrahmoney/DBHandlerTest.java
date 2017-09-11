package com.example.yrrah.yrrahmoney;

import android.util.Log;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static org.junit.Assert.*;

/**
 * First Test Attempt, seems to work but takes a very long time...
 * Remember to run the entire file and not specific methods OR refactor and create a new database
 * in every method.. sounds bad, maybe there is a standard for this.
 *
 * Created by Yrrahz on 2017-07-28.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21, packageName = "com.example.yrrah.yrrahmoney")
public class DBHandlerTest {
    private DBHandler dbHandlerTest;
    private String testAddCatString = "category";

    @Before
    public void setUp() throws Exception {
        dbHandlerTest = new DBHandler(RuntimeEnvironment.application);
    }

    @After
    public void tearDown() throws Exception {
        CategoryModel categoryModel = new CategoryModel(testAddCatString, 1337);
        dbHandlerTest.deleteCategory(categoryModel);
    }

    @Test
    public void addCategory() throws Exception {
        /**
         * Apparently the "failsafe" mechanism to ensure that no 2 Categories can be created
         * is located in (somewhere else). Which makes testing duplicates fairly hard.
         *
         * TODO : Find the method and test for duplicates
         */
        CategoryModel categoryModel = new CategoryModel(testAddCatString, 1337);

        dbHandlerTest.addCategory(categoryModel);

        String testResult = dbHandlerTest.getCategoryModel(testAddCatString).getName();
        if(testResult.equals(testAddCatString)){
            System.out.println(testResult);
            assertTrue(true);
        }else{
            assertTrue(false);
        }
    }

    @Test
    public void getCategoryModel() throws Exception {
        assertFalse("Not completed yet",true);
    }

    @Test
    public void getAllCategories() throws Exception {
        assertFalse("Not completed yet",true);
    }

    @Test
    public void getCategoriesCount() throws Exception {
        assertFalse("Not completed yet",true);
    }

    @Test
    public void updateCategory() throws Exception {
        assertFalse("Not completed yet",true);
    }

    @Test
    public void deleteCategory() throws Exception {
        assertFalse("Not completed yet",true);
    }

    @Test
    public void totalAmount() throws Exception {
        assertFalse("Not completed yet",true);
    }

    @Test
    public void deleteAllCategoryData() throws Exception {
        assertFalse("Not completed yet",true);
    }

    @Test
    public void addSubAmount() throws Exception {
        assertFalse("Not completed yet",true);
    }

    @Test
    public void getSubAmountModel() throws Exception {
        assertFalse("Not completed yet",true);
    }

    @Test
    public void getAllSubAmounts() throws Exception {
        assertFalse("Not completed yet",true);
    }

    @Test
    public void getAllSubToCategory() throws Exception {
        assertFalse("Not completed yet",true);
    }

    @Test
    public void updateSubAmount() throws Exception {
        assertFalse("Not completed yet",true);
    }

    @Test
    public void deleteSubAmount() throws Exception {
        assertFalse("Not completed yet",true);
    }

    @Test
    public void addMonth() throws Exception {
        assertFalse("Not completed yet",true);
    }

    @Test
    public void getMonthModel() throws Exception {
        assertFalse("Not completed yet",true);
    }

    @Test
    public void getAllMonths() throws Exception {
        assertFalse("Not completed yet",true);
    }

    @Test
    public void returnMonthCount() throws Exception {
        assertFalse("Not completed yet",true);
    }

    @Test
    public void deleteMonth() throws Exception {
        assertFalse("Not completed yet",true);
    }
}