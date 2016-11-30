package com.carbajal.danniel.ioapp.activities.InputActivities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.ViewGroup;

import com.carbajal.danniel.ioapp.R;
import com.carbajal.danniel.ioapp.views.input.transporte.TransporteController;

public class TransportInputActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setUpActionBar();
        setContentView(R.layout.activity_transport_input);

        ViewGroup root = (ViewGroup)findViewById(R.id.activity_transport_input);
        TransporteController transporteController = new TransporteController(this);
        root.addView(transporteController.getCustomViewPager());

    }

    private void setUpActionBar(){
        ActionBar actionBar = getSupportActionBar();

        actionBar.setTitle(getResources().getString(R.string.NorthwestCorner));

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
}
