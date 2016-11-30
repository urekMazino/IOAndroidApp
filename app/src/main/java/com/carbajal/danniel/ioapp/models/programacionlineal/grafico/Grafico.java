package com.carbajal.danniel.ioapp.models.programacionlineal.grafico;


import com.carbajal.danniel.ioapp.models.programacionlineal.ModeloOptimizacionLinealImp;
import com.carbajal.danniel.ioapp.models.programacionlineal.Restriccion;
import com.carbajal.danniel.ioapp.models.programacionlineal.TiposIgualdades;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;

/**
 * Created by daniel on 11/26/16.
 */

public class Grafico {

    ModeloOptimizacionLinealImp modelo;
    ArrayList<Line2D> lines = new ArrayList<>();
    ArrayList<DataPoint> points = new ArrayList<>();
    ArrayList<DataPoint> validPoints = new ArrayList<>();
    boolean tiene00 = false;
    Eventos listener;
    public Grafico(ModeloOptimizacionLinealImp modelo,Eventos listener){
        this.modelo = modelo;
        initRestricccionNoNegatividad();
        this.listener = listener;
    }
    private void initRestricccionNoNegatividad(){
        Restriccion r1 = new Restriccion(0,1,0);
        Restriccion r2 = new Restriccion(0,0,1);
        r1.setIgualdad(TiposIgualdades.MAYOR_IGUAL);
        r2.setIgualdad(TiposIgualdades.MAYOR_IGUAL);
        this.modelo.agregarRestriccion(r1,r2);
    }
    public void ejecutarAlgoritmo(){
        generarLineas();
        generarIntersecciones();

        encontrarInterseccionesValidas();
        DataPoint p = buscarMejorSolucion();
        System.out.println("Solucion optima: "+ p.getX()+","+p.getY());
        listener.imprimirSolucion(p);
    }
    private void printPuntos(){
        for (int i=0;i<points.size();i++){
            System.out.println("Punto: "+ points.get(i).getX()+","+points.get(i).getY());
        }
    }
    private void printPuntosValidos(){
        for (int i=0;i<validPoints.size();i++){
            System.out.println("Punto validos: "+ validPoints.get(i).getX()+","+validPoints.get(i).getY());
        }
    }
    private DataPoint buscarMejorSolucion(){
        if (validPoints.size()==0){
            return new DataPoint(0,0);
        }
        double mejorValor = (modelo.getFuncionObjetivo().getMaximizar()?-Double.MAX_VALUE:Double.MAX_VALUE);
        DataPoint mejorPunto =  new DataPoint(0,0);
        if (modelo.getFuncionObjetivo().getMaximizar()) {
            for (DataPoint p : validPoints) {
                double valorAProbar = evaluarEnFuncion(p);
                if (mejorValor<valorAProbar){
                    mejorValor = valorAProbar;
                    mejorPunto = p;
                }
            }
        }else {
            for (DataPoint p : validPoints) {
                double valorAProbar = evaluarEnFuncion(p);
                if (mejorValor>valorAProbar){
                    mejorValor = valorAProbar;
                    mejorPunto = p;
                }
            }
        }
        listener.graficarSolucion(mejorPunto);
        return mejorPunto;
    }

    private double evaluarEnFuncion(DataPoint p){
        return p.getX()*modelo.getFuncionObjetivo().getVariablesRestriccion().get(0) +
                p.getY()*modelo.getFuncionObjetivo().getVariablesRestriccion().get(1);

    }


    private void encontrarInterseccionesValidas(){
        for (DataPoint p: points){
            boolean valid = true;
            for (Restriccion r: modelo.getRestricciones()){
                if (!comprobarRestriccion(p,r)){
                    valid = false;
                    break;
                }
            }
            if (valid){
                validPoints.add(p);
            }
        }
        listener.graficarAreaSolucion(validPoints);
    }
    private boolean comprobarRestriccion(DataPoint p,Restriccion r){
        double x = r.getVariablesRestriccion().get(0)*p.getX();
        double y = r.getVariablesRestriccion().get(1)*p.getY();
        boolean valid = true;
        switch (r.getIgualdad()){
            case MENOR:
                valid = ((x+y)<r.getExpresionNumerica());
                break;
            case MENOR_IGUAL:
                valid = ((x+y)<=r.getExpresionNumerica());
                break;
            case IGUAL:
                valid = ((x+y)==r.getExpresionNumerica());
                break;
            case MAYOR_IGUAL:
                valid = ((x+y)>=r.getExpresionNumerica());
                break;
            case MAYOR:
                valid = ((x+y)>r.getExpresionNumerica());
                break;
        }
        return  valid;
    }

