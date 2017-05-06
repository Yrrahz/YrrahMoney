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
        Intent intent = new Intent(this, Dev4Activity.class);
        startActivity(intent);
    }


    /**
     * TestDB lyckades! Vi kan skapa och hämta data ur databasen.
     * Appen crashar om man försöker hämta data från en category som
     * inte finns.
     */
    public void testDB(View view){

        DBHandler dbHandler = new DBHandler(this);

        for(int i=0 ; i<20 ; i++){
            CategoryModel cm = new CategoryModel("Transport"+i, 1337+i);
            dbHandler.addCategory(cm);
        }


        int test = dbHandler.getCategoryModel("Transport3").getTotalAmount();

        Toast.makeText(getApplicationContext(),"Database value returned: "+test, Toast.LENGTH_SHORT).show();
    }

    public void testDB2(View view){
        DBHandler dbHandler = new DBHandler(this);

        int test = dbHandler.getCategoryModel("Transport").getTotalAmount();
        Toast.makeText(getApplicationContext(),"Database value returned: "+test, Toast.LENGTH_SHORT).show();
    }
}







// ===================== OLD CODE ============================
//addAndConfigure(choice);
    /*
    /**
     * addAndConfigure - Denna metod kan vara som startNewActivity.
     * I alla fall i dagsläget.
     *
     * int choice - Vilken knapp som trycktes, uppifrån och ner, 1-N
     *
    private void addAndConfigure(int choice){
        Intent intent = new Intent(this, development2Activity.class);
        intent.putExtra("choice", choice);
        startActivity(intent);
    }*/

    /*
    private void addAndConfigure(int choice){
        Intent intent = new Intent(this, addAndConfigureActivity.class);
        intent.putExtra("choice", choice);
        startActivity(intent);
    }
     */