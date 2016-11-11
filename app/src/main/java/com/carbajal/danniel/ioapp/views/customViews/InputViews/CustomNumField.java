package com.carbajal.danniel.ioapp.views.customViews.InputViews;

import android.content.Context;
import android.text.InputType;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.EditText;

import com.carbajal.danniel.ioapp.views.support.StringManipulation;

/**
 * Created by daniel on 11/9/16.
 */

public class CustomNumField extends EditText {

    private String parsedValue;

    public CustomNumField(Context context) {
        super(context);
        init();
    }

    public CustomNumField(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomNumField(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
        setMinimumWidth(300);
        setTextSize(TypedValue.COMPLEX_UNIT_SP,24);
        setSelectAllOnFocus(true);
    }

    public String parseValue(int i) {
        i = Math.max(i,1);
        double value = Double.parseDouble(getText().toString());
        parsedValue = StringManipulation.coeficientToVariable(value,i);
        return parsedValue;
    }
    public String getParsedValue(){
        return parsedValue;
    }
    public double getValue() throws Exception{
        return Double.parseDouble(getText().toString());
    }
}
