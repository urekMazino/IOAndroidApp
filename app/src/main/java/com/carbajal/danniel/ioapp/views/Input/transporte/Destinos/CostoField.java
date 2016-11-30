package com.carbajal.danniel.ioapp.views.input.transporte.Destinos;

import android.content.Context;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.carbajal.danniel.ioapp.R;
import com.carbajal.danniel.ioapp.support.PositiveInputFilter;
import com.carbajal.danniel.ioapp.views.customViews.InputViews.CustomNumField;
import com.carbajal.danniel.ioapp.views.exceptions.ExceptionTransporteInput;

/**
 * Created by daniel on 11/20/16.
 */

public class CostoField extends LinearLayout {

    private CustomNumField numField;

    protected int index;

    public CostoField(Context context, int index) {
        super(context);
        init();
        setIndex(index);
    }

    public CostoField(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CostoField(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    protected void init(){
        initLinearLayout();
        initNumField();

    }
    private void initLinearLayout(){
        this.setOrientation(HORIZONTAL);
    }
    private void initNumField(){
        numField = new CustomNumField(getContext());
        numField.setMaxWidth(getResources().getDimensionPixelSize(R.dimen.number_field_max_width));
        numField.setMinimumWidth(getResources().getDimensionPixelSize(R.dimen.number_field_min_width));
        numField.setFilters(new InputFilter[]{new PositiveInputFilter()});
        this.addView(numField);
    }

    public CustomNumField getNumField() {
        return numField;
    }

    protected void updatedIndex(){
        numField.setHint("Costo "+index);
    }
    public void setIndex(int index){
        this.index = index;
        updatedIndex();
    }
    public double getValue() throws ExceptionTransporteInput{
        try {
            return numField.getValue();
        } catch (Exception e) {
            ExceptionTransporteInput exception = new ExceptionTransporteInput();
            exception.setIndiceCosto(index);
            throw exception;
        }
    }
}
