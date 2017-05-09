package com.example.yrrah.yrrahmoney;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class addAndConfigureActivity extends AppCompatActivity {

    String categoryTitle;
    String categoryData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_and_configure);

        Intent intent = getIntent();
        categoryTitle = intent.getExtras().getString("categoryTitle");
        categoryData = intent.getExtras().getString("categoryData");

        findLabel();
    }

    /**
     * When a person has pressed a categoryAdd button, they get to this AAC-activity
     * and this method is adding what the user choosed to add to that perticular category.
     *
     * numberToAdd - This is the number the user typed in the small number field.
     * @param view - I dont actually know what this is.
     */
    public void addConfigureNumber(View view){
        EditText editNumberToAdd = (EditText) findViewById(R.id.addAndConfigureNumber);
        int numberToAdd = Integer.parseInt(editNumberToAdd.getText().toString());

        Intent returnIntent = new Intent();
        returnIntent.putExtra("result",numberToAdd);
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }

    private void findLabel(){
        TextView textLabel = (TextView) findViewById(R.id.addAndConfigureTextView);
        String label = "Category: "+categoryTitle;
        textLabel.setText(label);
    }
}
