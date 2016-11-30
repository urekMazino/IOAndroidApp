package com.carbajal.danniel.ioapp.views.input.transporte.Destinos.Header;

import android.content.Context;
import android.util.AttributeSet;

import com.carbajal.danniel.ioapp.views.input.transporte.Destinos.DestinoInputView;

/**
 * Created by daniel on 11/20/16.
 */

public class DestinoHeaderInputView extends DestinoInputView implements CostosContainerHeader.CostosContainerHeaderEvents{


    private HeaderEvents listener;

    public DestinoHeaderInputView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DestinoHeaderInputView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public DestinoHeaderInputView(Context context,int index) {
        super(context, index,1);
    }
    public int getOrigins(){
        return costosContainer.getCount();
    }

    @Override
    protected void initCloseButton() {

    }

    @Override
    protected void initCostosContainer(int fieldsToAdd) {
        costosContainer = new CostosContainerHeader(getContext(),this);
        addContent(costosContainer);
    }
    public void setListener(HeaderEvents listener) {
        this.listener = listener;
    }

    @Override
    public void requestCloseOrigin(int index) {
        listener.removeOrigen(index);
    }

    @Override
    public void requestAddOrigin() {
        listener.addOrigen();
    }

    public interface HeaderEvents{
        void addOrigen();
        void removeOrigen(int index);
    }
}
