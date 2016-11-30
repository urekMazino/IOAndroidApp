package com.carbajal.danniel.ioapp.views.input.transporte.Destinos;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import java.util.ArrayList;

/**
 * Created by daniel on 11/20/16.
 */

public class CostosContainer extends ScrollView{

    private ScrollView scrollView;
    protected LinearLayout scrollViewLL;
    protected ArrayList<CostoField> costoFields = new ArrayList<>();

    public CostosContainer(Context context,int fieldsToAdd) {
        super(context);
        init(fieldsToAdd);
    }

    public ArrayList<CostoField> getCostoFields() {
        return costoFields;
    }

    public CostosContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CostosContainer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init(int fieldsToAdd){
        initScrollView();
        for (int i=0;i<fieldsToAdd;i++) {
            addCostoField();
        }

    }
    public int getCount(){
        return costoFields.size();
    }

    private void initScrollView(){
        scrollView = new ScrollView(getContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,0);
        layoutParams.weight = 1;
        scrollView.setLayoutParams(layoutParams);

        initScrollViewLL();

        this.addView(scrollView);
    }
    private void initScrollViewLL(){
        scrollViewLL = new LinearLayout(getContext());
        scrollViewLL.setOrientation(LinearLayout.VERTICAL);
        scrollView.addView(scrollViewLL);
    }
    public CostoField addCostoField(){
        CostoField costoField = createCostoField(costoFields.size()+1);
        costoFields.add(costoField);
        scrollViewLL.addView(costoField);
        costoField.getNumField().requestFocus();
        return costoField;
    }
    protected CostoField createCostoField(int index){
        CostoField costoField = new CostoField(getContext(),index);
        return costoField;
    }
    public void removeCostoField(int index){
        if (costoFields.size()>1) {
            scrollViewLL.removeView(costoFields.get(index));
            costoFields.remove(index);
            changeFocusOnRemove(index);
            updateNombres();
        }
    }
    private void changeFocusOnRemove(int index){
        index = Math.min(index,costoFields.size()-1);
        costoFields.get(index).getNumField().selectAll();
        costoFields.get(index).getNumField().requestFocus();
    }
    private void updateNombres(){
        int i=1;
        for (CostoField x:costoFields){
            x.setIndex(i++);
        }
    }

}
