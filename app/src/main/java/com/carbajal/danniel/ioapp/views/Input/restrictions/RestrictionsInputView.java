package com.carbajal.danniel.ioapp.views.input.restrictions;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.carbajal.danniel.ioapp.R;
import com.carbajal.danniel.ioapp.models.programacionlineal.Restriccion;
import com.carbajal.danniel.ioapp.support.BindableString;
import com.carbajal.danniel.ioapp.support.StringManipulation;
import com.carbajal.danniel.ioapp.views.customViews.FontButtonBuilder;
import com.carbajal.danniel.ioapp.views.customViews.InputViews.CustomNumField;
import com.carbajal.danniel.ioapp.views.customViews.InputViews.DecisionVariableField;
import com.carbajal.danniel.ioapp.views.customViews.InputViews.FlowLayout;
import com.carbajal.danniel.ioapp.views.input.InputView;
import com.carbajal.danniel.ioapp.views.input.Preview;
import com.carbajal.danniel.ioapp.views.input.VariableInputView;
import com.carbajal.danniel.ioapp.views.input.funcionObjetivo.ObjectiveFunctionInputView;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by daniel on 11/3/16.
 */

public class RestrictionsInputView extends InputView implements VariableInputView.variableInputChange,
        ObjectiveFunctionInputView.ObjectiveFunctionChange,
        AdapterView.OnItemSelectedListener {

    private VariableInputView variableInputView;
    private TextView closeRestrictionButton;

    private RestrictionPreview functionPreview;
    private BindableString bindableStringRHS = new BindableString();
    private CustomNumField numFieldRHS;
    private Spinner spinner;
    private ArrayList<ButtonListener> listeners = new ArrayList<>();


    public RestrictionsInputView(Context context,int index) {
        super(context);
        init(index);

    }

    public RestrictionsInputView(Context context,int index, int amountOfVariables) {
        super(context);
        init(index,amountOfVariables);
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
        init(index,1);
    }

    private void init(int index,int amount){
        setTitle("Restriccion "+index);
        String[] coeficients = new String[amount];
        Arrays.fill(coeficients,"1");

        functionPreview = new RestrictionPreview(getContext());
        addContent(functionPreview);
        initCloseRestrictionButton();
        initSpinner();
        functionPreview.addTextViewRHS(bindableStringRHS);

        variableInputView = new VariableInputView(getContext(),coeficients,this);
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
        numFieldRHS.setOnEditorActionListener(new TextView.OnEditorActionListener() {
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
    public void changeTitleIndex(int index){
        setTitle("Restriccion "+index);
    }
    private void initCloseRestrictionButton(){
        closeRestrictionButton = FontButtonBuilder.BuildButton(getContext(),"delete",40);

        closeRestrictionButton.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View v) {
                notifyClosedRestriction(RestrictionsInputView.this);
            }
        });

        titleContainer.addView(closeRestrictionButton);

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
    public Restriccion buildRestriccion() throws Exception{
        Restriccion restriccion = new Restriccion(numFieldRHS.getValue(),getCoeficientsValues());
        restriccion.setIgualdad(spinner.getSelectedItemPosition());
        return restriccion;
    }
    public int getSpinnerItem(){
        return spinner.getSelectedItemPosition();
    }
    public Preview getFunctionPreview() {
        return functionPreview;
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
    public void finishInputs() {
        Log.v("ENTER 2 level","confirmed");
        notifyFinish();
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
    public void addListener(ButtonListener newListener){
        listeners.add(newListener);
    }

    private void notifyClosedRestriction(RestrictionsInputView restrictionsInputView){
        for (ButtonListener listener: listeners){
            listener.closedButtonClicked(restrictionsInputView);
        }
    }
    private void notifyFinish(){
        for (ButtonListener listener: listeners){
            Log.v("second level","confirmed");
            listener.finish();
        }
    }

    public interface ButtonListener {
        void closedButtonClicked(RestrictionsInputView restrictionsInputView);
        void finish();
    }
}
