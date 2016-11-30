package com.carbajal.danniel.ioapp.views.input.transporte.Destinos;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.carbajal.danniel.ioapp.R;
import com.carbajal.danniel.ioapp.views.customViews.InputViews.CustomNumField;

/**
 * Created by daniel on 11/20/16.
 */

public class DemandaField extends CostoField {

    private TextView header;
    private DemandaEvent listener;

    public DemandaField(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DemandaField(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public DemandaField(Context context, int index) {
        super(context, index);
    }

    @Override
    protected void init() {
        super.init();
        initHeader();
        setNumFieldListener(getNumField());
    }

    private void initHeader(){
        header =  new TextView(getContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.rightMargin = 30;
        header.setLayoutParams(layoutParams);
        header.setTextSize(TypedValue.COMPLEX_UNIT_SP,24);
        header.setText("Demanda:");
        header.setTypeface(Typeface.DEFAULT_BOLD);
        header.setTextColor(getResources().getColor(R.color.colorPrimary));

        addView(header,0);
    }
    private void setNumFieldListener(final CustomNumField numField){
        numField.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT) ){
                    listener.finishedDemanda();
                    return true;
                } else {
                    return false;
                }
            }
        });
    }

    public void setListener(DemandaEvent listener) {
        this.listener = listener;
    }

    @Override
    protected void updatedIndex() {
        getNumField().setHint("Demanda");
    }

    public interface DemandaEvent{
        void finishedDemanda();
    }
}
