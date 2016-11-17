package com.carbajal.danniel.ioapp.views.input;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.carbajal.danniel.ioapp.R;


public class InputView extends FrameLayout {

    private ScrollView outerContainer;
    private TextView titleTextView;
    protected LinearLayout titleContainer;
    protected LinearLayout innerContainer;
    private LinearLayout bottomButtonContainer;
    private Button captureButton;

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
        initScrollView();
        initInnerContainer();

        initTitleBar();
        initBottomButtonContainer();
        initCaptureButton();


    }
    private void initScrollView(){
        outerContainer = new ScrollView(getContext());

        ScrollView.LayoutParams layoutParams = new ScrollView.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);

        int horizontalMargin = getResources().getDimensionPixelSize(R.dimen.activity_horizontal_margin);
        int verticalMargin = getResources().getDimensionPixelSize(R.dimen.activity_vertical_margin);

        layoutParams.setMargins(horizontalMargin,
                verticalMargin,
                horizontalMargin,
                verticalMargin);

        outerContainer.setLayoutParams(layoutParams);

        outerContainer.setFillViewport(true);
        this.addView(outerContainer);
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
        titleContainer.setOrientation(LinearLayout.HORIZONTAL);
        initTitle();
        titleContainer.addView(titleTextView);
        this.innerContainer.addView(titleContainer,0);

    }
    private void initTitle(){
        titleTextView = new TextView(this.getContext());
        titleTextView.setText("Title");
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

        bottomButtonContainer.addView(spacer);

        innerContainer.addView(bottomButtonContainer,innerContainer.getChildCount());
    }
    private void initCaptureButton(){
        captureButton = new Button(this.getContext());
        captureButton.setText(getResources().getString(R.string.add_restriction));
        //bottomButtonContainer.addView(captureButton);
    }

    public void addContent(View view,int index){
        innerContainer.addView(view,Math.min(Math.max(0,index),innerContainer.getChildCount()-2));
    }
    public void addContent(View view){
        innerContainer.addView(view,Math.max(0,innerContainer.getChildCount()-2));
    }
    public Button getCaptureButton(){
        return captureButton;
    }
    public TextView getTitle(){
        return titleTextView;
    }
    public void setTitle(String titleText){
        getTitle().setText(titleText);
    }
}
