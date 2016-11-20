package com.carbajal.danniel.ioapp.views.customViews;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.carbajal.danniel.ioapp.R;

/**
 * Created by daniel on 11/17/16.
 */

public abstract class FontButtonBuilder {

    public static TextView BuildButton(Context context, String id){
        return BuildButton(context,id,26);
    }
    public static TextView BuildButton(Context context, String id,int sizeInSP){
        TextView button = new TextView(context);
        button.setText(id);
        button.setTextSize(TypedValue.COMPLEX_UNIT_SP,sizeInSP);
        button.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));

        Typeface typeFace=Typeface.createFromAsset(context.getAssets(),"fonts/MaterialIcons-Regular.ttf");
        button.setTypeface(typeFace);
        return button;
    }
    public static CircularTextView BuildCircularButton(Context context, String id,int sizeInSP){
        final LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lparams.gravity=Gravity.CENTER;
        lparams.leftMargin = 16;
        CircularTextView button = new CircularTextView(context);
        button.setText(id);
        button.setTextSize(TypedValue.COMPLEX_UNIT_SP,sizeInSP);
        button.setTextColor(ContextCompat.getColor(context, R.color.white));
        button.setLayoutParams(lparams);
        button.setGravity(Gravity.CENTER);
        button.setSolidColor(ContextCompat.getColor(context, R.color.colorAccent));
        button.setPadding(5,5,5,5);
        Typeface typeFace=Typeface.createFromAsset(context.getAssets(),"fonts/MaterialIcons-Regular.ttf");
        button.setTypeface(typeFace);
        return button;
    }

}
