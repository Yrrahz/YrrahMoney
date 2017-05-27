package com.example.yrrah.yrrahmoney;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
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

    /**
     * Add a new category logic.
     *
     * @param view -
     */
    public void onAddButtonClick(View view){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add new Category:");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT); // input type, such as normal text or pw...
        builder.setView(input);

// Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                insertNewCategory(input.getText().toString());
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    /*** START
     * dev7 development - More design to AAC and dev4Activity
     ***/
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent returnIntent = new Intent(this, addAndConfigureActivity.class);
        returnIntent.putExtra("categoryTitle",data.get(position).get(categoryTitle));

        startActivityForResult(returnIntent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){ // When "Add" button is pressed
                SubAmountModel subAmountToAdd = new SubAmountModel();
                subAmountToAdd.setAmount(data.getIntExtra("amountToAdd",-1)); // -1 is a default value. Propobly will never be used.
                subAmountToAdd.setEvent(data.getStringExtra("eventToAdd"));
                subAmountToAdd.setRefID(data.getStringExtra("toCategory"));

                dbHandler.addSubAmount(subAmountToAdd);
            }
            if (resultCode == Activity.RESULT_CANCELED) { // When the 'back' button is pressed
                // This is what happens when there is no reult.
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
        Integer totalAmount = dbHandler.totalAmount();

        TextView totalAmountPresent = (TextView) findViewById(R.id.amountResult);
        totalAmountPresent.setText(String.format("%s",totalAmount.toString()));
    }

    /**
     * This method takes the input from the user and first, takes away all special characters, trims
     * the input and removes excess whitespaces. Then checks if the Category already exists in the
     * database. If ut does, a toast saying it does will appear, if not, create a new Category into
     * the database with totalValue of 0.
     * @param newCategory - The input from the user to what the new Category name should be
     */
    private void insertNewCategory(String newCategory){
        newCategory = newCategory.replaceAll("[ ](?=[ ])|[^-_,A-Za-z0-9 ]+","").trim();
        newCategory = newCategory.substring(0,1).toUpperCase() + newCategory.substring(1).toLowerCase();

        List<CategoryModel> categoryList = dbHandler.getAllCategories();
        CategoryModel cm = new CategoryModel(newCategory,0);

        if(!categoryList.contains(cm)){
            dbHandler.addCategory(cm);
            Toast.makeText(getApplicationContext(),"Category Added!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getApplicationContext(),"Category Already Exists!", Toast.LENGTH_SHORT).show();
        }
    }
}