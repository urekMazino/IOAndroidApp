package com.carbajal.danniel.ioapp.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.carbajal.danniel.ioapp.R;
import com.carbajal.danniel.ioapp.models.programacionlineal.FuncionObjetivo;
import com.carbajal.danniel.ioapp.models.programacionlineal.ModeloOptimizacionLinealImp;
import com.carbajal.danniel.ioapp.models.programacionlineal.Restriccion;
import com.carbajal.danniel.ioapp.views.output.SimplexController;

import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity{

    private ModeloOptimizacionLinealImp modeloOptimizacion;
    private SimplexController simplexController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setUpActionBar();

        Bundle data = getIntent().getExtras();
        modeloOptimizacion = new ModeloOptimizacionLinealImp((FuncionObjetivo) data.getParcelable("funcion_objetivo"));

        ArrayList<Restriccion> restriccions = getIntent().getParcelableArrayListExtra("restricciones");
        modeloOptimizacion.agregarRestriccion(restriccions.toArray(new Restriccion[restriccions.size()]));

        setContentView(R.layout.activity_pop);

        simplexController = new SimplexController(this,modeloOptimizacion);

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
