package com.carbajal.danniel.ioapp.models.programacionlineal;
import java.util.ArrayList;

public abstract class ModeloOptimizacionLineaImp {

	protected FuncionObjetivo _funcionObjetivoa;
	protected ArrayList<Restriccion> _restricciones = new ArrayList<Restriccion>();
	
	public void setFuncionObjetivo(FuncionObjetivo funcionObjetivoa){
		_funcionObjetivoa = funcionObjetivoa;
	}
	
	public void imprimirFuncionObjetivo(){
		System.out.print(((_funcionObjetivoa.getMaximizar()==true)?"Max":"Min")+"Z = ");
		int i =1;
		for ( Double variableRestriccion: _funcionObjetivoa.getVariablesRestriccion()){
			System.out.print("+ "+variableRestriccion+"x"+(i++));
		}
		System.out.println();
	}
	
	public void agregarRestriccion(Restriccion...restricciones){
		for (Restriccion restriccion:restricciones){
			_restricciones.add(restriccion);
		}
	}
	
}
