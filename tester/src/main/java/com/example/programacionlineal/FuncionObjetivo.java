package com.example.programacionlineal;

import java.util.ArrayList;


public class FuncionObjetivo {
	
	private ArrayList<Double> _variablesRestriccion = new ArrayList<Double>();
	private boolean _maximizar = true;
	
	public FuncionObjetivo(double...valoresDeVariables){
		agregarVariableRestriccion(valoresDeVariables);
	}
	
	public FuncionObjetivo(Boolean maximizar, double...valoresDeVariables){
		this(valoresDeVariables);
		setMaximizar(maximizar);
	}
	
	public void agregarVariableRestriccion(double...valoresDeVariables){
		for (double valor:valoresDeVariables){
			_variablesRestriccion.add(valor);
		}
	}
	public void setMaximizar(boolean maximizar){
		_maximizar = maximizar;
	}
	public boolean getMaximizar(){
		return _maximizar;
	}
	public ArrayList<Double> getVariablesRestriccion(){
		return _variablesRestriccion;
	}
}
