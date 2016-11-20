package com.carbajal.danniel.ioapp.views.input;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.carbajal.danniel.ioapp.R;
import com.carbajal.danniel.ioapp.activities.ResultActivity;
import com.carbajal.danniel.ioapp.models.programacionlineal.ModeloOptimizacionLinealImp;
import com.carbajal.danniel.ioapp.views.customViews.FontButtonBuilder;
import com.carbajal.danniel.ioapp.views.input.funcionObjetivo.ObjectiveFunctionInputView;
import com.carbajal.danniel.ioapp.views.input.modelPreview.ModelPreviewView;
import com.carbajal.danniel.ioapp.views.input.restrictions.RestrictionManager;
import com.carbajal.danniel.ioapp.views.input.restrictions.RestrictionsInputView;
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

    ObjectiveFunctionInputView objectiveFunctionInputView;
    private RestrictionManager restrictionManager = new RestrictionManager();

    private TextView addRestrictionButton;
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
    private void initPagerTitleStrip(){
        ViewPager.LayoutParams layoutParams = new ViewPager.LayoutParams();
        layoutParams.height = ViewPager.LayoutParams.WRAP_CONTENT;
        layoutParams.width = ViewPager.LayoutParams.MATCH_PARENT;
        layoutParams.gravity = Gravity.TOP;

        PagerTitleStrip pagerTitleStrip = new PagerTitleStrip(getContext());
        pagerTitleStrip.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        pagerTitleStrip.setTextColor(Color.WHITE);
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
                        if (isPageChanged && closingRestriction) {
                            finishClosingRestriction();
                            isPageChanged = false;
                            closingRestriction=false;
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

        initAddButton();
        initSendButton();
        objectiveFunctionInputView = new ObjectiveFunctionInputView(getContext());
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
        Intent newIntent = new Intent(host,ResultActivity.class);
        newIntent.putExtra("funcion_objetivo",modeloOptimizacionLinealImp.getFuncionObjetivo());
        newIntent.putParcelableArrayListExtra("restricciones",modeloOptimizacionLinealImp.getRestricciones());
        host.startActivity(newIntent);
    }
    private void addRestriction(){
            restrictionManager.add(new RestrictionsInputView(getContext()
                    , restrictionManager.getCount() + 1
                    , objectiveFunctionInputView.getVariableCount()));
    }

    private void initAddButton(){
        addRestrictionButton = FontButtonBuilder.BuildCircularButton(getContext(),"add",40);
        addRestrictionButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                addRestriction();
            }
        });

    }

    private void closeRestrictionPager(RestrictionsInputView restrictionInputView) throws Exception {
        if (restrictionManager.getCount()<=1){
            throw new Exception("No se puede tener menos de 1 restriccion");
        }
        removeRestrictionButton();
        restrictionToClose = restrictionInputView;
        closingRestriction = true;
        int currentPosition = restrictionManager.getIndex(restrictionInputView);
        restrictionManager.remove(restrictionInputView);
        this.setCurrentItem(currentPosition,true);
        addRestrictionButtonToLast();
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
                .addBottomButton(addRestrictionButton);
    }
    private void removeRestrictionButton(){
        for (RestrictionsInputView x: restrictionManager.getRestrictionsInputViews()){
            x.removeBottomButton(addRestrictionButton);
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
        this.setCurrentItem(restrictionManager.getCount(),true);
        setPagingEnabled(false);
    }

    @Override
    public void requestAdd() {
        addRestriction();
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
}
