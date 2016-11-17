package com.carbajal.danniel.ioapp.views.input.restrictions;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.carbajal.danniel.ioapp.R;
import com.carbajal.danniel.ioapp.support.BindableString;
import com.carbajal.danniel.ioapp.support.StringManipulation;
import com.carbajal.danniel.ioapp.views.customViews.InputViews.CustomNumField;
import com.carbajal.danniel.ioapp.views.customViews.InputViews.DecisionVariableField;
import com.carbajal.danniel.ioapp.views.customViews.InputViews.FlowLayout;
import com.carbajal.danniel.ioapp.views.input.InputView;
import com.carbajal.danniel.ioapp.views.input.Preview;
import com.carbajal.danniel.ioapp.views.input.VariableInputView;
import com.carbajal.danniel.ioapp.views.input.funcionObjetivo.ObjectiveFunctionInputView;

import java.util.ArrayList;

/**
 * Created by daniel on 11/3/16.
 */

public class RestrictionsInputView extends InputView implements VariableInputView.variableInputChange,
        ObjectiveFunctionInputView.ObjectiveFunctionChange,
        AdapterView.OnItemSelectedListener {

    private VariableInputView variableInputView;
    private TextView closeRestrictionButton;
    private Preview functionPreview;
    private BindableString bindableStringRHS = new BindableString();
    private CustomNumField numFieldRHS;
    private Spinner spinner;

    public RestrictionsInputView(Context context,int index) {
        super(context);
        init(index);

    }

    public RestrictionsInputView(Context context,int index, String[] coeficients) {
        super(context);
        init(index,coeficients);
    }

    public RestrictionsInputView(Context context,int index, AttributeSet attrs) {
        super(context, attrs);
        init(index);

    }

    public RestrictionsInputView(Context context,int index, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(index);

    }

    private void init(int index){
        String[] coeficients = {"1"};
        init(index,coeficients);
    }

    private void init(int index,String[] coeficients){
        setTitle("Restriccion "+index);

        functionPreview = new RestrictionPreview(getContext());
        addContent(functionPreview);
        initCloseRestrictionButton();
        initSpinner();
        functionPreview.addTextViewUntracked(bindableStringRHS);

        variableInputView = new VariableInputView(getContext(),coeficients,this);
        variableInputView.setCanAddVariables(false);
        variableInputView.setCanRemoveVariables(false);
        addContent(variableInputView);

        addContent(initResultInput());
    }
    public FlowLayout initResultInput(){
        FlowLayout resultContainer = new FlowLayout(getContext());

        initNumFieldRHS();

        resultContainer.addView(spinner);
        resultContainer.addView(numFieldRHS);
        return resultContainer;
    }
    public void initNumFieldRHS(){
        numFieldRHS = new CustomNumField(this.getContext());
        numFieldRHS.setText("1");
        numFieldRHS.setMaxWidth(getResources().getDimensionPixelSize(R.dimen.number_field_max_width));
        numFieldRHS.setMinimumWidth(getResources().getDimensionPixelSize(R.dimen.number_field_min_width));
        numFieldRHS.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateBindableString();
            }
        });
    }
    public void changeTitleIndex(int index){
        setTitle("Restriccion "+index);
    }
    private void initCloseRestrictionButton(){
        closeRestrictionButton = new TextView(getContext());
        closeRestrictionButton.setText("delete");
        closeRestrictionButton.setTextSize(TypedValue.COMPLEX_UNIT_SP,32);
        closeRestrictionButton.setTextColor(ContextCompat.getColor(getContext(), R.color.colorAccent));

        Typeface typeFace=Typeface.createFromAsset(getContext().getAssets(),"fonts/MaterialIcons-Regular.ttf");
        closeRestrictionButton.setTypeface(typeFace);
        titleContainer.addView(closeRestrictionButton);
    }
    public TextView getCloseRestrictionButton(){
        return closeRestrictionButton;
    }
    private Spinner initSpinner(){
        spinner = new Spinner(this.getContext());

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.equalities_list, R.layout.spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        return spinner;
    }

    public double[] getCoeficientsValues() throws Exception{
        ArrayList<DecisionVariableField> decisionVariableFields = variableInputView.getDecisionVariableFields();
        double[] coeficentsVariables = new double[decisionVariableFields.size()];
        for (int i = 0; i< decisionVariableFields.size(); i++){
            coeficentsVariables[i] = decisionVariableFields.get(i).getNumField().getValue();
        }
        return coeficentsVariables;
    }
    public String[] getCoeficients(){
        ArrayList<DecisionVariableField> decisionVariableFields = variableInputView.getDecisionVariableFields();
        String[] coeficentsVariables = new String[decisionVariableFields.size()];
        for (int i = 0; i<decisionVariableFields.size();i++){
            coeficentsVariables[i] = String.valueOf(decisionVariableFields.get(i).getNumField().getText());
        }
        return coeficentsVariables;
    }


    @Override
    public void addedField(BindableString bindableString) {
        functionPreview.addTextView(bindableString);
    }

    @Override
    public void removedField(int index) {
        functionPreview.removeTextView(index);
    }


    @Override
    public void variableAdded() {
        variableInputView.addField();
    }

    @Override
    public void variableRemoved(int indice) {
        try {
            variableInputView.removeField(indice-1);
        } catch (Exception e){
            Log.v("",e.getMessage());
        }
    }

    private void updateBindableString(){
        try {
            bindableStringRHS.setValue(" "+spinner.getSelectedItem().toString()+ StringManipulation.DoubleToString(numFieldRHS.getValue())+" ");
        } catch (Exception e){

        }
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        updateBindableString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
