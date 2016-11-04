package com.carbajal.danniel.ioapp;

import android.content.Context;
import android.text.InputType;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;

/**
 * Created by daniel on 11/3/16.
 */

public class VariableDecisionCampo extends LinearLayout {

    ArrayList<LinearLayout> containers = new ArrayList<LinearLayout>();
    ToggleButton maxMinToggle;

    public VariableDecisionCampo(Context context) {
        super(context);
        init();
    }
    public VariableDecisionCampo(Context context, AttributeSet attrs) {
        super(context,attrs);
        init();
    }
    public VariableDecisionCampo(Context context,AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }
    private void init(){
        this.setOrientation(VERTICAL);

        TextView textView2 = new TextView(this.getContext());
        textView2.setText("Modelo");
        textView2.setGravity(Gravity.CENTER);
        textView2.setTextSize(TypedValue.COMPLEX_UNIT_SP,32);
        this.addView(textView2);

        maxMinToggle = new ToggleButton(this.getContext());
        maxMinToggle.setTextOn("Maximizar");
        maxMinToggle.setTextOff("Minimizar");
        maxMinToggle.toggle();
        this.addView(maxMinToggle);

        TextView textView = new TextView(this.getContext());
        textView.setText("Z =");
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,36);
        this.addView(textView);


        Button button = new Button(this.getContext());
        button.setText("Agregar Variable");
        button.setOnClickListener(new OnClickListener()
        {
            public void onClick(View v)
            {
                addField();
            }
        });
        this.addView(button);

        addField();

    }
    private void addField(){
        final LinearLayout container = new LinearLayout(this.getContext());
        containers.add(container);
        container.setOrientation(HORIZONTAL);

        final LayoutParams lparams = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);

        EditText numField = new EditText(this.getContext());
        numField.setInputType(InputType.TYPE_NUMBER_FLAG_SIGNED | InputType.TYPE_CLASS_NUMBER);
        //numField.setLayoutParams(lparams);
        numField.setMinimumWidth(300);
        numField.setHint("1");
        numField.setTextSize(TypedValue.COMPLEX_UNIT_SP,24);
        numField.requestFocus();
        numField.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if ( (actionId == EditorInfo.IME_ACTION_DONE) ){
                    addField();
                    return true;
                }
                else{
                    return false;
                }
            }
        });

        TextView textView = new TextView(this.getContext());
        textView.setText("X"+containers.size()+" +");
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,24);

        Button eliminar = new Button(this.getContext());
        eliminar.setText("eliminar");
        eliminar.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
        eliminar.setLayoutParams(lparams);
        eliminar.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v) {
                removeField(container);
            }
        });

        container.addView(numField);
        container.addView(textView);
        container.addView(eliminar);
        this.addView(container,this.getChildCount()-1);
    }
    private void removeField(View v){
        this.removeView(v);
        containers.remove(v);
        adjustNames();
    }
    private void adjustNames(){
        int i=1;
        for (LinearLayout x: containers){
            TextView textView = (TextView) x.getChildAt(x.getChildCount()-2);
            textView.setText("X"+(i++)+" +");
        }
    }
}
