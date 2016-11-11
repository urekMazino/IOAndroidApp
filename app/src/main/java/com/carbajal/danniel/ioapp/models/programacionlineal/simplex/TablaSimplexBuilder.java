package com.carbajal.danniel.ioapp.models.programacionlineal.simplex;
import com.carbajal.danniel.ioapp.models.programacionlineal.FuncionObjetivo;
import com.carbajal.danniel.ioapp.models.programacionlineal.Restriccion;
import com.carbajal.danniel.ioapp.models.programacionlineal.TiposIgualdades;

import java.util.ArrayList;

public abstract class
TablaSimplexBuilder {
	
	public static double[][] crearTablaSimplex(FuncionObjetivo funcionObjetivoa, ArrayList<Restriccion> restricciones){
		int numeroRestricciones = restricciones.size();
		int numeroVariablesRestriccion = funcionObjetivoa.getVariablesRestriccion().size();
		int numeroColumnas= numeroRestricciones+numeroVariablesRestriccion+1;
		double [][] tablaSimplex = new double[numeroRestricciones+1][numeroColumnas];
		
		llenarPrimerRenglon(funcionObjetivoa.getVariablesRestriccion(),tablaSimplex);
		renglonesRestricciones(restricciones,tablaSimplex);
		
		return tablaSimplex;
	}
	
	private static void llenarPrimerRenglon(ArrayList<Double> variablesRestriccionFuncionObjetivo,double[][] tablaSimplex){
		
		for (int i=0;i<variablesRestriccionFuncionObjetivo.size();i++){
			tablaSimplex[0][i] = variablesRestriccionFuncionObjetivo.get(i);
		}
		
	}
	
	private static void renglonesRestricciones(ArrayList<Restriccion> restricciones,double[][] tablaSimplex){		
		int numeroRestricciones = restricciones.size();
		
		for (int i=0;i<numeroRestricciones;i++){
			Restriccion restriccion = restricciones.get(i);
			ArrayList<Double> variablesRestriccion = restriccion.getVariablesRestriccion();
		
			for (int j=0;j<variablesRestriccion.size();j++){
				tablaSimplex[1+i][j] = variablesRestriccion.get(j);
				if (i==j){
					tablaSimplex[1+i][j+variablesRestriccion.size()] = (restriccion.getIgualdad()==TiposIgualdades.MENOR_IGUAL)?1:-1;
				}
			}
			tablaSimplex[1+i][variablesRestriccion.size()+numeroRestricciones]=restriccion.getExpresionNumerica();
			
		}
	}
	
}
