package com.carbajal.danniel.ioapp.views.input.transporte.Destinos;

import com.carbajal.danniel.ioapp.models.programacionlineal.transporte.TablaTransporte;
import com.carbajal.danniel.ioapp.views.exceptions.ExceptionTransporteInput;
import com.carbajal.danniel.ioapp.views.input.transporte.Destinos.Header.DestinoHeaderInputView;
import com.carbajal.danniel.ioapp.views.input.transporte.ofertas.OfertasInputView;

import java.util.ArrayList;

/**
 * Created by daniel on 11/20/16.
 */

public class DestinosManager implements DestinoHeaderInputView.HeaderEvents, DestinoInputView.DestinoInputEvents, OfertasInputView.OfertasInputViewEventos {

//    OriginManager
    private ArrayList<DestinoInputView> destinoInputViews = new ArrayList<>();
    private DestinoHeaderInputView headerInputView;
    private DestinosManagerEvents listener;
    private OfertasInputView ofertasInputView;

    public DestinosManager(DestinosManagerEvents listener,DestinoHeaderInputView headerInputView){
        this.listener = listener;
        this.headerInputView = headerInputView;
        headerInputView.setListenerDestinoInput(this);
        headerInputView.setListener(this);
    }

    public void setOfertasInputView(OfertasInputView ofertasInputView) {
        this.ofertasInputView = ofertasInputView;
        this.ofertasInputView.setListener(this);
    }

    public DestinoHeaderInputView getHeaderInputView() {
        return headerInputView;
    }

    public void addDestino(DestinoInputView destinoInputView){
        destinoInputViews.add(destinoInputView);
        destinoInputView.setListenerDestinoInput(this);
        listener.addedDestino(destinoInputView);

    }
    public void removeDestino(DestinoInputView destinoInputView){
        int index = destinoInputViews.indexOf(destinoInputView);
        if (index == destinoInputViews.size()-1){
            if (index==0){
                headerInputView.addAddButton();
            } else {
                destinoInputViews.get(index-1).addAddButton();
            }
        }
        listener.closedDestino(destinoInputView);
        destinoInputViews.remove(destinoInputView);
        adjustNames();
    }

    public int getCount(){
        return destinoInputViews.size()+1;
    }
    public int indexOf(DestinoInputView destinoInputView){
        return destinoInputViews.indexOf(destinoInputView)+1;
    }
    public int getOrigins(){
        return headerInputView.getOrigins();
    }

    @Override
    public void addOrigen() {
        for (DestinoInputView x: destinoInputViews){
            x.addOrigen();
        }
        ofertasInputView.addOrigen();
        headerInputView.addOrigen();
    }

    @Override
    public void removeOrigen(int index) {
        headerInputView.removeOrigen(index);
        ofertasInputView.removeOrigen(index);
        for (DestinoInputView x: destinoInputViews){
            x.removeOrigen(index);
        }
    }

    @Override
    public void requestNextDestino(DestinoInputView destinoInputView) {
        int index = destinoInputViews.indexOf(destinoInputView);
        if (index == destinoInputViews.size() - 1) {
            if (index==-1){
                 headerInputView.removeAddButton();
            } else {
                destinoInputViews.get(destinoInputViews.size()-1).removeAddButton();
            }
            listener.requestAdd();
        } else {
            listener.requestNextPage();
        }
    }

    public TablaTransporte getTabla() throws ExceptionTransporteInput{
        double[][] costos = getCostos();
        double[] demandas = getDemandas();
        double[] ofertas = getOfertas();
        return new TablaTransporte(costos,demandas,ofertas);
    }
    private double[] getDemandas() throws ExceptionTransporteInput{
        double[] demandas = new double[getCount()];
        int i=0;
        try{
            demandas[i++] = headerInputView.getDemandaValue();
            for (DestinoInputView x : destinoInputViews) {
                demandas[i++] = x.getDemandaValue();
            }
        } catch (ExceptionTransporteInput e){
            e.setIndiceDestino(i);
            throw e;
        }
        return demandas;
    }
    private double[][] getCostos() throws ExceptionTransporteInput{
        double[][] costos = new double[headerInputView.getOrigins()][getCount()];
        int i=0;
        int j=0;
        try {
            for (CostoField y : headerInputView.getCostos()) {
                costos[j++][i] = y.getValue();
            }
            i++;
            for (DestinoInputView x : destinoInputViews) {
                ArrayList<CostoField> currentCosts = x.getCostos();
                j=0;
                for (CostoField y : currentCosts) {
                    costos[j++][i] = y.getValue();
                }
                i++;
            }
        } catch(ExceptionTransporteInput e){
            e.setIndiceDestino(i);
            throw e;
        }
        return costos;
    }
    private double[] getOfertas() throws ExceptionTransporteInput {
        return ofertasInputView.getOfertas();
    }

    private void adjustNames(){
        int i =2;
        for (DestinoInputView x: destinoInputViews){
            x.setTitle("Destino "+(i++));
        }
    }


    @Override
    public void requestNextPage() {
        listener.requestNextPage();
    }


    public interface DestinosManagerEvents {
        void closedDestino(DestinoInputView destinoInputView);
        void addedDestino(DestinoInputView destinoInputView);
        void requestAdd();
        void requestNextPage();
    }
}
