package com.carbajal.danniel.ioapp.models.programacionlineal.simplex;

public class Resultado {
	
	private double _solucionOptima;
	private double[] _variablesRestriccionOptimas;
	private double[] _variablesHolguraOptimas;
	
	private int[] _variablesRestriccionRenglon;
	private int[] _variablesHolguraRenglon;
	
	public Resultado(int numVariablesRestriccion,int numVariablesHolgura){
		_variablesRestriccionOptimas = new double[numVariablesRestriccion];
		_variablesHolguraOptimas = new double[numVariablesHolgura];
		_variablesRestriccionRenglon = new int[numVariablesRestriccion];
		_variablesHolguraRenglon = new int[numVariablesHolgura];
	}
	
	public double[] getVariablesRestriccion(){
		return _variablesRestriccionOptimas;
	}
	
	public double[] getVariablesHolgura(){
		return _variablesRestriccionOptimas;
	}
	
	public int[] getVariablesRestriccionRenglon(){
		return _variablesRestriccionRenglon;
	}
	
	public int[] getVariablesHolguraRenglon(){
		return _variablesHolguraRenglon;
	}
	public double getSolucionOptima(){
		return _solucionOptima;
	}
	public void setSolucionOptima(double solucionOptima){
		
	}
}
