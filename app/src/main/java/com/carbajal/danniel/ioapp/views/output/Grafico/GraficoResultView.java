package com.carbajal.danniel.ioapp.views.output.Grafico;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.TextView;

import com.carbajal.danniel.ioapp.models.programacionlineal.FuncionObjetivo;
import com.carbajal.danniel.ioapp.support.StringManipulation;
import com.carbajal.danniel.ioapp.views.input.InputView;
import com.jjoe64.graphview.series.DataPoint;

/**
 * Created by daniel on 11/27/16.
 */

public class GraficoResultView extends InputView {

    public GraficoResultView(Context context, DataPoint punto, FuncionObjetivo funcionObjetivo) {
        super(context);
        init(punto,funcionObjetivo);
    }

    public GraficoResultView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GraficoResultView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    private void init(DataPoint punto, FuncionObjetivo funcionObjetivo){
        setTitle("Solucion");
        addContent(x1Result(punto));
        addContent(funcionObjetivoResult1(punto,funcionObjetivo));
        addContent(funcionObjetivoResult2(punto,funcionObjetivo));
    }
    private TextView x1Result(DataPoint punto){
        return ResultTextView("x"+ StringManipulation.subscript(1)+" = "+StringManipulation.DoubleToString(punto.getX())+", "+
                "x"+ StringManipulation.subscript(2)+" = "+StringManipulation.DoubleToString(punto.getY()));
    }
    private TextView funcionObjetivoResult1(DataPoint punto,FuncionObjetivo funcionObjetivo){
        String str = "Z = ";
        str += StringManipulation.DoubleToString(funcionObjetivo.getVariablesRestriccion().get(0))+"(";
        str += StringManipulation.DoubleToString(punto.getX())+") * ";
        str += StringManipulation.DoubleToString(funcionObjetivo.getVariablesRestriccion().get(1))+"(";
        str += StringManipulation.DoubleToString(punto.getY())+")";

        return ResultTextView(str);
    }
    private TextView funcionObjetivoResult2(DataPoint punto,FuncionObjetivo funcionObjetivo){
        String str = "Z = ";
        str += StringManipulation.DoubleToString(funcionObjetivo.getVariablesRestriccion().get(0)*punto.getX() +
                funcionObjetivo.getVariablesRestriccion().get(1)*punto.getY());

        return ResultTextView(str);
    }
    public TextView ResultTextView(String str){
        TextView textView= new TextView(getContext());
        textView.setText(str);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,28);
        return textView;

    }
}
