package com.carbajal.danniel.ioapp.views.input.restrictions;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by daniel on 11/16/16.
 */

public class RestrictionManager implements RestrictionsInputView.ButtonListener {

    private ArrayList<RestrictionsInputView> restrictionsInputViews = new ArrayList<>();
    private ArrayList<RestrictionListener> listeners = new ArrayList<>();

    public void addListener(RestrictionListener newListener){
        listeners.add(newListener);
    }

    public void updateRestrictions(){
        int i=1;
        for(RestrictionsInputView x: restrictionsInputViews){
            x.changeTitleIndex(i++);
        }
    }
    public void add(RestrictionsInputView restrictionsInputView){
        restrictionsInputView.addListener(this);
        restrictionsInputViews.add(restrictionsInputView);
        notifyAddedRestriction(restrictionsInputView);
        updateRestrictions();
    }

    public void remove(RestrictionsInputView restrictionsInputView){
        restrictionsInputViews.remove(restrictionsInputView);
        updateRestrictions();
    }
    public void closeRestriction(RestrictionsInputView restrictionsInputView){
        notifyClosedRestriction(restrictionsInputView);
    }

    private void notifyAddedRestriction(RestrictionsInputView restrictionsInputView){
        for (RestrictionListener listener: listeners){
            listener.addedRestriction(restrictionsInputView);
        }
    }
    private void notifyRequestAdd(){
        for (RestrictionListener listener: listeners){
            Log.v("third level","confirmed");

            listener.requestAdd();
        }
    }

    private void notifyClosedRestriction(RestrictionsInputView restrictionsInputView){
        int index = getIndex(restrictionsInputView);
        for (RestrictionListener listener: listeners){
            listener.closedRestriction(restrictionsInputView,index);
        }
    }
    public int getCount(){
        return restrictionsInputViews.size();
    }
    public int getIndex(RestrictionsInputView restrictionsInputView){
        return restrictionsInputViews.indexOf(restrictionsInputView);
    }
    public ArrayList<RestrictionsInputView> getRestrictionsInputViews(){
        return restrictionsInputViews;
    }

    @Override
    public void closedButtonClicked(RestrictionsInputView restrictionsInputView) {
        closeRestriction(restrictionsInputView);
    }

    @Override
    public void finish() {
        notifyRequestAdd();
    }

    public interface RestrictionListener {
        void closedRestriction(RestrictionsInputView restrictionsInputView,int index);
        void addedRestriction(RestrictionsInputView restrictionsInputView);
        void requestAdd();
    }

}
