package com.carbajal.danniel.ioapp.views.customViews.InputViews;

import android.content.Context;
import android.text.InputType;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.EditText;

/**
 * Created by daniel on 11/9/16.
 */

public class CustomNumField extends EditText {

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
        setTextSize(TypedValue.COMPLEX_UNIT_SP,24);
        setSelectAllOnFocus(true);
    }
    public double getValue(){
        try {
            return Double.parseDouble(getText().toString());
        } catch (Exception e){
            return 0;
        }
    }
}
