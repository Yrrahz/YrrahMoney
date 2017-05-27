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

import static java.lang.Math.round;

public class mainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        runUpdateCheck();
    }

    public void categoryButtonPressed(View view){
        Intent intent = new Intent(this, Dev4Activity.class);
        startActivity(intent);
        //DBHandler dbHandler = new DBHandler(this);
        //dbHandler.deleteMonth(2);

        //runUpdateCheck();
        //Toast.makeText(getApplicationContext(),"Database value returned!", Toast.LENGTH_SHORT).show();
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

        int currentMonth = Integer.parseInt(month_date.format(cal.getTime())); // month name as 1,2,3...10,11,12.
        int latestMonth = dbHandler.returnLatestMonth();

        //saveMonth(latestMonth, dbHandler); // To test the months delete logic - This should not be used in final product

        if(currentMonth > latestMonth){ // If a month has passed. Save last month, delete all categories, start over...
            saveMonth(latestMonth, dbHandler);
        }
        //Toast.makeText(getApplicationContext(),"CurrentMonth: "+month_name+" - LatestMonth: "+ latestMonth, Toast.LENGTH_SHORT).show();
    }

    private void saveMonth(int latestMonth, DBHandler dbHandler){
        MonthModel newMonth = new MonthModel();

        newMonth.setMonth(latestMonth);
        newMonth.setTotalAmount(dbHandler.totalAmount());
        newMonth.setText(monthStatistics(dbHandler));

        dbHandler.addMonth(newMonth);

        dbHandler.deleteAllCategoryData();


        //String monthStatistics = monthStatistics(dbHandler);
        //Toast.makeText(getApplicationContext(),"LatestMonth: "+ latestMonth, Toast.LENGTH_SHORT).show();
    }

    private String monthStatistics(DBHandler dbHandler){
        List<CategoryModel> categoryList= dbHandler.getAllCategories();
        int tmp,total = 0, size = categoryList.size();
        String[] testStringList = new String[size];
        double[] testList = new double[size];
        //double[] resultList = new double[size];
        int i = 0;

        for(CategoryModel cm : categoryList){
            testStringList[i] = cm.getName();
            tmp = cm.getTotalAmount();
            //testStringList[i] = cm.getName();
            testList[i] = tmp;
            total = total + tmp;
            i++;
        }

        StringBuilder sb = new StringBuilder();
        while(i > 0){
            //resultList[i-1] = round((testList[i-1] / total)*10000.00) / 100.00;
            sb.append(testStringList[i-1]);
            sb.append(" ");
            sb.append(round((testList[i-1] / total)*10000.00) / 100.00);
            sb.append(";");
            i--;
        } // resultList now contains every cateogry percentage

        return sb.toString();
    }
}