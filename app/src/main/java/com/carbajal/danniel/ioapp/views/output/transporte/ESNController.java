package com.carbajal.danniel.ioapp.views.output.transporte;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.ViewPager;

import com.carbajal.danniel.ioapp.R;
import com.carbajal.danniel.ioapp.models.programacionlineal.transporte.ESN.ESN;
import com.carbajal.danniel.ioapp.models.programacionlineal.transporte.TablaTransporte;
import com.carbajal.danniel.ioapp.views.output.CustomViewPager;
import com.carbajal.danniel.ioapp.views.output.ModelView;

/**
 * Created by daniel on 11/23/16.
 */

public class ESNController implements ESN.Events{

    private Context context;
    private Activity activity;
    private CustomViewPager resultViewPager;
    TablaTransporte tablaTransporte;
    private int paso = 1;

    public ESNController(Context context,TablaTransporte tablaTransporte){
        this.context = context;
        this.activity = (Activity)context;
        initViewPager();
        this.tablaTransporte = tablaTransporte;

        ModelView modeloInicial = new ModelView(context,tablaTransporte.generarModelo());
        modeloInicial.setTitle("Modelo Inicial");
        resultViewPager.addPage(modeloInicial,"Modelo Inicial");


        ESN esn = new ESN(tablaTransporte);
        esn.setListener(this);
        esn.ejecutarAlgoritmo();

    }

    public ViewPager initViewPager(){
        resultViewPager = (CustomViewPager)activity.findViewById(R.id.result_view_pager);
        return resultViewPager;
    }

    public Context getContext(){
        return context;
    }

    @Override
    public void asignar(int r, int c, double elementos) {
        TransportePreviewView transportePreviewView = new TransportePreviewView(getContext());
        transportePreviewView.renderTable(tablaTransporte);
        transportePreviewView.setTitle("Paso "+paso);
        transportePreviewView.assignElement(r,c,elementos);

        resultViewPager.addPage(transportePreviewView,"Paso "+paso);
        paso++;
    }

    @Override
    public void initialTable() {
        TransportePreviewView transportePreviewView = new TransportePreviewView(getContext());
        transportePreviewView.renderTable(tablaTransporte);
        transportePreviewView.setTitle("Tabla Inicial");
        resultViewPager.addPage(transportePreviewView,"Tabla Inicial");
    }

    @Override
    public void solucion(String stringSolucion) {
        TransportePreviewView transportePreviewView = new TransportePreviewView(getContext());
        transportePreviewView.renderTable(tablaTransporte);
        transportePreviewView.setTitle("Tabla final");
        resultViewPager.addPage(transportePreviewView,"Tabla final");

        ResultTransporteView inputView = new ResultTransporteView(getContext(),stringSolucion);
        inputView.setTitle("Solucion");
        resultViewPager.addPage(inputView,"Solucion");

    }
}
