package com.carbajal.danniel.ioapp.views.output;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.ViewPager;

import com.carbajal.danniel.ioapp.R;
import com.carbajal.danniel.ioapp.models.programacionlineal.ModeloOptimizacionLinealImp;
import com.carbajal.danniel.ioapp.models.programacionlineal.simplex.Simplex;
import com.carbajal.danniel.ioapp.views.input.InputView;

/**
 * Created by daniel on 11/18/16.
 */

public class SimplexController implements Simplex.Events{

    private Context context;
    private Activity activity;
    private ResultViewPager resultViewPager;
    private ModeloOptimizacionLinealImp modeloOptimizacion;
    private int iteracion = 1;

    public SimplexController(Context context, ModeloOptimizacionLinealImp modeloOptimizacion){
        this.context = context;
        this.activity = (Activity)context;
        this.modeloOptimizacion = modeloOptimizacion;
        initViewPager();
        ModelView modeloInicial = new ModelView(context,modeloOptimizacion);
        modeloInicial.setTitle("Modelo Inicial");
        resultViewPager.addPage(modeloInicial,"Modelo Inicial");

        Simplex simplex = new Simplex(modeloOptimizacion,this);
        simplex.algoritmoSimplex();

    }
    public ViewPager initViewPager(){
        resultViewPager = (ResultViewPager)activity.findViewById(R.id.result_view_pager);
        return resultViewPager;
    }

    public Context getContext(){
        return context;
    }

    @Override
    public void iterationEnd(double[][] tablaSimplex, String[] rowNames) {
        InputView inputView = new InputView(getContext());
        inputView.setTitle("Iteracion "+(iteracion));
        inputView.addContent(new SimplexTableView(getContext(),tablaSimplex,rowNames));
        resultViewPager.addPage(inputView,"Iteracion "+(iteracion++));
    }

    @Override
    public void initialTable(double[][] tablaSimplex, String[] rowNames) {
        InputView inputView = new InputView(getContext());
        inputView.setTitle("Tabla Simplex Inicial");
        inputView.addContent(new SimplexTableView(getContext(),tablaSimplex,rowNames));
        resultViewPager.addPage(inputView,"Tabla Inicial");
    }

    @Override
    public void result(String[] results) {
        ResultSimplexView inputView = new ResultSimplexView(getContext(),results);
        inputView.setTitle("Solucion");
        resultViewPager.addPage(inputView,"Solucion");
    }

    @Override
    public void transposed(ModeloOptimizacionLinealImp modeloNuevo) {
        ModelView nuevoModelo = new ModelView(context,modeloNuevo);
        nuevoModelo.setTitle("Transformar a un modelo de maximizar");
        resultViewPager.addPage(nuevoModelo,"Transformacion");
    }

    @Override
    public void standarized(ModeloOptimizacionLinealImp modeloNuevo) {
        FuncionObjetivoView nuevoModelo = new FuncionObjetivoView(context,modeloNuevo.getFuncionObjetivo());
        nuevoModelo.setTitle("Pasar Funcion Objetivo a forma estandar");
        resultViewPager.addPage(nuevoModelo,"Forma Estandar");
    }
}
