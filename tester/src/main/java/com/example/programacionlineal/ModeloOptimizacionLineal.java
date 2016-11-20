package com.example.programacionlineal;

public interface ModeloOptimizacionLineal {

	public void agregarRestriccion(Restriccion... restricciones);
	
	public void setFuncionObjetivo(FuncionObjetivo funcionObjetivoa);
	
}
