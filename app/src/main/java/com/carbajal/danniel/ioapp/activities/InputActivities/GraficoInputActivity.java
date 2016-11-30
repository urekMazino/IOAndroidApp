package com.carbajal.danniel.ioapp.activities.InputActivities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.carbajal.danniel.ioapp.R;
import com.carbajal.danniel.ioapp.views.input.ModelViewPager;

public class GraficoInputActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setUpActionBar();
        setContentView(R.layout.activity_simplex_input);

        ModelViewPager modelViewPager = (ModelViewPager)findViewById(R.id.model_view_pager);
        modelViewPager.setMaxVariables(2);
    }

    private void setUpActionBar(){
        ActionBar actionBar = getSupportActionBar();

        actionBar.setTitle(getResources().getString(R.string.Graphical));

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
