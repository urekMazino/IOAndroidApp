package com.carbajal.danniel.ioapp.views.input.transporte.Destinos;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.carbajal.danniel.ioapp.R;
import com.carbajal.danniel.ioapp.views.customViews.FontButtonBuilder;
import com.carbajal.danniel.ioapp.views.exceptions.ExceptionTransporteInput;
import com.carbajal.danniel.ioapp.views.input.transporte.TransporteInputView;

import java.util.ArrayList;


/**
 * Created by daniel on 11/20/16.
 */

public class DestinoInputView extends TransporteInputView implements DemandaField.DemandaEvent {

    protected CostosContainer costosContainer;
    private DemandaField demandaField;
    private DestinoInputEvents listenerDestinoInput;
    private TextView closeButton;
    private TextView addButton;
    private int index;

    public DestinoInputView(Context context,int index,int fieldsToAdd) {
        super(context);
        this.index = index;
        init(fieldsToAdd);
    }

    public DestinoInputView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DestinoInputView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    private void init(int fieldsToAdd){
        setTitle("Destino "+index);
        initDemanda();
        initCostosContainer(fieldsToAdd);
        initCloseButton();
        initAddButton();
    }
    public void removeAddButton(){
        try {
            titleContainer.removeView(addButton);
        } catch (Exception e){

        }
    }
    protected void initCloseButton(){
        closeButton = FontButtonBuilder.BuildButton(getContext(),"delete",40);

        closeButton.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View v) {
                listenerDestinoInput.removeDestino(DestinoInputView.this);
            }
        });

        titleContainer.addView(closeButton);
    }
    protected void initAddButton(){
        addButton = FontButtonBuilder.BuildCircularButton(getContext(),getResources().getString(R.string.add_icon),32,getResources().getColor(R.color.green));
        addButton.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                listenerDestinoInput.requestNextDestino(DestinoInputView.this);
            }
        });

        titleContainer.addView(addButton);
    }
    protected void addAddButton(){
        titleContainer.addView(addButton);
    }
    private void initDemanda(){
        demandaField = new DemandaField(getContext(),index);
        demandaField.setListener(this);
        this.addLeftBommonLL(demandaField);
    }
    protected void initCostosContainer(int fieldsToAdd){
        costosContainer = new CostosContainer(getContext(),fieldsToAdd);
        addContent(costosContainer);
    }
    public void addOrigen(){
        costosContainer.addCostoField();
    }
    public void removeOrigen(int index){
        costosContainer.removeCostoField(index);
    }

    public void setListenerDestinoInput(DestinoInputEvents listenerDestinoInput) {
        this.listenerDestinoInput = listenerDestinoInput;
    }
    public void setFocus(){
        costosContainer.getCostoFields().get(0).getNumField().requestFocus();
    }
    public ArrayList<CostoField> getCostos(){
        return costosContainer.getCostoFields();
    }
    public double getDemandaValue() throws ExceptionTransporteInput{
        try{
            return demandaField.getValue();
        } catch (Exception e){
            ExceptionTransporteInput exception = new ExceptionTransporteInput();
            exception.setIndiceCosto(-1);
            throw exception;
        }
    }

    @Override
    public void finishedDemanda() {
        listenerDestinoInput.requestNextDestino(this);
    }
    public interface DestinoInputEvents{
        void requestNextDestino(DestinoInputView destinoInputView);
        void removeDestino(DestinoInputView destinoInputView);
    }

}
