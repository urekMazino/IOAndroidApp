package com.carbajal.danniel.ioapp.models.programacionlineal;

public interface ModeloOptimizacionLineal {

	public void agregarRestriccion(Restriccion...restricciones);
	
	public void setFuncionObjetivo(FuncionObjetivo funcionObjetivoa);
	
}
