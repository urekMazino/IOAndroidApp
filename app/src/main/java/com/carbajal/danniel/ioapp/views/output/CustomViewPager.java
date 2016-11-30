package com.carbajal.danniel.ioapp.views.output;

import android.content.Context;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;

import com.carbajal.danniel.ioapp.R;

/**
 * Created by daniel on 11/16/16.
 */

public class CustomViewPager extends ViewPager{
    private boolean enabled = true;
    private boolean isPageChanged = true;
    private customViewPagerEvents listener;
    CustomPagerAdapter customPagerAdapter;


    public CustomViewPager(Context context) {
        super(context);
        init();
    }

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    private void init(){
        initListener();
        initPagerTitleStrip();
        initAdapter();
    }
    private void initListener(){
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
                            isPageChanged = false;
                            if (listener!=null) {
                                listener.finishChanging();
                            }
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

    public void setListener(customViewPagerEvents listener) {
        this.listener = listener;
    }

    private void initPagerTitleStrip(){
        LayoutParams layoutParams = new LayoutParams();
        layoutParams.height = LayoutParams.WRAP_CONTENT;
        layoutParams.width = LayoutParams.MATCH_PARENT;
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
    public CustomPagerAdapter getAdapter(){
        return customPagerAdapter;
    }
    public void addPage(ViewGroup page,String title){
        customPagerAdapter.addViewGroup(page,title);
    }
    public void addPage(ViewGroup page,String title,int index){
        customPagerAdapter.addViewGroup(page,title,index);
    }
    public void removePage(ViewGroup page) {
        customPagerAdapter.removeView(this, page);
    }
    public void smoothTransition(int index){
        this.setCurrentItem(index,true);
        setPagingEnabled(false);
    }
    public void setPagingEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public ViewGroup get(int index){
        return customPagerAdapter.getViewGroup(index);
    }

    public interface customViewPagerEvents{
        void finishChanging();
    }
}

