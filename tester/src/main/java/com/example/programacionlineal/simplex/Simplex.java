package com.example.programacionlineal.simplex;


import com.example.programacionlineal.ModeloOptimizacionLinealImp;
import com.example.programacionlineal.simplex.operaciones.TablaAnalisis;
import com.example.programacionlineal.simplex.operaciones.TablaModificador;

import java.util.ArrayList;

public class Simplex{


	private ModeloOptimizacionLinealImp modeloOptimizacionLinealImp;
	private double[][] _tablaSimplex;

	public Simplex(ModeloOptimizacionLinealImp modeloOptimizacionLinealImp){
		this.modeloOptimizacionLinealImp = modeloOptimizacionLinealImp;
		FuncionObjetivoEstandar();
	}

	private void FuncionObjetivoEstandar(){
		ArrayList<Double> variablesRestriccion = modeloOptimizacionLinealImp.getFuncionObjetivo().getVariablesRestriccion();
		for (int i=0;i<variablesRestriccion.size();i++){
			variablesRestriccion.set(i, variablesRestriccion.get(i)*-1);
		}
	}
	
	public void algoritmoSimplex(){
		if (modeloOptimizacionLinealImp == null){
			return;
		}
		CrearTablaSimplex();
		int columnaEntrada,renglonSalida;
		do{
			columnaEntrada = TablaAnalisis.buscarColumnaEntrada(_tablaSimplex);
			if (columnaEntrada<0){
				break;
			}
			renglonSalida = TablaAnalisis.buscarRenglonSalida(_tablaSimplex, columnaEntrada);
			TablaModificador.renglonSobreElementoPivote(_tablaSimplex[renglonSalida], _tablaSimplex[renglonSalida][columnaEntrada]);
			TablaModificador.eliminarElementosVerticalesAPivote(_tablaSimplex, renglonSalida, columnaEntrada);
			
			imprimirTablaSimplex();
		}while(true);
	}
	
	
	public void CrearTablaSimplex(){
		_tablaSimplex = TablaSimplexBuilder.crearTablaSimplex(modeloOptimizacionLinealImp.getFuncionObjetivo(),
				modeloOptimizacionLinealImp.getRestricciones());
	}

	private void imprimirTablaSimplex(){
		for (int i=0;i<_tablaSimplex.length;i++){
			for (int j=0;j<_tablaSimplex[i].length;j++){
				System.out.printf("%.2f\t",_tablaSimplex[i][j]);
			}
			System.out.println();
		}
		System.out.println();
	}
}
