package com.carbajal.danniel.ioapp.views.output;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;

import com.carbajal.danniel.ioapp.R;

/**
 * Created by daniel on 11/16/16.
 */

public class ResultViewPager extends ViewPager{

    CustomPagerAdapter customPagerAdapter;

    public ResultViewPager(Context context) {
        super(context);
        init();
    }

    public ResultViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    private void init(){
        initPagerTitleStrip();
        initAdapter();
    }

    private void initPagerTitleStrip(){
        LayoutParams layoutParams = new LayoutParams();
        layoutParams.height = LayoutParams.WRAP_CONTENT;
        layoutParams.width = LayoutParams.MATCH_PARENT;
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
    public CustomPagerAdapter getAdapter(){
        return customPagerAdapter;
    }
    public void addPage(ViewGroup page,String title){
        customPagerAdapter.addViewGroup(page,title);
    }
    public void addPage(ViewGroup page,int index,String title){
        customPagerAdapter.addViewGroup(page,title,index);
    }
}
