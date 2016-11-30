package com.carbajal.danniel.ioapp.views.output.lineal.simplex;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.TextView;

import com.carbajal.danniel.ioapp.R;
import com.carbajal.danniel.ioapp.views.input.InputView;

/**
 * Created by daniel on 11/18/16.
 */

public class ResultSimplexView extends InputView {

    public ResultSimplexView(Context context,String[] results) {
        super(context);
        init(results);
    }

    public ResultSimplexView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ResultSimplexView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    private void init(String[] results){
        setTitle(getResources().getString(R.string.model));
        for (String variable : results) {
            addContent(ResultTextView(variable));
        }
        
    }
    public TextView ResultTextView(String str){
        TextView textView= new TextView(getContext());
        textView.setText(str);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,28);
        return textView;
    }

}
