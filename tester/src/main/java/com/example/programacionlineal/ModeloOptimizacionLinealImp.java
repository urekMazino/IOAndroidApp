package com.example.programacionlineal;

import java.util.ArrayList;

/**
 * Created by daniel on 11/17/16.
 */

public class ModeloOptimizacionLinealImp {
    protected FuncionObjetivo funcionObjetivo;



    protected ArrayList<Restriccion> restricciones = new ArrayList<Restriccion>();

    public void setFuncionObjetivo(FuncionObjetivo funcionObjetivo){
        this.funcionObjetivo = funcionObjetivo;
    }
    public FuncionObjetivo getFuncionObjetivo() {
        return funcionObjetivo;
    }
    public ArrayList<Restriccion> getRestricciones() {
        return restricciones;
    }

    public void agregarRestriccion(Restriccion...restricciones){
        for (Restriccion restriccion:restricciones){
            this.restricciones.add(restriccion);
        }
    }
}
