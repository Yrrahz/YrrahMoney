package com.example.yrrah.yrrahmoney;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Dev4Activity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dev4);

        testMethod();
    }

    private void testMethod(){
        List<Map<String, String>> data = new ArrayList<>();

        for(int i=0 ; i<20 ; i++){
            Map<String, String> dateMap = new HashMap<>(2);
            dateMap.put("item","Category "+(i+1));
            dateMap.put("subItem","Total Amount: "+(i+10));

            data.add(dateMap);
        }

        SimpleAdapter adapter = new SimpleAdapter(this, data,
                android.R.layout.simple_list_item_2,
                new String[] {"item","subItem"}, // <-- must be same as dateMap's keys
                new int[] {android.R.id.text1, android.R.id.text2});


        ListView presentData = (ListView) findViewById(R.id.listA);
        if(presentData != null){
            presentData.setAdapter(adapter);
        }
    }
}
