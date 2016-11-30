package com.carbajal.danniel.ioapp.models.programacionlineal.grafico;

import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

/**
 * Created by daniel on 11/26/16.
 */

public class Line2D {

    LineGraphSeries<DataPoint> lineaGrafica;

    public Line2D(DataPoint p1, DataPoint p2) {
        lineaGrafica = new LineGraphSeries<>(new DataPoint[]{p1,p2});
    }

    public LineGraphSeries<DataPoint> getLineaGrafica() {
        return lineaGrafica;
    }
}
