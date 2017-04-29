package com.example.yrrah.yrrahmoney;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class development2Activity extends AppCompatActivity {

    int buttonChoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_development2);

        Intent intent = getIntent();
        buttonChoice = intent.getExtras().getInt("choice");
    }


    public void development2TestMethod(View view){

    }
}
