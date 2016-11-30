package com.carbajal.danniel.ioapp.views;

import android.content.Context;
import android.util.AttributeSet;

import com.carbajal.danniel.ioapp.R;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.PointsGraphSeries;
import com.jjoe64.graphview.series.Series;

import java.util.ArrayList;

/**
 * Created by daniel on 11/27/16.
 */

public class GraphCustom extends GraphView {

    ArrayList<Series<DataPoint>> seriesCustom = new ArrayList<>();
    double maxX=.00000001;
    double maxY=.00000001;
    double maxXVisual=.00000001;
    double maxYVisual=.00000001;

    public GraphCustom(Context context,GraphCustom toCopy){
        super(context);

        this.maxX = toCopy.maxX;
        this.maxY = toCopy.maxY;
        this.maxYVisual = toCopy.maxYVisual;
        this.maxXVisual = toCopy.maxXVisual;

        this.getViewport().setMaxY(maxYVisual);
        this.getViewport().setMaxX(maxXVisual);

        this.seriesCustom = toCopy.seriesCustom;
        for (Series<DataPoint> x: this.seriesCustom){
            this.addSeries(x);
        }
    }

    public GraphCustom(Context context) {
        super(context);
    }

    public GraphCustom(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GraphCustom(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void init() {
        super.init();
        this.getViewport().setXAxisBoundsManual(true);
        this.getViewport().setMinX(0);
        this.getViewport().setMaxX(.1);
        this.getViewport().setYAxisBoundsManual(true);
        this.getViewport().setMinY(0);
        this.getViewport().setMaxY(.1);
    }

    public void addUnboundRestriction(LineGraphSeries<DataPoint> res){
        res.setColor(getResources().getColor(R.color.colorPrimary));
        this.addSeries(res);
        this.seriesCustom.add(res);
    }
    public void addBoundRestriction(LineGraphSeries<DataPoint> res){
        addUnboundRestriction(res);
        maxX = Math.max(res.getHighestValueX(),maxX);
        maxXVisual = maxX + (maxX*.1);
        this.getViewport().setMaxX(maxXVisual);
        maxY = Math.max(res.getHighestValueY(),maxY);
        maxYVisual = maxY + (maxY*.1);
        this.getViewport().setMaxY(maxYVisual);
    }
    public void addPuntos(ArrayList<DataPoint> puntos){
        PointsGraphSeries<DataPoint> puntosSerie = new PointsGraphSeries<>(puntos.toArray(new DataPoint[puntos.size()]));
        puntosSerie.setColor(getResources().getColor(R.color.colorPrimary));
        this.addSeries(puntosSerie);
        this.seriesCustom.add(puntosSerie);
    }
    public void addPuntosSolucion(ArrayList<DataPoint> puntos){
        DataPoint[] arrayPuntos = puntos.toArray(new DataPoint[puntos.size()]);
        PointsGraphSeries<DataPoint> puntosSerie = new PointsGraphSeries<>(arrayPuntos);
            puntosSerie.setColor(getResources().getColor(R.color.colorHighlight));
        this.addSeries(puntosSerie);
        this.seriesCustom.add(puntosSerie);

    }
    public  void addSolucion(DataPoint p){
        PointsGraphSeries<DataPoint> puntos = new PointsGraphSeries<>(new DataPoint[]{p});
        puntos.setColor(getResources().getColor(R.color.colorHighlight));
        puntos.setSize(puntos.getSize()*1.5f);
        this.addSeries(puntos);
        this.seriesCustom.add(puntos);

    }

}
