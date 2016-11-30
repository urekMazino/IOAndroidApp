package com.carbajal.danniel.ioapp.models.programacionlineal.transporte.ESN;

import com.carbajal.danniel.ioapp.models.programacionlineal.transporte.TablaTransporte;

import java.util.ArrayList;

/**
 * Created by daniel on 11/23/16.
 */

public class ESN {

    TablaTransporte tablaTransporte;
    int columnaSeleccionada;
    int renglonSeleccionado;
    double[][] costos;
    double[] ofertas;
    double[] demandas;
    double sum;
    private Events listener;

    ArrayList<Double>costosFinales = new ArrayList<>();
    ArrayList<Double>elementosFinales = new ArrayList<>();

    public ESN(TablaTransporte tablaTransporte) {
        this.tablaTransporte = tablaTransporte;
        this.costos = tablaTransporte.getCostos();
        this.ofertas = tablaTransporte.getOfertas();
        this.demandas = tablaTransporte.getDemandas();
    }

    public void ejecutarAlgoritmo(){
        columnaSeleccionada = 0;
        renglonSeleccionado = 0;
        sum = 0;
        listener.initialTable();
        while (checkCondition()){
            costosFinales.add(costos[renglonSeleccionado][columnaSeleccionada]);
            if (checkLarger()){
                elementosFinales.add(ofertas[renglonSeleccionado]);
                listener.asignar(renglonSeleccionado,columnaSeleccionada,ofertas[renglonSeleccionado]);
                tablaTransporte.asignar(renglonSeleccionado,columnaSeleccionada,ofertas[renglonSeleccionado]);
                demandas[columnaSeleccionada] -= ofertas[renglonSeleccionado];
                ofertas[renglonSeleccionado] -= ofertas[renglonSeleccionado];
                renglonSeleccionado++;
            } else {
                elementosFinales.add(demandas[columnaSeleccionada]);
                listener.asignar(renglonSeleccionado,columnaSeleccionada,demandas[columnaSeleccionada]);
                tablaTransporte.asignar(renglonSeleccionado,columnaSeleccionada,demandas[columnaSeleccionada]);
                ofertas[renglonSeleccionado] -= demandas[columnaSeleccionada];
                demandas[columnaSeleccionada] -= demandas[columnaSeleccionada];
                columnaSeleccionada++;
            }
            int ultimoIndiceElementos = elementosFinales.size()-1;
            sum+=elementosFinales.get(ultimoIndiceElementos)*costosFinales.get(ultimoIndiceElementos);
        }
        listener.solucion(getResultadoString());
    }
    public String getResultadoString(){
        String str = "";
        for (int i=0;i<costosFinales.size();i++){
            str += "("+costosFinales.get(i)+")*("+elementosFinales.get(i)+")"+((i!=costosFinales.size()-1)?" + ":"");
        }
        str += " = "+sum;
        return str;
    }

    public void setListener(Events listener) {
        this.listener = listener;
    }

    private Boolean checkLarger(){
        return ofertas[renglonSeleccionado]<=demandas[columnaSeleccionada];
    }
    private Boolean checkCondition(){
        return (columnaSeleccionada<costos[0].length && renglonSeleccionado<costos.length);
    }

    public interface Events{
        void asignar(int r,int c,double elementos);
        void initialTable();
        void solucion(String stringSolucion);
    }

}
