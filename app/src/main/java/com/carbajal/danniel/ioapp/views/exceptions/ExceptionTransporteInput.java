package com.carbajal.danniel.ioapp.views.exceptions;

/**
 * Created by daniel on 11/21/16.
 */

public class ExceptionTransporteInput extends Exception {

    private int indiceCosto;
    private int indiceDestino;

    public ExceptionTransporteInput() {

    }

    public ExceptionTransporteInput(String message) {
        super(message);
    }

    public void setIndiceCosto(int indiceCosto) {
        this.indiceCosto = indiceCosto;
    }

    public void setIndiceDestino(int indiceDestino) {
        this.indiceDestino = indiceDestino;
    }
}
