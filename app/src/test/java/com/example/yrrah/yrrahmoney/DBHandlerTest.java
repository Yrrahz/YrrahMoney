package com.example.yrrah.yrrahmoney;

import android.util.Log;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.List;

import static org.junit.Assert.*;

/**
 * First Test Attempt, seems to work but takes a very long time...
 * Remember to run the entire file and not specific methods OR refactor and create a new database
 * in every method.. sounds bad, maybe there is a standard for this.
 *
 * Many of these tests only tests against their names and not amounts. Suppose duplicates of
 * names is possible. Then these tests might not be 100% perfect in their testing. Of course with
 * amounts also would not make them exclusive. An exclusive ID is then necessary.
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
        dbHandlerTest.deleteAllCategoryData();
    }

    //<editor-fold desc="Category methods">

    @Test
    public void addCategory() throws Exception {
        /*
          Apparently the "failsafe" mechanism to ensure that no 2 Categories can be created
          is located in (somewhere else). Which makes testing duplicates fairly hard.

          TODO : Find the method and test for duplicates
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
        /*
          Would prefer to use the same name as other tests. But in fear of duplicate...
          Currently I need to test with different names.
         */
        String testGetCategory = "testGetCategoryModel";
        CategoryModel categoryModel = new CategoryModel(testGetCategory, 1337);

        dbHandlerTest.addCategory(categoryModel);

        String testResult = dbHandlerTest.getCategoryModel(testGetCategory).getName();
        if(testResult.equals(testGetCategory)){
            System.out.println(testResult);
            assertTrue(true);
        }else{
            assertTrue(false);
        }
    }

    @Test
    public void getAllCategories() throws Exception {
        /*
        This method doesn't test weather or not the list acquired is containing the given
        categoryModels.

        TODO : check the objects from the List<?> and not directly from the database.
         */

        CategoryModel categoryModel1 = new CategoryModel("getAllCat1", 1337);
        CategoryModel categoryModel2 = new CategoryModel("getAllCat2", 1338);
        CategoryModel categoryModel3 = new CategoryModel("getAllCat3", 1339);

        dbHandlerTest.addCategory(categoryModel1);
        dbHandlerTest.addCategory(categoryModel2);
        dbHandlerTest.addCategory(categoryModel3);

        List<CategoryModel> testList = dbHandlerTest.getAllCategories();

        if(testList != null){
            String getAllCat1 = dbHandlerTest.getCategoryModel("getAllCat1").getName(); //
            String getAllCat2 = dbHandlerTest.getCategoryModel("getAllCat2").getName(); // These should be gathered from the testList<CM>
            String getAllCat3 = dbHandlerTest.getCategoryModel("getAllCat3").getName(); //
            if(getAllCat1.equals("getAllCat1") && getAllCat2.equals("getAllCat2") && getAllCat3.equals("getAllCat3")){
                System.out.println("getAllCat1, getAllCat2, getAllCat3");
                assertTrue(true);
            }else{
                assertTrue("testList in getAllCategories() in DBHandlerTest.java doesn't contain " +
                        "correct names.\ngetAllCat1 = "+getAllCat1+", getAllCat2 = "+getAllCat2+
                        ", getAllCat3 = "+getAllCat3, false);
            }
        }else{
            assertTrue("testList is null.", false);
        }
    }

    @Test
    public void getCategoriesCount() throws Exception {
        /*
        The CategoriesCount method is currently not in use.
         */
        int count = dbHandlerTest.getCategoriesCount();

        CategoryModel getCount = new CategoryModel("getCount", 1337);
        dbHandlerTest.addCategory(getCount);

        if(dbHandlerTest.getCategoriesCount() == count+1){
            System.out.println("Categories count increased with 1.");
            assertTrue(true);
        }else{
            assertTrue("getCategoriesCount() did not return expected amount.",false);
        }
    }

    @Test
    public void updateCategory() throws Exception {
        /*
        The updateCategory method is currently not in use. But it should.
        But how is it actually working?
        TODO : Once it's working, add test
         */

        /*
        CategoryModel updateCat = new CategoryModel("updateCat", 1337);
        dbHandlerTest.addCategory(updateCat);

        dbHandlerTest.updateCategory(updateCat); */

        assertFalse("Not completed yet",true);
    }

    @Test
    public void deleteCategory() throws Exception {

        CategoryModel deleteCat = new CategoryModel("deleteCat", 1337);
        dbHandlerTest.addCategory(deleteCat);

        if(dbHandlerTest.getCategoryModel("deleteCat").getName().equals("deleteCat")){
            // If our Category has been added...
            dbHandlerTest.deleteCategory(deleteCat); // ... Delete it!

            // TODO : getCategoryModel is generating error instead of returning false. Fix! Or make deleteCategory() a boolean.
            if(!dbHandlerTest.getCategoryModel("deleteCat").getName().equals("deleteCat")){
                System.out.println("Category deleted.");
                assertTrue(true);
            }else{
                assertTrue("Category not found or Category not deleted.",false);
            }
        }else{
            assertTrue("Can not find the created Category.",false);
        }
    }

    @Test
    public void totalAmount() throws Exception {

        //dbHandlerTest.deleteAllCategoryData();
        /*
        Create category..
        Create subAmounts.. added to the category
        Then call totalAmount and compare to the given subAmounts in our Category
        if(true) assert true
        else assert false
         */

        assertFalse("Not completed yet",true);
    }

    @Test
    public void deleteAllCategoryData() throws Exception {
        /*
        The problem with this method is that there is no "false" response on access methods which
        are asked for a Category that doesn't exist. It instead just crash..
         */
        assertFalse("Not completed yet",true);
    }
    //</editor-fold>
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