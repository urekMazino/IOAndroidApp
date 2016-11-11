package com.carbajal.danniel.ioapp.models.programacionlineal.simplex;
import java.util.ArrayList;

import com.carbajal.danniel.ioapp.models.programacionlineal.ModeloOptimizacionLineaImp;
import com.carbajal.danniel.ioapp.models.programacionlineal.simplex.operaciones.TablaAnalisis;
import com.carbajal.danniel.ioapp.models.programacionlineal.simplex.operaciones.TablaModificador;

public class Simplex extends ModeloOptimizacionLineaImp {

	private double[][] _tablaSimplex;
	
	
	public void FuncionObjetivoEstandar(){
		ArrayList<Double> variablesRestriccion = _funcionObjetivoa.getVariablesRestriccion();
		for (int i=0;i<variablesRestriccion.size();i++){
			variablesRestriccion.set(i, variablesRestriccion.get(i)*-1);
		}
	}
	
	public void algoritmoSimplex(){
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
		_tablaSimplex = TablaSimplexBuilder.crearTablaSimplex(_funcionObjetivoa,_restricciones);
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
