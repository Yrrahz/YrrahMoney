package com.example.yrrah.yrrahmoney;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class addAndConfigureActivity extends AppCompatActivity {

    String categoryTitle;
    List<SubAmountModel> subAmountModelList;
    Boolean radioButton; // <-- if more radiobuttons will be added, this can't be boolean.
    String eventChoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_and_configure);

        setUp(); // populates lists, sets defaults and global variables.
    }

    /**
     * This method returns what the user has chosen in the AAC activity.
     * This will add the chosen amount to an Event under a Category.
     * The user either choose Events from a list or creates a new one.
     *
     * numberToAdd - This is the number the user typed in the small number field.
     * @param view - I dont actually know what this is.
     */
    public void addConfigEventAndAmount(View view){
        Intent returnIntent = new Intent();

        EditText editNumberToAdd = (EditText) findViewById(R.id.addAndConfigureNumber);
        int numberToAdd = Integer.parseInt(editNumberToAdd.getText().toString());

        if(radioButton){ // if "Add Event" is checked, chose the text from that input
            EditText editEventToAdd = (EditText) findViewById(R.id.addEvent);
            String eventToAdd = editEventToAdd.getText().toString().trim().replaceAll(" ","_");
            returnIntent.putExtra("amountToAdd", numberToAdd); // Return varaible numberToAdd and call it "numberToAdd"
            returnIntent.putExtra("eventToAdd", eventToAdd); // Return varaible eventToAdd and call it "eventToAdd"
        }else{ // if "choose from list" is checked, chose the event from the DropDownList
            returnIntent.putExtra("amountToAdd", numberToAdd); // Return varaible numberToAdd and call it "numberToAdd"
            returnIntent.putExtra("eventToAdd", eventChoice); // Return varaible eventChoice from the DropDownList and call it "eventToAdd"
        }

        returnIntent.putExtra("toCategory", categoryTitle);
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }

    /**
     * This method determins what happens when any of the radio buttons are clicked.
     * If the user selects "Add event" the DropDown list will disappear and an Edittext for
     * entering a new Event will appear. And vice versa..
     * @param view -
     */
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        EditText et = (EditText) findViewById(R.id.addEvent);
        Spinner dropDownList = (Spinner) findViewById(R.id.eventDropDownList);

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.chooseAdd:
                if (checked)
                    // The user wants to add a new Event
                    et.setVisibility(View.VISIBLE);
                    et.setFocusableInTouchMode(true);

                    dropDownList.setVisibility(View.INVISIBLE);
                    radioButton = true;
                    break;
            case R.id.chooseList:
                if (checked)
                    // The user wants to choose event from a list
                    et.setVisibility(View.INVISIBLE);
                    et.setFocusable(false);

                    dropDownList.setVisibility(View.VISIBLE);
                    radioButton = false;
                    break;
        }
    }

    /**
     * This button should display a list of all events and their amount.
     * @param view -
     */
    // TODO : Finish this.
    public void onShowButtonClicked(View view){
        StringBuilder sb = new StringBuilder();
        for(SubAmountModel sm : subAmountModelList){
            sb.append(sm.getEvent());
            sb.append("\n");
            sb.append(sm.getAmount());
            sb.append("\n");
            sb.append(sm.getDate());
            sb.append("\n\n");
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Event Statistics:");
        builder.setCancelable(true);
        builder.setMessage(sb.toString());

        builder.setNegativeButton("Okay", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    /**
     * This method sets up the behaviour of different entities in the AAC activiy.
     * Such as default choices, texts in textviews... and setup for the Spinner.
     */
    private void setUp(){
        DBHandler db = new DBHandler(this);
        // Set global variables given from previous activity.
        Intent intent = getIntent();
        categoryTitle = intent.getExtras().getString("categoryTitle");
        subAmountModelList = db.getAllSubToCategory(categoryTitle); // TODO : Add logic if this is empty
        // Find Label
        TextView textLabel = (TextView) findViewById(R.id.addAndConfigureTextView);
        String label = categoryTitle;
        textLabel.setText(label);
        // Statistics for the user, TotalAmount and TotalEvents
        String totalEventsText = "Total Events: "+subAmountModelList.size();
        textLabel = (TextView) findViewById(R.id.totalEventsText);
        textLabel.setText(totalEventsText);
        String totalAmountsText = "Total Amounts: "+db.getCategoryModel(categoryTitle).getTotalAmount();
        textLabel = (TextView) findViewById(R.id.totalAmountText);
        textLabel.setText(totalAmountsText);
        // Per default, remove "Add Event" from view
        EditText et = (EditText) findViewById(R.id.addEvent);
        et.setFocusable(false);
        et.setVisibility(View.INVISIBLE);
        // Have "chooseList" chosen by default
        RadioGroup rg = (RadioGroup) findViewById(R.id.radioGroup);
        rg.check(findViewById(R.id.chooseList).getId());
        radioButton = false;
        // Populate the DropDown eventchoice list
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,convertSubAmountList(subAmountModelList));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner dropDownList = (Spinner) findViewById(R.id.eventDropDownList);
        dropDownList.setAdapter(adapter);

        dropDownList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                eventChoice = parent.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                eventChoice = null;
            }
        });
    }

    /**
     * Just a helping method to convert a list to arraylist
     * @param subAmountList - The list with subModelAmounts from the database. This list needs to
     *                      be converted to an ArrayList in order to be used in the spinner.
     * @return - ArrayList<String> - Return the converted list of subAmountList.
     */
    private ArrayList<String> convertSubAmountList(List<SubAmountModel> subAmountList){
        ArrayList<String> convertedList = new ArrayList<>();
        for(SubAmountModel subModel: subAmountList){
            if(!convertedList.contains(subModel.event)){ // Only show the different events.
                convertedList.add(subModel.getEvent());
            }
        }
        return convertedList;
    }
}