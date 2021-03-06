package com.carbajal.danniel.ioapp.support;

import java.text.DecimalFormat;
import java.text.NumberFormat;


/**
 * Created by daniel on 11/6/16.
 */

public abstract class StringManipulation {

    public static String subscript(int num){
        String str = Integer.toString(num);
        str = str.replaceAll("0", "₀");
        str = str.replaceAll("1", "₁");
        str = str.replaceAll("2", "₂");
        str = str.replaceAll("3", "₃");
        str = str.replaceAll("4", "₄");
        str = str.replaceAll("5", "₅");
        str = str.replaceAll("6", "₆");
        str = str.replaceAll("7", "₇");
        str = str.replaceAll("8", "₈");
        str = str.replaceAll("9", "₉");
        return str;
    }

    public static String coeficientToVariable(double coeficient, int i){
        i = Math.max(i,1);
        String index = StringManipulation.subscript(i);
        String resultingString = DoubleToString(Math.abs(coeficient)) + "x" + index;

        if (Math.abs(coeficient) == 1) {
            resultingString = (coeficient > 0 ? ((i!=1)?" + ":""): " - " ) + "x" + index;
        } else if (coeficient >= 0) {
            resultingString = ((i!=1)?" + ":"") + resultingString;
        } else {
            resultingString = " - " + resultingString;
        }
        return resultingString;
        
    }
    public static String firstCoeficientToVariable(double coeficient,int i){
        i = Math.max(i,1);
        String index = StringManipulation.subscript(i);
        String resultingString = DoubleToString(Math.abs(coeficient)) + "x" + index;

        if (Math.abs(coeficient) == 1) {
            resultingString = (coeficient > 0 ? "": " - " ) + "x" + index;
        } else if (coeficient >= 0) {
            resultingString = ""+ resultingString;
        } else {
            resultingString = " - " + resultingString;
        }
        return resultingString;
    }
    public static  String DoubleToString(double value){
        NumberFormat nf = new DecimalFormat("##.###");
        return nf.format(value);
    }
    
}
