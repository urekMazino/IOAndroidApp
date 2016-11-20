package com.carbajal.danniel.ioapp.views.customViews.InputViews;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.carbajal.danniel.ioapp.support.BindableString;

/**
 * Created by daniel on 11/13/16.
 */

public class BindableTextView extends TextView implements BindableString.bindableString{


    public BindableTextView(Context context) {
        super(context);
    }

    public BindableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BindableTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }



    @Override
    public void changed(String newValue) {
        this.setText(newValue);
    }

}
