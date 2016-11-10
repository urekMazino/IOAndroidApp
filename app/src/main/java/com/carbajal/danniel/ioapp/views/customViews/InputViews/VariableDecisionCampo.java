package com.carbajal.danniel.ioapp.views.customViews.InputViews;

import android.content.Context;
import android.util.TypedValue;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.carbajal.danniel.ioapp.R;
import com.carbajal.danniel.ioapp.StringManipulation;

/**
 * Created by daniel on 11/6/16.
 */

public class VariableDecisionCampo extends LinearLayout {

    private int index;

    private CustomNumField numField;
    private TextView textView;
    private Button eliminateButton;

    public VariableDecisionCampo(Context context,int index) {
        super(context);
        this.setOrientation(HORIZONTAL);
        this.index = index;

        numField = createNumField();
        textView = createTextView(index);
        eliminateButton = createEliminateButton();


        addView(numField);
        addView(textView);
        addView(eliminateButton);

    }
    public VariableDecisionCampo(Context context,int index,String coeficient){
        this(context,index);
        numField.setText(coeficient);
    }

    private CustomNumField createNumField(){
        CustomNumField numField = new CustomNumField(this.getContext());
        numField.requestFocus();

        return numField;
    }
    private TextView createTextView(int index){
        TextView textView = new TextView(this.getContext());
        textView.setText(getResources().getString(R.string.variable_decision_simbolo)+ StringManipulation.subscript(index)+" +");
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,24);

        return textView;
    }
    private Button createEliminateButton(){
        final LayoutParams lparams = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        Button eliminateButton = new Button(this.getContext());
        eliminateButton.setText("eliminar");
        eliminateButton.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
        eliminateButton.setLayoutParams(lparams);

        return eliminateButton;
    }
    public CustomNumField getNumField() {
        return numField;
    }
    public void setTextView(String str) {
        textView.setText(str);
    }

    public Button getEliminateButton() {
        return eliminateButton;
    }

}
