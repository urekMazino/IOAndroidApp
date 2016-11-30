package com.carbajal.danniel.ioapp.views.output.transporte;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.TextView;

import com.carbajal.danniel.ioapp.R;
import com.carbajal.danniel.ioapp.models.programacionlineal.transporte.TablaTransporte;
import com.carbajal.danniel.ioapp.views.input.transporte.TransporteInputView;

/**
 * Created by daniel on 11/21/16.
 */

public class TransportePreviewView extends TransporteInputView {

    com.carbajal.danniel.ioapp.views.output.transporte.TablaTransporte tablaTransporteView;
    TablaTransporte tablaTransporte;
    private TextView button;

    public TransportePreviewView(Context context) {
        super(context);
        init();
    }

    public TransportePreviewView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TransportePreviewView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    private void init(){
        setTitle("Tabla Transporte");
    }
    public void renderTable(TablaTransporte tablaTransporte){
        this.tablaTransporte = tablaTransporte;
        clearContent();
        double sumaOfertas = tablaTransporte.getSumaOfertas();
        double sumaDemandas = tablaTransporte.getSumaDemandas();
        if (sumaDemandas==sumaOfertas) {
            createTable(tablaTransporte);
            try{
                addBottomLL(button);
            } catch (Exception e){

            }
        } else {
            try{
                removeBottomLL(button);
            } catch (Exception e){

            }
            createMensajeError(sumaOfertas,sumaDemandas);
        }
    }
    private void createTable(TablaTransporte tablaTransporte){
        tablaTransporteView =new com.carbajal.danniel.ioapp.views.output.transporte.TablaTransporte(
                getContext(),
                tablaTransporte);
        addContent(tablaTransporteView);
    }
    public void assignElement(int r,int c,double elementos){
        tablaTransporteView.assignElement(r,c,elementos,getResources().getColor(R.color.colorAccent));
        tablaTransporteView.restarElementosColumna(c,elementos);
        tablaTransporteView.restarElementosRenglon(r,elementos);
    }

    private void createMensajeError(double sumaOfertas, double sumaDemandas){
        String mensaje = "La suma de las demandas tiene que ser igual a la suma de las ofertas. \n" +
                "Ofertas = "+sumaOfertas+"\n" +
                "Demandas = "+sumaDemandas;
        TextView mensajeTextView = new TextView(getContext());
        mensajeTextView.setText(mensaje);
        mensajeTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP,24);
        mensajeTextView.setTextColor(getResources().getColor(R.color.colorAccent));
        addContent(mensajeTextView);
    }
    public void setButton(TextView button){
        this.button = button;
    }

}
