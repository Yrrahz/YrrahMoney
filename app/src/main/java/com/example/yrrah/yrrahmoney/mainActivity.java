package com.example.yrrah.yrrahmoney;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.lang.Math.round;

public class mainActivity extends AppCompatActivity {

    List<Map<String, String>> data = new ArrayList<>();
    List<MonthModel> monthList;
    boolean monthStatShow = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setup();
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

    /**
     * Populate the listview on mainActivity, showing stats from previous month abd current stats
     * from this month.
     * @param view - The android view
     */
    public void stat2ButtonPressed(View view){ // TODO : Change name!!! If I decide to use this method.
        // stat2Button Pressed...
        Toast.makeText(getApplicationContext(),"stat2ButtonPressed", Toast.LENGTH_SHORT).show();
    }

    public void addButtonPressed(View view){
        Intent intent = new Intent(this, Dev4Activity.class);
        startActivity(intent);
    }

    // Not supposed to exist when app is finished.
    public void testDB2(View view){
        DBHandler dbHandler = new DBHandler(this);
        dbHandler.populateDatabaseWithData();
    }

    private void setup(){
        TextView incomeNr = (TextView) findViewById(R.id.incomeNr);
        TextView expenditureNr = (TextView) findViewById(R.id.expenditureNr);
        TextView totalNr = (TextView) findViewById(R.id.totalNr);

        DBHandler dbHandler = new DBHandler(this);
        CategoryModel cm = new CategoryModel("Income", 1000);
        dbHandler.addCategory(cm);
        cm = new CategoryModel("Expenditure", -500);
        dbHandler.addCategory(cm);


        incomeNr.setText(String.format("%s","20 000"));
        expenditureNr.setText(String.format("%s","15 000"));
        totalNr.setText(String.format("%s",dbHandler.totalAmount()));
    }
}