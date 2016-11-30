package com.carbajal.danniel.ioapp.views.input.PL.funcionObjetivo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.carbajal.danniel.ioapp.models.programacionlineal.FuncionObjetivo;
import com.carbajal.danniel.ioapp.support.BindableString;
import com.carbajal.danniel.ioapp.views.input.PL.DecisionVariableField;
import com.carbajal.danniel.ioapp.views.input.InputView;
import com.carbajal.danniel.ioapp.views.input.VariableInputView;

import java.util.ArrayList;

/**
 * Created by daniel on 11/3/16.
 */

public class ObjectiveFunctionInputView extends InputView implements VariableInputView.variableInputChange{

    private VariableInputView variableInputView;
    private ObjectiveFunctionPreview objectiveFunctionPreview;
    private  ArrayList<ObjectiveFunctionChange> listeners = new ArrayList<>();
    private int maxVariables = Integer.MAX_VALUE;

    public ObjectiveFunctionInputView(Context context) {
        super(context);
        init();

    }

    public ObjectiveFunctionInputView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();

    }

    public ObjectiveFunctionInputView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }


    private void init(){
        String[] coeficients = {"1"};


        init(coeficients);
    }

    private void init(String[] coeficients) {

        setTitle("Funcion Objetivo");


        objectiveFunctionPreview = new ObjectiveFunctionPreview(getContext());
        addContent(objectiveFunctionPreview);

        variableInputView = new VariableInputView(getContext(), coeficients, this);
        addContent(variableInputView);


        final EditText toFocus = variableInputView.getDecisionVariableFields().get(0).getNumField();

        final InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        toFocus.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                toFocus.requestFocus();
                imm.showSoftInput(toFocus, 0);
            }
        }, 100);
    }

    public double[] getCoeficientsValues() throws Exception{
        ArrayList<DecisionVariableField> decisionVariableFields = variableInputView.getDecisionVariableFields();
        double[] coeficentsVariables = new double[decisionVariableFields.size()];
        for (int i = 0; i< decisionVariableFields.size(); i++){
            coeficentsVariables[i] = decisionVariableFields.get(i).getNumField().getValue();
        }
        return coeficentsVariables;
    }
    public int getVariableCount(){
        return variableInputView.getDecisionVariableFields().size();
    }

    public void setMaxVariables(int maxVariables) {
        if (maxVariables==2){
            variableInputView.addField();
            variableInputView.setMinFields(2);
            variableInputView.setCanRemoveVariables(false);
        }
        this.maxVariables = maxVariables;
    }

    @Override
    public void addedField(BindableString bindableString) {
        notifyVariableAdded();
        objectiveFunctionPreview.addTextView(bindableString);
    }

    @Override
    public void removedField(int index) {
        notifyVariableRemoved(index);
        objectiveFunctionPreview.removeTextView(index);
    }

    @Override
    public void finishInputs() {
        if (variableInputView.getDecisionVariableFields().size() < maxVariables) {
            variableInputView.addField();
        }
    }

    public void addListener(ObjectiveFunctionChange objectiveFunctionChange){
        listeners.add(objectiveFunctionChange);
    }     
    

    public void notifyVariableAdded(){
        for (ObjectiveFunctionChange lister: listeners){
            lister.variableAdded();
        }
    }
    private void notifyVariableRemoved(int index){
        for (ObjectiveFunctionChange lister: listeners){
            lister.variableRemoved(index);
        }
    }
    public FuncionObjetivo buildObjectiveFunction() throws Exception{
        return new FuncionObjetivo(getObjectiveFunctionPreview().getMinMaxBoolean(),getCoeficientsValues());
    }
    public ObjectiveFunctionPreview getObjectiveFunctionPreview(){
        return objectiveFunctionPreview;
    }
    public interface ObjectiveFunctionChange{
        void variableAdded();
        void variableRemoved(int index);
    }
}
