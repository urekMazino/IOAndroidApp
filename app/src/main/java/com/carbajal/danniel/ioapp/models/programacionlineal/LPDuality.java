package com.carbajal.danniel.ioapp.models.programacionlineal;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by daniel on 11/18/16.
 */

public abstract class LPDuality {

    public static ModeloOptimizacionLinealImp DualModel(ModeloOptimizacionLinealImp modeloOptimizacionLineal){

        ModeloOptimizacionLinealImp nuevoModelo =
                new ModeloOptimizacionLinealImp(
                        contruirNuevaFuncion(
                                modeloOptimizacionLineal.getFuncionObjetivo().getMaximizar(),
                                modeloOptimizacionLineal.getRestricciones()
                        )
                );

        Restriccion[] restriccions = contruirRestricciones(
                modeloOptimizacionLineal.getFuncionObjetivo(),
                modeloOptimizacionLineal.getRestricciones()
        );
        for (int i=0;i<restriccions.length;i++){
            nuevoModelo.agregarRestriccion(restriccions[i]);
        }

        printModelo(nuevoModelo);

        return nuevoModelo;
    }
    private static FuncionObjetivo contruirNuevaFuncion(Boolean max,ArrayList<Restriccion> restricciones){
        FuncionObjetivo nuevaFuncion = new FuncionObjetivo();
        nuevaFuncion.setMaximizar(!max);
        for (Restriccion restriccion: restricciones){
            nuevaFuncion.agregarVariableDecision(restriccion.getExpresionNumerica());
        }
        return nuevaFuncion;
    }
    private static Restriccion[] contruirRestricciones(FuncionObjetivo funcionObjetivo,ArrayList<Restriccion> restricciones) {
        int numDeVariables = funcionObjetivo.getVariablesRestriccion().size();
        int numDeRestricciones = restricciones.size();
        Restriccion[] nuevasRestricciones = new Restriccion[numDeVariables];

        for (int i = 0; i < numDeVariables; i++) {
            Restriccion restriccion = new Restriccion(funcionObjetivo.getVariablesRestriccion().get(i));
            for (int j = 0; j < numDeRestricciones; j++) {
                restriccion.agregarVariableRestriccion(restricciones.get(j).getVariablesRestriccion().get(i));
            }
            nuevasRestricciones[i] = (restriccion);
        }
        return nuevasRestricciones;
    }
    private static void printModelo(ModeloOptimizacionLinealImp modelo){
        Log.v("funcionObj",modelo.getFuncionObjetivo().getString());
        int i=1;
        for(Restriccion res : modelo.getRestricciones()){
            Log.v("res"+i,res.getString());
        }

    }
}
