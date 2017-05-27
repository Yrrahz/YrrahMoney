package com.example.yrrah.yrrahmoney;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class mainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        runUpdateCheck();
    }

    public void categoryButtonPressed(View view){
        //Intent intent = new Intent(this, Dev4Activity.class);
        //startActivity(intent);
        DBHandler dbHandler = new DBHandler(this);
        dbHandler.deleteMonth(2);

        Toast.makeText(getApplicationContext(),"Database value returned!", Toast.LENGTH_SHORT).show();
    }
    /**
     ** TestDB lyckades! Vi kan skapa och hämta data ur databasen.
     ** Appen crashar om man försöker hämta data från en category som
     ** inte finns.
     **/

    public void testDB2(View view){
        DBHandler dbHandler = new DBHandler(this);
        dbHandler.populateDatabaseWithData();

        //int test = dbHandler.getCategoryModel("Transport").getTotalAmount();
        //Toast.makeText(getApplicationContext(),"Database value returned: "+test, Toast.LENGTH_SHORT).show();
    }

    // TODO : When month is implemented, delete entire Category table. Save one month and generate the month index with a method similair to this.
    private void runUpdateCheck(){
        DBHandler dbHandler = new DBHandler(this);
        Calendar cal=Calendar.getInstance();
        SimpleDateFormat month_date = new SimpleDateFormat("MM", Locale.ENGLISH);
        int month_name = Integer.parseInt(month_date.format(cal.getTime())); // month name as 1,2,3...10,11,12.

        int latestMonth = dbHandler.returnLatestMonth();

        Toast.makeText(getApplicationContext(),"CurrentMonth: "+month_name+" - LatestMonth: "+ latestMonth, Toast.LENGTH_SHORT).show();
    }
}