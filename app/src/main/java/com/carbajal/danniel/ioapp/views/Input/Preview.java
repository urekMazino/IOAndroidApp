package com.carbajal.danniel.ioapp.views.input;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.TextView;

import com.carbajal.danniel.ioapp.R;
import com.carbajal.danniel.ioapp.support.BindableString;
import com.carbajal.danniel.ioapp.views.customViews.InputViews.BindableTextView;
import com.carbajal.danniel.ioapp.views.customViews.InputViews.FlowLayout;

import java.util.ArrayList;

/**
 * Created by daniel on 11/15/16.
 */

public abstract class Preview extends FlowLayout{

    protected ArrayList<BindableTextView> textViews = new ArrayList<>();

    protected BindableString entireString = new BindableString();

    public Preview(Context context) {
        super(context);
        init();
    }

    public Preview(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Preview(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }
    protected void init(){

    }

    public BindableTextView addTextView(String str){
        textViews.add(addTextViewUntracked(str));
        updateEntireString();
        return textViews.get(textViews.size()-1);
    }
    public BindableTextView addTextViewUntracked(String str){
        BindableTextView bindableTextView = new BindableTextView(getContext());
        bindableTextView.setText(str);
        bindableTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP,getResources().getDimensionPixelSize(R.dimen.preview_text_size));
        this.addView(bindableTextView);
        addListener(bindableTextView);
        return bindableTextView;
    }

    public BindableTextView addTextViewUntracked(BindableString str){
        BindableTextView bindableTextView = new BindableTextView(getContext());
        bindableTextView.setText(str.getValue());
        bindableTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP,getResources().getDimensionPixelSize(R.dimen.preview_text_size));
        str.bind(bindableTextView);
        this.addView(bindableTextView);
        addListener(bindableTextView);
        updateEntireString();
        return bindableTextView;
    }

    protected void addListener(TextView textView){
        textView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                updateEntireString();
            }
        });
    }

    protected abstract void updateEntireString();

    public void addTextView(BindableString str){
        str.bind(addTextView(str.getValue()));
    }

    public void removeTextView(int index){
        this.removeView(textViews.get(index));
        textViews.remove(index);
    }
    public BindableString getEntireString() {
        return entireString;
    }

}

