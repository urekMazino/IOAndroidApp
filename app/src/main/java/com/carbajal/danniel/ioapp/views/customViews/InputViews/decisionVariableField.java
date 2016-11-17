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
import com.carbajal.danniel.ioapp.support.BindableString;
import com.carbajal.danniel.ioapp.views.customViews.CircularTextView;
import com.carbajal.danniel.ioapp.support.StringManipulation;


/**
 * Created by daniel on 11/6/16.
 */

public class DecisionVariableField extends LinearLayout{

    private int index;
    private BindableString variableString = new BindableString();

    private CustomNumField numField;
    private TextView textView;
    private CircularTextView eliminateButton;

    public DecisionVariableField(Context context, int index) {
        super(context);
        //this.setBaselineAligned(false);
        DecisionVariableField.initLinearLayout(this);
        this.index = index;

        numField = createNumField();
        textView = createTextView(index);
        eliminateButton = createEliminateButton();


        addView(numField);
        addView(textView);
        addView(eliminateButton);

        adjustName(index);
    }

    public DecisionVariableField(Context context, int index, String coeficient){
        this(context,index);
        numField.setText(coeficient);
    }



    private CustomNumField createNumField(){
        CustomNumField numField = new CustomNumField(this.getContext());
        numField.setMaxWidth(getResources().getDimensionPixelSize(R.dimen.number_field_max_width));
        numField.setMinimumWidth(getResources().getDimensionPixelSize(R.dimen.number_field_min_width));

        setNumFieldListener(numField);
        numField.requestFocus();

        return numField;
    }
    private void setNumFieldListener(final CustomNumField numField){
        numField.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

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
            variableString.setValue(StringManipulation.coeficientToVariable(numField.getValue(),index));
        } catch (Exception e){
            variableString.setValue("");
        }
    }
    private TextView createTextView(int index){
        TextView textView = new TextView(this.getContext());
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,24);

        return textView;
    }
    public void adjustName(int index){
        setTextView(getResources().getString(R.string.decision_variable_symbol)+StringManipulation.subscript(index));
    }
    private CircularTextView createEliminateButton(){
        final LayoutParams lparams = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        lparams.gravity=Gravity.CENTER;
        lparams.leftMargin = 16;
        CircularTextView eliminateButton = new CircularTextView(this.getContext());
        eliminateButton.setText(getResources().getString(R.string.clear_icon));
        eliminateButton.setTextSize(TypedValue.COMPLEX_UNIT_SP,28);
        eliminateButton.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
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

    public void removeEliminateButton(){
        removeView(eliminateButton);
    }
    public TextView getEliminateButton() {
        return eliminateButton;
    }


    public String getVariableStringValue() {
        return variableString.getValue();
    }
    public BindableString getVariableString(){
        return variableString;
    }
    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public static void initLinearLayout(LinearLayout linearLayout){
        LinearLayout.LayoutParams layoutParams= new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.topMargin = 16;
        layoutParams.rightMargin = 48;
        linearLayout.setPadding(0,0,48,0);
        linearLayout.setLayoutParams(layoutParams);
        linearLayout.setOrientation(HORIZONTAL);
    }
}
