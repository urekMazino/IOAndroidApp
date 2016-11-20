package com.carbajal.danniel.ioapp.views.output;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.TextView;

import com.carbajal.danniel.ioapp.models.programacionlineal.FuncionObjetivo;
import com.carbajal.danniel.ioapp.views.input.InputView;

/**
 * Created by daniel on 11/18/16.
 */

public class FuncionObjetivoView extends InputView {

    public FuncionObjetivoView(Context context, FuncionObjetivo funcionObjetivo) {
        super(context);
        init(funcionObjetivo);
    }

    public FuncionObjetivoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FuncionObjetivoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    private void init(FuncionObjetivo funcionObjetivo){
        addContent(createTV(funcionObjetivo.getString()));
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
