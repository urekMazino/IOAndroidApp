package com.carbajal.danniel.ioapp.views.customViews;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
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
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.gravity = Gravity.CENTER_HORIZONTAL|Gravity.LEFT;
        button.setLayoutParams(layoutParams);
        button.setGravity(Gravity.CENTER_VERTICAL);
        button.setText(id);
        button.setTextSize(TypedValue.COMPLEX_UNIT_SP,sizeInSP);
        button.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));

        Typeface typeFace=Typeface.createFromAsset(context.getAssets(),"fonts/MaterialIcons-Regular.ttf");
        button.setTypeface(typeFace);
        return button;
    }
    public static TextView BuildButtonSolidHeight(Context context, String id,int sizeInSP){
        TextView button = new TextView(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER_HORIZONTAL|Gravity.LEFT;
        button.setLayoutParams(layoutParams);
        button.setGravity(Gravity.CENTER_VERTICAL);
        button.setText(id);
        button.setTextSize(TypedValue.COMPLEX_UNIT_SP,sizeInSP);
        button.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));

        Typeface typeFace=Typeface.createFromAsset(context.getAssets(),"fonts/MaterialIcons-Regular.ttf");
        button.setTypeface(typeFace);
        return button;
    }
    public static CircularTextView BuildCircularButton(Context context, String id,int sizeInSP){
        return BuildCircularButton(context,id,sizeInSP,ContextCompat.getColor(context,R.color.colorAccent));
    }
    public static CircularTextView BuildCircularButton(Context context, String id,int sizeInSP,int color){
        final LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lparams.gravity=Gravity.CENTER;
        lparams.leftMargin = 16;
        CircularTextView button = new CircularTextView(context);
        button.setText(id);
        button.setTextSize(TypedValue.COMPLEX_UNIT_SP,sizeInSP);
        button.setTextColor(ContextCompat.getColor(context, R.color.backgroundColor));
        button.setLayoutParams(lparams);
        button.setGravity(Gravity.CENTER);
        button.setSolidColor(color);
        button.setPadding(5,5,5,5);
        Typeface typeFace=Typeface.createFromAsset(context.getAssets(),"fonts/MaterialIcons-Regular.ttf");
        button.setTypeface(typeFace);
        return button;
    }

}
