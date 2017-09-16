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
    private String testAddCatString = "category"; // should maybe be local..

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

            if(dbHandlerTest.deleteCategory("deleteCat")){ // ...delete it!
                System.out.println("Category successfully deleted.");
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
        CategoryModel deleteAllCat1 = new CategoryModel("deleteAllCat1", 1337);
        CategoryModel deleteAllCat2 = new CategoryModel("deleteAllCat2", 1338);
        CategoryModel deleteAllCat3 = new CategoryModel("deleteAllCat3", 1339);
        dbHandlerTest.addCategory(deleteAllCat1);
        dbHandlerTest.addCategory(deleteAllCat2);
        dbHandlerTest.addCategory(deleteAllCat3);

        if(dbHandlerTest.getCategoriesCount() >= 3){
            dbHandlerTest.deleteAllCategoryData();
            if(dbHandlerTest.getCategoriesCount() == 0){
                System.out.println("No categories found in the Database.");
                assertTrue(true);
            }else{
                assertTrue("Can't confirm that the Categories has been deleted.",false);
            }
        }else{
            assertTrue("Can't confirm categories has been created. Nothing to test deletion with.",false);
        }
    }
    //</editor-fold>

    /**
     * This method can't test the addition of a subAmount directly. But since all Categories are
     * unique, when we also creates a new Category here. There can ONLY be one subAmount for that
     * Category. Based on that we can conclude this test to be true or false.
     * @throws Exception <Any Exception>
     */
    @Test
    public void addSubAmount() throws Exception {
        CategoryModel addSubCategory = new CategoryModel("addSubCategory", 1337);
        SubAmountModel addSubAmount = new SubAmountModel(100,100,"addSubAmount","addSubCategory");

        dbHandlerTest.addCategory(addSubCategory);
        dbHandlerTest.addSubAmount(addSubAmount);

        List<SubAmountModel> addSubAmountTestList = dbHandlerTest.getAllSubToCategory(addSubCategory.getName());

        if(addSubAmountTestList.isEmpty()){
            assertTrue("The Category created is empty.",false);
        }else if(addSubAmountTestList.size() != 1){
            assertTrue("The Category does not contain the correct amount of SubAmounts.",false);
        }else if(addSubAmountTestList.size() == 1){
            System.out.println("The SubAmount is found. SubAmount = "+addSubAmountTestList.get(0).getEvent());
            assertTrue(true);
        }else{
            assertTrue("Error while trying to populate the testList.",false);
        }
    }

    /**
     * This test can't really work because SubAmount's ID is handled by the database
     * by auto increment. So you can never know the individual ID of a SubAmount unless you check
     * before. This is very timeconsuming and since this method is never used. Maybe should remove
     * this method all together.
     * @throws Exception <any Excemption>
     */
    /*
    @Test
    public void getSubAmountModel() throws Exception {
        assertFalse("Not completed yet",true);
    }*/

    /**
     * This method can't test the addition of a subAmount directly. But since all Categories are
     * unique, when we also creates a new Category here. There can ONLY be three subAmounts for that
     * Category. Based on that we can conclude this test to be true or false.
     * @throws Exception <Any Exception>
     */
    @Test
    public void getAllSubAmounts() throws Exception {
        CategoryModel getAllSubCategory = new CategoryModel("getAllSubCategory", 1337);
        SubAmountModel addGetAllSubAmount1 = new SubAmountModel(100,100,"addGetAllSubAmount1","getAllSubCategory");
        SubAmountModel addGetAllSubAmount2 = new SubAmountModel(100,100,"addGetAllSubAmount2","getAllSubCategory");
        SubAmountModel addGetAllSubAmount3 = new SubAmountModel(100,100,"addGetAllSubAmount3","getAllSubCategory");

        dbHandlerTest.addCategory(getAllSubCategory);
        dbHandlerTest.addSubAmount(addGetAllSubAmount1);
        dbHandlerTest.addSubAmount(addGetAllSubAmount2);
        dbHandlerTest.addSubAmount(addGetAllSubAmount3);

        List<SubAmountModel> getAllSubAmountTestList = dbHandlerTest.getAllSubToCategory(getAllSubCategory.getName());

        if(getAllSubAmountTestList.isEmpty()){
            assertTrue("The Category created is empty.",false);
        }else if(getAllSubAmountTestList.size() != 3){
            assertTrue("The Category does not contain the correct amount of SubAmounts.",false);
        }else if(getAllSubAmountTestList.size() == 3){
            System.out.println("The SubAmounts are found.\nSubAmount1 = "+getAllSubAmountTestList.get(0).getEvent());
            System.out.println("SubAmount2 = "+getAllSubAmountTestList.get(1).getEvent());
            System.out.println("SubAmount3 = "+getAllSubAmountTestList.get(2).getEvent());
            assertTrue(true);
        }else{
            assertTrue("Error while trying to populate the testList.",false);
        }
    }

    @Test
    public void getAllSubToCategory() throws Exception {
        CategoryModel getAllSubToCategory = new CategoryModel("getAllSubToCategory", 1337);
        SubAmountModel addGetAllSubAmount1 = new SubAmountModel(100,100,"addGetAllSubAmount1","getAllSubToCategory");
        SubAmountModel addGetAllSubAmount2 = new SubAmountModel(100,100,"addGetAllSubAmount2","getAllSubToCategory");
        SubAmountModel addGetAllSubAmount3 = new SubAmountModel(100,100,"addGetAllSubAmount3","getAllSubToCategory");

        dbHandlerTest.addCategory(getAllSubToCategory);
        dbHandlerTest.addSubAmount(addGetAllSubAmount1);
        dbHandlerTest.addSubAmount(addGetAllSubAmount2);
        dbHandlerTest.addSubAmount(addGetAllSubAmount3);

        List<SubAmountModel> getAllSubToCategoryTestList = dbHandlerTest.getAllSubToCategory(getAllSubToCategory.getName());

        if(getAllSubToCategoryTestList.isEmpty()){
            assertTrue("The Category created is empty.",false);
        }else if(getAllSubToCategoryTestList.size() != 3){
            assertTrue("The Category does not contain the correct amount of SubAmounts.",false);
        }else if(getAllSubToCategoryTestList.size() == 3){
            System.out.println("The SubAmounts are found.\nSubAmount1 = "+getAllSubToCategoryTestList.get(0).getEvent());
            System.out.println("SubAmount2 = "+getAllSubToCategoryTestList.get(1).getEvent());
            System.out.println("SubAmount3 = "+getAllSubToCategoryTestList.get(2).getEvent());
            assertTrue(true);
        }else{
            assertTrue("Error while trying to populate the testList.",false);
        }
    }

    /**
     * This test is kind of useless. It proves that, with correct SubAmountID, you actually CAN
     * update a SubAmount. However, since we will never be able to know about their ID, unless we do
     * some List-trickery, and we would never need to know update SubAmounts, this test is useless.
     * ... But it works.
     * @throws Exception <any>
     */
    @Test
    public void updateSubAmount() throws Exception {

        CategoryModel updateSubAmountCategory = new CategoryModel("updateSubAmountCategory", 1337);
        SubAmountModel updateSubAmount1 = new SubAmountModel(1,100,"addUpdateSubAmount1","updateSubAmountCategory");

        dbHandlerTest.addCategory(updateSubAmountCategory);
        dbHandlerTest.addSubAmount(updateSubAmount1);

        List<SubAmountModel> updateSubAmountTestList = dbHandlerTest.getAllSubToCategory("updateSubAmountCategory");
        SubAmountModel updateSubAmount2 = new SubAmountModel(updateSubAmountTestList.get(0).getSubAmountId(),1337,"addUpdateSubAmount2","updateSubAmountCategory"); // This is needed to check what the ID of the SubAmmount turned out to be. As the Database is handling it by itself

        if(updateSubAmountTestList.isEmpty()){
            assertTrue("The created Category is empty.",false);
        }else if(updateSubAmountTestList.size() == 1){
            dbHandlerTest.updateSubAmount(updateSubAmount2);
            updateSubAmountTestList = dbHandlerTest.getAllSubToCategory("updateSubAmountCategory");
            if(updateSubAmountTestList.isEmpty()){
                assertTrue("The created Category is empty after update subAmount was run.",false);
            }else if(updateSubAmountTestList.size() == 1 && updateSubAmountTestList.get(0).getAmount() == updateSubAmount2.getAmount()){ // I think this is wrong!!!!
                System.out.println("The subAmount for the created Category was updated properly.\n"+
                "Old amount = "+updateSubAmount1.getAmount()+"\nNew amount = " + updateSubAmount2.getAmount());
                assertTrue(true);
            }else if(updateSubAmountTestList.size() == 1){
                assertTrue("The subAmount for the Category was not updated properly.",false);
            }else{
                assertTrue("Something went wrong with List<SubAmountModel> updateSubAmountTestList.",false);
            }
        }else{
            assertTrue("Something went wrong. Check the Category.",false);
        }
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