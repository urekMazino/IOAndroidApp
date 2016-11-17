package com.carbajal.danniel.ioapp.views.input;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.TextView;

import com.carbajal.danniel.ioapp.R;
import com.carbajal.danniel.ioapp.support.BindableString;
import com.carbajal.danniel.ioapp.views.customViews.InputViews.CustomNumField;
import com.carbajal.danniel.ioapp.views.customViews.InputViews.FlowLayout;
import com.carbajal.danniel.ioapp.views.customViews.InputViews.DecisionVariableField;

import java.util.ArrayList;

/**
 * Created by daniel on 11/12/16.
 */

public class VariableInputView extends FlowLayout{


    private boolean canAddVariables = true;
    private boolean canRemoveVariables = true;
    private ArrayList<DecisionVariableField> decisionVariableFields = new ArrayList<>();
    private variableInputChange listener;

    public VariableInputView(Context context,String[] coeficients,variableInputChange listener) {
        super(context);
        setListener(listener);
        init(coeficients);
    }
    public VariableInputView(Context context) {
        super(context);
        init();
    }

    public VariableInputView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public VariableInputView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }
    void init(){
        String[] array = {"1"};
        init(array);
    }
    void init(String[] coeficients){
        for (String x:coeficients){
            addField(x);
        }
    }
    public void addField(){
        addField("1");
    }

    public void addField(String coeficient){
        final DecisionVariableField decisionVariableField = new DecisionVariableField(this.getContext(), decisionVariableFields.size()+1,coeficient);

        final CustomNumField numField = decisionVariableField.getNumField();
        numField.requestFocus();
        numField.selectAll();
        createNumFieldListener(numField);

        if (canRemoveVariables) {
            createEliminateButtonListener(decisionVariableField);
        } else {
            decisionVariableField.removeEliminateButton();
        }
        decisionVariableFields.add(decisionVariableField);
        this.addView(decisionVariableField);

        decisionVariableField.updateVariableString();
        notifyFieldAdded(decisionVariableField.getVariableString());
    }

    private void createNumFieldListener(final CustomNumField numField){
        numField.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ( (actionId == EditorInfo.IME_ACTION_DONE && canAddVariables) ){
                    numField.clearFocus();
                    addField();
                    return true;
                } else {
                    return false;
                }
            }
        });

    }

    private void createEliminateButtonListener(final DecisionVariableField decisionVariableField){
        decisionVariableField.getEliminateButton().setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                try{
                    removeField(decisionVariableField);
                }catch (Exception e){
                    Log.v("no se removio",e.getMessage());
                }
            }
        });
    }

    public void removeField(DecisionVariableField v) throws Exception{
        Log.v("index"+decisionVariableFields.indexOf(v),decisionVariableFields.size()+"size");

        if (decisionVariableFields.size()>1) {
            notifyFieldRemoved(v.getIndex());
            removeContainer(v);
            adjustNames();
        } else {
            Log.v((getResources().getString(R.string.minimum_variables_message)+"se tiene solamente "+decisionVariableFields.size()),"wtf");
            throw new Exception(getResources().getString(R.string.minimum_variables_message)+"se tiene solamente "+decisionVariableFields.size());
        }
    }
    public void removeField(int indice) throws Exception{
        if (decisionVariableFields.size()>1) {
            notifyFieldRemoved(indice);
            removeContainer(indice);
            adjustNames();
        } else {
            Log.v((getResources().getString(R.string.minimum_variables_message)+"se tiene solamente "+decisionVariableFields.size()),"wtf");
            throw new Exception(getResources().getString(R.string.minimum_variables_message)+"se tiene solamente "+decisionVariableFields.size());
        }
    }

    private void adjustNames(){
        int index=1;
        for (DecisionVariableField decisionVariableField : decisionVariableFields){
            decisionVariableField.adjustName(index);
            decisionVariableField.setIndex(index);
            decisionVariableField.updateVariableString();
            index++;
        }
    }
    private void removeContainer(DecisionVariableField v){
        Log.v("",Boolean.toString(decisionVariableFields.contains(v)));
        this.removeView(v);
        decisionVariableFields.remove(v);
    }
    private void removeContainer(int indice){
        this.removeViewAt(indice);
        decisionVariableFields.remove(indice);
    }

    public void setListener(variableInputChange listener){
        this.listener = listener;
    }

    public void setCanAddVariables(boolean canAddVariables) {
        this.canAddVariables = canAddVariables;
    }

    public void setCanRemoveVariables(boolean canRemoveVariables) {
        this.canRemoveVariables = canRemoveVariables;
        if (canRemoveVariables == false) {
            for (DecisionVariableField decisionVariableField : decisionVariableFields) {
                decisionVariableField.removeEliminateButton();
            }
        }
    }

    private void notifyFieldRemoved(int index){
        if(listener != null){
            listener.removedField(index);
        }
    }
    private void notifyFieldAdded(BindableString bindableString){
        if(listener != null){
            listener.addedField(bindableString);
        }
    }

    public ArrayList<DecisionVariableField> getDecisionVariableFields() {
        return decisionVariableFields;
    }

    public interface variableInputChange {
        void addedField(BindableString bindableString);
        void removedField(int index);
    }
}
