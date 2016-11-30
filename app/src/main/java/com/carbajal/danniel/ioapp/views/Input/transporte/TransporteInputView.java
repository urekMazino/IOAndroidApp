package com.carbajal.danniel.ioapp.views.input.transporte;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.carbajal.danniel.ioapp.R;

/**
 * Created by daniel on 11/20/16.
 */

public class TransporteInputView extends FrameLayout{

    private LinearLayout outerContainer;
    private LinearLayout innerContainer;
    private TextView title;
    private LinearLayout bottomLL;
    protected LinearLayout titleContainer;

    public TransporteInputView(Context context) {
        super(context);
        init();
    }

    public TransporteInputView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TransporteInputView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    private void init(){
        initOuterContainer();
        initTitleBar();
        initInnerContainer();

        initBottomLL();
    }
    private void initInnerContainer(){
        innerContainer =new LinearLayout(getContext());
        innerContainer.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        layoutParams.weight = 1;

        innerContainer.setLayoutParams(layoutParams);
        outerContainer.addView(innerContainer,2);
    }
    private void initOuterContainer(){
        outerContainer = new LinearLayout(getContext());
        outerContainer.setOrientation(LinearLayout.VERTICAL);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,FrameLayout.LayoutParams.MATCH_PARENT);

        int horizontalMargin = getResources().getDimensionPixelSize(R.dimen.activity_horizontal_margin);
        int verticalMargin = getResources().getDimensionPixelSize(R.dimen.activity_vertical_margin);

        layoutParams.setMargins(horizontalMargin,
                verticalMargin,
                horizontalMargin,
                verticalMargin);

        outerContainer.setLayoutParams(layoutParams);

        View spacer = new View(getContext());
        spacer.setBackgroundColor(Color.GREEN);
        LinearLayout.LayoutParams spacerLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        spacerLayoutParams.width = 0;
        spacerLayoutParams.height = 0;
        spacerLayoutParams.weight = 0;
        spacer.setLayoutParams(spacerLayoutParams);

        outerContainer.addView(spacer);

        this.addView(outerContainer);
    }
    private void initTitleBar(){
        titleContainer = new LinearLayout(getContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        layoutParams.setMargins(0,0,0,(int)getResources().getDimension(R.dimen.activity_vertical_margin));
        titleContainer.setLayoutParams(layoutParams);
        titleContainer.setOrientation(LinearLayout.HORIZONTAL);
        initTitle();
        titleContainer.addView(title);
        outerContainer.addView(titleContainer,0);

    }
    private void initTitle(){
        title = new TextView(this.getContext());
        title.setTypeface(Typeface.DEFAULT_BOLD);
        title.setText("Title");
        title.setTextColor(getResources().getColor(R.color.colorPrimary));
        title.setTextSize(TypedValue.COMPLEX_UNIT_SP,32);
    }
    private void initBottomLL(){
        bottomLL = new LinearLayout(getContext());
        bottomLL.setOrientation(LinearLayout.HORIZONTAL);

        View spacer = new View(getContext());

        LinearLayout.LayoutParams spacerLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        spacerLayoutParams.width = 0;
        spacerLayoutParams.height = 0;
        spacerLayoutParams.weight = 1;
        spacer.setLayoutParams(spacerLayoutParams);
        spacer.setBackgroundColor(Color.RED);
        bottomLL.addView(spacer);

        outerContainer.addView(bottomLL);
    }
    public void addBottomLL(View view){
        bottomLL.addView(view);
    }
    public void addLeftBommonLL(View view){

        bottomLL.addView(view,0);
    }
    public void removeBottomLL(View view){
        bottomLL.removeView(view);
    }
    public  void clearContent(){
        innerContainer.removeAllViews();
    }
    public void addContent(View view){
        innerContainer.addView(view);
    }
    public void setTitle(String str){
        title.setText(str);
    }
}
