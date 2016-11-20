package com.carbajal.danniel.ioapp.views.customViews.InputViews;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by daniel on 11/18/16.
 */

public class FontTextView extends TextView {
    public FontTextView(Context context) {
        super(context);
        init();
    }

    public FontTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FontTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    private void init(){
        Typeface typeFace=Typeface.createFromAsset(getContext().getAssets(),"fonts/MaterialIcons-Regular.ttf");
        setTypeface(typeFace);
    }
}
