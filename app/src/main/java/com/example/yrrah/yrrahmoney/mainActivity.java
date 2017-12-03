package com.example.yrrah.yrrahmoney;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static java.lang.Math.round;

public class mainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    List<Map<String, String>> data = new ArrayList<>();
    List<MonthModel> monthList;
    boolean monthStatShow = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void monthButtonPressed(View view){
        // Populate listview in mainActivity.
        // Take a chosen month and show all categories and their value for that month
        // ... maybe some visual effects, similair to what Month's %-data would do.
        // This will take some time to do.
        Toast.makeText(getApplicationContext(),"MonthButton pressed.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        if(monthStatShow){
            Button button = (Button) findViewById(R.id.monthButton);
            button.performClick();
        }else{
            super.onBackPressed();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //data.get(position).get("heading") => Month
        //data.get(position).get("subItem") => Value
        Toast.makeText(getApplicationContext(),"Clicked on item in list.", Toast.LENGTH_SHORT).show();
        /*
        if(monthStatShow){
            return;
        }
        data.clear();
        DBHandler dbHandler = new DBHandler(this);
        //MonthModel monthChosen = dbHandler.getMonthModel(monthList.get(position).getId());
        //String[] x = monthChosen.getText().split("[;\\s]");

        for(int i = 0 ; i < x.length ; i = i+2){
            Map<String, String> listViewElement = new HashMap<>(2);
            listViewElement.put("heading", x[i]);
            listViewElement.put("subItem", x[i+1]);

            data.add(listViewElement);
        }

        SimpleAdapter adapter = new SimpleAdapter(this, data,
                android.R.layout.simple_list_item_2, // <-- Standard lib item, contains both Item and SubItem in listView
                new String[] {"heading","subItem"}, // <-- must be same as dateMap's keys
                new int[] {android.R.id.text1, android.R.id.text2});

        ListView monthListView = (ListView) findViewById(R.id.mainListView);
        monthListView.setAdapter(adapter);
        monthStatShow = true; */
    }

    private String monthConverter(int monthNr){
        switch (monthNr){
            case 1 :
                return "Januari";
            case 2 :
                return "Februari";
            case 3 :
                return "Mars";
            case 4 :
                return "April";
            case 5 :
                return "Maj";
            case 6 :
                return "Juni";
            case 7 :
                return "Juli";
            case 8 :
                return "Augusti";
            case 9 :
                return "September";
            case 10 :
                return "Oktober";
            case 11 :
                return "November";
            case 12 :
                return "December";
            default:
                return "custom";
        }
    }

    public void stat2ButtonPressed(View view){
        // stat2Button Pressed...
        Toast.makeText(getApplicationContext(),"stat2ButtonPressed", Toast.LENGTH_SHORT).show();
    }

    public void categoryButtonPressed(View view){
        Intent intent = new Intent(this, Dev4Activity.class);
        startActivity(intent);
    }

    public void testDB2(View view){
        DBHandler dbHandler = new DBHandler(this);
        dbHandler.populateDatabaseWithData();
    }
}