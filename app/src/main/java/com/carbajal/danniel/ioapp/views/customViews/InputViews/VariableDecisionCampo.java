package com.carbajal.danniel.ioapp.views.customViews.InputViews;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.carbajal.danniel.ioapp.R;
import com.carbajal.danniel.ioapp.views.customViews.CircularTextView;
import com.carbajal.danniel.ioapp.views.support.StringManipulation;

import org.apmem.tools.layouts.FlowLayout;

/**
 * Created by daniel on 11/6/16.
 */

public class VariableDecisionCampo extends LinearLayout{




    private int index;
    private String variableString;

    private CustomNumField numField;
    private TextView textView;
    private CircularTextView eliminateButton;

    public VariableDecisionCampo(Context context,int index) {
        super(context);
        //this.setBaselineAligned(false);
        FlowLayout.LayoutParams layoutParams= new FlowLayout.LayoutParams(FlowLayout.LayoutParams.WRAP_CONTENT,FlowLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.topMargin = 16;
        layoutParams.rightMargin = 32;
        this.setLayoutParams(layoutParams);

        this.setOrientation(HORIZONTAL);
        this.index = index;

        numField = createNumField();
        textView = createTextView(index);
        eliminateButton = createEliminateButton();


        addView(numField);
        addView(textView);
        addView(eliminateButton);

        adjustName(index);
    }
    public VariableDecisionCampo(Context context,int index,String coeficient){
        this(context,index);
        numField.setText(coeficient);
    }

    private CustomNumField createNumField(){
        CustomNumField numField = new CustomNumField(this.getContext());
        setNumFieldListener(numField);
        numField.requestFocus();

        return numField;
    }
    private void setNumFieldListener(final CustomNumField numField){
        numField.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateVariableString();
            }
        });
    }
    public void updateVariableString(){
        try{
            variableString = StringManipulation.coeficientToVariable(numField.getValue(),index);
        } catch (Exception e){
            variableString = "";
        }
    }
    private TextView createTextView(int index){
        TextView textView = new TextView(this.getContext());
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,24);

        return textView;
    }
    public void adjustName(int index){
        setTextView(getResources().getString(R.string.variable_decision_simbolo)+StringManipulation.subscript(index));
    }
    private CircularTextView createEliminateButton(){
        final LayoutParams lparams = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        lparams.gravity=Gravity.CENTER;
        lparams.leftMargin = 8;
        CircularTextView eliminateButton = new CircularTextView(this.getContext());
        eliminateButton.setText(getResources().getString(R.string.clear_icon));
        eliminateButton.setTextSize(TypedValue.COMPLEX_UNIT_SP,28);
        eliminateButton.setTextColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
        eliminateButton.setLayoutParams(lparams);
        eliminateButton.setGravity(Gravity.CENTER);
        eliminateButton.setSolidColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
        eliminateButton.setPadding(5,5,5,5);
        Typeface typeFace=Typeface.createFromAsset(getContext().getAssets(),"fonts/MaterialIcons-Regular.ttf");
        eliminateButton.setTypeface(typeFace);

        return eliminateButton;
    }
    public CustomNumField getNumField() {
        return numField;
    }
    public void setTextView(String str) {
        textView.setText(str);
    }

    public TextView getEliminateButton() {
        return eliminateButton;
    }


    public String getVariableString() {
        return variableString;
    }
    public void setIndex(int index) {
        this.index = index;
    }
}
