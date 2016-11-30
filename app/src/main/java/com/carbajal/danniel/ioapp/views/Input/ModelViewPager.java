package com.carbajal.danniel.ioapp.views.input;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.carbajal.danniel.ioapp.R;
import com.carbajal.danniel.ioapp.activities.OutputActivities.ResultActivity;
import com.carbajal.danniel.ioapp.activities.OutputActivities.ResultGraficoActivity;
import com.carbajal.danniel.ioapp.models.programacionlineal.ModeloOptimizacionLinealImp;
import com.carbajal.danniel.ioapp.views.customViews.FontButtonBuilder;
import com.carbajal.danniel.ioapp.views.input.PL.funcionObjetivo.ObjectiveFunctionInputView;
import com.carbajal.danniel.ioapp.views.input.PL.modelPreview.ModelPreviewView;
import com.carbajal.danniel.ioapp.views.input.PL.restrictions.RestrictionManager;
import com.carbajal.danniel.ioapp.views.input.PL.restrictions.RestrictionsInputView;
import com.carbajal.danniel.ioapp.views.output.CustomPagerAdapter;

/**
 * Created by daniel on 11/16/16.
 */

public class ModelViewPager extends ViewPager implements RestrictionManager.RestrictionListener{

    CustomPagerAdapter customPagerAdapter;


    private boolean enabled = true;
    private boolean isPageChanged=false;
    private boolean closingRestriction = false;
    private RestrictionsInputView restrictionToClose;
    private int maxVariables = Integer.MAX_VALUE;
    ObjectiveFunctionInputView objectiveFunctionInputView;
    private RestrictionManager restrictionManager = new RestrictionManager();

    private RestrictionsInputView toAdd;
    private TextView nextButton;
    private TextView addResButton;
    private TextView sendButton;

    public ModelViewPager(Context context) {
        super(context);
        init();
    }

    public ModelViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    private void init(){
        initPager();
        initPagerTitleStrip();
        initAdapter();
        initLister();
        initViews();
    }
    private void initPager(){

    }

    public void setMaxVariables(int maxVariables) {
        this.maxVariables = maxVariables;
        objectiveFunctionInputView.setMaxVariables(maxVariables);
    }