    public void generarIntersecciones(){
        for (int i=0;i<modelo.getRestricciones().size()-1;i++){
            for (int j=i+1;j<modelo.getRestricciones().size();j++) {
                try {
                    points.add(getIntersection2(modelo.getRestricciones().get(i),modelo.getRestricciones().get(j)));
                } catch (Exception e) {

                }
            }
        }
        listener.graficarPuntos(points);
    }
    private DataPoint getIntersection2(Restriccion res1,Restriccion res2) throws Exception{
        double C1 = res1.getExpresionNumerica();
        double C2 = res2.getExpresionNumerica();
        double delta = res1.getVariablesRestriccion().get(0)*res2.getVariablesRestriccion().get(1) - res2.getVariablesRestriccion().get(0)*res1.getVariablesRestriccion().get(1);
        if(delta == 0)
            throw new Exception("Lines are parallel");

        double x = (res2.getVariablesRestriccion().get(1)*C1 - res1.getVariablesRestriccion().get(1)*C2)/delta;
        double y = (res1.getVariablesRestriccion().get(0)*C2 - res2.getVariablesRestriccion().get(0)*C1)/delta;
        DataPoint punto = new DataPoint(x,y);
        if (x<0 || y<0){
            throw new Exception("Menor 0");
        }
        if ( x==0 && y==0){
            if (tiene00)
                throw new Exception("Menor 0");
            else
                tiene00=true;
        }
        for (DataPoint p: points){
            if (p.equals(punto))
                throw new Exception("Repetido");
        }
        return punto;
    }
    private DataPoint getIntersectionPure(Restriccion res1,Restriccion res2){
        double C1 = res1.getExpresionNumerica();
        double C2 = res2.getExpresionNumerica();
        double delta = res1.getVariablesRestriccion().get(0)*res2.getVariablesRestriccion().get(1) - res2.getVariablesRestriccion().get(0)*res1.getVariablesRestriccion().get(1);

        double x = (res2.getVariablesRestriccion().get(1)*C1 - res1.getVariablesRestriccion().get(1)*C2)/delta;
        double y = (res1.getVariablesRestriccion().get(0)*C2 - res2.getVariablesRestriccion().get(0)*C1)/delta;
        x = (Double.isInfinite(x)?0:x);
        DataPoint punto = new DataPoint(x,y);
        return punto;
    }

    public void generarLineas(){
        int counter = 1;
        Restriccion res0 = new Restriccion(0,1,0);
        Restriccion resy0 = new Restriccion(0,0,1);
        Restriccion res1000000 = new Restriccion(1000000,1,0);
        for (Restriccion x: modelo.getRestricciones()){
            double var1 = x.getVariablesRestriccion().get(0);
            double var2 = x.getVariablesRestriccion().get(1);
            double expresion = x.getExpresionNumerica();
            boolean bound = (var1<=0 || var2<=0)? false : true;;
            DataPoint p1,p2;
            if (var2==0){
                double valor = ((expresion==0)?0:(expresion/var1));
                p1 = new DataPoint(valor, 0);
                p2 = new DataPoint(valor, Double.MAX_VALUE);
            } else {
                p1 = getIntersectionPure(res0, x);
                if (var1 > 0){
                    p2 = getIntersectionPure(resy0, x);
                } else {
                    p2 = getIntersectionPure(res1000000, x);
                }
            }
            Line2D line = new Line2D(p1,p2);
            line.getLineaGrafica().setTitle("Restriccion "+counter++);
            lines.add(line);
            if (bound) {
                listener.agregarRestriccionBound(line.getLineaGrafica());
            }else
                listener.agregarRestriccionUnbound(line.getLineaGrafica());
        }
        listener.graficarRestricciones();
    }

    public interface Eventos{
        void agregarRestriccionBound(LineGraphSeries<DataPoint> linea);
        void agregarRestriccionUnbound(LineGraphSeries<DataPoint> linea);
        void graficarRestricciones();
        void graficarPuntos(ArrayList<DataPoint> puntos);
        void graficarAreaSolucion(ArrayList<DataPoint> puntos);
        void graficarSolucion(DataPoint puntoSolucion);
        void imprimirSolucion(DataPoint puntoSolucion);
    }

}
