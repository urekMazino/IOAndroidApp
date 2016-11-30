package com.carbajal.danniel.ioapp.views.output.transporte;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.TextView;

import com.carbajal.danniel.ioapp.views.input.transporte.TransporteInputView;

/**
 * Created by daniel on 11/25/16.
 */

public class ResultTransporteView extends TransporteInputView {

    public ResultTransporteView(Context context, String result) {
        super(context);
        init(result);
    }

    public ResultTransporteView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ResultTransporteView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init(String result) {
        setTitle("Solucion");
        addContent(ResultTextView(result));

    }

    public TextView ResultTextView(String str) {
        TextView textView = new TextView(getContext());
        textView.setText(str);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 28);
        return textView;

    }
}