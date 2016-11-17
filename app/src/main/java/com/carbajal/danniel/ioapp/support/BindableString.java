package com.carbajal.danniel.ioapp.support;

import java.util.ArrayList;

/**
 * Created by daniel on 11/13/16.
 */

public class BindableString {

    private ArrayList<bindableString> bindings = new ArrayList<>();

    private String value;

    public void bind(bindableString newBind){
        bindings.add(newBind);
    }

    private void notifyBindings(String newValue){
        for (bindableString x: bindings){
            x.changed(newValue);
        }
    }

    public interface bindableString{
        void changed(String newValue);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
        notifyBindings(this.value);
    }

}
