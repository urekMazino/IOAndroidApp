package com.carbajal.danniel.ioapp.views.input.PL.restrictions;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;

import com.carbajal.danniel.ioapp.R;
import com.carbajal.danniel.ioapp.support.BindableString;
import com.carbajal.danniel.ioapp.views.customViews.InputViews.BindableTextView;
import com.carbajal.danniel.ioapp.views.input.Preview;

/**
 * Created by daniel on 11/15/16.
 */

public class RestrictionPreview extends Preview {

    private BindableString bindableTextViewRHS;

    public RestrictionPreview(Context context) {
        super(context);
    }

    public RestrictionPreview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RestrictionPreview(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void addTextViewRHS(BindableString str) {
        BindableTextView bindableTextView = new BindableTextView(getContext());
        bindableTextView.setText(str.getValue());
        str.bind(bindableTextView);
        bindableTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP,getResources().getDimensionPixelSize(R.dimen.preview_text_size));
        this.addView(bindableTextView);
        addListener(bindableTextView);
        bindableTextViewRHS = str;

        updateEntireString();
    }

    @Override
    public BindableTextView addTextViewUntracked(String str){
        BindableTextView bindableTextView = new BindableTextView(getContext());
        bindableTextView.setText(str);
        bindableTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP,getResources().getDimensionPixelSize(R.dimen.preview_text_size));
        this.addView(bindableTextView,getChildCount()-1);
        addListener(bindableTextView);
        updateEntireString();
        return bindableTextView;
    }

    @Override
    protected void updateEntireString() {
        String string = "";
        for (BindableTextView textView: textViews){
            string = string + (textView.getText());
        }
        string = string + bindableTextViewRHS.getValue();
        entireString.setValue(string);
    }

}
