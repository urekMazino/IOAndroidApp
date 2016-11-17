package com.carbajal.danniel.ioapp.backups;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.carbajal.danniel.ioapp.R;
import com.carbajal.danniel.ioapp.views.customViews.InputViews.CustomNumField;
import com.carbajal.danniel.ioapp.views.customViews.InputViews.FlowLayout;
import com.carbajal.danniel.ioapp.views.customViews.InputViews.DecisionVariableField;
import com.carbajal.danniel.ioapp.support.StringManipulation;

import java.util.ArrayList;

/**
 * Created by daniel on 11/3/16.
 */

public class FuncionObjetivoInputViewBackup extends ScrollView {

    private LinearLayout innerContainer;
    private ArrayList<DecisionVariableField> containers = new ArrayList<DecisionVariableField>();
    private FlowLayout flowLayout;

    private ToggleButton maxMinToggle;
    private TextView modelPreview;
    private Button captureButton;

    public FuncionObjetivoInputViewBackup(Context context) {
        super(context);
        init();

    }

    public FuncionObjetivoInputViewBackup(Context context, String[] coeficients) {
        super(context);
        init(coeficients);
    }

    public FuncionObjetivoInputViewBackup(Context context, AttributeSet attrs) {
        super(context,attrs);
        init();
    }

    public FuncionObjetivoInputViewBackup(Context context, AttributeSet attrs, int defStyle) {
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


        flowLayout = new FlowLayout(getContext());
        this.innerContainer.addView(flowLayout);

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
        DecisionVariableField decisionVariableField = new DecisionVariableField(this.getContext(),containers.size()+1,coeficient);
        final CustomNumField numField = decisionVariableField.getNumField();
        createNumFieldListener(numField);
        createEliminateButtonListener(decisionVariableField);

        containers.add(decisionVariableField);
        flowLayout.addView(decisionVariableField,flowLayout.getChildCount());

        decisionVariableField.updateVariableString();
        updateFuncionPreview();

        adjustScroll(numField);

    }
    private void adjustScroll(final CustomNumField numField){
        post(new Runnable() {
            @Override
            public void run() {
                fullScroll(View.FOCUS_DOWN);
                numField.requestFocus();
                numField.selectAll();

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
            public void afterTextChanged(Editable s) {
                updateFuncionPreview();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
    }

    private void createEliminateButtonListener(final DecisionVariableField decisionVariableField){
        decisionVariableField.getEliminateButton().setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                try{
                    removeField(decisionVariableField);
                }catch (Exception e){
                }
            }
        });
    }

    private void removeField(DecisionVariableField v) throws Exception{
        if (containers.size()>1) {
            removeContainer(v);
            adjustNames();
            remakeFuncionPreview();
        } else {
            throw new Exception(getResources().getString(R.string.minimum_variables_message));
        }
    }
    private void removeContainer(DecisionVariableField v){
        this.flowLayout.removeView(v);
        int index = containers.indexOf(v);
        containers.remove(index);
    }

    private void adjustNames(){
        int i=1;
        for (DecisionVariableField x: containers){
            String index = StringManipulation.subscript(i);
            x.adjustName(i);
            x.setIndex(i);
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

    private void updateFuncionPreview() {
        String modelPreviewStr = "Z =";
        for (DecisionVariableField x : containers) {
            modelPreviewStr += x.getVariableStringValue();
        }
        modelPreview.setText(modelPreviewStr);
    }
    private void remakeFuncionPreview(){
        String funcionPreviewStr = "Z =";
        for (DecisionVariableField x : containers) {
            x.updateVariableString();
            funcionPreviewStr += x.getVariableStringValue();
        }
        modelPreview.setText(funcionPreviewStr);
    }


    private void setLayoutParams(){
        LayoutParams layoutParams = new LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

        int horizontalMargin = getResources().getDimensionPixelSize(R.dimen.activity_horizontal_margin);
        int verticalMargin = getResources().getDimensionPixelSize(R.dimen.activity_vertical_margin);

        layoutParams.setMargins(horizontalMargin,
                verticalMargin,
                horizontalMargin,
                verticalMargin);

        this.setLayoutParams(layoutParams);
    }

}
