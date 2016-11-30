package com.carbajal.danniel.ioapp.views.input;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.carbajal.danniel.ioapp.R;


public class InputView extends FrameLayout{

    private RelativeLayout relativeLayout;
    private ScrollView outerContainer;
    protected TextView titleTextView;
    protected LinearLayout titleContainer;
    protected LinearLayout innerContainer;
    protected LinearLayout bottomButtonContainer;

    public InputView(Context context) {
        super(context);
        init();
    }

    public InputView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public InputView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }


    private void init(){
        initRelativeLayout();
        initScrollView();
        initInnerContainer();

        initTitleBar();
        initBottomButtonContainer();

    }

    private void initRelativeLayout(){
        relativeLayout = new RelativeLayout(getContext());
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,FrameLayout.LayoutParams.MATCH_PARENT);

        int horizontalMargin = getResources().getDimensionPixelSize(R.dimen.activity_horizontal_margin);
        int verticalMargin = getResources().getDimensionPixelSize(R.dimen.activity_vertical_margin);

        layoutParams.setMargins(horizontalMargin,
                verticalMargin,
                horizontalMargin,
                verticalMargin);

        relativeLayout.setLayoutParams(layoutParams);
        this.addView(relativeLayout);
    }
    private void initScrollView(){
        outerContainer = new ScrollView(getContext());
        ScrollView.LayoutParams layoutParams = new ScrollView.LayoutParams(ScrollView.LayoutParams.MATCH_PARENT,ScrollView.LayoutParams.MATCH_PARENT);
        outerContainer.setLayoutParams(layoutParams);
        outerContainer.setFillViewport(true);

        relativeLayout.addView(outerContainer);
    }
    private  void initInnerContainer(){
        innerContainer = new LinearLayout(getContext());
        LinearLayout.LayoutParams innerContainerLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        innerContainer.setLayoutParams(innerContainerLayoutParams);
        innerContainer.setWeightSum(1);
        innerContainer.setOrientation(LinearLayout.VERTICAL);

        View spacer = new View(getContext());

        LinearLayout.LayoutParams spacerLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        spacerLayoutParams.width = 0;
        spacerLayoutParams.height = 0;
        spacerLayoutParams.weight = 1;
        spacer.setLayoutParams(spacerLayoutParams);

        this.innerContainer.addView(spacer);
        outerContainer.addView(innerContainer);

    }
    private void initTitleBar(){
        titleContainer = new LinearLayout(getContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            layoutParams.setMargins(0,0,0,(int)getResources().getDimension(R.dimen.activity_vertical_margin));
        titleContainer.setLayoutParams(layoutParams);
        titleContainer.setOrientation(LinearLayout.HORIZONTAL);
        initTitle();
        titleContainer.addView(titleTextView);
        this.innerContainer.addView(titleContainer,0);

    }
    private void initTitle(){
        titleTextView = new TextView(this.getContext());

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.gravity = Gravity.CENTER_HORIZONTAL|Gravity.LEFT;
        titleTextView.setLayoutParams(layoutParams);
        titleTextView.setGravity(Gravity.CENTER_VERTICAL);
        titleTextView.setTypeface(Typeface.DEFAULT_BOLD);
        titleTextView.setText("Title");
        titleTextView.setTextColor(getResources().getColor(R.color.colorPrimary));
        titleTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP,32);
    }

    private void initBottomButtonContainer(){
        bottomButtonContainer = new LinearLayout(getContext());
        bottomButtonContainer.setOrientation(LinearLayout.HORIZONTAL);

        View spacer = new View(getContext());

        LinearLayout.LayoutParams spacerLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        spacerLayoutParams.width = 0;
        spacerLayoutParams.height = 0;
        spacerLayoutParams.weight = 1;
        spacer.setLayoutParams(spacerLayoutParams);
        spacer.setBackgroundColor(Color.RED);
        bottomButtonContainer.addView(spacer);

        innerContainer.addView(bottomButtonContainer,innerContainer.getChildCount());
    }
    public void addBottomButton(TextView view){
        RelativeLayout.LayoutParams relativeParams = new RelativeLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        relativeParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        relativeParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        relativeLayout.addView(view,relativeParams);
    }
    public void removeBottomButton(View view){
        try {
            this.relativeLayout.removeView(view);
        } catch (Exception e){

        }
    }
    public void removeTitleButton(View view){
        try {
            this.titleContainer.removeView(view);
        } catch (Exception e){

        }
    }
    public void addContent(View view){
        innerContainer.addView(view,Math.max(0,innerContainer.getChildCount()-2));
    }
    public TextView getTitle(){
        return titleTextView;
    }
    public void setTitle(String titleText){
        getTitle().setText(titleText);
    }
}
