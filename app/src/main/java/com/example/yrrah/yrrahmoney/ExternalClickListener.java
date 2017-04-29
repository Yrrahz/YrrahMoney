package com.example.yrrah.yrrahmoney;

import android.view.View;

/**
 * Created by Yrrah on 2017-04-29.
 */

public class ExternalClickListener implements View.OnClickListener{

    development2Activity d2;
    public ExternalClickListener(development2Activity d2){
        this.d2 = d2;
    }

    @Override public void onClick(View v) {
        d2.addButtonClicked(v);
    }
}
