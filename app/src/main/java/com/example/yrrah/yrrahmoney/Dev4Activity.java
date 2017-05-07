package com.example.yrrah.yrrahmoney;

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
    public void onAddButtonClick(View view){

        int test = dbHandler.updateCategory(new CategoryModel("Transport",0));
        dbHandler.deleteCategory(new CategoryModel("Rent",0));

        Toast.makeText(getApplicationContext(),"Database Table Count: "+dbHandler.getCategoriesCount(), Toast.LENGTH_SHORT).show();
        Toast.makeText(getApplicationContext(),"Database Table Update: "+test, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        String s = data.get(position).get(categoryTitle);
        String t = data.get(position).get(categoryData);

        Toast.makeText(getApplicationContext(),"Category: "+s+" data: "+t, Toast.LENGTH_SHORT).show();
    }

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