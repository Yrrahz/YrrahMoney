package com.example.yrrah.yrrahmoney;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class development2Activity extends AppCompatActivity {

    private int i = 0;
    private TableLayout tableLayout;
    private ExternalClickListener ecl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_development2);

        ecl = new ExternalClickListener(this);
        tableLayout = (TableLayout) findViewById(R.id.tableLayoutTest);
    }


    public void development2TestMethod(View view){



        TableRow row = new TableRow(this);
        TextView tv;
        Button addBtn;
        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT);


        // TODO : be able to update each of the textviews by it's "Add" button.
        row.setLayoutParams(lp);
        tv = new TextView(this);
        tv.setText("helloWorld"+(i+1)); //this is not intended, currently just a placeholder.
        addBtn = new Button(this);
        //addBtn.setId(View.generateViewId()); // Do I need this?
        addBtn.setText(R.string.categoryButtonAdd);
        addBtn.setOnClickListener(ecl);


        row.addView(tv);
        row.addView(addBtn);
        tableLayout.addView(row,i);
        i++;
    }

    public void addButtonClicked(View view){
        Toast.makeText(getApplicationContext(),"Button Works!",Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, addAndConfigureActivity.class);
        startActivity(intent);
    }
}

/*
        TableRow row= new TableRow(this);
        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
        row.setLayoutParams(lp);
        checkBox = new CheckBox(this);
        tv = new TextView(this);
        addBtn = new ImageButton(this);
        addBtn.setImageResource(R.drawable.add);
        minusBtn = new ImageButton(this);
        minusBtn.setImageResource(R.drawable.minus);
        qty = new TextView(this);
        checkBox.setText("hello");
        qty.setText("10");
        row.addView(checkBox);
        row.addView(minusBtn);
        row.addView(qty);
        row.addView(addBtn);
        ll.addView(row,i);
 */