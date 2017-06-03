package com.example.yrrah.yrrahmoney;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static java.lang.Math.round;

public class mainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        runUpdateCheck();
    }

    public void monthButtonPressed(View view){
        // Month button pressed...
        DBHandler dbHandler = new DBHandler(this);
        List<MonthModel> monthList = dbHandler.getAllMonths();
        List<Map<String, String>> data = new ArrayList<>();

        for(MonthModel m : monthList){
            Map<String, String> listViewElement = new HashMap<>(2);
            listViewElement.put("heading", monthConverter(m.getMonth()));
            listViewElement.put("subItem", Integer.toString(m.getTotalAmount()));

            data.add(listViewElement);
        }

        SimpleAdapter adapter = new SimpleAdapter(this, data,
                android.R.layout.simple_list_item_2, // <-- Standard lib item, contains both Item and SubItem in listView
                new String[] {"heading","subItem"}, // <-- must be same as dateMap's keys
                new int[] {android.R.id.text1, android.R.id.text2});

        ListView monthListView = (ListView) findViewById(R.id.mainListView);
        monthListView.setAdapter(adapter);
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
    }

    public void categoryButtonPressed(View view){
        Intent intent = new Intent(this, Dev4Activity.class);
        startActivity(intent);
    }

    public void testDB2(View view){
        DBHandler dbHandler = new DBHandler(this);
        dbHandler.populateDatabaseWithData();
    }

    private void runUpdateCheck(){
        DBHandler dbHandler = new DBHandler(this);
        Calendar cal=Calendar.getInstance();
        SimpleDateFormat month_date = new SimpleDateFormat("MM", Locale.ENGLISH);

        int currentMonth = Integer.parseInt(month_date.format(cal.getTime())); // month name as 1,2,3...10,11,12.
        int latestMonth = dbHandler.returnLatestMonth();

        // TODO : Add support for new year.. 1 !> 12
        if(currentMonth > latestMonth){ // If a month has passed. Save last month, delete all categories, start over...
            saveMonth(latestMonth, dbHandler);
        }
    }

    private void saveMonth(int latestMonth, DBHandler dbHandler){
        MonthModel newMonth = new MonthModel();

        newMonth.setMonth(latestMonth);
        newMonth.setTotalAmount(dbHandler.totalAmount());
        newMonth.setText(monthStatistics(dbHandler));

        dbHandler.addMonth(newMonth);

        dbHandler.deleteAllCategoryData();
    }

    private String monthStatistics(DBHandler dbHandler){
        List<CategoryModel> categoryList= dbHandler.getAllCategories();
        int tmp,total = 0, size = categoryList.size();
        String[] testStringList = new String[size];
        double[] testList = new double[size];
        int i = 0;

        for(CategoryModel cm : categoryList){
            testStringList[i] = cm.getName();
            tmp = cm.getTotalAmount();
            testList[i] = tmp;
            total = total + tmp;
            i++;
        }

        StringBuilder sb = new StringBuilder();
        while(i > 0){
            sb.append(testStringList[i-1]);
            sb.append(" ");
            sb.append(round((testList[i-1] / total)*10000.00) / 100.00);
            sb.append(";");
            i--;
        }

        return sb.toString();
    }
}