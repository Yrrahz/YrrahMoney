package com.example.yrrah.yrrahmoney;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class mainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void categoryButtonPressed(View view){
        int choice = Integer.parseInt(view.getTag().toString());
        Toast.makeText(getApplicationContext(),"categoryButton "+choice,Toast.LENGTH_SHORT).show();
        addAndConfigure(choice);
    }

    /**
     * addAndConfigure - Denna metod kan vara som startNewActivity.
     * I alla fall i dagsläget.
     *
     * int choice - Vilken knapp som trycktes, uppifrån och ner, 1-N
     */
    private void addAndConfigure(int choice){
        Intent intent = new Intent(this, addAndConfigureActivity.class);
        intent.putExtra("choice", choice);
        startActivity(intent);
    }
}
