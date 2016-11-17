package com.carbajal.danniel.ioapp.views.input;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;

import com.carbajal.danniel.ioapp.R;
import com.carbajal.danniel.ioapp.support.BindableString;
import com.carbajal.danniel.ioapp.views.customViews.InputViews.BindableTextView;
import com.carbajal.danniel.ioapp.views.customViews.InputViews.FlowLayout;

import java.util.ArrayList;

/**
 * Created by daniel on 11/15/16.
 */

public class Preview extends FlowLayout {

    protected ArrayList<BindableTextView> textViews = new ArrayList<>();

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
        return textViews.get(textViews.size()-1);
    }
    public BindableTextView addTextViewUntracked(String str){
        BindableTextView bindableTextView = new BindableTextView(getContext());
        bindableTextView.setText(str);
        bindableTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP,getResources().getDimensionPixelSize(R.dimen.preview_text_size));
        this.addView(bindableTextView);
        return bindableTextView;
    }
    public BindableTextView addTextViewUntracked(BindableString str){
        BindableTextView bindableTextView = new BindableTextView(getContext());
        bindableTextView.setText(str.getValue());
        bindableTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP,getResources().getDimensionPixelSize(R.dimen.preview_text_size));
        str.bind(bindableTextView);
        this.addView(bindableTextView);
        return bindableTextView;
    }

    public void addTextView(BindableString str){
        str.bind(addTextView(str.getValue()));
    }

    public void removeTextView(int index){
        this.removeView(textViews.get(index));
        textViews.remove(index);
    }
}

