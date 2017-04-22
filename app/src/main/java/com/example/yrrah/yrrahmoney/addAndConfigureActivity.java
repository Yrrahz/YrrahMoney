package com.example.yrrah.yrrahmoney;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class addAndConfigureActivity extends AppCompatActivity {

    int buttonChoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addAndConfigure);

        Intent intent = getIntent();
        buttonChoice = intent.getExtras().getInt("choice");
    }
}
