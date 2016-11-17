package com.carbajal.danniel.ioapp.views.customViews;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.carbajal.danniel.ioapp.R;

import java.util.ArrayList;

/**
 * Created by daniel on 11/13/16.
 */

public class ModelInputAdapter extends PagerAdapter {


    private Context context;
    private ArrayList<ViewGroup> roots = new ArrayList<>();


    public ModelInputAdapter(Context context) {
        this.context = context;
    }

    public void addViewGroup(ViewGroup viewGroup){
        roots.add(viewGroup);
        notifyDataSetChanged();
    }
    public int removeView (ViewPager pager, View v)
    {
        return removeView (pager, roots.indexOf (v));
    }

    //-----------------------------------------------------------------------------
    // Removes the "view" at "position" from "views".
    // Retuns position of removed view.
    // The app should call this to remove pages; not used by ViewPager.
    public int removeView (ViewPager pager, int position)
    {
        // ViewPager doesn't have a delete method; the closest is to set the adapter
        // again.  When doing so, it deletes all its views.  Then we can delete the view
        // from from the adapter and finally set the adapter to the pager again.  Note
        // that we set the adapter to null before removing the view from "views" - that's
        // because while ViewPager deletes all its views, it will call destroyItem which
        // will in turn cause a null pointer ref.
        pager.setAdapter (null);
        roots.remove (position);
        pager.setAdapter (this);

        return position;
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
        String PageTitle;
        PageTitle = (position == 0)? context.getResources().getString(R.string.objective_function_contraction)
                :context.getResources().getString(R.string.restriction_contraction)+" "+position;
        return PageTitle;
    }

}
