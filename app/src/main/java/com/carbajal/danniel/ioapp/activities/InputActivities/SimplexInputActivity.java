package com.carbajal.danniel.ioapp.activities.InputActivities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.carbajal.danniel.ioapp.R;

public class SimplexInputActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setUpActionBar();
        setContentView(R.layout.activity_simplex_input);

    }

    private void setUpActionBar(){
        ActionBar actionBar = getSupportActionBar();

        actionBar.setTitle(getResources().getString(R.string.Simplex));

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
