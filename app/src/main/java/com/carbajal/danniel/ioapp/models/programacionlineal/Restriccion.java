package com.carbajal.danniel.ioapp.models.programacionlineal;
import java.util.ArrayList;


public class Restriccion {
	/*
		restriccion representacion A1X1 + A2X2 + .. + AnXn = expresionNumerica
		donde Ai es el coeficiente de cada variable de restriccion
	*/
	private ArrayList<Double> _variablesRestriccion = new ArrayList<Double>();
	private double _expresionNumerica=0;
	private TiposIgualdades _igualdad = TiposIgualdades.MENOR_IGUAL;
	
	public Restriccion(double expresionNumerica,double...valoresDeVariables){
		agregarVariableRestriccion(valoresDeVariables);
		setExpresionNumerica(expresionNumerica);
	}

	public void agregarVariableRestriccion(double...valoresDeVariables){
		for (double valor:valoresDeVariables){
			_variablesRestriccion.add(valor);
		}
	}
	public void setExpresionNumerica(double expresionNumerica){
		_expresionNumerica=expresionNumerica;
	}
	public double getExpresionNumerica(){
		return _expresionNumerica;
	}
	public void setIgualdad(TiposIgualdades igualdad){
		_igualdad = igualdad;
	}
	public TiposIgualdades getIgualdad(){
		return _igualdad;
	}
	public ArrayList<Double> getVariablesRestriccion(){
		return _variablesRestriccion;
	}
}
