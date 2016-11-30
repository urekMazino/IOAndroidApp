package com.carbajal.danniel.ioapp.views.output.transporte;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.carbajal.danniel.ioapp.R;
import com.carbajal.danniel.ioapp.models.programacionlineal.transporte.Asignacion;
import com.carbajal.danniel.ioapp.support.StringManipulation;

/**
 * Created by daniel on 11/21/16.
 */

public class TablaTransporte extends HorizontalScrollView{

    private TableLayout tableLayout;
    private int destinos,origenes;
    double [] ofertas,demandas;
    double[][] costos;
    TextView[][] costosViews;
    TextView[] demandasViews;
    TextView[] ofertasViews;
    int currentColumn=0,currentRow=0;

    public TablaTransporte(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TablaTransporte(Context context, com.carbajal.danniel.ioapp.models.programacionlineal.transporte.TablaTransporte tablaTransporte) {
        super(context);
        init(tablaTransporte);
    }
    private void init(com.carbajal.danniel.ioapp.models.programacionlineal.transporte.TablaTransporte tablaTransporte){

        this.costos =  tablaTransporte.getCostos();
        this.ofertas = tablaTransporte.getOfertas();
        this.demandas = tablaTransporte.getDemandas();
        destinos =costos[0].length;
        origenes =  costos.length;
        initTableLayout();

        ofertasViews = new TextView[ofertas.length];
        demandasViews = new TextView[demandas.length];
        costosViews = new TextView[costos.length][];

        tableLayout.addView(initHeader());
        int i=0;
        currentColumn =0;
        currentRow =0;
        for (;i<origenes;i++){
            currentColumn=0;
            tableLayout.addView(createRow(costos[i],i+1,ofertas[i]));
            currentRow++;
        }
        currentColumn=0;
        tableLayout.addView(createRow(demandas));
        for (Asignacion a: tablaTransporte.getAsignaciones()){
            assignElement(a.getR(),a.getC(),a.getElementos());
        }
    }

    private TableRow initHeader(){
        TableRow headersRow = new TableRow(getContext());
        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT,TableLayout.LayoutParams.WRAP_CONTENT);
        headersRow.setLayoutParams(layoutParams);
        TextView blankSpace =createTextView("");
        blankSpace.setBackgroundColor(getResources().getColor(R.color.backgroundColor));
        headersRow.addView(blankSpace);
        for (int i=0;i<destinos;i++){
            headersRow.addView(createTextViewTitle("D "+(i+1)));
        }
        headersRow.addView(createTextViewTitle("Ofertas"));

        return headersRow;
    }
    private void initTableLayout(){
        tableLayout = new TableLayout(getContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        tableLayout.setLayoutParams(layoutParams);

        this.removeAllViews();
        this.addView(tableLayout);
    }
    private TextView createTextView(String str){
        TextView textView = new TextView(getContext());
        textView.setText(str);
        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(1,1,1,1);
        textView.setPadding(10,0,10,0);
        textView.setMinimumWidth(115);
        textView.setTextAlignment(TEXT_ALIGNMENT_TEXT_END);
        textView.setLayoutParams(layoutParams);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,26);

        if (demandas[Math.min(currentColumn,demandas.length-1)]==0 || ofertas[Math.min(currentRow,ofertas.length-1)]==0)
            textView.setBackgroundColor(getResources().getColor(R.color.lightAccent));
        else
            textView.setBackgroundColor(getResources().getColor(R.color.backgroundColor));
        return textView;
    }

    private TextView createTextViewTitle(String str){
        TextView textView = createTextView(str);
        textView.setTypeface(Typeface.DEFAULT_BOLD);

        textView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        textView.setTextColor(getResources().getColor(R.color.backgroundColor));
        return textView;
    }
    private TableRow createRow(double[] rowValues,int index,double oferta) {
        TableRow tableRow = new TableRow(getContext());
        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT,TableLayout.LayoutParams.WRAP_CONTENT);
        tableRow.setLayoutParams(layoutParams);
        tableRow.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        tableRow.addView(createTextViewTitle("O "+index));
        costosViews[currentRow] = new TextView[costos[0].length+1];
        for (int i = 0; i < rowValues.length; i++) {
                costosViews[currentRow][i]=createTextView(StringManipulation.DoubleToString(rowValues[i]));
            currentColumn++;
            tableRow.addView(costosViews[currentRow][i]);
        }
        ofertasViews[currentRow] = createTextView(StringManipulation.DoubleToString(oferta));
        tableRow.addView(ofertasViews[currentRow]);
        return tableRow;
    }
    private TableRow createRow(double[] rowValues) {
        TableRow tableRow = new TableRow(getContext());
        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT,TableLayout.LayoutParams.WRAP_CONTENT);
        tableRow.setLayoutParams(layoutParams);
        tableRow.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        tableRow.addView(createTextViewTitle("D "));
        for (int i = 0; i < rowValues.length; i++) {
            demandasViews[currentColumn]=createTextView(StringManipulation.DoubleToString(rowValues[i]));
            tableRow.addView(demandasViews[currentColumn]);
            currentColumn++;

        }
        return tableRow;
    }

    public TextView getCosto(int columna, int renglon){
        return costosViews[renglon][columna];
    }
    public TextView getDemanda(int indice){
        return demandasViews[indice];
    }
    public TextView getOferta(int indice){
        return ofertasViews[indice];
    }
    public void assignElement(int r,int c,double elementos){
        TextView celda =  getCosto(c,r);
        celda.setText(uniteStrings(celda.getText().toString(),"  "+StringManipulation.DoubleToString(elementos)));
    }
    public void assignElement(int r,int c,double elementos,int color){
        TextView celda =  getCosto(c,r);
        celda.setText(uniteStrings(celda.getText().toString(),"  "+StringManipulation.DoubleToString(elementos),color));
    }
    public void restarElementosColumna(int i, double valor){
        String valorStr = StringManipulation.DoubleToString(valor);
        TextView celda =  getDemanda(i);
        celda.setText(uniteStrings(StringManipulation.DoubleToString(demandas[i])," - "+ valorStr,getResources().getColor(R.color.colorAccent)));
    }
    public void restarElementosRenglon(int i, double valor){
        String valorStr = StringManipulation.DoubleToString(valor);
        TextView celda2 = getOferta(i);
        celda2.setText(uniteStrings(StringManipulation.DoubleToString(ofertas[i])," - "+ valorStr,getResources().getColor(R.color.colorAccent)));
    }
    private SpannableStringBuilder uniteStrings(String str1, String str2){
        return uniteStrings(str1,str2,getResources().getColor(R.color.black));
    }
    private SpannableStringBuilder uniteStrings(String str1, String str2, int color){
        String text = str1+str2;
        SpannableStringBuilder sb = new SpannableStringBuilder(text);
        final StyleSpan bss = new StyleSpan(android.graphics.Typeface.BOLD);
        sb.setSpan(bss, str1.length(), text.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE); // make last 2 characters Italic
        sb.setSpan(new ForegroundColorSpan(color), str1.length(), text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return sb;
    }
}
