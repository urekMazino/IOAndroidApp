package com.carbajal.danniel.ioapp.views.input.transporte.ofertas;

import android.content.Context;
import android.util.AttributeSet;

import com.carbajal.danniel.ioapp.views.exceptions.ExceptionTransporteInput;
import com.carbajal.danniel.ioapp.views.input.transporte.Destinos.CostoField;
import com.carbajal.danniel.ioapp.views.input.transporte.TransporteInputView;

import java.util.ArrayList;

/**
 * Created by daniel on 11/21/16.
 */

public class OfertasInputView extends TransporteInputView implements OfertasContainer.OfertasContainerEventos {

    protected OfertasContainer ofertasContainer;
    private OfertasInputViewEventos listener;

    public OfertasInputView(Context context) {
        super(context);
        init();
    }

    public OfertasInputView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OfertasInputView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    private void init(){
        setTitle("Ofertas");
        initDestinoContainer();
    }
    protected void initDestinoContainer(){
        ofertasContainer = new OfertasContainer(getContext());
        ofertasContainer.setListener(this);
        addContent(ofertasContainer);
    }

    public void setListener(OfertasInputViewEventos listener) {
        this.listener = listener;
    }

    public void addOrigen(){
        ofertasContainer.addCostoField();
    }
    public void removeOrigen(int index){
        ofertasContainer.removeCostoField(index);
    }

    @Override
    public void finish() {
        listener.requestNextPage();
    }

    public interface OfertasInputViewEventos{
        void requestNextPage();
    }
    public double[] getOfertas() throws ExceptionTransporteInput{
        ArrayList<CostoField> ofertasFields = ofertasContainer.getCostoFields();
        double[] ofertas = new double[ofertasFields.size()];
        int i=0;
        try {
            for (CostoField x : ofertasFields) {
                ofertas[i++] = x.getValue();
            }
        } catch (ExceptionTransporteInput e){
            e.setIndiceDestino(-1);
        }
        return ofertas;
    }
}
