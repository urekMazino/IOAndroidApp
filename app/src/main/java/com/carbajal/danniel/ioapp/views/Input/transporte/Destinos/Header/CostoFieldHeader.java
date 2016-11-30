package com.carbajal.danniel.ioapp.views.input.transporte.Destinos.Header;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.carbajal.danniel.ioapp.R;
import com.carbajal.danniel.ioapp.views.customViews.FontButtonBuilder;
import com.carbajal.danniel.ioapp.views.input.transporte.Destinos.CostoField;

/**
 * Created by daniel on 11/20/16.
 */

public class CostoFieldHeader extends CostoField {

    private TextView header;
    private TextView closeButton;
    private Events listener;

    public CostoFieldHeader(Context context, Events listener, int index) {
        super(context,index);
        this.listener = listener;
    }
    public CostoFieldHeader(Context context,int index){
        super(context,index);
    }

    public CostoFieldHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CostoFieldHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void init() {
        super.init();
        initHeader();
        initCloseButton();
    }
    private void initHeader(){
        header =  new TextView(getContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.rightMargin = 30;
        header.setLayoutParams(layoutParams);
        header.setTextSize(TypedValue.COMPLEX_UNIT_SP,24);
        header.setText("Origen "+index);
        header.setTypeface(Typeface.DEFAULT_BOLD);
        header.setTextColor(getResources().getColor(R.color.colorPrimary));

        addView(header,0);
    }

    protected void setHeader(String str){
        header.setText(str);
    }

    protected void initCloseButton(){
        closeButton = FontButtonBuilder.BuildCircularButton(getContext(),getResources().getString(R.string.clear_icon),24);
        closeButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
            notifyListener();
            }
        });
        addView(closeButton,getChildCount());
    }

    public void addAddButton(View view){
        addView(view);
    }
    public void removeButton(View view){
        try{
            removeView(view);
        } catch (Exception e){
            Log.v("not removed","");
        }
    }
    private void notifyListener(){
        if (listener!=null){
            listener.closeButtonClicked(this);
        }
    }

    @Override
    protected void updatedIndex() {
        super.updatedIndex();
        header.setText("Origen "+index+":");
    }

    public interface Events{
        void closeButtonClicked(CostoFieldHeader costoFieldHeader);
    }
}
