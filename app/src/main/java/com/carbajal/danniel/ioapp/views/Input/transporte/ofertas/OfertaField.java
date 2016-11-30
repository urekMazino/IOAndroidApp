package com.carbajal.danniel.ioapp.views.input.transporte.ofertas;

import android.content.Context;
import android.util.AttributeSet;

import com.carbajal.danniel.ioapp.views.input.transporte.Destinos.Header.CostoFieldHeader;

/**
 * Created by daniel on 11/21/16.
 */

public class OfertaField extends CostoFieldHeader{

    public OfertaField(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OfertaField(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public OfertaField(Context context, int index) {
        super(context, index);
        this.index = index;
    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    protected void initCloseButton() {

    }

    @Override
    protected void updatedIndex() {
        getNumField().setHint("Oferta "+index);
        setHeader("Origen "+index);
    }
}
