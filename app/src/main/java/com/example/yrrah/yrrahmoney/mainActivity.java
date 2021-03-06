package com.example.yrrah.yrrahmoney;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
    String spinnerCategory;

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

    public void stat2ButtonPressed(View view){ // TODO : Change name!!! If I decide to use this method.
        // stat2Button Pressed...
        Toast.makeText(getApplicationContext(),"stat2ButtonPressed", Toast.LENGTH_SHORT).show();
    }

    public void addButtonPressed(View view){
        Intent intent = new Intent(this, Dev4Activity.class);
        startActivity(intent);
    }

    public void quickAdd(View view){

        EditText quickAddNr = (EditText) findViewById(R.id.quickAddNr); // amount added in quickAdd
        String nrInput = quickAddNr.getText().toString();
        if(!nrInput.equals("")){
            int quickAddInt = Integer.parseInt(nrInput)*(-1);
            DBHandler dbHandler = new DBHandler(this);

            SubAmountModel sm = new SubAmountModel(1,quickAddInt,"QuickAdd",spinnerCategory,20180106); // TODO : Fix current date.
            dbHandler.addSubAmount(sm);

            quickAddNr.setText("");
            Toast.makeText(getApplicationContext(),spinnerCategory + " added!", Toast.LENGTH_SHORT).show();

            updateMainDisplayBoard(quickAddInt);
        }else{
            Toast.makeText(getApplicationContext(),"No value added", Toast.LENGTH_SHORT).show();
        }
    }

    private void setup(){
        TextView incomeNr = (TextView) findViewById(R.id.incomeNr);
        TextView expenditureNr = (TextView) findViewById(R.id.expenditureNr);
        TextView totalNr = (TextView) findViewById(R.id.totalNr);

        Spinner dropDownList = (Spinner) findViewById(R.id.quickAddSpinner);

        DBHandler dbHandler = new DBHandler(this);

        List<CategoryModel> categoryModelList = dbHandler.getAllCategories();
        List<SubAmountModel> transactionList = dbHandler.getAllSubAmounts();

        if(categoryModelList.size() < 2){
            CategoryModel cm = new CategoryModel("Income", 0);
            dbHandler.addCategory(cm);
            categoryModelList.add(cm);
            cm = new CategoryModel("Expenditure", 0);
            dbHandler.addCategory(cm);
            categoryModelList.add(cm);
        }

        int[] incExp = getIncomeExpenditure(transactionList);

        incomeNr.setText(String.format("%s",incExp[0]));
        expenditureNr.setText(String.format("%s",incExp[1]));
        totalNr.setText(String.format("%s",dbHandler.totalAmount()));


        // Populate the DropDown eventchoice list
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,convertCategoryList(categoryModelList));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropDownList.setAdapter(adapter);

        dropDownList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerCategory = parent.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                spinnerCategory = null;
            }
        });
    }

    /**
     * Helpmethod to convert a list of CategoryModels to ArrayList<String>.
     * Since Category names are unique, we don't need to remove duplicates.
     *
     * @param categoryModelList - List of categories in model form
     * @return categoryNames - List of categories in ArrayList<String> form
     */
    private ArrayList<String> convertCategoryList(List<CategoryModel> categoryModelList){
        ArrayList<String> categoryNames = new ArrayList<>();
        for(CategoryModel cm : categoryModelList){
            categoryNames.add(cm.getName());
        }
        return categoryNames;
    }

    /**
     * This is a help method to sort of what transactions are incoms and expenditures.
     *
     * @param transactionList - List of all transactions in the Database
     * @return int[] - int[0] contains all incomes and int[1] all expenditures.
     */
    private int[] getIncomeExpenditure(List<SubAmountModel> transactionList){

        int[] incExp = {0,0};

        for(SubAmountModel sam : transactionList){
            int amount = sam.getAmount();
            if(amount > 0){
                incExp[0] = incExp[0] + amount;
            }else{
                incExp[1] = incExp[1] + amount;
            }
        }
        return incExp;
    }

    /**
     * TODO : Fix this method in a new thread.
     * Help method to update MainActivity's board display. Only supports quickAdd!!
     */
    private void updateMainDisplayBoard(int newExpenditure){
        TextView expenditureNr = (TextView) findViewById(R.id.expenditureNr);
        TextView totalNr = (TextView) findViewById(R.id.totalNr);

        expenditureNr.setText(String.format("%s",Integer.parseInt(expenditureNr.getText().toString()) + newExpenditure));
        totalNr.setText(String.format("%s",Integer.parseInt(totalNr.getText().toString()) + newExpenditure));
    }
}