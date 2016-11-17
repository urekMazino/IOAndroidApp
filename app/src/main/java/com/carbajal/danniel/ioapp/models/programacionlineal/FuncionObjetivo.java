package com.carbajal.danniel.ioapp.models.programacionlineal;

import com.carbajal.danniel.ioapp.support.StringManipulation;

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

    public String getString(){
        String functionPreviewStr = "Z =";
        int i = 1;
        for (double x : _variablesRestriccion) {
            functionPreviewStr += StringManipulation.coeficientToVariable(x,i);
            i++;
        }
        return functionPreviewStr;
    }
}
