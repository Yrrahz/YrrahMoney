package com.example.yrrah.yrrahmoney;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class addAndConfigureActivity extends AppCompatActivity {

    String categoryTitle;
    String categoryData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_and_configure);

        setUp();
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
        EditText editNumberToAdd = (EditText) findViewById(R.id.addAndConfigureNumber);
        EditText editEventToAdd = (EditText) findViewById(R.id.addEvent);
        int numberToAdd = Integer.parseInt(editNumberToAdd.getText().toString());
        String eventToAdd = editEventToAdd.getText().toString();

        Intent returnIntent = new Intent();
        returnIntent.putExtra("amountToAdd", numberToAdd); // Return varaible numberToAdd and call it "numberToAdd"
        returnIntent.putExtra("eventToAdd", eventToAdd); // Return varaible eventToAdd and call it "eventToAdd"
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        EditText et = (EditText) findViewById(R.id.addEvent);

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.chooseAdd:
                if (checked)
                    // The user wants to add a new Event
                    et.setVisibility(View.VISIBLE);
                    et.setFocusableInTouchMode(true);
                    break;
            case R.id.chooseList:
                if (checked)
                    // The user wants to choose event from a list
                    et.setVisibility(View.INVISIBLE);
                    et.setFocusable(false);
                    break;
        }
    }

    private void setUp(){
        // Set global variables given from previous activity.
        Intent intent = getIntent();
        categoryTitle = intent.getExtras().getString("categoryTitle");
        categoryData = intent.getExtras().getString("categoryData"); // Dont think I need this like this. Get all subAmounts instead...
        // Find Label
        TextView textLabel = (TextView) findViewById(R.id.addAndConfigureTextView);
        String label = categoryTitle;
        textLabel.setText(label);
        // Per default, remove "Add Event" from view
        EditText et = (EditText) findViewById(R.id.addEvent);
        et.setFocusable(false);
        et.setVisibility(View.INVISIBLE);
        // Have "chooseList" chosen by default
        RadioGroup rg = (RadioGroup) findViewById(R.id.radioGroup);
        rg.check(findViewById(R.id.chooseList).getId());
    }
}
/*
* you can use EditText.setFocusable(false) to disable editing
* EditText.setFocusableInTouchMode(true) to enable editing
* */