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
import com.carbajal.danniel.ioapp.views.customViews.FontButtonBuilder;
import com.carbajal.danniel.ioapp.views.customViews.InputViews.CustomNumField;
import com.carbajal.danniel.ioapp.views.input.PL.DecisionVariableField;
import com.carbajal.danniel.ioapp.views.customViews.InputViews.FlowLayout;

import java.util.ArrayList;

/**
 * Created by daniel on 11/12/16.
 */

public class VariableInputView extends FlowLayout{

    private int minFields = 1;
        private boolean canRemoveVariables = true;
    private ArrayList<DecisionVariableField> decisionVariableFields = new ArrayList<>();
    private variableInputChange listener;
    private TextView addButton;

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
        initAddButton();
        for (String x:coeficients){
            addField(x);
        }
    }

    public void setMinFields(int minFields) {
        this.minFields = minFields;
    }

    public void initAddButton(){
        TextView button = FontButtonBuilder.BuildCircularButton(getContext(),getResources().getString(R.string.add_icon),30,getResources().getColor(R.color.green));
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyFinish();
            }
        });
        addButton = button;
    }
    public void addField(){
        addField("1");
    }

    public void addField(String coeficient){
        final DecisionVariableField decisionVariableField = new DecisionVariableField(this.getContext(), decisionVariableFields.size()+1,coeficient);

        final CustomNumField numField = decisionVariableField.getNumField();
        createNumFieldListener(numField);

        if (canRemoveVariables) {
            createEliminateButtonListener(decisionVariableField);
            if (decisionVariableFields.size()>0)
                decisionVariableFields.get(decisionVariableFields.size()-1).removeButton(addButton);
            decisionVariableField.addButton(addButton);
        } else {
            decisionVariableField.removeEliminateButton();
        }

        decisionVariableFields.add(decisionVariableField);
        this.addView(decisionVariableField);

        decisionVariableField.updateVariableString();
        notifyFieldAdded(decisionVariableField.getVariableString());
        numField.requestFocus();
        numField.selectAll();
    }
    private void createNumFieldListener(final CustomNumField numField){

        numField.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((actionId == EditorInfo.IME_ACTION_DONE) ){
                    notifyFinish();
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

        if (decisionVariableFields.size()>minFields) {
            notifyFieldRemoved(v.getIndex());
            adjustButton(v.getIndex()-1);
            removeContainer(v);
            adjustNames();
            decisionVariableFields.get(decisionVariableFields.size()-1).getNumField().requestFocus();
        } else {
            Log.v((getResources().getString(R.string.minimum_variables_message)+"se tiene solamente "+decisionVariableFields.size()),"wtf");
            throw new Exception(getResources().getString(R.string.minimum_variables_message)+"se tiene solamente "+decisionVariableFields.size());
        }
    }
    public void removeField(int indice) throws Exception{
        if (decisionVariableFields.size()>minFields) {
            notifyFieldRemoved(indice);
            adjustButton(indice);
            removeContainer(indice);
            adjustNames();
            decisionVariableFields.get(decisionVariableFields.size()-1).getNumField().requestFocus();
        } else {
            Log.v((getResources().getString(R.string.minimum_variables_message)+"se tiene solamente "+decisionVariableFields.size()),"wtf");
            throw new Exception(getResources().getString(R.string.minimum_variables_message)+"se tiene solamente "+decisionVariableFields.size());
        }
    }
    private void adjustButton(int indice){
        if (canRemoveVariables && indice==decisionVariableFields.size()-1) {
            decisionVariableFields.get(decisionVariableFields.size() - 1).removeButton(addButton);
            decisionVariableFields.get(decisionVariableFields.size() - 2).addButton(addButton);
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

    public void setCanRemoveVariables(boolean canRemoveVariables) {
        this.canRemoveVariables = canRemoveVariables;
        decisionVariableFields.get(decisionVariableFields.size() - 1).removeButton(addButton);
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
    private void notifyFinish(){
        if(listener != null){
            listener.finishInputs();
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

    public interface    variableInputChange {
        void addedField(BindableString bindableString);
        void removedField(int index);
        void finishInputs();
    }
}
