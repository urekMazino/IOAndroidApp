package com.carbajal.danniel.ioapp.views.input.transporte.ofertas;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.carbajal.danniel.ioapp.views.customViews.InputViews.CustomNumField;
import com.carbajal.danniel.ioapp.views.input.transporte.Destinos.CostoField;
import com.carbajal.danniel.ioapp.views.input.transporte.Destinos.CostosContainer;

/**
 * Created by daniel on 11/21/16.
 */

public class OfertasContainer extends CostosContainer {

    private OfertasContainerEventos listener;

    public OfertasContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OfertasContainer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public OfertasContainer(Context context) {
        super(context, 1);
    }

    @Override
    protected CostoField createCostoField(int index) {
        OfertaField ofertaField = new OfertaField(getContext(),index);
        setNumFieldListener(ofertaField.getNumField());
        return ofertaField;
    }
    private void setNumFieldListener(final CustomNumField numField){
        numField.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE){
                    listener.finish();
                    return true;
                } else {
                    return false;
                }
            }
        });
    }

    public void setListener(OfertasContainerEventos listener) {
        this.listener = listener;
    }

    public interface OfertasContainerEventos{
        void finish();
    }

}
