package com.carbajal.danniel.ioapp.models.programacionlineal.transporte;

import android.os.Parcel;
import android.os.Parcelable;

import com.carbajal.danniel.ioapp.models.programacionlineal.FuncionObjetivo;
import com.carbajal.danniel.ioapp.models.programacionlineal.ModeloOptimizacionLinealImp;
import com.carbajal.danniel.ioapp.models.programacionlineal.Restriccion;
import com.carbajal.danniel.ioapp.models.programacionlineal.TiposIgualdades;

import java.util.ArrayList;

/**
 * Created by daniel on 11/21/16.
 */

public class TablaTransporte implements Parcelable{

    private double[] ofertas;
    private double[] demandas;
    private  double[][] costos;
    private ArrayList<Asignacion> asignaciones= new ArrayList<>();

    public TablaTransporte(double[][] costos, double[] demandas, double[] ofertas) {
        this.costos = costos;
        this.demandas = demandas;
        this.ofertas = ofertas;
    }

    protected TablaTransporte(Parcel in) {
        ofertas = in.createDoubleArray();
        demandas = in.createDoubleArray();
        costos = (double[][])in.readSerializable();
        asignaciones = (ArrayList<Asignacion>)in.readSerializable();
    }

    public static final Creator<TablaTransporte> CREATOR = new Creator<TablaTransporte>() {
        @Override
        public TablaTransporte createFromParcel(Parcel in) {
            return new TablaTransporte(in);
        }

        @Override
        public TablaTransporte[] newArray(int size) {
            return new TablaTransporte[size];
        }
    };

    public void asignar(int r,int c, double valor){
        this.asignaciones.add(new Asignacion(valor,r,c));
    }

    public double[][] getCostos() {
        return costos;
    }

    public double[] getDemandas() {
        return demandas;
    }

    public double[] getOfertas() {
        return ofertas;
    }

    public ArrayList<Asignacion> getAsignaciones() {
        return asignaciones;
    }

    public double getSumaDemandas(){
        double suma=0;
        for (int i=0;i<demandas.length;i++){
            suma += demandas[i];
        }
        return suma;
    }
    public double getSumaOfertas(){
        double suma=0;
        for (int i=0;i<ofertas.length;i++){
            suma += ofertas[i];
        }
        return suma;
    }
    public ModeloOptimizacionLinealImp generarModelo(){
        FuncionObjetivo fnObj = new FuncionObjetivo(false);
        Restriccion[] restricciones = new Restriccion[ofertas.length+demandas.length];
        for (int i=0;i<costos.length;i++){
            for (int j=0;j<costos[0].length;j++){
                fnObj.agregarVariableDecision(costos[i][j]);

                for (int k=0;k<costos.length;k++) {
                    if (i==0 && j==0) {
                        restricciones[k] = new Restriccion(ofertas[k]);
                        restricciones[k].setIgualdad(TiposIgualdades.IGUAL);

                    }
                    if (i==k) {
                        restricciones[k].agregarVariableRestriccion(1);
                    } else {
                        restricciones[k].agregarVariableRestriccion(0);
                    }

                }
                for (int k=0;k<costos[0].length;k++) {
                    int indice = k+costos.length;
                    if (i==0 && j==0) {
                        restricciones[indice] = new Restriccion(demandas[k]);
                        restricciones[indice].setIgualdad(TiposIgualdades.IGUAL);
                    }
                    if (j==k){
                        restricciones[indice].agregarVariableRestriccion(1);
                    } else {
                        restricciones[indice].agregarVariableRestriccion(0);
                    }

                }
            }
        }
        ModeloOptimizacionLinealImp modelo = new ModeloOptimizacionLinealImp(fnObj);
        modelo.agregarRestriccion(restricciones);
        return modelo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDoubleArray(ofertas);
        dest.writeDoubleArray(demandas);
        dest.writeSerializable(costos);
        dest.writeSerializable(asignaciones);
    }
}

