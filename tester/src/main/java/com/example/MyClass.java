package com.example;


import com.example.programacionlineal.FuncionObjetivo;
import com.example.programacionlineal.ModeloOptimizacionLinealImp;
import com.example.programacionlineal.Restriccion;
import com.example.programacionlineal.simplex.Simplex;

public class MyClass {

    public static void main(String args[]){
        ModeloOptimizacionLinealImp model = new ModeloOptimizacionLinealImp();
        model.setFuncionObjetivo(new FuncionObjetivo(2,-1,2));
        model.agregarRestriccion(new Restriccion(10,2,1,0),
                new Restriccion(20,1,2,-2),
                new Restriccion(5,0,1,2));
        Simplex simplex = new Simplex(model);
        simplex.algoritmoSimplex();

    }
}
