package com.carbajal.danniel.ioapp.views.input.PL.modelPreview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;

import com.carbajal.danniel.ioapp.R;
import com.carbajal.danniel.ioapp.support.BindableString;
import com.carbajal.danniel.ioapp.views.customViews.InputViews.BindableTextView;
import com.carbajal.danniel.ioapp.views.input.InputView;
import com.carbajal.danniel.ioapp.views.input.PL.funcionObjetivo.ObjectiveFunctionInputView;
import com.carbajal.danniel.ioapp.views.input.PL.restrictions.RestrictionManager;
import com.carbajal.danniel.ioapp.views.input.PL.restrictions.RestrictionsInputView;

import java.util.ArrayList;

/**
 * Created by daniel on 11/17/16.
 */

public class ModelPreviewView extends InputView implements RestrictionManager.RestrictionListener{

    private ObjectiveFunctionInputView objectiveFunctionInputView;
    private ArrayList<BindableTextView> restrictionsTextViews = new ArrayList<>();
    private RestrictionManager restrictionManager;

    public ModelPreviewView(Context context, ObjectiveFunctionInputView objectiveFunctionInputView,RestrictionManager restrictionManager) {
        super(context);
        init(objectiveFunctionInputView,restrictionManager);
    }

    public ModelPreviewView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ModelPreviewView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    private void init(ObjectiveFunctionInputView objectiveFunctionInputView,RestrictionManager restrictionManager){
        this.setTitle(getResources().getString(R.string.model));
        this.restrictionManager = restrictionManager;
        this.objectiveFunctionInputView = objectiveFunctionInputView;
        restrictionManager.addListener(this);
        addTextViewUntracked(objectiveFunctionInputView.getObjectiveFunctionPreview().getEntireString());
        addTextViewUntracked("\t\t\t\t"+getResources().getString(R.string.subject_to));
    }

    public void addRestriction(BindableString str){
        restrictionsTextViews.add(addTextViewUntracked(str));
    }

    public BindableTextView addTextViewUntracked(BindableString str){
        BindableTextView bindableTextView = new BindableTextView(getContext());
        bindableTextView.setText(str.getValue());
        bindableTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP,getResources().getDimensionPixelSize(R.dimen.preview_text_size));
        str.bind(bindableTextView);
        addContent(bindableTextView);

        return bindableTextView;
    }
    public BindableTextView addTextViewUntracked(String str){
        BindableTextView bindableTextView = new BindableTextView(getContext());
        bindableTextView.setText(str);
        bindableTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP,getResources().getDimensionPixelSize(R.dimen.preview_text_size));
        addContent(bindableTextView);

        return bindableTextView;
    }

    @Override
    public void closedRestriction(RestrictionsInputView restrictionsInputView,int index) {
        if (restrictionManager.getCount()>1) {
            BindableTextView toRemove = restrictionsTextViews.remove(index);
            this.innerContainer.removeView(toRemove);
        }
    }

    @Override
    public void addedRestriction(RestrictionsInputView restrictionsInputView) {
        addRestriction(restrictionsInputView.getFunctionPreview().getEntireString());
    }

    @Override
    public void requestAdd() {

    }

    @Override
    public void requestChangePage(int i) {

    }
}
