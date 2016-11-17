package com.carbajal.danniel.ioapp.views.customViews.InputViews;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.EditText;

import com.carbajal.danniel.ioapp.support.BindableString;

/**
 * Created by daniel on 11/13/16.
 */

public class EditTextBindable extends EditText{

    private BindableString bindableString;

    public EditTextBindable(Context context) {
        super(context);
        init();
    }

    public EditTextBindable(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EditTextBindable(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    private void init(){
        new BindableString();
        initListener();
    }
    public BindableString getBindableString(){
        return bindableString;
    }
    private  void initListener(){
        this.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                getText();
            }
        });
    }
}
