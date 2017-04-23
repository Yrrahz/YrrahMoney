package com.example.yrrah.yrrahmoney;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class addAndConfigureActivity extends AppCompatActivity {

    int buttonChoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addAndConfigure);

        Intent intent = getIntent();
        buttonChoice = intent.getExtras().getInt("choice");
    }

    /**
     * When a person has pressed a categoryAdd button, they get to this AAC-activity
     * and this method is adding what the user choosed to add to that perticular category.
     *
     * numberToAdd - This is the number the user typed in the small number field.
     * @param view - I dont actually know what this is.
     */
    public void addConfigureNumber(View view){
        EditText editNumberToAdd = (EditText) findViewById(R.id.numberToAddAndConfigure);
        int numberToAdd = Integer.parseInt(editNumberToAdd.getText().toString());
    }
}
