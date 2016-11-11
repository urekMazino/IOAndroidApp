package com.carbajal.danniel.ioapp.models.programacionlineal.simplex.operaciones;

public abstract class TablaAnalisis {
	
	public static int buscarColumnaEntrada(double[][] tablaSimplex){
		double masNegativo = 0;
		int columnaSeleccionada=-1;
		for (int j=0;j<tablaSimplex.length;j++){
			if(tablaSimplex[0][j]<masNegativo){
				masNegativo = tablaSimplex[0][j];
				columnaSeleccionada = j;
			}
		}
		return columnaSeleccionada;
	}
	
	public static int buscarRenglonSalida(double[][] tablaSimplex,int columnaEntrada){
		int renglonSeleccionado = 0;
		double valorMinimo = Double.MAX_VALUE;
		int ultimoIndice = tablaSimplex[0].length-1;
		
		for (int i=0;i<tablaSimplex.length;i++){
			if (tablaSimplex[i][ultimoIndice] > 0 && tablaSimplex[i][columnaEntrada]>0){
				double valorCalculado = tablaSimplex[i][ultimoIndice]/tablaSimplex[i][columnaEntrada];
				if(valorCalculado < valorMinimo){
					renglonSeleccionado = i;
					valorMinimo = valorCalculado;
				}
			}
		}
		return renglonSeleccionado;
	}
	
}
