package com.example.yrrah.yrrahmoney;

import android.app.Dialog;
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
    private static final String categoryTitle = "item";
    private static final String categoryData = "subItem";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dev4);

        testMethod();
    }

    public void onAddButtonClick(View view){
        Toast.makeText(getApplicationContext(),"Add Category button clicked!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        String s = data.get(position).get(categoryTitle);
        String t = data.get(position).get(categoryData);

        Toast.makeText(getApplicationContext(),"Category: "+s+" data: "+t, Toast.LENGTH_SHORT).show();


        /*
        // Create custom dialog object
        final Dialog dialog = new Dialog(context);
        // Include dialog.xml file
        dialog.setContentView(R.layout.dialog); // layout of your dialog
        // Set dialog title
        dialog.setTitle("Detail");

        // set values for custom dialog components - text, image and button
        TextView text = (TextView) dialog.findViewById(R.id.textDialog);
        text.setText(exerciseList.get(position).getNama());
        // similar add statements for other details
        dialog.show();
        */
    }

    private void testMethod(){

        for(int i=0 ; i<20 ; i++){
            Map<String, String> dateMap = new HashMap<>(2);
            dateMap.put(categoryTitle,"Category "+(i+1));
            dateMap.put(categoryData,"Total Amount: "+(i+10));

            data.add(dateMap);
        }



        SimpleAdapter adapter = new SimpleAdapter(this, data,
                android.R.layout.simple_list_item_2, // <-- Standard lib item, contains both Item and SubItem in listView
                new String[] {categoryTitle,categoryData}, // <-- must be same as dateMap's keys
                new int[] {android.R.id.text1, android.R.id.text2});


        ListView presentData = (ListView) findViewById(R.id.listA);
        presentData.setOnItemClickListener(this);
        presentData.setAdapter(adapter);

    }
}