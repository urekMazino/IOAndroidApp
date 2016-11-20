package com.example.programacionlineal.simplex.operaciones;

public class TablaModificador {
	
	public static void renglonSobreElementoPivote(double[] renglonSalida,double elementoPivote){
		for (int j=0;j<renglonSalida.length;j++){
			renglonSalida[j] /= elementoPivote;
		}
	}
	
	public static void eliminarElementosVerticalesAPivote(double[][] tablaSimplex, int renglonSalida,int columnaEntrada){
		for (int i=0;i<tablaSimplex.length;i++){
			if (i!=renglonSalida){
				double factor = tablaSimplex[i][columnaEntrada];
				for (int j=0;j<tablaSimplex[i].length;j++){
					tablaSimplex[i][j] -= tablaSimplex[renglonSalida][j]*factor;
				}
			}
		}
	}
	
}
