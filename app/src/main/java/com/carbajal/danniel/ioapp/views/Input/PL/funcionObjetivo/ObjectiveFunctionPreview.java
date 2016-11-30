package com.carbajal.danniel.ioapp.views.input.PL.funcionObjetivo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.carbajal.danniel.ioapp.R;
import com.carbajal.danniel.ioapp.views.customViews.InputViews.BindableTextView;
import com.carbajal.danniel.ioapp.views.input.Preview;

/**
 * Created by daniel on 11/13/16.
 */

public class ObjectiveFunctionPreview extends Preview {

    private ToggleButton maxMinToggle;

    public ObjectiveFunctionPreview(Context context) {
        super(context);
    }

    public ObjectiveFunctionPreview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ObjectiveFunctionPreview(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    @Override
    protected void init(){
        initToggle();

        addTextViewUntracked("Z = ");
    }

    @Override
    protected void updateEntireString() {
        String string = ((maxMinToggle.isChecked())?maxMinToggle.getTextOn():maxMinToggle.getTextOff())+" Z = ";
        for (BindableTextView textView: textViews){
            string = string + (textView.getText());
        }
        entireString.setValue(string);
    }

    private void initToggle(){
        maxMinToggle = new ToggleButton(this.getContext());
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        layoutParams.height = (int)(getResources().getDimensionPixelOffset(R.dimen.preview_text_size)*4.4);
        maxMinToggle.setLayoutParams(layoutParams);
        maxMinToggle.setTextOn(getResources().getString(R.string.max));
        maxMinToggle.setTextOff(getResources().getString(R.string.min));
        maxMinToggle.toggle();
        maxMinToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                updateEntireString();
            }
        });
        addView(maxMinToggle);
    }
    public boolean getMinMaxBoolean(){
        return maxMinToggle.isChecked();
    }
    @Override
    public void removeTextView(int index){
        index--;
        this.removeView(textViews.get(index));
        textViews.remove(index);
    }
}
