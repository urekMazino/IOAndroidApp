package com.carbajal.danniel.ioapp.support;

import android.text.InputFilter;
import android.text.Spanned;

/**
 * Created by daniel on 11/21/16.
 */

public class PositiveInputFilter implements InputFilter {

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        try {
            double input = Double.parseDouble(dest.toString() + source.toString());
            if (isInRange(input))
                return null;
        } catch (NumberFormatException nfe) { }
        return "";
    }

    private boolean isInRange(double c) {
        return c>=0;
    }
}
