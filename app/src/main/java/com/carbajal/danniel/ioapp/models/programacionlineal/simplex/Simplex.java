package com.carbajal.danniel.ioapp.models.programacionlineal.simplex;

import android.util.Log;

import com.carbajal.danniel.ioapp.models.programacionlineal.LPDuality;
import com.carbajal.danniel.ioapp.models.programacionlineal.ModeloOptimizacionLinealImp;
import com.carbajal.danniel.ioapp.models.programacionlineal.simplex.operaciones.TablaAnalisis;
import com.carbajal.danniel.ioapp.models.programacionlineal.simplex.operaciones.TablaModificador;
import com.carbajal.danniel.ioapp.support.StringManipulation;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class Simplex{


	private boolean originalMin = false;
	private ModeloOptimizacionLinealImp modeloOptimizacionLinealImp;
	private double[][] tablaSimplex;
    private String[] rowNames;
	private String[] columnNames;
	private HashMap<String,Integer> rowReference;
	private Events listener;

	public Simplex(ModeloOptimizacionLinealImp modeloOptimizacionLinealImp,Events listener){
		this.modeloOptimizacionLinealImp = modeloOptimizacionLinealImp;
		this.listener = listener;
		checkMinMax();
		FuncionObjetivoEstandar();
	}

	public void ejecutarAlgoritmo(){
		if (modeloOptimizacionLinealImp == null){
			return;
		}
		CrearTablaSimplex();
		setNames();
		listener.initialTable(tablaSimplex,rowNames);
		int columnaEntrada,renglonSalida;
		do{
			columnaEntrada = TablaAnalisis.buscarColumnaEntrada(tablaSimplex);
			if (columnaEntrada<0){
				break;
			}
			renglonSalida = TablaAnalisis.buscarRenglonSalida(tablaSimplex, columnaEntrada);
			TablaModificador.renglonSobreElementoPivote(tablaSimplex[renglonSalida], tablaSimplex[renglonSalida][columnaEntrada]);
			TablaModificador.eliminarElementosVerticalesAPivote(tablaSimplex, renglonSalida, columnaEntrada);

			cambiarRenglonPorColumna(columnaEntrada,renglonSalida);

			listener.iterationEnd(tablaSimplex,rowNames);
		}while(true);
		listener.result((originalMin)?generarSolucionMin():generarSolucion());
	}

    private void setNames(){
		int numRestricciones = modeloOptimizacionLinealImp.getRestricciones().size();
		int numVariables = modeloOptimizacionLinealImp.getFuncionObjetivo().getVariablesRestriccion().size();
        rowNames = new String[numRestricciones+1];
		columnNames = new String[numRestricciones+numVariables+1];
		rowReference = new HashMap<>();
        rowNames[0] = "Z";
		columnNames[0] = "Z";
		rowReference.put("Z",0);
        for (int i=1;i<rowNames.length;i++){
            rowNames[i] = columnNames[i+numVariables] = "h"+ StringManipulation.subscript(i);
			rowReference.put(rowNames[i],i);
        }
		for (int i=1;i<=numVariables;i++){
			columnNames[i] = "x"+ StringManipulation.subscript(i);
			rowReference.put(columnNames[i],-1);
		}
    }
	private void FuncionObjetivoEstandar(){
		ArrayList<Double> variablesRestriccion = modeloOptimizacionLinealImp.getFuncionObjetivo().getVariablesRestriccion();
		for (int i=0;i<variablesRestriccion.size();i++){
			variablesRestriccion.set(i, variablesRestriccion.get(i)*-1);
		}
        listener.standarized(modeloOptimizacionLinealImp);

	}
	public void checkMinMax(){
		if (!modeloOptimizacionLinealImp.getFuncionObjetivo().getMaximizar()){
			modeloOptimizacionLinealImp = LPDuality.DualModel(modeloOptimizacionLinealImp);
			listener.transposed(modeloOptimizacionLinealImp);
			originalMin = true;
		}
	}


	public String[] generarSolucion(){
		String[] soluciones = new String[tablaSimplex[0].length+1];
		int index=0;
		int numRestricciones = modeloOptimizacionLinealImp.getRestricciones().size();
		int numVariables = modeloOptimizacionLinealImp.getFuncionObjetivo().getVariablesRestriccion().size();
		soluciones[index++] = getResultString("Z");
		for (int i=1;i<=numVariables;i++){
			soluciones[index++] = getResultString("x"+ StringManipulation.subscript(i));
		}
		for (int i=1;i<=numRestricciones;i++){
			soluciones[index++] = getResultString("h"+ StringManipulation.subscript(i));
		}
		return soluciones;
	}

	public String[] generarSolucionMin(){
		String[] soluciones = new String[tablaSimplex[0].length];
        NumberFormat nf = new DecimalFormat("##.######");
		int index=1;
		int numRestricciones = modeloOptimizacionLinealImp.getRestricciones().size();
		int numVariables = modeloOptimizacionLinealImp.getFuncionObjetivo().getVariablesRestriccion().size();
		for (int i=numVariables;i<numVariables+numRestricciones;i++){
			soluciones[i-numVariables+1] = "x"+ StringManipulation.subscript(index++)+"* = "+nf.format(tablaSimplex[0][i]);
		}
		index = 1;
		for (int i=0;i<numVariables;i++){
			soluciones[i+numVariables] = "h"+ StringManipulation.subscript(index++)+"* = "+nf.format(tablaSimplex[0][i]);
		}
		soluciones[0] = "Z* = "+nf.format(tablaSimplex[0][tablaSimplex[0].length-1]);
		return soluciones;
	}
	private String getResultString(String str){
		String variableResult;

		NumberFormat nf = new DecimalFormat("##.######");

		String nombre = str;
		String value;
		int reference = rowReference.get(str);

		if (reference!=-1) {
			value = nf.format(tablaSimplex[reference][tablaSimplex[0].length - 1]);
		} else {
			value = "0";
		}
		variableResult  = (nombre + "* = " + value);
		return variableResult;
	}

	public void CrearTablaSimplex(){
		tablaSimplex = TablaSimplexBuilder.crearTablaSimplex(modeloOptimizacionLinealImp.getFuncionObjetivo(),
				modeloOptimizacionLinealImp.getRestricciones());
	}
	private void cambiarRenglonPorColumna(int columnaEntrada,int renglonSalida){
		int temp = rowReference.get(columnNames[columnaEntrada+1]);
		rowReference.put(columnNames[columnaEntrada+1], rowReference.get(rowNames[renglonSalida]));
		rowReference.put(rowNames[renglonSalida],temp);
		rowNames[renglonSalida] = columnNames[columnaEntrada+1];
	}
	private void imprimirTablaSimplex(){
		for (int i = 0; i< tablaSimplex.length; i++){
			for (int j = 0; j< tablaSimplex[i].length; j++){
				System.out.printf("%.2f\t", tablaSimplex[i][j]);
			}
			System.out.println();
		}
		System.out.println();
		Log.v("","");
	}
	public void setListener(Events listener){
		this.listener = listener;
	}

	public interface Events{
		void iterationEnd(double[][] tablaSimplex,String[] rowNames);
		void initialTable(double[][] tablaSimplex,String[] rowNames);
		void result(String[] results);
		void transposed(ModeloOptimizacionLinealImp modeloNuevo);
		void standarized(ModeloOptimizacionLinealImp modeloNuevo);
	}
}
