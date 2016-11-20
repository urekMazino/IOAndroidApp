package com.carbajal.danniel.ioapp.views.output;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.TextView;

import com.carbajal.danniel.ioapp.R;
import com.carbajal.danniel.ioapp.models.programacionlineal.ModeloOptimizacionLinealImp;
import com.carbajal.danniel.ioapp.models.programacionlineal.Restriccion;
import com.carbajal.danniel.ioapp.views.input.InputView;

/**
 * Created by daniel on 11/18/16.
 */

public class ModelView extends InputView {

    public ModelView(Context context, ModeloOptimizacionLinealImp modelo) {
        super(context);
        init(modelo);
    }

    public ModelView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ModelView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    private void init(ModeloOptimizacionLinealImp modelo){
        setTitle(getResources().getString(R.string.model));
        addContent(createTV(modelo.getFuncionObjetivo().getString()));
        addContent(createTV("\t\t\t\t Sujeto a:"));
        for (Restriccion restriccion : modelo.getRestricciones()) {
            addContent(createTV(restriccion.getString()));
        }
        
    }

    @Override
    public void setTitle(String string){
        super.setTitle(string);
    }
    private TextView createTV(String str){
        TextView textView= new TextView(getContext());
        textView.setText(str);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,28 );

        return textView;
    }
}
