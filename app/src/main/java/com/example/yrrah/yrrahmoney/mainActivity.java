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

    public void testButtonToast(View view){
        Toast.makeText(getApplicationContext(),"testButton pressed in mainActivity",Toast.LENGTH_SHORT).show();

        testMethod();
    }

    /**
     * Detta är så som vi gjorde i vårt exJobb, men det känns en aning... fel.
     * Eller i alla fall att det vore värt att kolla i hur man annars kan göra.
     */
    private void testMethod(){
        Intent intent = new Intent(this, indexActivity.class);
        startActivity(intent);
    }
}
