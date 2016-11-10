package com.carbajal.danniel.ioapp.views.input.model;

import android.content.Context;
import android.content.res.Resources;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.carbajal.danniel.ioapp.R;
import com.carbajal.danniel.ioapp.StringManipulation;
import com.carbajal.danniel.ioapp.views.customViews.InputViews.CustomNumField;
import com.carbajal.danniel.ioapp.views.customViews.InputViews.VariableDecisionCampo;

import java.util.ArrayList;

/**
 * Created by daniel on 11/3/16.
 */

public class ModelInputView extends ScrollView{

    private LinearLayout innerContainer;
    private ArrayList<VariableDecisionCampo> containers = new ArrayList<VariableDecisionCampo>();
    private ToggleButton maxMinToggle;
    private TextView modelPreview;
    private Button captureButton;

    public ModelInputView(Context context) {
        super(context);
        init();

    }

    public ModelInputView(Context context,String[] coeficients) {
        super(context);
        init(coeficients);
    }

    public ModelInputView(Context context, AttributeSet attrs) {
        super(context,attrs);
        init();
    }

    public ModelInputView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }
    private void init(){
        String[] coeficients = new String[1];
        coeficients[0] = "1";
        init(coeficients);
    }

    private void init(String[] coeficients){

        setLayoutParams();

        innerContainer = new LinearLayout(getContext());
        innerContainer.setOrientation(innerContainer.VERTICAL);
        addView(innerContainer);

        TextView title = new TextView(this.getContext());
        title.setText("Modelo");
        title.setGravity(Gravity.CENTER);
        title.setTextSize(TypedValue.COMPLEX_UNIT_SP,32);
        this.innerContainer.addView(title);

        maxMinToggle = new ToggleButton(this.getContext());
        maxMinToggle.setTextOn("Maximizar");
        maxMinToggle.setTextOff("Minimizar");
        maxMinToggle.toggle();
        this.innerContainer.addView(maxMinToggle);

        modelPreview = new TextView(this.getContext());
        modelPreview.setTextSize(TypedValue.COMPLEX_UNIT_SP,36);
        this.innerContainer.addView(modelPreview);


        captureButton = new Button(this.getContext());
        captureButton.setText("Siguiente");
        this.innerContainer.addView(captureButton);

        for (String x:coeficients){
            addField(x);
        }
    }
    public Button getCaptureButton(){
        return captureButton;
    }

    private void addField(){
        addField("1");
    }

    private void addField(String coeficient){
        final VariableDecisionCampo variableDecisionCampo = new VariableDecisionCampo(this.getContext(),containers.size()+1,coeficient);
        containers.add(variableDecisionCampo);
        createNumFieldListener(variableDecisionCampo.getNumField());
        createEliminateButtonListener(variableDecisionCampo);
        this.innerContainer.addView(variableDecisionCampo,this.innerContainer.getChildCount()-1);
        variableDecisionCampo.getNumField().parseValue(containers.size());

        updateModelPreview();

        post(new Runnable() {
            @Override
            public void run() {

                fullScroll(View.FOCUS_DOWN);
                variableDecisionCampo.getNumField().requestFocus();
                variableDecisionCampo.getNumField().selectAll();

            }
        });

    }

    private void createNumFieldListener(final CustomNumField numField){
        numField.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ( (actionId == EditorInfo.IME_ACTION_DONE) ){
                    numField.clearFocus();
                    addField();
                    return true;
                } else {
                    return false;
                }
            }
        });
        numField.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                try{
                    numField.getValue();
                    numField.parseValue(containers.size()-1);
                    updateModelPreview();
                } catch (Exception e){

                }
            }
        });
    }

    private void createEliminateButtonListener(final VariableDecisionCampo variableDecisionCampo){
        variableDecisionCampo.getEliminateButton().setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                try{
                    removeField(variableDecisionCampo);
                }catch (Exception e){
                    Log.v("WTF",e.getLocalizedMessage());
                }
            }
        });
    }

    private void removeField(VariableDecisionCampo v) throws Exception{
        if (containers.size()>1) {
            removeContainer(v);
            adjustNames();
            updateModelPreview();
        } else {
            throw new Exception(getResources().getString(R.string.variables_minimas));
        }
    }
    private void removeContainer(VariableDecisionCampo v){
        this.innerContainer.removeView(v);
        int index = containers.indexOf(v);
        containers.remove(index);
    }

    private void adjustNames(){
        int i=1;
        for (VariableDecisionCampo x: containers){
            String index = StringManipulation.subscript(i);
            x.setTextView(getResources().getString(R.string.variable_decision_simbolo)+index+" +");
            i++;
        }
    }
    public double[] getCoeficientsValues() throws Exception{
        double[] coeficientsVariables = new double[containers.size()];
        for (int i = 0; i<containers.size();i++){
            try {
                coeficientsVariables[i] = containers.get(i).getNumField().getValue();
            } catch (Exception e){
                throw e;
            }
        }
        return coeficientsVariables;
    }
    public String[] getCoeficients(){
        String[] coeficientsVariables = new String[containers.size()];
        for (int i = 0; i<containers.size();i++){
            coeficientsVariables[i] = String.valueOf(containers.get(i).getNumField().getText());
        }
        return coeficientsVariables;
    }

    private void updateModelPreview() {
        String modelPreviewStr = "Z =";
        int i = 1;
        for (VariableDecisionCampo x : containers) {
            modelPreviewStr += x.getNumField().getParsedValue();
            i++;
        }
        modelPreview.setText(modelPreviewStr);
    }

    private void setLayoutParams(){
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);

        Resources r = getResources();
        int horizontalMargin = getResources().getDimensionPixelSize(R.dimen.activity_horizontal_margin);
        int verticalMargin = getResources().getDimensionPixelSize(R.dimen.activity_vertical_margin);

        Log.v(horizontalMargin + "", "WUTF");

        layoutParams.setMargins(horizontalMargin,
                verticalMargin,
                horizontalMargin,
                verticalMargin);

        this.setLayoutParams(layoutParams);
    }


}
