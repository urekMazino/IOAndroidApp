package com.carbajal.danniel.ioapp.views.output;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.ArrayList;

/**
 * Created by daniel on 11/13/16.
 */

public class CustomPagerAdapter extends PagerAdapter {


    private Context context;
    private ArrayList<ViewGroup> roots = new ArrayList<>();
    private ArrayList<String> titles = new ArrayList<>();


    public CustomPagerAdapter(Context context) {
        this.context = context;
    }



    public int addViewGroup(ViewGroup viewGroup,String title){
        return addViewGroup(viewGroup,title,roots.size());
    }
    public int addViewGroup(ViewGroup viewGroup,String title,int position){
        roots.add(position,viewGroup);
        titles.add(position,title);
        notifyDataSetChanged();
        return position;
    }
    public ViewGroup getViewGroup(int index){
        return roots.get(index);
    }

    public int removeView (ViewPager pager, View v)
    {
        return removeView (pager, roots.indexOf (v));
    }

    public int removeView (ViewPager pager, int position)
    {
        pager.setAdapter (null);
        titles.remove(position);
        roots.remove (position);
        pager.setAdapter (this);

        return position;
    }


    @Override
    public int getItemPosition (Object object)
    {
        int index = roots.indexOf (object);
        if (index == -1)
            return POSITION_NONE;
        else
            return index;
    }

    @Override
    public int getCount() {
        return roots.size();
    }

    public Object instantiateItem(ViewGroup container){
        return instantiateItem(container,roots.size()-1);

    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(roots.get(position));
        return roots.get(position);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((FrameLayout)object);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }

}
