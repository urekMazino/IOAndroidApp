package com.carbajal.danniel.ioapp.views.output.Grafico;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.ViewPager;

import com.carbajal.danniel.ioapp.R;
import com.carbajal.danniel.ioapp.models.programacionlineal.ModeloOptimizacionLinealImp;
import com.carbajal.danniel.ioapp.models.programacionlineal.grafico.Grafico;
import com.carbajal.danniel.ioapp.views.GraphCustom;
import com.carbajal.danniel.ioapp.views.input.InputView;
import com.carbajal.danniel.ioapp.views.output.CustomViewPager;
import com.carbajal.danniel.ioapp.views.output.ModelView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;

/**
 * Created by daniel on 11/18/16.
 */

public class GraficoController implements Grafico.Eventos{

    private Context context;
    private Activity activity;
    private GraphCustom grafica;
    private GraphCustom grafica2;
    private GraphCustom grafica3;
    private GraphCustom grafica4;
    private CustomViewPager resultViewPager;
    private ModeloOptimizacionLinealImp modeloOptimizacion;

    public GraficoController(Context context, ModeloOptimizacionLinealImp modeloOptimizacion){
        this.context = context;
        this.activity = (Activity)context;
        this.modeloOptimizacion = modeloOptimizacion;
        initViewPager();

        ModelView modeloInicial = new ModelView(context,modeloOptimizacion);
        modeloInicial.setTitle("Modelo Inicial");
        resultViewPager.addPage(modeloInicial,"Modelo Inicial");

        grafica = new GraphCustom(getContext());
        Grafico grafico = new Grafico(modeloOptimizacion,this);
        grafico.ejecutarAlgoritmo();

    }
    public ViewPager initViewPager(){
        resultViewPager = (CustomViewPager)activity.findViewById(R.id.result_view_pager);
        return resultViewPager;
    }

    public Context getContext(){
        return context;
    }

    @Override
    public void agregarRestriccionBound(LineGraphSeries<DataPoint> linea) {
        grafica.addBoundRestriction(linea);
    }

    @Override
    public void agregarRestriccionUnbound(LineGraphSeries<DataPoint> linea) {
        grafica.addUnboundRestriction(linea);
    }

    @Override
    public void graficarRestricciones() {
        InputView inputView = new InputView(getContext());
        inputView.setTitle("Graficar Restricciones");
        inputView.addContent(grafica);
        resultViewPager.addPage(inputView,"Graficar Restricciones");
    }

    @Override
    public void graficarPuntos(ArrayList<DataPoint> puntos) {
        InputView inputView = new InputView(getContext());
        inputView.setTitle("Intersecciones");
        grafica2 = new GraphCustom(getContext(),grafica);
        grafica2.addPuntos(puntos);
        inputView.addContent(grafica2);
        resultViewPager.addPage(inputView,"Intersecciones");
    }

    @Override
    public void graficarAreaSolucion(ArrayList<DataPoint> puntos) {
        InputView inputView = new InputView(getContext());
        inputView.setTitle("Puntos Solucion");
        grafica4 = new GraphCustom(getContext(),grafica2);
        grafica3 = new GraphCustom(getContext(),grafica2);
        grafica3.addPuntosSolucion(puntos);
        inputView.addContent(grafica3);
        resultViewPager.addPage(inputView,"Puntos Solucion");
    }

    @Override
    public void graficarSolucion(DataPoint puntoSolucion) {
        InputView inputView = new InputView(getContext());
        inputView.setTitle("Punto Solucion");
        grafica4.addSolucion(puntoSolucion);
        inputView.addContent(grafica4);
        resultViewPager.addPage(inputView,"Punto Solucion");
    }

    @Override
    public void imprimirSolucion(DataPoint puntoSolucion) {
        InputView inputView = new GraficoResultView(getContext(),puntoSolucion,modeloOptimizacion.getFuncionObjetivo());
        resultViewPager.addPage(inputView,"Solucion");

    }
}
