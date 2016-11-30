package com.carbajal.danniel.ioapp.activities.OutputActivities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.carbajal.danniel.ioapp.R;
import com.carbajal.danniel.ioapp.models.programacionlineal.transporte.TablaTransporte;
import com.carbajal.danniel.ioapp.views.output.transporte.ESNController;

public class ResultTransporteActivity extends AppCompatActivity {

    TablaTransporte tablaTransporte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setUpActionBar();

        setContentView(R.layout.activity_pop);
        Bundle data = getIntent().getExtras();
        tablaTransporte = data.getParcelable("tabla_transporte");
        new ESNController(this,tablaTransporte);
    }

    private void setUpActionBar(){
        ActionBar actionBar = getSupportActionBar();

        actionBar.setTitle("Resultado");

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
