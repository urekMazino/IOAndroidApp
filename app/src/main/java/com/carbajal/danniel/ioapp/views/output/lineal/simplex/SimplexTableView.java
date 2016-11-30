package com.carbajal.danniel.ioapp.views.output.lineal.simplex;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.carbajal.danniel.ioapp.R;
import com.carbajal.danniel.ioapp.support.StringManipulation;

/**
 * Created by daniel on 11/19/16.
 */

public class SimplexTableView extends HorizontalScrollView{

    private TableLayout tableLayout;
    private int variableAmount,restrictionAmount;

    public SimplexTableView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SimplexTableView(Context context,double[][] tablaSimplex,String[] rowNames) {
        super(context);
        init(tablaSimplex,rowNames);
    }
    private void init(double[][] tablaSimplex,String[] rowNames){
        restrictionAmount = rowNames.length-1;
        variableAmount =  tablaSimplex[0].length -1 - restrictionAmount;
        initTableLayout();

        tableLayout.addView(initHeader());
        for (int i=0;i<rowNames.length;i++){
            tableLayout.addView(createRow(tablaSimplex[i],rowNames[i]));
        }
    }
    private TableRow initHeader(){
        TableRow headersRow = new TableRow(getContext());
        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT,TableLayout.LayoutParams.WRAP_CONTENT);
        headersRow.setLayoutParams(layoutParams);
        headersRow.addView(createTextView(""));

        for (int i=0;i<variableAmount;i++){
            headersRow.addView(createTextViewTitle("x"+ StringManipulation.subscript(i+1)));
        }
        for (int i=0;i<restrictionAmount;i++){
            headersRow.addView(createTextViewTitle("h"+ StringManipulation.subscript(i+1)));
        }
        headersRow.addView(createTextViewTitle("Xb"));

        return headersRow;
    }
    private void initTableLayout(){
        tableLayout = new TableLayout(getContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        tableLayout.setLayoutParams(layoutParams);

        this.removeAllViews();
        this.addView(tableLayout);
    }
    private TextView createTextView(String str){
        TextView textView = new TextView(getContext());
        textView.setText(str);
        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(1,1,1,1);
        textView.setPadding(10,0,10,0);
        textView.setMinimumWidth(115);
        textView.setTextAlignment(TEXT_ALIGNMENT_TEXT_END);
        textView.setLayoutParams(layoutParams);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,26);
        textView.setBackgroundColor(getResources().getColor(R.color.backgroundColor));
        return textView;
    }
    private TextView createTextViewTitle(String str){
        TextView textView = createTextView(str);
        textView.setTypeface(Typeface.DEFAULT_BOLD);

        textView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        textView.setTextColor(getResources().getColor(R.color.backgroundColor));
        return textView;
    }
    private TableRow createRow(double[] rowValues,String rowName) {
        TableRow tableRow = new TableRow(getContext());
        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT,TableLayout.LayoutParams.WRAP_CONTENT);
        tableRow.setLayoutParams(layoutParams);
        tableRow.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        tableRow.addView(createTextViewTitle(rowName));
        for (int i = 0; i < rowValues.length; i++) {
            tableRow.addView(createTextView(StringManipulation.DoubleToString(rowValues[i])));
        }
        return tableRow;
    }
}