    private void initPagerTitleStrip(){
        ViewPager.LayoutParams layoutParams = new ViewPager.LayoutParams();
        layoutParams.height = ViewPager.LayoutParams.WRAP_CONTENT;
        layoutParams.width = ViewPager.LayoutParams.MATCH_PARENT;
        layoutParams.gravity = Gravity.TOP;

        PagerTitleStrip pagerTitleStrip = new PagerTitleStrip(getContext());
        pagerTitleStrip.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        pagerTitleStrip.setTextColor(getResources().getColor(R.color.backgroundColor));
        float scale = getResources().getDisplayMetrics().density;
        int dpAsPixels = (int) (4*scale + 0.5f);
        pagerTitleStrip.setPadding(0,dpAsPixels,0,dpAsPixels);
        this.addView(pagerTitleStrip,layoutParams);
    }
    private void initAdapter(){
        customPagerAdapter = new CustomPagerAdapter(getContext());
        this.setAdapter(customPagerAdapter);
    }
    private void initLister(){
        this.addOnPageChangeListener(new ViewPager.OnPageChangeListener(){

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                isPageChanged = true;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                switch (state) {
                    case ViewPager.SCROLL_STATE_IDLE:
                        if (isPageChanged) {
                            if (closingRestriction) {
                                finishClosingRestriction();
                                closingRestriction = false;
                                isPageChanged = false;
                            }
                            finishChanging();
                        }
                        setPagingEnabled(true);
                        break;
                    case ViewPager.SCROLL_STATE_DRAGGING:
                        break;
                    case ViewPager.SCROLL_STATE_SETTLING:
                        break;
                }
            }
        });
    }
    private void initViews() {
        restrictionManager.addListener(this);

        nextButton = initNextButton();
        TextView nextButtonFnObj = initNextButton();
        addResButton = initAddButton();
        initSendButton();
        objectiveFunctionInputView = new ObjectiveFunctionInputView(getContext());
        objectiveFunctionInputView.addBottomButton(nextButtonFnObj);
        customPagerAdapter.addViewGroup(objectiveFunctionInputView,"Funcion Objetivo");
        ModelPreviewView modelPreviewView= new ModelPreviewView(getContext(),objectiveFunctionInputView,restrictionManager);
        modelPreviewView.addBottomButton(sendButton);
        customPagerAdapter.addViewGroup(modelPreviewView,"Modelo");
        addRestriction();
        this.setCurrentItem(0);
        setPagingEnabled(true);

    }
    private void initSendButton(){
        sendButton = FontButtonBuilder.BuildButton(getContext(),getResources().getString(R.string.send_icon),40);
        sendButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                    buildModel();
            }
        });
    }
    private void buildModel(){

        ModeloOptimizacionLinealImp modeloOptimizacionLinealImp = new ModeloOptimizacionLinealImp();
        try{
            modeloOptimizacionLinealImp = new ModeloOptimizacionLinealImp(objectiveFunctionInputView.buildObjectiveFunction());
        } catch (Exception e){
            Log.v("Fallo","funcion objetivo");
        }

        try{
            for(RestrictionsInputView x: restrictionManager.getRestrictionsInputViews()) {
                modeloOptimizacionLinealImp.agregarRestriccion(x.buildRestriccion());
            }
        } catch (Exception e){
            Log.v("Fallo","restriccion");
        }

        Activity host = (Activity) getContext();
        Intent newIntent = new Intent(host, ((maxVariables==2)?ResultGraficoActivity.class:ResultActivity.class));
        newIntent.putExtra("funcion_objetivo",modeloOptimizacionLinealImp.getFuncionObjetivo());
        newIntent.putParcelableArrayListExtra("restricciones",modeloOptimizacionLinealImp.getRestricciones());
        host.startActivity(newIntent);
    }
    private void addRestriction(){
            restrictionManager.add(new RestrictionsInputView(getContext()
                    , restrictionManager.getCount() + 1
                    , objectiveFunctionInputView.getVariableCount()));
    }

    private TextView initNextButton(){
        TextView nextButton = FontButtonBuilder.BuildButton(getContext(),getResources().getString(R.string.send_icon),40);
        nextButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                smoothTransition(getCurrentItem()+1);
            }
        });
        return nextButton;
    }
    private TextView initAddButton(){
        TextView button = FontButtonBuilder.BuildCircularButton(getContext(),getResources().getString(R.string.add_icon),32,getResources().getColor(R.color.green));
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                addRestriction();
            }
        });
        return button;
    }
    private void closeRestrictionPager(RestrictionsInputView restrictionInputView) throws Exception {
        if (restrictionManager.getCount()<=1){
            throw new Exception("No se puede tener menos de 1 restriccion");
        }
        removeRestrictionButton();
        removeAddResButton();
        restrictionToClose = restrictionInputView;
        closingRestriction = true;
        int currentPosition = restrictionManager.getIndex(restrictionInputView);
        restrictionManager.remove(restrictionInputView);
        this.setCurrentItem(currentPosition,true);
        addRestrictionButtonToLast();
        addAddResButtonToLast();
    }
    private void finishClosingRestriction(){
        closingRestriction = false;
        int currentPosition = this.getCurrentItem();
        customPagerAdapter.removeView(this, restrictionToClose);
        this.setCurrentItem(currentPosition);
    }
    private void addRestrictionButtonToLast(){
        restrictionManager.getRestrictionsInputViews()
                .get(restrictionManager.getCount()-1)
                .addBottomButton(nextButton);
    }
    private void removeRestrictionButton(){
        for (RestrictionsInputView x: restrictionManager.getRestrictionsInputViews()){
            x.removeBottomButton(nextButton);
        }
    }
    private void addAddResButtonToLast(){
        restrictionManager.getRestrictionsInputViews()
                .get(restrictionManager.getCount()-1)
                .addTitleButton(addResButton);
    }
    private void removeAddResButton(){
        for (RestrictionsInputView x: restrictionManager.getRestrictionsInputViews()){
            x.removeTitleButton(addResButton);
        }
    }

    @Override
    public void closedRestriction(RestrictionsInputView restrictionsInputView,int index) {
        try{
            if(!closingRestriction) {
                closeRestrictionPager(restrictionsInputView);
            }
        }catch (Exception e){
            Log.v("no se removio",e.getMessage());
        }
    }

    @Override
    public void addedRestriction(RestrictionsInputView restrictionsInputView) {
        objectiveFunctionInputView.addListener(restrictionsInputView);
        customPagerAdapter.addViewGroup(restrictionsInputView,"Restriccion "+(customPagerAdapter.getCount()-1),customPagerAdapter.getCount()-1);
        removeRestrictionButton();
        addRestrictionButtonToLast();
        removeAddResButton();
        addAddResButtonToLast();
        toAdd = restrictionsInputView;
        smoothTransition(restrictionManager.getCount());
    }

    private void smoothTransition(int index){
        this.setCurrentItem(index,true);
        setPagingEnabled(false);
    }

    @Override
    public void requestAdd() {
        addRestriction();
    }

    @Override
    public void requestChangePage(int i) {
        nextPage();
    }

    public void nextPage(){
        smoothTransition(getCurrentItem()+1);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (this.enabled) {
            return super.onTouchEvent(event);
        }

        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (this.enabled) {
            return super.onInterceptTouchEvent(event);
        }

        return false;
    }

    public void setPagingEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void finishChanging() {
        if (toAdd!=null){
            toAdd.focusFirst();
            toAdd = null;
        }
    }
}
