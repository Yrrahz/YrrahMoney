package com.example.yrrah.yrrahmoney;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Dev4Activity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    List<Map<String, String>> data = new ArrayList<>();
    private static final String categoryTitle = "item"; // These two variables...
    private static final String categoryData = "subItem"; // ... are final for a reason.
    DBHandler dbHandler;
    List<CategoryModel> listOfCategories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dev4);

        dbHandler = new DBHandler(this);
        listOfCategories = dbHandler.getAllCategories();
        populateListView();
        calculateTotalAmount();
    }

    // This method has been used a lot for testing functions. But should go to AAC!
    // no! It should specifically go to add a Category! while the list click goes to AAC
    public void onAddButtonClick(View view){

        //int test = dbHandler.updateCategory(new CategoryModel("Transport",0)); // Test Catategory Update method
        //dbHandler.deleteCategory(new CategoryModel("Rent",0)); // Test Category Delete method

        //Toast.makeText(getApplicationContext(),"Database Table Count: "+dbHandler.getCategoriesCount(), Toast.LENGTH_SHORT).show(); // Count Categories
        //Toast.makeText(getApplicationContext(),"Database Table Update: "+test, Toast.LENGTH_SHORT).show(); // Just checking the int response from update method

        List<SubAmountModel> subAmountModelList = new ArrayList<>();
        List<SubAmountModel> subAmountModelList2 = new ArrayList<>();
        subAmountModelList = dbHandler.getAllSubAmounts();
        subAmountModelList2 = dbHandler.getAllSubToCategory("Entertainment"); // All works!

        Toast.makeText(getApplicationContext(),"Check Data", Toast.LENGTH_SHORT).show();
    }

    /*** START
     * dev7 development - More design to AAC and dev4Activity
     ***/
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent returnIntent = new Intent(this, addAndConfigureActivity.class);
        returnIntent.putExtra("categoryTitle",data.get(position).get(categoryTitle));
        returnIntent.putExtra("categoryData",data.get(position).get(categoryData));

        startActivityForResult(returnIntent,1);
        //startActivity(returnIntent);
        //Toast.makeText(getApplicationContext(),"Category: "+s+" data: "+t, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){ // When "Add" button is pressed
                int result = data.getIntExtra("amountToAdd",-1); // -1 is a default value. Propobly will never be used.
                String event = data.getStringExtra("eventToAdd");
                Toast.makeText(getApplicationContext(),"Success. Amount is: " + result, Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(),"Success. Event is: " + event, Toast.LENGTH_SHORT).show();
            }
            if (resultCode == Activity.RESULT_CANCELED) { // When the 'back' button is pressed
                //Write your code if there's no result
                Toast.makeText(getApplicationContext(),"No data collected...", Toast.LENGTH_SHORT).show();
            }
        }
    }
    /*** END
     * dev7 development - More design to AAC and dev4Activity
     ***/

    private void populateListView(){

        for(CategoryModel cm: listOfCategories){
            Map<String, String> listViewElement = new HashMap<>(2);
            listViewElement.put(categoryTitle, cm.getName());
            listViewElement.put(categoryData,"Total Amount: "+cm.getTotalAmount());

            data.add(listViewElement);
        }

        SimpleAdapter adapter = new SimpleAdapter(this, data,
                android.R.layout.simple_list_item_2, // <-- Standard lib item, contains both Item and SubItem in listView
                new String[] {categoryTitle,categoryData}, // <-- must be same as dateMap's keys
                new int[] {android.R.id.text1, android.R.id.text2});


        ListView presentData = (ListView) findViewById(R.id.listA);
        presentData.setOnItemClickListener(this);
        presentData.setAdapter(adapter);
    }

    private void calculateTotalAmount(){
        Integer totalAmount = 0;
        for(CategoryModel cm: listOfCategories){
            totalAmount = totalAmount + cm.getTotalAmount();
        }
        TextView totalAmountPresent = (TextView) findViewById(R.id.amountResult);
        totalAmountPresent.setText(totalAmount.toString());
    }
}